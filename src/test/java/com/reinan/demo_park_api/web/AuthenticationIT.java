package com.reinan.demo_park_api.web;


import com.reinan.demo_park_api.web.dto.UserLoginRequest;
import com.reinan.demo_park_api.web.dto.UserTokenResponse;
import com.reinan.demo_park_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthenticationIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void authenticate_WithCredentialsValid_ReturnTokenWithStatus200(){
        UserTokenResponse responseBody = testClient
                .post()
                .uri("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginRequest("ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserTokenResponse.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
    }

    @Test
    public void authenticate_WithCredentialsInvalid_ReturnErrorWithStatus400(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginRequest("invalid@gmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .post()
                .uri("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginRequest("ana@gmail.com", "12345622"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void authenticate_WithUsernameInvalid_ReturnErrorWithStatus422(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginRequest("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginRequest("@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void authenticate_WithPasswordInvalid_ReturnErrorWithStatus422(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginRequest("ana@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginRequest("ana@gmail.com", "1236"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
    }

}
