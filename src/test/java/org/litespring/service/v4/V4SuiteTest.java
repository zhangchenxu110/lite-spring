package org.litespring.service.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author 张晨旭
 * @DATE 2018/10/8
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTest4.class,
        ClassPathBeanDefinitionScannerTest.class,
        ClassReaderTest.class,
        DependencyDescriptorTest.class,
        MetadataReaderTest.class,
        PackageResourceLoaderTest.class,
        XmlBeanDefinitionReaderTest.class})
public class V4SuiteTest {
}

