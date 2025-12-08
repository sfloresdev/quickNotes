package com.sfloresdev.quicknote;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
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

    public Note save(Note note){
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes(Pageable pageable) {
        return noteRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, "title")
                        ))
                .getContent();
    }

    public void delete(long noteId) {
        noteRepository.deleteById(noteId);
    }
}
