package com.sfloresdev.quicknote;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record NoteDto (Long id,
                       String title,
                       String content,
                       String type,
                       @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                       LocalDateTime creationDate,
                       boolean isPinned,
                       boolean isArchived,
                       String color){

    public static NoteDto fromEntity(Note note){
        return new NoteDto(
            note.getId(),
            note.getTitle(),
            note.getContent(),
            note.getType(),
            note.getCreationDate(),
            note.isPinned(),
            note.isArchived(),
            note.getColor());
    }

    public Note toEntity(){
        Note note = new Note();
        note.setId(this.id);
        note.setTitle(this.title);
        note.setContent(this.content);
        note.setType(this.type);
        note.setCreationDate(this.creationDate);
        note.setPinned(this.isPinned);
        note.setArchived(this.isArchived);
        note.setColor(this.color);
        return note;
    }
}
