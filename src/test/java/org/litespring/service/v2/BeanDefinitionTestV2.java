package org.litespring.service.v2;

import java.util.List;

import com.litespring.bean.BeanDefinition;
import com.litespring.bean.PropertyValue;
import com.litespring.core.io.ClassPathResource;
import com.litespring.bean.factory.config.RuntimeBeanReference;
import com.litespring.bean.factory.support.DefaultBeanFactory;
import com.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

/**
 * 为了实现Property注入  在BeanDefinition中加入PropertyValue列表。
 * 在xml解析时，将PropertyValue注册当BeanDefinition的propertyValues中。
 *
 * @author 张晨旭
 * @DATE 2018/8/27
 */
public class BeanDefinitionTestV2 {


    @Test
    public void testGetBeanDefinition() {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        List<PropertyValue> pvs = bd.getPropertyValues();

        Assert.assertTrue(pvs.size() == 4);
        {
            PropertyValue pv = this.getPropertyValue("accountDao", pvs);

            Assert.assertNotNull(pv);

            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

        {
            PropertyValue pv = this.getPropertyValue("itemDao", pvs);

            Assert.assertNotNull(pv);

            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> pvs) {
        for (PropertyValue pv : pvs) {
            if (pv.getName().equals(name)) {
                return pv;
            }
        }
        return null;
    }

}

