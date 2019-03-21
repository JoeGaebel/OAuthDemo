package com.oauth.shenanigans.demo

import com.oauth.shenanigans.demo.utilities.WithMockCustomUser
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [UserController::class])
@WebMvcTest(secure = true)
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @WithMockCustomUser("steve@steve.com")
    fun `returns the email when logged in`() {
        mockMvc.perform(
            get("/users/me"))
            .andExpect(status().`is`(200))
            .andExpect(content().json(
                """ {
                    "email": "steve@steve.com"
                }"""
            ))
    }

    @Test
    fun `gives 401 when requesting preference and not logged in`() {
        mockMvc.perform(
            get("/users/me"))
            .andExpect(status().`is`(401))
    }
}
