package notes.project.oaut2registration.config.oauth;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
public class DataSourceConfiguration {
    @Bean
    @Profile("!it")
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("registration-ms-postgres");
        dataSource.setDatabaseName("registration_system");
        dataSource.setUser("registration_system_user");
        dataSource.setPassword("1q2w3e");
        return dataSource;

    }
}
