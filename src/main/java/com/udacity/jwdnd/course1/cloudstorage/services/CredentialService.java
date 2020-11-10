package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    CredentialMapper credentialMapper;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public String addCredential(CredentialForm credentialForm) {
        Credential newCredential = new Credential();
        newCredential.setUrl(credentialForm.getUrl());
        newCredential.setUsername(credentialForm.getUsername());
        newCredential.setUserId(credentialForm.getUserId());

        // set key and encrypted password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        newCredential.setKey(encodedKey);
        newCredential.setPassword(encryptedPassword);

        // check whether id already exists (if so, credential was edited)
        if (credentialForm.getCredentialid() == null) {
            credentialMapper.addCredential(newCredential);
            return "credentialAdded";
        }
        else {
            newCredential.setCredentialid(credentialForm.getCredentialid());
            credentialMapper.updateCredential(newCredential);
            return "credentialUpdated";
        }

    }


    public List<Credential> getCredentialsForUser(Integer userId) {
        List<Credential> credentials = credentialMapper.getCredentialsForUser(userId);
        for (Credential credential : credentials) {
            credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }
        return credentials;
    }


    public String getCredentialUrl(Integer credentialId, Integer userId) {
        return credentialMapper.getCredentialUrl(credentialId, userId);
    }

    public int numberOfCredentialsForUser(Integer userId) {
        return credentialMapper.numberOfCredentialsForUser(userId);
    }

    public void deleteCredential(Integer credentialid, Integer userId) {
        credentialMapper.deleteCredential(credentialid, userId);
    }

    public boolean isCredentialStillInDB(Integer credentialId, Integer userId) {
        return credentialMapper.numberOfCredentialsForUserWithSpecificId(credentialId, userId) != 0 ? true : false;
    }
}
