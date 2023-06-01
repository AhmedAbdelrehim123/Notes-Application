package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.model.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }

    @Test
    void getAllNotes() throws Exception {
        Note note1 = new Note(1, "Title 1", "Content 1");
        Note note2 = new Note(2, "Title 2", "Content 2");
        when(noteService.getAllNotes()).thenReturn(Arrays.asList(note1, note2));

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[0].content").value("Content 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Title 2"))
                .andExpect(jsonPath("$[1].content").value("Content 2"));

        verify(noteService).getAllNotes();
    }

    @Test
    void createNote() throws Exception {
        Note note = new Note(1, "Title", "Content");
        when(noteService.createNote(any(Note.class))).thenReturn(note);

        String requestJson = "{\"id\": 1, \"title\": \"Title\", \"content\": \"Content\"}";

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Content"));

        verify(noteService).createNote(any(Note.class));
    }

    @Test
    void getNoteById() throws Exception {
        Note note = new Note(1, "Title", "Content");
        when(noteService.getNoteById(1)).thenReturn(Optional.of(note));

        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Content"));

        verify(noteService).getNoteById(1);
    }

    @Test
    void updateNote() throws Exception {
        Note updatedNote = new Note(1, "Updated Title", "Updated Content"); // Updated note object
        when(noteService.updateNoteById(eq(1), any(Note.class))).thenReturn(Optional.of(updatedNote));

        String requestJson = "{\"id\": 1, \"title\": \"Updated Title\", \"content\": \"Updated Content\"}";

        mockMvc.perform(put("/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"));

        verify(noteService).updateNoteById(eq(1), any(Note.class));
    }

    @Test
    void deleteNoteById() throws Exception {
        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isOk());

        verify(noteService).deleteNoteById(1);
    }
}
