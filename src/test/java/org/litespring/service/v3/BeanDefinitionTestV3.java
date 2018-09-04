package org.litespring.service.v3;

import com.litespring.bean.factory.BeanDefinition;
import com.litespring.bean.ConstructorArgument;
import com.litespring.bean.core.io.ClassPathResource;
import com.litespring.bean.core.io.Resource;
import com.litespring.bean.factory.config.RuntimeBeanReference;
import com.litespring.bean.factory.config.TypedStringValue;
import com.litespring.bean.factory.support.DefaultBeanFactory;
import com.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 构造方法注入具体实现：将ConstructorArgument(参数列表)聚合到BeanDefinition中
 *
 * @author 张晨旭
 * @DATE 2018/8/28
 */
public class BeanDefinitionTestV3 {

    @Test
    public void testConstructorArgument() {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefinitions(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        ConstructorArgument args = bd.getConstructorArgument();
        //获取BeanDefinition的参数列表
        List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentValues();

        Assert.assertEquals(3, valueHolders.size());

        RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao", ref1.getBeanId());
        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao", ref2.getBeanId());

        TypedStringValue strValue = (TypedStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1", strValue.getValue());
    }

}

