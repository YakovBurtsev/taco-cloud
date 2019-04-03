package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();
/*        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
                .antMatchers(HttpMethod.POST, "/api/ingredients").permitAll()
                .antMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
                .antMatchers("/ingredients/**", "/design/**", "/orders/**")
                .permitAll() //fixme: need fix this and angular login
                //.access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").permitAll()

                .and()
                .formLogin()
                .loginPage("/login")

                .and()
                .logout()
                .logoutSuccessUrl("/")

                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**", "/api/**", "/ingredients/**", "/design/**", "/orders/**", "/login")

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
        ;*/
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
