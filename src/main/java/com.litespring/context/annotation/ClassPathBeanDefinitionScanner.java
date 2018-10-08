package com.litespring.context.annotation;

import com.litespring.core.io.Resource;
import com.litespring.core.io.support.PackageResourceLoader;
import com.litespring.core.type.MetadataReader;
import com.litespring.core.type.classreading.SimpleMetadataReader;
import com.litespring.bean.BeanDefinition;
import com.litespring.bean.BeanDefinitionRegistry;
import com.litespring.bean.factory.support.BeanNameGenerator;
import com.litespring.bean.factory.BeanDefinitionStoreException;
import com.litespring.stereotype.Component;
import com.litespring.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 扫描注解核心类
 * 传入包路径，解析包下的类 并将解析后的BeanDefinition通过register注册进BeanFactory
 * 持有PackageResourceLoad进行包路径到Resource的转换，持有MetadataReader，解析类的元数据
 * 将解析结果存入这个类的BeanDefinition中，为了不污染以前的GenericBeanDefinition，新加一个AnnotationBeanDefinition接口，做一个继承GenericBeanDefinition和AnnotationBeanDefinition的实现类。
 * 最后将这个新的ScannedGenericBeanDefinition register到BeanFactory中。
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class ClassPathBeanDefinitionScanner {
    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    protected final Log logger = LogFactory.getLog(getClass());

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packagesToScan) {

        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan, ",");

        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();
        for (String basePackage : basePackages) {
            //找到当前包下的所有BeanDefinition
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                beanDefinitions.add(candidate);
                registry.registerBeanDefinition(candidate.getID(), candidate);

            }
        }
        return beanDefinitions;
    }


    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
        try {
            //通过PackageResourceLoader
            Resource[] resources = this.resourceLoader.getResources(basePackage);
            for (Resource resource : resources) {
                try {
                    //用MetadataReader读取每个类的metadata
                    MetadataReader metadataReader = new SimpleMetadataReader(resource);
                    if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                        String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
                        sbd.setId(beanName);
                        candidates.add(sbd);
                    }
                } catch (Throwable ex) {
                    throw new BeanDefinitionStoreException(
                            "Failed to read candidate component class: " + resource, ex);
                }

            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }
}
