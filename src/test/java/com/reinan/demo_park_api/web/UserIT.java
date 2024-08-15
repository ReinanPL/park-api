package com.reinan.demo_park_api.web;

import com.reinan.demo_park_api.web.dto.UserCreateDto;
import com.reinan.demo_park_api.web.dto.UserPasswordDto;
import com.reinan.demo_park_api.web.dto.UserResponseDto;
import com.reinan.demo_park_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

	@Autowired
	WebTestClient testClient;

	@Test
	public void createUser_WithUsernameAndPasswordValid_ReturnUserCreatedWithStatus201(){
		UserResponseDto responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tody@gmail.com", "123456"))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(UserResponseDto.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getId()).isNotNull();
		assertThat(responseBody.getUsername()).isEqualTo("tody@gmail.com");
		assertThat(responseBody.getRole()).isEqualTo("CLIENT");
	}

	@Test
	public void createUser_WithUsernameInvalid_ReturnErrorMessageStatus422(){
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("", "123456"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tod@", "123456"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tody@gmail", "123456"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(422);
	}

	@Test
	public void createUser_WithPasswordInvalid_ReturnErrorMessageStatus422(){
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tody@gmail.com", ""))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tody@gmail.com", "1236"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(422);

	}

	@Test
	public void createUser_WithUsernameRepeated_ReturnErrorMessageWithStatus409(){
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("ana@gmail.com", "123456"))
				.exchange()
				.expectStatus().isEqualTo(409)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(409);
	}

	@Test
	public void findUserById_WithExistId_ReturnUserCreatedWithStatus200(){
		UserResponseDto responseBody = testClient
				.get()
				.uri("/api/v1/users/101")
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserResponseDto.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getId()).isEqualTo(101);
		assertThat(responseBody.getUsername()).isEqualTo("ana@gmail.com");
		assertThat(responseBody.getRole()).isEqualTo("ADMIN");
	}

	@Test
	public void findUserById_WithNonExistId_ReturnErrorMessageStatus404(){
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/users/0")
				.exchange()
				.expectStatus().isEqualTo(404)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(404);
	}

	@Test
	public void setPassword_WithValidData_ReturnStatus204(){
		testClient
				.patch()
				.uri("/api/v1/users/101")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("123456", "12345678", "12345678"))
				.exchange()
				.expectStatus().isNoContent();
	}

	@Test
	public void setPassword_WithNonExistId_ReturnErrorMessageStatus404(){
		ErrorMessage responseBody = testClient
				.patch()
				.uri("/api/v1/users/0")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("123456", "1234567", "1234567"))
				.exchange()
				.expectStatus().isEqualTo(404)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(404);
	}

	@Test
	public void setPassword_WithInvalidPassword_ReturnErrorMessageStatus422(){
		ErrorMessage responseBody = testClient
				.patch()
				.uri("/api/v1/users/101")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("", "", ""))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = testClient
				.patch()
				.uri("/api/v1/users/101")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("12345", "12345", "12345"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(422);
	}

	@Test
	public void setPassword_WithNotMatchPassword_ReturnErrorMessageStatus400(){
		ErrorMessage responseBody = testClient
				.patch()
				.uri("/api/v1/users/101")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("123478", "123456", "123456"))
				.exchange()
				.expectStatus().isEqualTo(400)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(400);

		responseBody = testClient
				.patch()
				.uri("/api/v1/users/101")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("123456", "111111", "1234567"))
				.exchange()
				.expectStatus().isEqualTo(400)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(400);

		responseBody = testClient
				.patch()
				.uri("/api/v1/users/101")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("123456", "1234567", "12345678"))
				.exchange()
				.expectStatus().isEqualTo(400)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getStatus()).isEqualTo(400);
	}

	@Test
	public void getAllUsers_WithNoParameters_ReturnListOfUserWithStatus200(){
		List<UserResponseDto> responseBody = testClient
				.get()
				.uri("/api/v1/users")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(UserResponseDto.class)
				.returnResult().getResponseBody();

		assertThat(responseBody).isNotNull();
		assertThat(responseBody.size()).isEqualTo(3);
	}
}




