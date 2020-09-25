package com.update.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;


@Component
public class PropertiesUtil {
    @Value("${linux.svn.path}")
    private String linuxSvnPath;

    public Boolean isWindows(){
        String OS = System.getProperty("os.name").toLowerCase().split(" ")[0];
        return OS.equals("windows");
    }


    public  String getSvnPath(String svn_path) {
        Resource resource = null;
        Properties props = null;
        String SvnPath = svn_path;
        try {
            resource = new ClassPathResource("META-INF/application.properties");
            props = PropertiesLoaderUtils.loadProperties(resource);
            if (!isWindows()) {
                linuxSvnPath = (String) props.get("linux.svn.path");
                SvnPath = svn_path.replaceAll(".*\\\\03-产品开发",linuxSvnPath+"/03-产品开发");
                SvnPath = SvnPath.replaceAll(".*\\\\04-测试运维",linuxSvnPath+"/04-测试运维");
                SvnPath = SvnPath.replace("\\","/");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SvnPath;

    }

    public  String getSvnBasePath() {
        Resource resource = null;
        Properties props = null;
        String SvnPath = null;
        try {
            resource = new ClassPathResource("META-INF/application.properties");
            props = PropertiesLoaderUtils.loadProperties(resource);
            if (!isWindows()) {
                linuxSvnPath = (String) props.get("linux.Base.path");
                SvnPath = linuxSvnPath;
            }else {
                SvnPath = (String) props.get("win.Base.path");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SvnPath;
    }

    public  String getSvnProPath() {
        Resource resource = null;
        Properties props = null;
        String SvnPath = null;
        try {
            resource = new ClassPathResource("META-INF/application.properties");
            props = PropertiesLoaderUtils.loadProperties(resource);
            if (!isWindows()) {
                linuxSvnPath = (String) props.get("linux.Pro.path");
                SvnPath = linuxSvnPath;
            }else {
                SvnPath = (String) props.get("win.Pro.path");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SvnPath;
    }

    public  String getSvnBranchPath() {
        Resource resource = null;
        Properties props = null;
        String SvnPath = null;
        try {
            resource = new ClassPathResource("META-INF/application.properties");
            props = PropertiesLoaderUtils.loadProperties(resource);
            if (!isWindows()) {
                linuxSvnPath = (String) props.get("linux.Branch.path");
                SvnPath = linuxSvnPath;
            }else {
                SvnPath = (String) props.get("win.Branch.path");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SvnPath;
    }

    public  String getDbResultPath() {
        Resource resource = null;
        Properties props = null;
        String SvnPath = null;
        try {
            resource = new ClassPathResource("META-INF/application.properties");
            props = PropertiesLoaderUtils.loadProperties(resource);
            if (!isWindows()) {
                linuxSvnPath = (String) props.get("linux.db.result.path");
                SvnPath = linuxSvnPath;
            }else {
                SvnPath = (String) props.get("win.db.result.path");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SvnPath;
    }

    public  String getLinuxConnectionPath() {
        Resource resource = null;
        Properties props = null;
        String SvnPath = null;
        try {
            resource = new ClassPathResource("META-INF/application.properties");
            props = PropertiesLoaderUtils.loadProperties(resource);
            if (!isWindows()) {
                linuxSvnPath = (String) props.get("linux.connection.result.path");
                SvnPath = linuxSvnPath;
            }else {
                SvnPath = (String) props.get("win.connection.result.path");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SvnPath;
    }


}
