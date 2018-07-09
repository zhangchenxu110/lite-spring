package service.v1.test;

import com.litespring.bean.BeanDefinition;
import com.litespring.bean.BeanFactory;
import com.litespring.bean.DefaultBeanFactory;
import com.litespring.testBean.PetStoreService;
import org.junit.Test;
import static org.junit.Assert.*;     //导入Assert类中的所有静态方法

/**
 * 两个要点：1、验证获得的Bean的定义。(验证Factory)
 *          2、验证获得实例。(验证Bean)
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public class BeanFactoryTest {

    @Test
    public void testGetBean() {

        //读取配置文件  类加载器工厂  维护一个Map 类的id和类的BeanDefinition
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");

        BeanDefinition bd = factory.getBeanDefinition("petStore");
        //验证类名
        assertEquals("com.litespring.testBean.PetStoreService",bd.getBeanClassName());

        PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore");

        assertNotNull(petStore1);
    }
}
