package com.sfloresdev.quicknote;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuicknoteApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnANote(){
        ResponseEntity<String> response = restTemplate
                .getForEntity("/notes/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Long id = documentContext.read("$.id", Long.class);
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(1L);

        String title = documentContext.read("$.title", String.class);
        assertThat(title).isNotNull();
        assertThat(title).isEqualTo("Reunión equipo");
    }

    @Test
    void shouldNotReturnANoteWithAnUnknownId(){
        ResponseEntity<String> response = restTemplate
                .getForEntity("/notes/25", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }


    @Test
    @DirtiesContext // <- To make sure the test is executed in a clean environment
    void shouldCreateANote(){
        // Create a "Note" example object
        NoteDto newNote = new NoteDto(
                null,
                "Meeting Notes",
                "Project discussion",
                "text",
                null,
                false,
                false,
                "blue");
        // Check the response of the action
        ResponseEntity<Void> createResponse = restTemplate
                .postForEntity("/notes", newNote, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Save the URI location of the note that we created
        URI locationOfNote = createResponse.getHeaders().getLocation();
        // Check that the response status is OK
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity(locationOfNote, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnAllNotes(){
        ResponseEntity<String> response = restTemplate
                .getForEntity("/notes", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int size = documentContext.read("$.size()");
        assertThat(size).isEqualTo(7);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).isNotEmpty();
        assertThat(ids).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 7);

        JSONArray titles = documentContext.read("$..title");
        assertThat(titles).isNotEmpty();
        assertThat(titles).containsExactlyInAnyOrder("Reunión equipo",
                "Lista de compras",
                "Recordatorio médico",
                "Idea app",
                "Tareas pendientes",
                "Cumpleaños Ana",
                "Libro recomendado");
    }

    @Test
    @DirtiesContext
    void shouldUpdateANote(){
        NoteDto updatedNote = new NoteDto(
                null,
                "Updated Meeting Notes",
                "Updated Project discussion",
                "text",
                null,
                false,
                false,
                "blue"
        );
        HttpEntity<NoteDto> requestEntity = new HttpEntity<>(updatedNote);
        ResponseEntity<Void> response = restTemplate
                .exchange("/notes/1", HttpMethod.PUT, requestEntity, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/notes/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("Updated Meeting Notes");
        String content = documentContext.read("$.content");
        assertThat(content).isEqualTo("Updated Project discussion");
    }

    @Test
    @DirtiesContext
    void shouldDeleteANote(){
        ResponseEntity<Void> response = restTemplate
                .exchange("/notes/1", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/notes/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shoulReturnNotFoundNonExistenNote(){
        ResponseEntity<Void> response = restTemplate
                .exchange("/notes/999", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

	@Test
	void contextLoads() {
	}
}
