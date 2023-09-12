package com.tool.common.resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class AppConfig
{
    private static Logger         logger = LoggerFactory.getLogger(AppConfig.class);
    private static Properties     userProperties;
    private static ResourceBundle appProperties;
    private static ResourceBundle stringProperties;

    static
    {
        userProperties = loadConfig(System.getProperty("user.dir") + File.separator + "resource" + File.separator + "config.properties");
        appProperties = ResourceBundle.getBundle(AppConfig.class.getPackage().getName() + ".AppConfig");
        stringProperties = ResourceBundle.getBundle(AppConfig.class.getPackage().getName() + ".StringResource");
    }

    public static Properties loadConfig(String url)
    {
        Properties      properties = null;
        FileInputStream fileInput  = null;
        try
        {
            File userConfig = new File(url);
            if (userConfig.exists() && userConfig.isFile())
            {
                fileInput = new FileInputStream(userConfig);
                properties = new Properties();
                properties.load(fileInput);
            }
        }
        catch (Exception e)
        {
            logger.error("{} | Couldn't load bundleConfig: ", url);
        }
        finally
        {
            if (fileInput != null)
            {
                try
                {
                    fileInput.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public static ImageIcon getImageIcon(String filename)
    {
        return getImageIcon(filename, null);
    }

    public static ImageIcon getImageIcon(String filename, String description)
    {
        String path     = "images/" + filename;
        URL    imageURL = AppConfig.class.getResource(path);

        if (imageURL == null)
            return null;
        else
            return new ImageIcon(imageURL, StringUtils.defaultString(description));
    }

    public static ImageIcon getScaledImageIcon(String filename, int width, int height)
    {
        ImageIcon imageIcon = getImageIcon(filename);
        return new ImageIcon(imageIcon.getImage().getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH));
    }

    public static String getUserConfig(String key)
    {
        return userProperties != null && userProperties.containsKey(key) ? userProperties.getProperty(key) : "";
    }

    public static String getAppConfig(String key)
    {
        return appProperties != null && appProperties.containsKey(key) ? appProperties.getString(key) : "";
    }

    public static String getString(String key)
    {
        return stringProperties != null && stringProperties.containsKey(key) ? stringProperties.getString(key) : "";
    }
}
