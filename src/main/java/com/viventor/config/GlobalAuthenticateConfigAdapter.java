package com.viventor.config;

import com.viventor.entity.ApplicationUser;
import com.viventor.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class GlobalAuthenticateConfigAdapter extends GlobalAuthenticationConfigurerAdapter {
    private static final String USER_ROLE = "USER";

    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                if (authentication != null && authentication.getName() != null && authentication.getCredentials() != null) {
                    String userName = authentication.getName();
                    String password = authentication.getCredentials().toString();

                    ApplicationUser applicationUser = userRepository.findUserByUserName(userName);
                    if (applicationUser != null) {
                        if (applicationUser.getUserName().equals(userName) &&
                                passwordEncoder.matches(password, applicationUser.getPassword())) {

                            return new UsernamePasswordAuthenticationToken(userName, applicationUser.getPassword(),
                                    AuthorityUtils.createAuthorityList(USER_ROLE));
                        }
                    }
                }

                return null;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        };
    }
}