package com.litespring.context.support;

import com.litespring.bean.factory.annotation.AutowiredAnnotationProcessor;
import com.litespring.bean.factory.config.ConfigurableBeanFactory;
import com.litespring.context.ApplicationContext;
import com.litespring.core.io.Resource;
import com.litespring.bean.factory.support.DefaultBeanFactory;
import com.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import com.litespring.util.ClassUtils;

/**
 * 实现applicationContext 构造时持有Resource和DefaultBeanFactory和XmlReader
 * <p>
 * 在完成DI时补充：在构造时，设置DI注入的钩子函数(AutowiredAnnotationProcessor)
 * 在实际getBean的setter属性时，调用钩子函数进行DI
 *
 * @author 张晨旭
 * @DATE 2018/8/8
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    //持有核心类
    private DefaultBeanFactory factory = null;
    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
        factory.setBeanClassLoader(this.getBeanClassLoader());
        registerBeanPostProcessors(factory);
    }

    @Override
    public Object getBean(String beanID) {

        return factory.getBean(beanID);
    }

    protected abstract Resource getResourceByPath(String path);

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {
        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);
    }

}
