package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.WebAttributesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {


    private NoteService noteService;
    private UserService userService;
    private WebAttributesService webAttributesService;


    public NoteController(NoteService noteService, UserService userService, WebAttributesService webAttributesService) {
        this.noteService = noteService;
        this.userService = userService;
        this.webAttributesService = webAttributesService;
    }


    @PostMapping("/updateNote")
    public String postNote(Authentication authentication, CredentialForm credentialForm, NoteForm noteForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        noteForm.setUserId(userId);
        String noteChangeType = this.noteService.addNote(noteForm);
        model = this.webAttributesService.addAttributes(model, userId);
        model.addAttribute("target", "notes");
        if (noteChangeType.equals("noteAdded")) {
            model.addAttribute("noteCreateSuccess", noteForm.getNoteTitle());
        }
        else if (noteChangeType.equals("noteUpdated")) {
            model.addAttribute("noteEditSuccess", noteForm.getNoteTitle());
        }
        else {
            model.addAttribute("errorMessage", "Note failed to save.");
        }
        return "home";
    }


    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Authentication authentication, CredentialForm credentialForm, NoteForm noteForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        String title = this.noteService.getNoteTitle(noteId, userId);
        this.noteService.deleteNote(noteId, userId);
        model = this.webAttributesService.addAttributes(model, userId);
        model.addAttribute("target", "notes");
        if (!this.noteService.isNoteStillInDB(noteId, userId)) {
            model.addAttribute("noteDeleteSuccess", title);
        }
        else {
            model.addAttribute("errorMessage", "Error: Note (" + title + ") was not deleted.");
        }
        return "home";
    }


}
