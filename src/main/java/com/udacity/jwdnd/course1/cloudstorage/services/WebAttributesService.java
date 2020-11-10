package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class WebAttributesService {


    private NoteService noteService;
    private CredentialService credentialService;
    private FileService fileService;


    public WebAttributesService(NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }


    public Model addAttributes(Model model, Integer userId) {
        model.addAttribute("files", this.fileService.getListOfFilesForUser(userId));
        model.addAttribute("fileCount", this.fileService.numberOfFilesForUser(userId));
        model.addAttribute("notes", this.noteService.getNotesForUser(userId));
        model.addAttribute("noteCount", this.noteService.numberOfNotesForUser(userId));
        model.addAttribute("credentials", this.credentialService.getCredentialsForUser(userId));
        model.addAttribute("credentialCount", this.credentialService.numberOfCredentialsForUser(userId));
        return model;
    }


}
