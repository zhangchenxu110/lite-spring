package com.litespring.aop.framework;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.litespring.aop.Advice;
import com.litespring.util.Assert;
import net.sf.cglib.core.CodeGenerationException;
import net.sf.cglib.proxy.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author 张晨旭
 * @DATE 2018/11/29
 */
@SuppressWarnings("serial")
public class CglibProxyFactory implements AopProxyFactory {

    // Constants for CGLIB callback array indices
    private static final int AOP_PROXY = 0;
    private static final int INVOKE_TARGET = 1;
    private static final int NO_OVERRIDE = 2;
    private static final int DISPATCH_TARGET = 3;
    private static final int DISPATCH_ADVISED = 4;
    private static final int INVOKE_EQUALS = 5;
    private static final int INVOKE_HASHCODE = 6;


    /**
     * Logger available to subclasses; static to optimize serialization
     */
    protected static final Log logger = LogFactory.getLog(CglibProxyFactory.class);


    protected final AopConfig config;

    private Object[] constructorArgs;

    private Class<?>[] constructorArgTypes;


    public CglibProxyFactory(AopConfig config) throws AopConfigException {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0 /*&& config.getTargetSource() == AdvisedSupport.EMPTY_TARGET_SOURCE*/) {
            throw new AopConfigException("No advisors and no TargetSource specified");
        }
        this.config = config;

    }


    /**
     * Set constructor arguments to use for creating the proxy.
     *
     * @param constructorArgs     the constructor argument values
     * @param constructorArgTypes the constructor argument types
     */
    /*public void setConstructorArguments(Object[] constructorArgs, Class<?>[] constructorArgTypes) {
        if (constructorArgs == null || constructorArgTypes == null) {
			throw new IllegalArgumentException("Both 'constructorArgs' and 'constructorArgTypes' need to be specified");
		}
		if (constructorArgs.length != constructorArgTypes.length) {
			throw new IllegalArgumentException("Number of 'constructorArgs' (" + constructorArgs.length +
					") must match number of 'constructorArgTypes' (" + constructorArgTypes.length + ")");
		}
		this.constructorArgs = constructorArgs;
		this.constructorArgTypes = constructorArgTypes;
	}*/
    public Object getProxy() {
        return getProxy(null);
    }

    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating CGLIB proxy: target source is " + this.config.getTargetClass());
        }

        try {
            Class<?> rootClass = this.config.getTargetClass();

            // Configure CGLIB Enhancer...
            Enhancer enhancer = new Enhancer();
            if (classLoader != null) {
                enhancer.setClassLoader(classLoader);
            }
            enhancer.setSuperclass(rootClass);

//            enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE); //"BySpringCGLIB"
            enhancer.setInterceptDuringConstruction(false);

            //增强类数组
            Callback[] callbacks = getCallbacks(rootClass);
            Class<?>[] types = new Class<?>[callbacks.length];
            for (int x = 0; x < types.length; x++) {
                types[x] = callbacks[x].getClass();
            }

            //简化CallBackFilter   让目标类的方法都让callbacks列表中的第一个处理
            enhancer.setCallbackFilter(new ProxyCallbackFilter(this.config));
            enhancer.setCallbackTypes(types);
            enhancer.setCallbacks(callbacks);

            // Generate the proxy class and create a proxy instance.
            Object proxy = enhancer.create();
            /*if (this.constructorArgs != null) {
                proxy = enhancer.create(this.constructorArgTypes, this.constructorArgs);
			}
			else {*/
            //proxy = enhancer.create();
            /*}*/

            return proxy;
        } catch (CodeGenerationException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of class [" +
                    this.config.getTargetClass() + "]: " +
                    "Common causes of this problem include using a final class or a non-visible class",
                    ex);
        } catch (IllegalArgumentException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of class [" +
                    this.config.getTargetClass() + "]: " +
                    "Common causes of this problem include using a final class or a non-visible class",
                    ex);
        } catch (Exception ex) {
            // TargetSource.getTarget() failed
            throw new AopConfigException("Unexpected AOP exception", ex);
        }
    }

    /**
     * Creates the CGLIB {@link Enhancer}. Subclasses may wish to override this to return a custom
     * {@link Enhancer} implementation.
     */
    /*protected Enhancer createEnhancer() {
        return new Enhancer();
	}*/
    private Callback[] getCallbacks(Class<?> rootClass) throws Exception {

        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.config);


        //Callback targetInterceptor = new StaticUnadvisedExposedInterceptor(this.advised.getTargetObject());

        //Callback targetDispatcher = new StaticDispatcher(this.advised.getTargetObject());

        Callback[] callbacks = new Callback[]{
                aopInterceptor,  // AOP_PROXY for normal advice
                /*targetInterceptor,  // INVOKE_TARGET invoke target without considering advice, if optimized
                new SerializableNoOp(),  // NO_OVERRIDE  no override for methods mapped to this
				targetDispatcher,        //DISPATCH_TARGET
				this.advisedDispatcher,  //DISPATCH_ADVISED
				new EqualsInterceptor(this.advised),
				new HashCodeInterceptor(this.advised)*/
        };

        return callbacks;
    }

    /**
     * Process a return value. Wraps a return of {@code this} if necessary to be the
     * {@code proxy} and also verifies that {@code null} is not returned as a primitive.
     */
	/*private static Object processReturnType(Object proxy, Object target, Method method, Object retVal) {

		if (retVal != null && retVal == target && !RawTargetAccess.class.isAssignableFrom(method.getDeclaringClass())) {

			retVal = proxy;
		}
		Class<?> returnType = method.getReturnType();
		if (retVal == null && returnType != Void.TYPE && returnType.isPrimitive()) {
			throw new AopInvocationException(
					"Null return value from advice does not match primitive return type for: " + method);
		}
		return retVal;
	}*/


    /**
     * 动态增强类
     * 对目标类通过Cglib进行增强（增强的结果就是intercept方法中的）
     * 这里只是说明我要怎么增强  将这个Interceptor(cglib的) 放在Callback中 传给Enhancer，Enhancer生成代理类。
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {

        private final AopConfig config;

        public DynamicAdvisedInterceptor(AopConfig advised) {
            this.config = advised;
        }

        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {


            Object target = this.config.getTargetObject();

            //获取和Method匹配的Advice。
            List<Advice> chain = this.config.getAdvices(method/*, targetClass*/);
            Object retVal;
            // Check whether we only have one InvokerInterceptor: that is,
            // no real advice, but just reflective invocation of the target.
            if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
                // We can skip creating a MethodInvocation: just invoke the target directly.
                // Note that the final invoker must be an InvokerInterceptor, so we know
                // it does nothing but a reflective operation on the target, and no hot
                // swapping or fancy proxying.
                retVal = methodProxy.invoke(target, args);
            } else {
                List<org.aopalliance.intercept.MethodInterceptor> interceptors =
                        new ArrayList<org.aopalliance.intercept.MethodInterceptor>();

                interceptors.addAll(chain);


                // We need to create a method invocation...
                //产生MethodInvocation  执行process方法  开启链式调用
                ReflectiveMethodInvocation rm = new ReflectiveMethodInvocation(target, method, args, interceptors);
                //开启链式调用  最终是目标方法的执行。
                retVal = rm.proceed();

            }
            //retVal = processReturnType(proxy, target, method, retVal);
            return retVal;

        }
    }


    /**
     * cglib的callbackFilter  指明用第几个callback对目标类增强
     */
    private static class ProxyCallbackFilter implements CallbackFilter {

        private final AopConfig config;


        public ProxyCallbackFilter(AopConfig advised) {
            this.config = advised;

        }


        public int accept(Method method) {
            // 注意，这里做了简化
            return AOP_PROXY;

        }

    }
}
