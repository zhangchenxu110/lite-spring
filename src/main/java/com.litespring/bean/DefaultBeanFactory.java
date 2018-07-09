package com.litespring.bean;

import com.litespring.exception.BeanCreationException;
import com.litespring.exception.BeanDefinitionStoreException;
import com.litespring.util.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.transform.sax.SAXResult;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public class DefaultBeanFactory implements BeanFactory {

    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE_ATTRIBUTE = "scope";

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);

    public DefaultBeanFactory(String path) {
        this.loadBeanDefinition(path);
    }

    //构造类加载方法
    private void loadBeanDefinition(String path) {
        try {
            //类加载器
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
            InputStream is = null;
            is = classLoader.getResourceAsStream(path);
            //dom4j读取xml文件
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            Iterator iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Element ele = (Element) iterator.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                if (ele.attribute(SCOPE_ATTRIBUTE) != null) {
                    bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                }
                this.beanDefinitionMap.put(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("bean definition exception", e);
        }
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public Object getBean(String beanId) {
        BeanDefinition bd = this.getBeanDefinition(beanId);
        if (bd == null) {
            return null;
        }
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        try {
            Class<?> clz = classLoader.loadClass(bd.getBeanClassName());
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("bean create exception", e);
        }
    }
}
