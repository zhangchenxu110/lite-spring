package org.litespring.service.v4;

import com.litespring.bean.core.annotation.AnnotationAttributes;
import com.litespring.bean.core.type.AnnotationMetadata;
import com.litespring.bean.factory.BeanDefinition;
import com.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import com.litespring.bean.factory.support.DefaultBeanFactory;
import com.litespring.context.annotation.ScannedGenericBeanDefinition;
import com.litespring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试ClassPathBeanDefinitionScanner这个核心类(context.annotation下)
 * 传入包路径，解析包下的类 并将解析后的BeanDefinition通过register注册进BeanFactory
 * 持有PackageResourceLoad进行包路径到Resource的转换，持有MetadataReader，解析类的元数据
 * 将解析结果存入这个类的BeanDefinition中，为了不污染以前的GenericBeanDefinition，新加一个AnnotationBeanDefinition接口，做一个继承GenericBeanDefinition和AnnotationBeanDefinition的实现类。
 * 最后将这个新的ScannedGenericBeanDefinition register到BeanFactory中。
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class ClassPathBeanDefinitionScannerTest {
    @Test
    public void testParseScanedBean() {

        DefaultBeanFactory factory = new DefaultBeanFactory();

        String basePackages = "org.litespring.testBean.v4";

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackages);


        String annotation = Component.class.getName();

        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();


            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore", attributes.get("value"));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }
}
