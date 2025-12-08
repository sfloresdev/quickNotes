package com.sfloresdev.quicknote;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id){
        Optional<Note> noteOptional = noteService.getNoteById(id);
        if (noteOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Note note = noteOptional.get();
        NoteDto noteDto = NoteDto.fromEntity(note);
        return ResponseEntity.ok(noteDto);
    }

    @PostMapping("")
    private ResponseEntity<Void> createNote(@RequestBody NoteDto noteDto, UriComponentsBuilder ucb){
        Note note = noteDto.toEntity(); // Create a note
        note  = noteService.save(note);
        // Save the URI path to "location"
        URI location = ucb.path("/notes/{id}")
                .buildAndExpand(note.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("")
    private ResponseEntity<List<NoteDto>> getAllNotes(Pageable pageable) {
        List<Note> notes = noteService.getAllNotes(pageable);
        List<NoteDto> noteDtos = notes.stream()
                .map(NoteDto::fromEntity)
                .toList();
        return ResponseEntity.ok(noteDtos);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Void> updateNote(
            @PathVariable Long id,
            @RequestBody NoteDto noteDto) {
        Optional<Note> noteOptional = noteService.getNoteById(id);
        if (noteOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Note note = noteOptional.get();
        Note updatedNote = noteDto.toEntity();
        updatedNote.setId(note.getId());
        noteService.save(updatedNote);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteNote(@PathVariable Long id){
        Optional<Note> noteOptional = noteService.getNoteById(id);
        if (noteOptional.isEmpty())
            return ResponseEntity.notFound().build();
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}