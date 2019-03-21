package com.oauth.shenanigans.demo

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @GetMapping("/users/me")
    fun getPreference(user: OAuth2AuthenticationToken): ResponseEntity<Response> {
        val email = extractEmail(user)
        return ok(Response(email))
    }

    private fun extractEmail(user: OAuth2AuthenticationToken) = user.principal.attributes["email"] as String
}

data class Response(
    val email: String
)
