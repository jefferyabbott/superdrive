package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    List<Credential> getCredentialsForUser(Integer userId);


    @Select("SELECT COUNT(*) FROM CREDENTIALS WHERE userId=#{userId}")
    int numberOfCredentialsForUser(Integer userId);


    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int addCredential(Credential credential);


    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key} , password=#{password} WHERE credentialid=#{credentialid}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialid} AND userid=#{userId}")
    void deleteCredential(Integer credentialid, Integer userId);
}
