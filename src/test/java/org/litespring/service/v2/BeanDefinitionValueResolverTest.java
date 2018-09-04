package org.litespring.service.v2;

import com.litespring.core.io.ClassPathResource;
import com.litespring.bean.factory.config.RuntimeBeanReference;
import com.litespring.bean.factory.config.TypedStringValue;
import com.litespring.bean.factory.support.BeanDefinitionValueResolver;
import com.litespring.bean.factory.support.DefaultBeanFactory;
import com.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import org.junit.Test;
import org.junit.Assert;
import org.litespring.testBean.v2.dao.AccountDao;


/**
 * 持有BeanFactory，可以将RuntimeBeanDefinition转换成实际的bean。
 * BeanFactory中也有Resolver，在getBean时，将属性对象转换后注入。
 *
 * @author 张晨旭
 * @DATE 2018/8/27
 */
public class BeanDefinitionValueResolverTest {
    /**
     * 验证RuntimeBeanReference转换
     */
    @Test
    public void testResolveRuntimeBeanReference() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    /**
     * 验证TypeStringValue转换
     */
    @Test
    public void testResolveTypedStringValue() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);
        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);

    }
}
