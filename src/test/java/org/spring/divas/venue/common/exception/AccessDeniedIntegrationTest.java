package org.spring.divas.venue.common.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.security.interfaces.RSAPublicKey;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "security.jwt.enabled=true",
        "spring.grpc.server.port=0"
})
@AutoConfigureMockMvc
class AccessDeniedIntegrationTest {

    private static final String VENUE_JSON =
            """
            {"name": "Ресторан", "address": "Київ"}
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean(name = "jwtPublicKey")
    private RSAPublicKey jwtPublicKey;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void shouldReturnForbiddenProblemDetailWhenRoleIsNotAllowed() throws Exception {
        mockMvc.perform(createVenue().with(user("CUSTOMER")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.title").value("Access Denied"));
    }

    @Test
    void shouldAllowAdmin() throws Exception {
        mockMvc.perform(createVenue().with(user("ADMIN")))
                .andExpect(status().isCreated());
    }

    private static MockHttpServletRequestBuilder createVenue() {
        return post("/venue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VENUE_JSON);
    }

    private static RequestPostProcessor user(String role) {
        return jwt()
                .jwt(token -> token.subject("1"))
                .authorities(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
