package com.litespring.bean.factory.support;

import com.litespring.bean.BeanDefinition;
import com.litespring.bean.BeanDefinitionRegistry;
import com.litespring.bean.BeansException;
import com.litespring.bean.PropertyValue;
import com.litespring.bean.factory.BeanCreationException;
import com.litespring.bean.factory.BeanFactoryAware;
import com.litespring.bean.factory.NoSuchBeanDefinitionException;
import com.litespring.bean.factory.config.BeanPostProcessor;
import com.litespring.bean.factory.config.DependencyDescriptor;
import com.litespring.bean.factory.config.InstantiationAwareBeanPostProcessor;
import com.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1、构造方法中读取xml路径 将解析结果存在beanDefinitionMap中。
 * 2、getBeanDefinition方法 返回BeanDefinition。
 * 3、getBean方法 通过反射的方法式生成实例。
 *
 * @author 张晨旭
 * @DATE 2018/7/9
 */
public class DefaultBeanFactory extends AbstractBeanFactory
        implements BeanDefinitionRegistry {

    //所有的bd
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    private ClassLoader beanClassLoader;
    //
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();


    public DefaultBeanFactory() {
    }

    @Override
    public void registerBeanDefinition(String beanID, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanID, bd);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {

        return this.beanDefinitionMap.get(beanID);
    }

    /**
     * 传入类别  获取bean实例
     *
     * @param type
     * @return
     */
    public List<Object> getBeansByType(Class<?> type) {
        List<Object> result = new ArrayList<Object>();
        List<String> beanIDs = this.getBeanIDsByType(type);
        for (String beanID : beanIDs) {
            result.add(this.getBean(beanID));
        }
        return result;
    }

    private List<String> getBeanIDsByType(Class<?> type) {
        List<String> result = new ArrayList<String>();
        for (String beanName : this.beanDefinitionMap.keySet()) {
            if (type.isAssignableFrom(this.getType(beanName))) {
                result.add(beanName);
            }
        }
        return result;
    }

    /**
     * getBean 分两步
     * 1、实例化bean 2、注入property
     *
     * @param beanID
     * @return
     */
    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);
        if (bd == null) {
            return null;
        }

        if (bd.isSingleton()) {
            Object bean = this.getSingleton(beanID);
            if (bean == null) {
                bean = createBean(bd);
                this.registerSingleton(beanID, bean);
            }
            return bean;
        }
        return createBean(bd);
    }

    protected Object createBean(BeanDefinition bd) {
        //创建实例
        Object bean = instantiateBean(bd);
        //设置属性
        populateBean(bd, bean);

        //初始化bean  产生代理bean
        bean = initializeBean(bd, bean);

        return bean;
    }

    protected Object initializeBean(BeanDefinition bd, Object bean) {
        invokeAwareMethods(bean);
        //Todo，调用Bean的init方法，暂不实现
        //当非人造bean  就要产生代理bean
        if (!bd.isSynthetic()) {
            return applyBeanPostProcessorsAfterInitialization(bean, bd.getID());
        }
        return bean;
    }

    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException {
        Object result = existingBean;
        //调用所有钩子函数
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.afterInitialization(result, beanName);
            if (result == null) {
                return null;
            }
        }
        return result;
    }

    /**
     * 当这个bean是调用的方法的1 3个参数  MethodLocatingFactory和AspectInstanceFactory  设置BeanFactory
     *
     * @param bean
     */
    private void invokeAwareMethods(final Object bean) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }

    private Object instantiateBean(BeanDefinition bd) {
        //当有构造参数时  通过合适的构造参数实例化bean
        if (bd.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        } else {
            ClassLoader cl = this.getBeanClassLoader();
            String beanClassName = bd.getBeanClassName();
            try {
                Class<?> clz = cl.loadClass(beanClassName);
                return clz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
            }
        }
    }

    /**
     * 注入属性
     * 1、当property是RuntimeBeanReference时，要用resolver转换。
     * 2、当property是TypeStringValue时,用TypeConverter从String转换成需要的类型。
     *
     * @param bd
     * @param bean
     */
    protected void populateBean(BeanDefinition bd, Object bean) {

        //调用bean生命周期中处理属性的钩子函数  进行DI
        this.getBeanPostProcessors()
                .stream()
                .filter(processor -> processor instanceof InstantiationAwareBeanPostProcessor)
                .forEach(processor -> ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(bean, bd.getID()));

        List<PropertyValue> pvs = bd.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);

                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }


            }
        } catch (Exception ex) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object resolveDependency(DependencyDescriptor dependencyDescriptor) {
        Class<?> typeToMatch = dependencyDescriptor.getDependencyType();
        for (BeanDefinition bd : this.beanDefinitionMap.values()) {
            //确保BeanDefinition 有Class对象
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if (typeToMatch.isAssignableFrom(beanClass)) {
                return this.getBean(bd.getID());
            }
        }
        return null;
    }

    private void resolveBeanClass(BeanDefinition bd) {
        if (!bd.hasBeanClass()) {
            try {
                bd.resolveBeanClass(this.getBeanClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class:" + bd.getBeanClassName());
            }
        }
    }

    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        BeanDefinition bd = this.getBeanDefinition(name);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(name);
        }
        resolveBeanClass(bd);
        return bd.getBeanClass();
    }
}
