package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;
import org.springframework.core.io.Resource;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Select("SELECT COUNT(*) FROM FILES WHERE userId=#{userId}")
    int numberOfFilesForUser(Integer userId);

    @Select("SELECT fileName FROM FILES WHERE userId=#{userId}")
    List<String> getListOfFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileName=#{fileName} AND userId=#{userId}")
    File getFile(Integer userId, String fileName);

    @Select("SELECT count(*) FROM FILES WHERE userId=#{userId} AND fileName=#{fileName}")
    int checkForExistingFileName(Integer userId, String fileName);


    @Delete("DELETE FROM FILES WHERE fileName=#{fileName} AND userId=#{userId}")
    void deleteFile(Integer userId, String fileName);



}
