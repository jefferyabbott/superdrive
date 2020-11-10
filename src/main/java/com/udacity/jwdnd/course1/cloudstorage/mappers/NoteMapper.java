package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userId=#{userId}")
    List<Note> getNotesForUser(Integer userId);

    @Select("SELECT COUNT(*) FROM NOTES WHERE userId=#{userId}")
    int numberOfNotesForUser(Integer userId);

    @Select("SELECT COUNT(*) FROM NOTES WHERE noteId=#{noteId} AND userId=#{userId}")
    int numberOfNotesForUserWithSpecificId(Integer noteId, Integer userId);

    @Select("SELECT noteTitle FROM NOTES WHERE noteId=#{noteId} AND userId=#{userId}")
    String getNoteTitle(Integer noteId, Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);


    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, NoteDescription=#{noteDescription} WHERE noteId=#{noteId}")
    void updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId} AND userId=#{userId}")
    void deleteNote(Integer noteId, Integer userId);
}


