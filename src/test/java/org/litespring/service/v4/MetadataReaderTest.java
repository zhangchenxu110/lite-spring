package org.litespring.service.v4;

import com.litespring.bean.core.annotation.AnnotationAttributes;
import com.litespring.bean.core.io.Resource;
import com.litespring.bean.core.io.support.PackageResourceLoader;
import com.litespring.bean.core.type.AnnotationMetadata;
import com.litespring.bean.core.type.MetadataReader;
import com.litespring.bean.core.type.classreading.SimpleMetadataReader;
import com.litespring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * 测试MetadataReader
 * MetadataReader是将classMetadata和AnnotationMetadata进行封装,接收Resource
 * 对外提供了getClassMetadata和getAnnotationMetadata  将具体的ASM解析过程封装了
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class MetadataReaderTest {
    @Test
    public void testGetMetadata() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.litespring.testBean.v4");

        MetadataReader reader = new SimpleMetadataReader(resources[0]);
        //注意：不需要单独使用ClassMetadata
        //ClassMetadata clzMetadata = reader.getClassMetadata();
        AnnotationMetadata amd = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        Assert.assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("accountDao", attributes.get("value"));

        //注：下面对class metadata的测试并不充分
        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("org.litespring.testBean.v4.dao.AccountDao", amd.getClassName());

    }
}
