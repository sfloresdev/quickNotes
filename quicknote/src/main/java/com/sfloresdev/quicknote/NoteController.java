package com.sfloresdev.quicknote;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
}
