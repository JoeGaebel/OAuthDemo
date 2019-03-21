package com.oauth.shenanigans.demo

import com.nimbusds.oauth2.sdk.http.HTTPResponse.SC_UNAUTHORIZED
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Value("\${frontend.url}")
    private lateinit var frontendUrl: String

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/users/**/*")
            .authenticated()

        http.oauth2Login()
            .defaultSuccessUrl(frontendUrl, true)

        http.exceptionHandling()
            .authenticationEntryPoint(UnauthorizedEntryPoint())
    }
}

class UnauthorizedEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.sendError(SC_UNAUTHORIZED)
    }
}
