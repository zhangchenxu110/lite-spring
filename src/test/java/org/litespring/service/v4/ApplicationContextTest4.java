package org.litespring.service.v4;

import com.litespring.context.ApplicationContext;
import com.litespring.context.support.ClassPathXmlApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import org.litespring.testBean.v4.PetStoreService;

/**
 * @author 张晨旭
 * @DATE 2018/9/3
 */
public class ApplicationContextTest4 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v4.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

    }
}
