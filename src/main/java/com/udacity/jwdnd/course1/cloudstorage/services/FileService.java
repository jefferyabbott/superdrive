package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    public void addFile(File incomingFile) {
        fileMapper.addFile(incomingFile);
    }

    public List<String> getListOfFilesForUser(Integer userId) {
        return fileMapper.getListOfFiles(userId);
    }

    public int numberOfFilesForUser(Integer userId) {
        return fileMapper.numberOfFilesForUser(userId);
    }

    public int checkForExistingFileName(Integer userId, String filename) {
        return fileMapper.checkForExistingFileName(userId, filename);
    }

    public void deleteFile(String filename, Integer userId) {
        fileMapper.deleteFile(userId, filename);
    }

    public File getFile(String filename, Integer userId) {
        return fileMapper.getFile(userId, filename);
    }
}
