package com.oauth.shenanigans.demo

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class CSRFTokenFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = request.getAttribute("_csrf") as CsrfToken?

        if (token != null) {
            response.setHeader("X-CSRF-TOKEN", token.token)
        }

        filterChain.doFilter(request, response)
    }
}
