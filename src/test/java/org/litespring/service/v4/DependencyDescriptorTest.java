package org.litespring.service.v4;

import com.litespring.bean.factory.config.DependencyDescriptor;
import com.litespring.bean.factory.support.DefaultBeanFactory;
import com.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import com.litespring.core.io.ClassPathResource;
import com.litespring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.litespring.testBean.v4.PetStoreService;
import org.litespring.testBean.v4.dao.AccountDao;

import java.lang.reflect.Field;

/**
 * 解析依赖描述符，resolveDependency方法  放在AutowireCapableBeanFactory接口中
 *
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public class DependencyDescriptorTest {

    @Test
    public void testResolveDependency() throws Exception {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinitions(resource);

        Field f = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);
    }

}
