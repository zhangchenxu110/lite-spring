package com.litespring.context.support;

import com.litespring.core.io.ClassPathResource;
import com.litespring.core.io.Resource;

/**
 * 从ClassPath里加载资源
 *
 * @author 张晨旭
 * @DATE 2018/8/8
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);

    }

    @Override
    protected Resource getResourceByPath(String path) {

        return new ClassPathResource(path, this.getBeanClassLoader());
    }

}
