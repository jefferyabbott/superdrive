package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController /*implements HandlerExceptionResolver*/ {


    private FileService fileService;
    private UserService userService;
    private WebAttributesService webAttributesService;


    public FileController(FileService fileService, UserService userService, WebAttributesService webAttributesService) {
        this.fileService = fileService;
        this.userService = userService;
        this.webAttributesService = webAttributesService;
    }


    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, CredentialForm credentialForm, NoteForm noteForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        try {
            String filename = file.getOriginalFilename();
            if (filename.equals("")) {
                model.addAttribute("errorMessage", "Please select a file to upload.");
            }
            else if (fileService.checkForExistingFileName(userId, filename) != 0) {
                model.addAttribute("errorMessage", "A file named " + filename + " exists in SuperDrive.");
            }
            else {
                File incomingFile = new File();
                incomingFile.setFileName(filename);
                incomingFile.setContentType(file.getContentType());
                incomingFile.setFileSize(file.getSize());
                incomingFile.setFileData(file.getBytes());
                incomingFile.setUserId(userId);
                fileService.addFile(incomingFile);
                model.addAttribute("fileUploadSuccess", filename);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        model = this.webAttributesService.addAttributes(model, userId);
        return "home";
    }




    @GetMapping("/deleteFile/{file}")
    public String deleteFile(@PathVariable String file, Authentication authentication, CredentialForm credentialForm, NoteForm noteForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        // add userId to ensure that the delete target is owned by the authenticated user
        this.fileService.deleteFile(file, userId);
        model = this.webAttributesService.addAttributes(model, userId);
        model.addAttribute("fileDeleteSuccess", file);
        return "home";
    }


    @GetMapping("downloadFile/{file}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String file, Authentication authentication) {
        Integer userId = userService.getUserId(authentication.getName());
        File requestedFile = fileService.getFile(file, userId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(requestedFile.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + requestedFile.getFileName() + "\"")
                .body(new ByteArrayResource(requestedFile.getFileData()));
    }

}
