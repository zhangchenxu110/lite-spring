package org.litespring.service.v4;

import com.litespring.bean.core.io.Resource;
import com.litespring.bean.core.io.support.PackageResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * 通过PackageResourceLoader(放在core.io.support下)  将包路径下的所有class转变成FileResource
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class PackageResourceLoaderTest {
    @Test
    public void testGetResources() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.litespring.testBean.v4");
        Assert.assertEquals(3, resources.length);

    }
}
