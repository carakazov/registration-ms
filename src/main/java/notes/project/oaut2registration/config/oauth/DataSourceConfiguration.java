package notes.project.oaut2registration.config.oauth;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataSourceConfiguration {
    @Bean
    @Profile("!it")
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("registration_service");
        dataSource.setUser("registration_service_user");
        dataSource.setPassword("1q2w3e");
        return dataSource;
    }
}
