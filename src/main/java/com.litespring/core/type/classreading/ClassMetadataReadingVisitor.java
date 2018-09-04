package com.litespring.core.type.classreading;

import com.litespring.core.type.ClassMetadata;
import com.litespring.util.ClassUtils;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * 被classReader  ele回调的访问者  回调时拿到类的各个metadata
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {
    private String className;

    private boolean isInterface;

    private boolean isAbstract;

    private boolean isFinal;

    private String superClassName;

    private String[] interfaces;

    public ClassMetadataReadingVisitor() {
        super(Opcodes.ASM5);
    }

    /**
     * ClassReader 读取完类的信息后的回调函数
     *
     * @param version    当前类的编译版本
     * @param access     当前类的范围(public private 接口 抽象类信息等)
     * @param name       类名
     * @param signature
     * @param supername
     * @param interfaces
     */
    public void visit(int version, int access, String name, String signature, String supername, String[] interfaces) {
        //类名 /变成.
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        if (supername != null) {
            this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }


    public String getClassName() {
        return this.className;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public boolean isAbstract() {
        return this.isAbstract;
    }

    public boolean isConcrete() {
        return !(this.isInterface || this.isAbstract);
    }

    public boolean isFinal() {
        return this.isFinal;
    }


    public boolean hasSuperClass() {
        return (this.superClassName != null);
    }

    public String getSuperClassName() {
        return this.superClassName;
    }

    public String[] getInterfaceNames() {
        return this.interfaces;
    }

}
