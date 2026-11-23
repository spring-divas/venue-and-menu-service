package org.spring.divas.venue.feature.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.security.interfaces.RSAPublicKey;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "security.jwt.enabled=true",
        "spring.grpc.server.port=0"
})
@AutoConfigureMockMvc
class MenuSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean(name = "jwtPublicKey")
    private RSAPublicKey jwtPublicKey;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void shouldAllowAnonymousUserToViewMenu() throws Exception {
        mockMvc.perform(get("/menu").param("venueId", "1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/menu/pdf").param("venueId", "1"))
                .andExpect(status().is(not(HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    void shouldRequireTokenForOtherEndpoints() throws Exception {
        mockMvc.perform(get("/venue")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/dish")).andExpect(status().isUnauthorized());
        mockMvc.perform(post("/menu")).andExpect(status().isUnauthorized());
    }
}
