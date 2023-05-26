package com.example.notes;

import com.example.notes.model.Note;
import com.example.notes.model.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test"})
public class NoteServiceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private NoteService noteService;

	@Test
	public void createNoteTest() {
		// Arrange
		Note note = new Note(1, "New Note", "Note Content");

		when(noteService.createNote(any(Note.class))).thenReturn(note);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Note> request = new HttpEntity<>(note, headers);

		// Act
		ResponseEntity<Note> response = restTemplate.exchange("/api/notes", HttpMethod.POST, request, Note.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo("1");
		assertThat(response.getBody().getTitle()).isEqualTo("New Note");
		assertThat(response.getBody().getContent()).isEqualTo("Note Content");
	}


	@Test
	public void getNoteByIdTest() {
		// Arrange
		Note note = new Note(1, "Note 1", "Content 1");

		when(noteService.getNoteById(1));

		// Act
		ResponseEntity<Note> response = restTemplate.getForEntity("/api/notes/1", Note.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(1);
		assertThat(response.getBody().getTitle()).isEqualTo("Note 1");
		assertThat(response.getBody().getContent()).isEqualTo("Content 1");
	}

	@Test
	public void getAllNotesTest() {
		// Arrange
		Note note1 = new Note(1, "Note 1", "Content 1");

		Note note2 = new Note(2, "Note 2", "Content 2");

		when(noteService.getAllNotes()).thenReturn(Arrays.asList(note1, note2));

		// Act
		ResponseEntity<Note[]> response = restTemplate.getForEntity("/api/notes", Note[].class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).hasSize(2);
		assertThat(response.getBody()[0].getId()).isEqualTo(1);
		assertThat(response.getBody()[0].getTitle()).isEqualTo("Note 1");
		assertThat(response.getBody()[0].getContent()).isEqualTo("Content 1");
		assertThat(response.getBody()[1].getId()).isEqualTo(2);
		assertThat(response.getBody()[1].getTitle()).isEqualTo("Note 2");
		assertThat(response.getBody()[1].getContent()).isEqualTo("Content 2");
	}

	@Test
	public void updateNoteTest() {
		// Arrange
		Note existingNote = new Note(1, "Note 1", "Content 1");


		Note updatedNote = new Note(1, "Updated Note", "Updated Content");


		when(noteService.updateNoteById(eq(1), any(Note.class)));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Note> request = new HttpEntity<>(updatedNote, headers);

		// Act
		ResponseEntity<Note> response = restTemplate.exchange("/api/notes/1", HttpMethod.PUT, request, Note.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(1);
		assertThat(response.getBody().getTitle()).isEqualTo("Updated Note");
		assertThat(response.getBody().getContent()).isEqualTo("Updated Content");
	}

	@Test
	public void deleteNoteByIdTest() {

		// Act
		ResponseEntity<Void> response = restTemplate.exchange("/api/notes/1", HttpMethod.DELETE, null, Void.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(response.getBody()).isNull();
		verify(noteService, times(1)).deleteNoteById(1);
	}

}
