package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public String addNote(NoteForm noteForm) {
        Note newNote = new Note();
        newNote.setNoteTitle(noteForm.getNoteTitle());
        newNote.setNoteDescription(noteForm.getNoteDescription());
        newNote.setUserId(noteForm.getUserId());

        // check whether note id already exists (if so, note was edited)
        if (noteForm.getNoteId() == null) {
            noteMapper.addNote(newNote);
            return "noteAdded";
        }
        else {
            newNote.setNoteId(noteForm.getNoteId());
            noteMapper.updateNote(newNote);
            return "noteUpdated";
        }
    }

    public List<Note> getNotesForUser(Integer userId) {
        return noteMapper.getNotesForUser(userId);
    }

    public int numberOfNotesForUser(Integer userId) {
        return noteMapper.numberOfNotesForUser(userId);
    }

    public String getNoteTitle(Integer noteId, Integer userId) {
        return noteMapper.getNoteTitle(noteId, userId);
    }

    public void deleteNote(Integer noteId, Integer userId) {
        noteMapper.deleteNote(noteId, userId);
    }

    public boolean isNoteStillInDB(Integer noteId, Integer userId) {
        return noteMapper.numberOfNotesForUserWithSpecificId(noteId, userId) != 0 ? true : false;
    }

}

