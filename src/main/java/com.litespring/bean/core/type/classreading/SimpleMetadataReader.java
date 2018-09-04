package com.litespring.bean.core.type.classreading;

import com.litespring.bean.core.io.Resource;
import com.litespring.bean.core.type.AnnotationMetadata;
import com.litespring.bean.core.type.ClassMetadata;
import com.litespring.bean.core.type.MetadataReader;
import jdk.internal.org.objectweb.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 封装ASM读取metadata的实现类。
 * 传入待解析的Resource，封装ASM解析。
 * 对外提供查询metadata的方法。
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class SimpleMetadataReader implements MetadataReader {

    private final Resource resource;

    private final ClassMetadata classMetadata;

    private final AnnotationMetadata annotationMetadata;


    public SimpleMetadataReader(Resource resource) throws IOException {
        InputStream is = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader;

        try {
            classReader = new ClassReader(is);
        } finally {
            is.close();
        }

        //ASM对类的meta进行解析
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);

        this.annotationMetadata = visitor;
        this.classMetadata = visitor;
        this.resource = resource;
    }


    public Resource getResource() {
        return this.resource;
    }

    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }

}