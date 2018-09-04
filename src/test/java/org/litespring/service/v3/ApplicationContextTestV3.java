package org.litespring.service.v3;

import com.litespring.context.ApplicationContext;
import com.litespring.context.support.ClassPathXmlApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import org.litespring.testBean.v3.PetStoreService;

/**
 * 终极大boss
 * 验证applicationContext读取配置文件，getBean获取的实例中构造参数都已经注入了
 *
 * @author 张晨旭
 * @DATE 2018/8/28
 */
public class ApplicationContextTestV3 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
        Assert.assertEquals(1, petStore.getVersion());

    }

}

