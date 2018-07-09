package com.gueka.mutant.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.gueka.mutant.config.DBTestConfig;
import com.gueka.mutant.request.DNARequest;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DBTestConfig.class)
public class IntegrationTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void isMutant() {
		String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
		DNARequest request = new DNARequest();
		request.setDna(dna);
		webTestClient
			.post().uri("/mutant")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(request), DNARequest.class)
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void invalidLengthDNA() {
		String[] dna = {"ATGCGA","CAGTGC","TTATGT"};
		DNARequest request = new DNARequest();
		request.setDna(dna);
		webTestClient
			.post().uri("/mutant")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(request), DNARequest.class)
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.BAD_GATEWAY);
	}
	
	@Test
	public void isHuman() {
		String[] dna = {"AAGGTT","AAGGTT","TTCCAA","TTCCAA","AAGGTT","AAGGTT"};
		DNARequest request = new DNARequest();
		request.setDna(dna);
		webTestClient
		.post().uri("/mutant")
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(request), DNARequest.class)
		.exchange()
		.expectStatus().isEqualTo(HttpStatus.BAD_GATEWAY);
	}
	
	@Test
	public void getStats() {
		webTestClient
		.get().uri("/stats")
		.accept(MediaType.APPLICATION_STREAM_JSON)
		.exchange()
		.expectStatus().isEqualTo(HttpStatus.OK)
		.expectBody();
	}
		
}
