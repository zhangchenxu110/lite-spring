package org.litespring.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.service.v2.V2SuiteTest;
import org.litespring.service.v3.V3AllTests;

/**
 * suite全部测试
 *
 * @author 张晨旭
 * @DATE 2018/8/28
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({V2SuiteTest.class, V3AllTests.class})
public class SuiteTest {
}
