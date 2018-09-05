package com.ness.hackathon.support.adminserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

@Configuration
@EnableAutoConfiguration
@Profile({ "local", "development", "qa1", "pp", "prod", "qa2", "qa3" })
@EnableWebSecurity
@EnableAdminServer
public class AdminServerApplication extends WebSecurityConfigurerAdapter{
	@Autowired
	Environment env;
	 private final String adminContextPath;

    public AdminServerApplication(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll()
			.and()
				.logout().logoutUrl("/logout")
			.and()
				.authorizeRequests()
				.antMatchers(adminContextPath + "/assets/**").permitAll()
				.antMatchers(adminContextPath + "/login").permitAll()
				.antMatchers(adminContextPath + "/actuator/**").permitAll()
			.and()
				.authorizeRequests()
					// Allow only Admin to access jmx
					.antMatchers("/**/instances/**/jmx*").hasRole("ADMIN")
					.antMatchers("/**/instances/**/jolokia*").hasRole("ADMIN")
					// Allow only Admin to access Environment
					.antMatchers("/**/instances/**/refresh").hasRole("ADMIN")
					.antMatchers("/**/instances*/**/env*").hasRole("ADMIN")
					// Allow only Admin to access logging
					.antMatchers("/**/instances/**/loggers").hasRole("ADMIN")
					.antMatchers("/**").hasAnyRole("ADMIN", "USER")
				.anyRequest().authenticated().and().httpBasic().and().csrf().disable();
	}
    
    public SecurityWebFilterChain securityWebFilterChainSecure(ServerHttpSecurity http) {
        // @formatter:off
        return http.authorizeExchange()
                .pathMatchers(adminContextPath + "/assets/**").permitAll()
                .pathMatchers(adminContextPath + "/login").permitAll()
                .anyExchange().authenticated()
                .and()
            .formLogin().loginPage(adminContextPath + "/login").and()
            .logout().logoutUrl(adminContextPath + "/logout").and()
            .httpBasic().and()
            .csrf().disable()
            .build();
        // @formatter:on
    }
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser(env.getProperty("authorizedUsers.admin-name"))
			.password("{noop}"+env.getProperty("authorizedUsers.admin-password"))
			.roles(env.getProperty("authorizedUsers.admin-role"))
			.and()
			.withUser(env.getProperty("authorizedUsers.user-name"))
			.password("{noop}"+env.getProperty("authorizedUsers.user-password"))
			.roles(env.getProperty("authorizedUsers.user-role"));
	}
}
