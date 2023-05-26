package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.model.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping
    public Collection<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @GetMapping("/{id}")
    public Optional<Note> getNoteById(@PathVariable("id") int id) {
        return noteService.getNoteById(id);
    }

    @PutMapping("/{id}")
    public Optional<Note> updateNote(@PathVariable("id") int id, @RequestBody Note updatedNote) {
        return noteService.updateNoteById(id, updatedNote);
    }

    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable("id") int id) {
        noteService.deleteNoteById(id);
    }
}