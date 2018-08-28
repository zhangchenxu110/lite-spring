package org.litespring.service.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author 张晨旭
 * @DATE 2018/8/28
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTestV2.class, BeanDefinitionTestV2.class, BeanDefinitionValueResolverTest.class, TypeConverterTest.class})
public class V2SuiteTest {
}
