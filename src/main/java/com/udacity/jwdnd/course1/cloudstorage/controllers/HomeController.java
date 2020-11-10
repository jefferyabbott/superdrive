package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/home")
public class HomeController {


    private UserService userService;
    private WebAttributesService webAttributesService;


    public HomeController(UserService userService, WebAttributesService webAttributesService) {
        this.userService = userService;
        this.webAttributesService = webAttributesService;
    }


    @GetMapping()
    public String getHomePage(Authentication authentication, CredentialForm credentialForm, NoteForm noteForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        model = this.webAttributesService.addAttributes(model, userId);
        return "home";
    }


}
