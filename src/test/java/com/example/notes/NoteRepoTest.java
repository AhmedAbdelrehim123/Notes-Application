//package com.example.notes;
//
//import com.example.notes.model.Note;
//import com.example.notes.repository.NoteRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import static com.mongodb.assertions.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataMongoTest
//public class NoteRepoTest {
//
//    @Autowired
//    private NoteRepo repo;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @BeforeEach
//    public void setup() {
//        mongoTemplate.dropCollection(Note.class);
//    }
//
//    @Test
//    public void testSaveNote() {
//        Note note = new Note("Test Note", "Test Content");
//        note.setTitle("Test Note");
//        note.setContent("Test Content");
//
//        Note savedNote = repo.save(note);
//
//        assertNotNull(savedNote.getId());
//        assertEquals("Test Note", savedNote.getTitle());
//        assertEquals("Test Content", savedNote.getContent());
//    }
//
//    // Other repository tests for find, update, delete, etc.
//}
