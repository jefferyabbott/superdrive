package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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
@RequestMapping("/credential")
public class CredentialController {


    private CredentialService credentialService;
    private UserService userService;
    private WebAttributesService webAttributesService;


    public CredentialController(CredentialService credentialService, UserService userService, WebAttributesService webAttributesService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.webAttributesService = webAttributesService;
    }


    @PostMapping("/updateCredential")
    public String postCredential(Authentication authentication, CredentialForm credentialForm, NoteForm noteForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        credentialForm.setUserId(userId);
        String credentialChangeType = this.credentialService.addCredential(credentialForm);
        model = this.webAttributesService.addAttributes(model, userId);
        model.addAttribute("target", "credentials");
        if (credentialChangeType.equals("credentialAdded")) {
            model.addAttribute("credentialCreateSuccess", credentialForm.getUrl());
        }
        else if (credentialChangeType.equals("credentialUpdated")) {
            model.addAttribute("credentialEditSuccess", credentialForm.getUrl());
        }
        else {
            model.addAttribute("errorMessage", "Credential failed to save.");
        }
        return "home";
    }


    @GetMapping("/deleteCredential/{credentialid}")
    public String deleteCredential(@PathVariable Integer credentialid, Authentication authentication, CredentialForm credentialForm, NoteForm noteForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        String url = this.credentialService.getCredentialUrl(credentialid, userId);
        this.credentialService.deleteCredential(credentialid, userId);
        model = this.webAttributesService.addAttributes(model, userId);
        model.addAttribute("target", "credentials");
        if (!this.credentialService.isCredentialStillInDB(credentialid, userId)) {
            model.addAttribute("credentialDeleteSuccess", url);
        }
        else {
            model.addAttribute("errorMessage", "Error: Credential (" + url + ") was not deleted.");
        }
        return "home";
    }


}
