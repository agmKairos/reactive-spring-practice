package es.kairosds.reactive.practice.blog.entry;


import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import es.kairosds.reactive.practice.blog.api.dto.EntryDTO;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntriesApiTest {

    
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void createNewEntryTest() {
    	
    	EntryDTO entryDTO = new EntryDTO("id", "authorName", "title", "text", new ArrayList<>());
    	
		webTestClient.post().uri("/entries/")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(entryDTO), EntryDTO.class)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
			.jsonPath("$.authorName").isNotEmpty()
			.jsonPath("$.authorName").isEqualTo("authorName");

    }

    @Test
    public void getById() {

    	 webTestClient.get().uri("/entries/")
         .accept(MediaType.APPLICATION_JSON)
         .exchange()
         .expectStatus().isOk()
         .expectHeader().contentType(MediaType.APPLICATION_JSON)
         .expectBodyList(EntryDTO.class);
    }

//    @Test
//    public void getByIdNofFound() {
//
//    	 webTestClient.get().uri("/entries/{id}", "notFoundId")
//         .accept(MediaType.APPLICATION_JSON)
//         .exchange()
//         .expectStatus().isNotFound()
//         .expectHeader().contentType(MediaType.APPLICATION_JSON);
//    }

   

    @Test
    public void deleteEntry() {

    	webTestClient.delete().uri("/entries/{id}", "id")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody(EntryDTO.class);

    }

}