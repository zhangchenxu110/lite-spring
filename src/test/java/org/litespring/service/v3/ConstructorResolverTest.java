package org.litespring.service.v3;

import com.litespring.bean.BeanDefinition;
import com.litespring.core.io.ClassPathResource;
import com.litespring.core.io.Resource;
import com.litespring.bean.factory.support.ConstructorResolver;
import com.litespring.bean.factory.support.DefaultBeanFactory;
import com.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;
import org.litespring.testBean.v3.PetStoreService;

/**
 * 通过ConstructorResolver  将BeanDefinition实例化，并且将constructorArg注入进去
 *
 * @author 张晨旭
 * @DATE 2018/8/28
 */
public class ConstructorResolverTest {

    @Test
    public void testAutowireConstructor() {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefinitions(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        ConstructorResolver resolver = new ConstructorResolver(factory);

        PetStoreService petStore = (PetStoreService) resolver.autowireConstructor(bd);

        // 验证参数version 正确地通过此构造函数做了初始化
        // PetStoreService(AccountDao accountDao, ItemDao itemDao,int version)
        Assert.assertEquals(1, petStore.getVersion());

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());


    }
}
