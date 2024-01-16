package com.picpayChallenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.picpayChallenge.dataTypes.UserType;
import com.picpayChallenge.dtos.TransactionDto;
import com.picpayChallenge.dtos.UserDto;
import com.picpayChallenge.entities.UserEntity;
import com.picpayChallenge.repositories.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
class PicpayChallengeApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private UserRepository userRepository;

	@Test
	void createUserSuccess() {
		UserDto user = new UserDto("Jin", "S123k", "k1337983", "a313211@ggmail.com", "ab213dgdfc5780", 100, UserType.common);
		webTestClient
			.post()
			.uri("/user")
			.bodyValue(user)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.name").isEqualTo(user.getName())
			.jsonPath("$.surname").isEqualTo(user.getSurname())
			.jsonPath("$.document").isEqualTo(user.getDocument())
			.jsonPath("$.email").isEqualTo(user.getEmail())
			.jsonPath("$.password").isEqualTo(user.getPassword())
			.jsonPath("$.balance").isEqualTo(user.getBalance())
			.jsonPath("$.userType").isEqualTo(user.getUserType().toString());
	}

	@Test
	void createUserFailure() {
		webTestClient
			.post()
			.uri("/user")
			.bodyValue(new UserDto("", "S123k", "k1337983", "a313211@ggmail.com", "ab213dgdfc5780", 100, UserType.common))
			.exchange().expectStatus().isBadRequest();
			
		webTestClient
			.post()
			.uri("/user")
			.bodyValue(new UserDto("Jin", "", "k1337983", "a313211@ggmail.com", "ab213dgdfc5780", 100, UserType.common))
			.exchange().expectStatus().isBadRequest();

		webTestClient
			.post()
			.uri("/user")
			.bodyValue(new UserDto("Jin", "S123k", "", "a313211@ggmail.com", "ab213dgdfc5780", 100, UserType.common))
			.exchange().expectStatus().isBadRequest();

		webTestClient
			.post()
			.uri("/user")
			.bodyValue(new UserDto("Jin", "S123k", "k1337983", "", "ab213dgdfc5780", 100, UserType.common))
			.exchange().expectStatus().isBadRequest();

		webTestClient
			.post()
			.uri("/user")
			.bodyValue(new UserDto("Jin", "S123k", "k1337983", "a313211@ggmail.com", "", 100, UserType.common))
			.exchange().expectStatus().isBadRequest();

		webTestClient
			.post()
			.uri("/user")
			.bodyValue(new UserDto("Jin", "S123k", "k1337983", "a313211@ggmail.com", "ab213dgdfc5780", 0, null))
			.exchange().expectStatus().isBadRequest();

	}
	
	@Test
	public void getUsers() {
		UserEntity user = new UserEntity("Jin", "S123k", "k1337983", "a313211@ggmail.com", "ab213dgdfc5780", 100, UserType.common);
		userRepository.save(user);

		webTestClient
			.get()
			.uri("/user")
			.exchange()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(1)
			.jsonPath("$[0].name").isEqualTo(user.getName())
			.jsonPath("$[0].surname").isEqualTo(user.getSurname())
			.jsonPath("$[0].document").isEqualTo(user.getDocument())
			.jsonPath("$[0].email").isEqualTo(user.getEmail())
			.jsonPath("$[0].password").isEqualTo(user.getPassword())
			.jsonPath("$[0].balance").isEqualTo(user.getBalance())
			.jsonPath("$[0].userType").isEqualTo(user.getUserType().toString());
	}

	@Test
	void transferFundsSuccess() {
		UserEntity userCommon = new UserEntity("Jin", "S123k", "k1337983", "a313211@ggmail.com", "ab213dgdfc5780", 100, UserType.common);
		userRepository.save(userCommon);

		UserEntity userSeller = new UserEntity("Jason", "Sternauer", "697-2937409", "andae1231@gmail.com", "134123412341234", 100, UserType.seller);
		userRepository.save(userSeller);

		UserEntity userCommon2 = new UserEntity("Jon", "Bones", "7777777", "goat@ggmail.com", "97934549", 100, UserType.common);
		userRepository.save(userCommon2);

		TransactionDto transaction = new TransactionDto((long) 2, (long) 1, (double) 20.0);

		webTestClient
			.post()
			.uri("/transaction")
			.bodyValue(transaction)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isNotEmpty()
			.jsonPath("$.payerId").isEqualTo(transaction.getPayerId())
			.jsonPath("$.receiverId").isEqualTo(transaction.getReceiverId())
			.jsonPath("$.value").isEqualTo(transaction.getValue());

		transaction = new TransactionDto((long) 1, (long) 3, (double) 20.0);

		webTestClient
			.post()
			.uri("/transaction")
			.bodyValue(transaction)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isNotEmpty()
			.jsonPath("$.payerId").isEqualTo(transaction.getPayerId())
			.jsonPath("$.receiverId").isEqualTo(transaction.getReceiverId())
			.jsonPath("$.value").isEqualTo(transaction.getValue());
	}



}
