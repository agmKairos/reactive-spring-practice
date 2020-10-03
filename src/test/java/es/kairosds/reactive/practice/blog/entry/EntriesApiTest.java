package es.kairosds.reactive.practice.blog.entry;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

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
//
//    @Test
//    public void getById() {
//
//        given().
//                request().
//        when().
//                get("/users/1").
//        then().
//                statusCode(200).
//                body("id", is(1));
//    }
//
//    @Test
//    public void getByIdNofFound() {
//
//        given().
//                request().
//                when().
//                get("/users/1000").
//                then().
//                statusCode(404);
//    }
//
//    @Test
//    public void getByFirstName() {
//
//        given().
//                request().param("firstName", "Juan").
//        when().
//                get("/users/").
//        then().
//                statusCode(200).
//                body("size()", is(2));
//    }
//
//    @Test
//    public void deleteUser() {
//
//        given().
//            request().
//        when().
//            delete("/users/2").
//        then().
//            statusCode(200).
//            body("id", is(2));
//
//        given().
//            request().
//        when().
//            get("/users/2").
//        then().
//            statusCode(404);
//
//    }

}