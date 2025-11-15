package com.sfloresdev.quicknote;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public Optional<Note> getNoteById(Long id){
        if (id == null)
            return Optional.empty();
        return noteRepository.findById(id);
    }
}
