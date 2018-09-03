package org.litespring.service.v2;

import com.litespring.bean.context.ApplicationContext;
import com.litespring.bean.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import org.litespring.testBean.v2.dao.AccountDao;
import org.litespring.testBean.v2.dao.ItemDao;
import org.litespring.testBean.v2.PetStoreService;

import static org.junit.Assert.*;

/**
 * 终极boss
 *
 * @author 张晨旭
 * @DATE 2018/8/27
 */
public class ApplicationContextTestV2 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());

        assertTrue(petStore.getAccountDao() instanceof AccountDao);
        assertTrue(petStore.getItemDao() instanceof ItemDao);

        assertEquals("liuxin", petStore.getOwner());
        assertEquals(2, petStore.getVersion());

    }
}
