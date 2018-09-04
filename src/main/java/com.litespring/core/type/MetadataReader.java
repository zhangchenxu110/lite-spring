package com.litespring.core.type;

import com.litespring.core.io.Resource;

/**
 * 封装了ASM解析metadata的过程 对外提供获取类和注解的metadata的方法
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public interface MetadataReader {

    /**
     * Return the resource reference for the class file.
     */
    Resource getResource();

    /**
     * Read basic class metadata for the underlying class.
     */
    ClassMetadata getClassMetadata();

    /**
     * Read full annotation metadata for the underlying class,
     * including metadata for annotated methods.
     */
    AnnotationMetadata getAnnotationMetadata();

}
