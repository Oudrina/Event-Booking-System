package com.events.eventsbooking.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map<String,String> uploadImage(MultipartFile file) throws IOException;
void  deleteImage(String publicId) throws IOException;

}
