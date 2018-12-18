package org.litespring.service.v5;

/**
 * CglibProxyFactory：生成目标类的代理类
 *
 * （Cglib的Enhancer 将Advice增强到目标类上）
 * Advice持有PointCut,自己判断需不需要加到目录类方法上。
 * @author 张晨旭
 * @DATE 2018/11/29
 */
public class CglibAopProxyTest {
//    private static AspectJBeforeAdvice beforeAdvice = null;
//    private static AspectJAfterReturningAdvice afterAdvice = null;
//    private static AspectJExpressionPointcut pc = null;
//
//    private TransactionManager tx;
//
//    @Before
//    public  void setUp() throws Exception{
//
//
//        tx = new TransactionManager();
//        String expression = "execution(* org.litespring.service.v5.*.placeOrder(..))";
//        pc = new AspectJExpressionPointcut();
//        pc.setExpression(expression);
//
//        beforeAdvice = new AspectJBeforeAdvice(
//                TransactionManager.class.getMethod("start"),
//                pc,
//                tx);
//
//        afterAdvice = new AspectJAfterReturningAdvice(
//                TransactionManager.class.getMethod("commit"),
//                pc,
//                tx);
//
//    }
//
//    @Test
//    public void testGetProxy(){
//
//        AopConfig config = new AopConfigSupport();
//
//        config.addAdvice(beforeAdvice);
//        config.addAdvice(afterAdvice);
//        config.setTargetObject(new PetStoreService());
//
//
//        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);
//
//        PetStoreService proxy = (PetStoreService)proxyFactory.getProxy();
//
//        proxy.placeOrder();
//
//
//        List<String> msgs = MessageTracker.getMsgs();
//        Assert.assertEquals(3, msgs.size());
//        Assert.assertEquals("start tx", msgs.get(0));
//        Assert.assertEquals("place order", msgs.get(1));
//        Assert.assertEquals("commit tx", msgs.get(2));
//
//        proxy.toString();
//    }
}
