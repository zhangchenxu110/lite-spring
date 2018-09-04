package com.litespring.bean.core.io.support;

import com.litespring.bean.core.io.FileSystemResource;
import com.litespring.bean.core.io.Resource;
import com.litespring.util.Assert;
import com.litespring.util.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 解析包路径下的所有class文件
 *
 * @author 张晨旭
 * @DATE 2018/9/4
 */
public class PackageResourceLoader {
    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);
    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * 传入包路径 先通过相对路径转成绝对路径文件 然后向每个文件下递归出所有class文件
     *
     * @param basePackage
     * @return
     * @throws IOException
     */
    public Resource[] getResources(String basePackage) throws IOException {
        Assert.notNull(basePackage, "basePackage  must not be null");
        //相对路径
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader cl = getClassLoader();
        URL url = cl.getResource(location);
        //文件的绝对路径
        Assert.notNull(url, "basePackage url must not be null");
        File rootDir = new File(url.getFile());
        //检索所有的class文件
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        //将file装入Resource[]中
        Resource[] result = new Resource[matchingFiles.size()];
        int i = 0;
        for (File file : matchingFiles) {
            result[i++] = new FileSystemResource(file);
        }
        return result;
    }

    /**
     * 检索rootDir下的所有文件
     *
     * @param rootDir
     * @return
     * @throws IOException
     */
    private Set<File> retrieveMatchingFiles(File rootDir) throws IOException {
        if (!rootDir.exists()) {
            // Silently skip non-existing directories.
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist");
            }
            return Collections.emptySet();
        }
        if (!rootDir.isDirectory()) {
            // Complain louder if it exists but is no directory.
            if (logger.isWarnEnabled()) {
                logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
            }
            return Collections.emptySet();
        }
        if (!rootDir.canRead()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
                        "] because the application is not allowed to read the directory");
            }
            return Collections.emptySet();
        }
        /*String fullPattern = StringUtils.replace(rootDir.getAbsolutePath(), File.separator, "/");
        if (!pattern.startsWith("/")) {
			fullPattern += "/";
		}
		fullPattern = fullPattern + StringUtils.replace(pattern, File.separator, "/");
		*/
        Set<File> result = new LinkedHashSet<File>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    /**
     * 递归dir下面的所有  当是文件时就加入result  是dir时继续递归
     *
     * @param dir
     * @param result
     * @throws IOException
     */
    private void doRetrieveMatchingFiles(File dir, Set<File> result) throws IOException {
        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
            }
            return;
        }
        for (File content : dirContents) {
            if (content.isDirectory()) {
                if (!content.canRead()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
                                "] because the application is not allowed to read the directory");
                    }
                } else {
                    doRetrieveMatchingFiles(content, result);
                }
            } else {
                result.add(content);
            }

        }
    }
}
