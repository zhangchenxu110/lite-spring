package org.litespring.service.v5;

/**
 * reflectMethodInvocation实现MethodInvocation
 * 链式调用advice(实现MethodInterceptor)
 *
 * 将切面方法抽象成一个advice 持有PointCut 可以判断某方法是否可以被这个advice处理
 * reflectMethodInvocation持有advice list，process()传入自己  链式调用下一个
 *
 * @author 张晨旭
 * @DATE 2018/11/28
 */
public class ReflectiveMethodInvocationTest {

//    private AspectJBeforeAdvice beforeAdvice = null;
//    private AspectJAfterReturningAdvice afterAdvice = null;
//    private AspectJAfterThrowingAdvice afterThrowingAdvice = null;
//    private PetStoreService petStoreService = null;
//    private TransactionManager tx;
//
//
//    @Before
//    public void setUp() throws Exception {
//        petStoreService = new PetStoreService();
//        tx = new TransactionManager();
//
//        MessageTracker.clearMsgs();
//        //三个advice
//        beforeAdvice = new AspectJBeforeAdvice(
//                TransactionManager.class.getMethod("start"),//通知类的方法
//                null,//PointCut
//                tx);//通知的类
//
//        afterAdvice = new AspectJAfterReturningAdvice(
//                TransactionManager.class.getMethod("commit"),
//                null,
//                tx);
//
//        afterThrowingAdvice = new AspectJAfterThrowingAdvice(
//                TransactionManager.class.getMethod("rollback"),
//                null,
//                tx
//        );
//
//    }
//
//
//    @Test
//    public void testMethodInvocation() throws Throwable {
//
//
//        Method targetMethod = PetStoreService.class.getMethod("placeOrder");
//
//        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
//        interceptors.add(beforeAdvice);
//        interceptors.add(afterAdvice);
//
//        //ReflectiveMethodInvocation持有目标类 持有目标方法
//        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService, //目标类
//                                                                        targetMethod,   //目标方法
//                                                                        new Object[0],  //目标方法参数列表
//                                                                        interceptors);  //advice列表
//
//        //Invocation开始执行链式调用
//        mi.proceed();
//
//
//        List<String> msgs = MessageTracker.getMsgs();
//        Assert.assertEquals(3, msgs.size());
//        Assert.assertEquals("start tx", msgs.get(0));
//        Assert.assertEquals("place order", msgs.get(1));
//        Assert.assertEquals("commit tx", msgs.get(2));
//
//    }
//
//    @Test
//    public void testMethodInvocation2() throws Throwable {
//
//
//        Method targetMethod = PetStoreService.class.getMethod("placeOrder");
//
//        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
//        interceptors.add(afterAdvice);
//        interceptors.add(beforeAdvice);
//
//
//        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService, targetMethod, new Object[0], interceptors);
//
//        Object proceed = mi.proceed();
//
//        List<String> msgs = MessageTracker.getMsgs();
//        Assert.assertEquals(3, msgs.size());
//        Assert.assertEquals("start tx", msgs.get(0));
//        Assert.assertEquals("place order", msgs.get(1));
//        Assert.assertEquals("commit tx", msgs.get(2));
//
//    }
//
//    @Test
//    public void testAfterThrowing() throws Throwable {
//
//
//        Method targetMethod = PetStoreService.class.getMethod("placeOrderWithException");
//
//        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
//        interceptors.add(afterThrowingAdvice);
//        interceptors.add(beforeAdvice);
//
//
//        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService, targetMethod, new Object[0], interceptors);
//        try {
//            mi.proceed();
//
//        } catch (Throwable t) {
//            List<String> msgs = MessageTracker.getMsgs();
//            Assert.assertEquals(2, msgs.size());
//            Assert.assertEquals("start tx", msgs.get(0));
//            Assert.assertEquals("rollback tx", msgs.get(1));
//            return;
//        }
//
//
//        Assert.fail("No Exception thrown");
//
//
//    }
}
