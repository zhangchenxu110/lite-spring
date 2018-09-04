package com.litespring.bean.core.io;

import com.litespring.util.Assert;

import java.io.*;

/**
 * 文件资源resource
 *
 * @author 张晨旭
 * @DATE 2018/8/9
 */
public class FileSystemResource implements Resource {

    private final String path;
    private final File file;

    public FileSystemResource(File file) {
        this.path = file.getPath();
        this.file = file;
    }

    public FileSystemResource(String path) {
        Assert.notNull(path, "Path must not be null");
        this.file = new File(path);
        this.path = path;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public String getDescription() {
        return "file [" + this.file.getAbsolutePath() + "]";
    }

}

