package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.URL;


public class HomePage {

    // home page elements

    @FindBy(css = "#uploadLabel")
    private WebElement uploadLabel;

    @FindBy(css = "#logoutButton")
    private WebElement logoutButton;



    // note elements
    @FindBy(css = "#nav-notes-tab")
    private WebElement notesTab;

    @FindBy(css = "#newNote")
    private WebElement newNoteButton;

    @FindBy(css = "#note-title")
    private WebElement noteTitle;

    @FindBy(css = "#note-description")
    private WebElement noteDescription;

    @FindBy(css = "#editNoteButton")
    private WebElement editNoteButton;

    @FindBy(css = "#saveNoteButton")
    private WebElement saveNoteButton;

    @FindBy(css = "#cancelEditNoteButton")
    private WebElement cancelEditNoteButton;

    @FindBy(css = "#nav-notes")
    private WebElement notesTable;

    @FindBy(css = "#deleteNoteButton")
    private WebElement deleteNoteButton;




    // credential elements

    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(css = "#newCredentialButton")
    private WebElement newCredentialButton;

    @FindBy(css = "#credential-url")
    private WebElement credUrlInput;

    @FindBy(css = "#credential-username")
    private WebElement credUsernameInput;

    @FindBy(css = "#credential-password")
    private WebElement credPasswordInput;

    @FindBy(css = "#saveCredentialButton")
    private WebElement saveCredentialButton;

    @FindBy(css = "#nav-credentials")
    private WebElement credentialsTable;

    @FindBy(css = "#editCredentialButton")
    private WebElement editCredentialButton;

    @FindBy(css = "#credentialModal")
    private WebElement credentialModal;

    @FindBy(css = "#cancelCredentialEditButton")
    private WebElement cancelCredentialEditButton;

    @FindBy(css = "#deleteCredentialButton")
    private WebElement deleteCredentialButton;




    // test methods

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getUploadLabel() {
        return uploadLabel.getText();
    }



    public void addNote(String title, String description) throws InterruptedException {
        this.notesTab.click();
        Thread.sleep(500);
        this.newNoteButton.click();
        Thread.sleep(500);
        this.noteTitle.sendKeys(title);
        this.noteDescription.sendKeys(description);
        this.saveNoteButton.click();
    }

    public boolean editNote(String newNoteText) throws InterruptedException {
        this.editNoteButton.click();
        Thread.sleep(500);
        this.noteDescription.clear();
        this.noteDescription.sendKeys(newNoteText);
        Thread.sleep(500);
        this.saveNoteButton.click();
        Thread.sleep(500);
        this.editNoteButton.click();
        Thread.sleep(500);
        String modifiedText = this.noteDescription.getText();
        this.cancelEditNoteButton.click();
        Thread.sleep(500);
        return modifiedText.equals(newNoteText);
    }


    public boolean findNoteText(String title) {
        return this.notesTable.getText().contains(title);
    }


    public void logout() {
        this.logoutButton.click();
    }

    public void deleteNote() {
        this.deleteNoteButton.click();
    }

    public void addCredential(String url, String username, String password) throws InterruptedException {
        this.credentialsTab.click();
        Thread.sleep(500);
        this.newCredentialButton.click();
        Thread.sleep(500);
        this.credUrlInput.sendKeys(url);
        this.credUsernameInput.sendKeys(username);
        this.credPasswordInput.sendKeys(password);
        this.saveCredentialButton.click();
    }

    public boolean editCredential(String newCredentialUrl) throws InterruptedException {
        this.editCredentialButton.click();
        Thread.sleep(500);
        this.credUrlInput.clear();
        this.credUrlInput.sendKeys(newCredentialUrl);
        Thread.sleep(500);
        this.saveCredentialButton.click();
        Thread.sleep(500);
        return this.findCredentialText(newCredentialUrl);
    }

    public boolean findCredentialText(String url) {
        return this.credentialsTable.getText().contains(url);
    }

    public boolean passwordVisibleInTable(String password) {
        return this.credentialsTable.getText().contains(password);
    }

    public boolean passwordVisibleInEditModal(String password) throws InterruptedException {
        this.credentialsTab.click();
        Thread.sleep(500);
        this.editCredentialButton.click();
        Thread.sleep(500);
        System.out.println("--- " + this.credPasswordInput.getText());
        System.out.println(this.credentialModal.getText());
        boolean passwordVisible = this.credentialModal.getText().contains(password);
        this.cancelCredentialEditButton.click();
        return passwordVisible;
    }



    public void deleteCredential() {
        this.deleteCredentialButton.click();
    }


}
