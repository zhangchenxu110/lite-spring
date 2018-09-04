package com.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Resource 不同来源的资源抽象
 *
 * @author 张晨旭
 * @DATE 2018/8/9
 */
public interface Resource {
    public InputStream getInputStream() throws IOException;

    public String getDescription();
}
