package cz.muni.fi.pv168.swing;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;

/**
 * @author Slavomir Katkin
 */
public class Data {
    DataSource dataSource;

    Data() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(DERBY)
                .addScript("classpath:data.sql")
                .build();
    }
    public DataSource dataSource() {
        return dataSource;
    }
}
