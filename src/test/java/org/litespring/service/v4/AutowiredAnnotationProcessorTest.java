package org.litespring.service.v4;

import com.litespring.bean.factory.annotation.AutowiredAnnotationProcessor;
import com.litespring.bean.factory.annotation.AutowiredFieldElement;
import com.litespring.bean.factory.annotation.InjectionElement;
import com.litespring.bean.factory.annotation.InjectionMetadata;
import com.litespring.bean.factory.support.DefaultBeanFactory;
import com.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import com.litespring.core.io.ClassPathResource;
import com.litespring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.litespring.testBean.v4.PetStoreService;
import org.litespring.testBean.v4.dao.AccountDao;
import org.litespring.testBean.v4.dao.ItemDao;

import java.lang.reflect.Field;
import java.util.List;

/**
 * build一个InjectionMetadata(找到这个bean的所有待注入依赖injectElement列表)
 * InjectionMetadata调用inject方法，对每一个依赖进行注入
 *
 * @author 张晨旭
 * @DATE 2018/10/8
 */
public class AutowiredAnnotationProcessorTest {
    @Test
    public void testGetInjectionMetadata() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinitions(resource);

        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(factory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

        //验证通过AutowiredAnnotationProcessor构造出的InjectionMetadata中已经持有了它所有需要注入的依赖
        assertFieldExists(elements, "accountDao");
        assertFieldExists(elements, "itemDao");

        PetStoreService petStore = new PetStoreService();

        injectionMetadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
    }

    private void assertFieldExists(List<InjectionElement> elements, String fieldName) {
        for (InjectionElement ele : elements) {
            AutowiredFieldElement fieldEle = (AutowiredFieldElement) ele;
            Field f = fieldEle.getField();
            if (f.getName().equals(fieldName)) {
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }

}
