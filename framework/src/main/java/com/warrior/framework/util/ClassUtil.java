package com.warrior.framework.util;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 *
 * @author panyi on 18-7-17.
 */
public final class ClassUtil {
    private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     *
     * @return ClassLoader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param className 类名
     * @param isInit    是否初始化
     * @return cls
     */
    public static Class<?> loadClass(String className, boolean isInit) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, isInit, getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return cls;
    }

    /**
     * 获取指定包名下的所有类
     *
     * @param packageName 包名
     * @return 类集合
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(Constant.PACKAGE_PATH_SEPARATOR, Constant.FILE_PATH_SEPARATOR));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (StringUtils.isNotBlank(protocol)) {
                        if (protocol.endsWith("file")) {
                            String packagePath = url.getPath().replaceAll("%20", " ");
                            addClass(classSet, packagePath, packageName);
                        } else if (protocol.endsWith("jar")) {
                            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                            if (jarURLConnection != null) {
                                JarFile jarFile = jarURLConnection.getJarFile();
                                if (jarFile != null) {
                                    Enumeration<JarEntry> entries = jarFile.entries();
                                    while (entries.hasMoreElements()) {
                                        JarEntry jarEntry = entries.nextElement();
                                        String jarEntryName = jarEntry.getName();
                                        if (jarEntryName.endsWith(Constant.SUF_CLASS)) {
                                            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf
                                                    (Constant.PACKAGE_PATH_SEPARATOR)).replaceAll(Constant.FILE_PATH_SEPARATOR, Constant.PACKAGE_PATH_SEPARATOR);
                                            doAddClass(classSet, className);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classSet;
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotBlank(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotBlank(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotBlank(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }
}
