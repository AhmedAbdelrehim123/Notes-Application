package com.example.notes.model;

import com.example.notes.repository.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class NoteService {
    @Autowired
    private NoteRepo repo;

    public Collection<Note> getAllNotes() {
        return repo.findAll();
    }

    public Note createNote(Note note) {
        return repo.insert(note);
    }

    public Optional<Note> getNoteById(int id) {
        return repo.findById(id);
    }

    public Optional<Note> updateNoteById(int id, Note updatedNote) {
        Optional<Note> note = repo.findById(id);
        if (note != null) {
            note.ifPresent(n -> n.setTitle(updatedNote.getTitle()));
            note.ifPresent(n -> n.setContent(updatedNote.getContent()));
            note.ifPresent(n -> repo.save(n));
            return note;
        }
        return null;
    }

    public void deleteNoteById(int id) {
        repo.deleteById(id);
    }

}

