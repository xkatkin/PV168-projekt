package cz.muni.fi.pv168.web;

import cz.muni.fi.agents.AgentManagerImpl;
import cz.muni.fi.contracts.ContractManagerImpl;
import cz.muni.fi.missions.MissionManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.Month;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;


/**
 * @author Slavomir Katkin
 */
@Configuration
@EnableTransactionManagement
public class SpringConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(DERBY)
                .addScript("classpath:data.sql")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public AgentManagerImpl agentManager() {
        return new AgentManagerImpl(dataSource());
    }

    @Bean
    public MissionManagerImpl missionManager() {
        return new MissionManagerImpl(dataSource());
    }

    @Bean
    public ContractManagerImpl contractManager() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 1);
        return new ContractManagerImpl(dataSource(), date);
    }




}
