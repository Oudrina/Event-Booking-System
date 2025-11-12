package com.events.eventsbooking.service;

import com.cloudinary.Cloudinary;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map<String,String> uploadImage(MultipartFile file) throws IOException;
void  deleteImage(String publicId) throws IOException;

}
