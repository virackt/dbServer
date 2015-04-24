package com.nineLin.game.teaseZombies.dbServer.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by vic on 15-4-20.
 */
public class DBUtil {

    private static DruidDataSource dataSource;

    public static DruidDataSource getDataSource() {
        if (dataSource == null) {
            final String configFileName = "dbConfig.properties";
            final Properties properties = new Properties();
            try {
                InputStream is = DBUtil.class.getResourceAsStream(configFileName);
                if (is == null) {
                    is = new FileInputStream(DBUtil.class.getResource("/").getPath() + File.separator + configFileName);
                }
                properties.load(is);
                dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }
}
