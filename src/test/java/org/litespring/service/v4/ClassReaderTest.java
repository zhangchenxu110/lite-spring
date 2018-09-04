package org.litespring.service.v4;

import com.litespring.core.io.Resource;
import com.litespring.core.io.support.PackageResourceLoader;
import com.litespring.core.type.classreading.ClassMetadataReadingVisitor;
import jdk.internal.org.objectweb.asm.ClassReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * 测试ASM读入类的metadata
 * visitor模式  reader
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class ClassReaderTest {
    @Test
    public void testGetClasMetaData() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.litespring.testBean.v4");
        //element  接收一个访问者 然后回调访问者的方法
        ClassReader reader = new ClassReader(resources[0].getInputStream());
        //读取类信息的访问者
        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        //ele接收一个visitor  进行解析后回调visitor
        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("org.litespring.testBean.v4.dao.AccountDao", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }
}
