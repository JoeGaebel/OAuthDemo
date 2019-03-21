package com.oauth.shenanigans.demo.utilities

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithSecurityContextFactory
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.annotation.AnnotationRetention.RUNTIME

@Retention(RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory::class)
annotation class WithMockCustomUser(val email: String)

class WithMockCustomUserSecurityContextFactory : WithSecurityContextFactory<WithMockCustomUser> {
    override fun createSecurityContext(customUser: WithMockCustomUser): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()

        val authorities = setOf(OAuth2UserAuthority(mapOf("everybody gets" to "one")))
        val attributes = mapOf("email" to customUser.email)
        val principal = DefaultOAuth2User(authorities, attributes, "email")

        val auth = OAuth2AuthenticationToken(principal, principal.authorities, "anything, really")

        context.authentication = auth
        return context
    }
}

@RestController
class TestController {
    @GetMapping
    fun getEmail(user: OAuth2AuthenticationToken): String {
        return user.principal.attributes["email"] as String
    }
}

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [TestController::class])
@WebMvcTest(secure = true)
class WithMockCustomUserTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @WithMockCustomUser("sick@sick.com")
    fun `it creates an OAuth2AuthenticationToken correctly`() {
        mockMvc.perform(
            get("/users/me"))
            .andExpect(status().`is`(200))
            .andExpect(content().string("sick@sick.com"))
    }
}
