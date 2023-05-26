package com.example.notes.repository;

import com.example.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepo
        extends MongoRepository<Note, Integer> {
}
