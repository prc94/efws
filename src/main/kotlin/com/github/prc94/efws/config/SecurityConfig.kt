package com.github.prc94.efws.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.authorizeHttpRequests { it.anyRequest().authenticated() }
            .formLogin { }
            .build()

    @Bean
    fun userDetailsService(dataSource: DataSource) =
        JdbcUserDetailsManager(dataSource)
            .also {
                if (!it.userExists("test"))
                    it.createUser(
                        User("test", "{noop}test",
                            setOf(SimpleGrantedAuthority("ROLE_ADMIN")))
                    )
            }

    @Bean
    fun passwordEncoder() =
        BCryptPasswordEncoder()
/*
    @Bean
    fun userSetup(userDetailsManager: UserDetailsManager) = ApplicationRunner {
        if (!userDetailsManager.userExists("test"))
            userDetailsManager.createUser(
                User("test", "{noop}test",
                    setOf(SimpleGrantedAuthority("ROLE_ADMIN")))
            )
    }*/
}