package org.litespring.service.v1;

import com.litespring.bean.factory.BeanDefinition;
import com.litespring.bean.core.io.ClassPathResource;
import com.litespring.bean.factory.support.DefaultBeanFactory;
import com.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import com.litespring.exception.BeanDefinitionStoreException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 两个要点：1、验证获得的Bean的定义。(验证Factory)
 *          2、验证获得实例。(验证Bean)
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public class BeanFactoryTest {
    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);

    }
    @Test
    public void testGetBean() {

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertTrue(bd.isSingleton());

        assertFalse(bd.isPrototype());

        assertEquals(BeanDefinition.SCOPE_DEFAULT,bd.getScope());

    }
    @Test
    public void testInvalidXML(){

        try{
            reader.loadBeanDefinitions(new ClassPathResource("xxxx.xml"));
        }catch(BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }

}
