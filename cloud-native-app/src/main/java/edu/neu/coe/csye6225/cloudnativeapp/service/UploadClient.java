package edu.neu.coe.csye6225.cloudnativeapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface UploadClient {


    void storeProfilePic(MultipartFile file);

    InputStream getProfilePic();

    void deleteProfilePic();
}
