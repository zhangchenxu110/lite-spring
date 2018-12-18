package org.litespring.service.v5;

import com.litespring.aop.MethodMatcher;
import com.litespring.aop.aspectj.AspectJExpressionPointcut;
import org.junit.Assert;
import org.junit.Test;
import org.litespring.testBean.v5.PetStoreService;

import java.lang.reflect.Method;

/**
 * PointCut: 持有一个表达式和MethodMatcher类
 * 可以matches一个方法，判断它是不是符合符合传入的表达式
 * PointCut和MethodMatcher有一个共同的实现类：AspectJExpressionPointcut
 *
 * @author 张晨旭
 * @DATE 2018/11/28
 */
public class PointcutTest {
    @Test
    public void testPointcut() throws Exception {

        String expression = "execution(* org.litespring.testBean.v5.*.placeOrder(..))";

        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        MethodMatcher mm = pc.getMethodMatcher();

        {
            Class<?> targetClass = PetStoreService.class;

            Method method1 = targetClass.getMethod("placeOrder");
            Assert.assertTrue(mm.matches(method1));

            Method method2 = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method2));
        }

        {
            Class<?> targetClass = org.litespring.testBean.v5.PetStoreService.class;

            Method method = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method));
        }

    }
}
