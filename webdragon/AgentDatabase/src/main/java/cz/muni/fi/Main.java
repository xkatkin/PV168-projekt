package cz.muni.fi;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Slavomir Katkin
 */
public class Main {

    public static void main(String[] args) throws SQLException, IOException{
        Properties config = new Properties();
        config.load(Main.class.getResourceAsStream("/config"));

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(config.getProperty("jdbc.driver"));
        ds.setUrl(config.getProperty("jdbc.url"));
        ds.setUsername(config.getProperty("jdbc.user"));
        ds.setPassword(config.getProperty("jdbc.password"));

        try (Connection c = ds.getConnection()) {
            DatabaseMetaData metaData = c.getMetaData();
            System.out.println(metaData.getDriverName() + " " + metaData.getDriverVersion());
            for (String line : Files.readAllLines(Paths.get("src", "main", "resources", "data.sql"))) {
                if (line.trim().isEmpty() || line.startsWith("--")) continue;
                if (line.endsWith(";")) line = line.substring(0, line.length() - 1);
                try (PreparedStatement st1 = c.prepareStatement(line)) {
                    st1.execute();
                }
            }
        }
    }
}
