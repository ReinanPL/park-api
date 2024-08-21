package com.reinan.demo_park_api.common;

import com.reinan.demo_park_api.web.dto.UserLoginRequest;
import com.reinan.demo_park_api.web.dto.UserTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String Username, String Password){
        String token = client
                .post()
                .uri("/api/v1/login")
                .bodyValue(new UserLoginRequest(Username, Password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserTokenResponse.class)
                .returnResult().getResponseBody().getAccessToken();
        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
