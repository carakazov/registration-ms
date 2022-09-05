package notes.project.oaut2registration.config.oauth;

import notes.project.oaut2registration.config.oauth.service.InnerScopeFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private InnerScopeFilterService innerScopeFilterService;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/role").hasAnyAuthority("OAUTH_CLIENT","CREATE_ROLE")
            .antMatchers("/client").hasAnyAuthority("OAUTH_CLIENT", "REGISTER_USER", "ANON")
            .antMatchers("/client/*/changeRoles").hasAnyAuthority("CHANGE_ROLES")
            .antMatchers("/client/changePassword").hasAuthority("CHANGE_PASSWORD")
            .antMatchers("/client/restorePassword").hasAuthority("ANON")
            .and()
            .addFilterBefore(new InnerScopeFilter(innerScopeFilterService), BasicAuthenticationFilter.class);
    }
}
