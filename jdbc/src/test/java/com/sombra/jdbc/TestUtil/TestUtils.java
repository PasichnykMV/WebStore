package com.sombra.jdbc.TestUtil;

import com.sombra.jdbc.JdbcUserDaoTest;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/*
 * Created by Artyomov on 22.01.2016.
 */
public class TestUtils {
    public static void insertIntoDB(){
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ClassLoader classLoader = JdbcUserDaoTest.class.getClassLoader();

            URL url = classLoader.getResource("insert-script_v1.01_.sql");
            if (url != null) {
                String file = new String(Files.readAllBytes(Paths.get(url.toURI())));
                statement.execute(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Connection getConnection() {
        try {
            ClassLoader classLoader = JdbcUserDaoTest.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("dbConfig.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String login = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            String url = properties.getProperty("db.url");
            return DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void clear() throws Exception {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ClassLoader classLoader = JdbcUserDaoTest.class.getClassLoader();

            URL url = classLoader.getResource("delete-script_v1.01_.sql");
            if (url != null) {
                String file = new String(Files.readAllBytes(Paths.get(url.toURI())));
                statement.execute(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearAndInsertIntoDB() throws Exception {
        clear();
        insertIntoDB();
    }
}
