package com.events.eventsbooking.service.ServiceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.events.eventsbooking.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public Map<String, String> uploadImage(MultipartFile file) throws IOException {

        Map uploadFile = cloudinary
                .uploader()
                .upload(file.getBytes(), ObjectUtils.asMap("folder", "event-booking"));

        return Map.of("url", uploadFile.get("secure_url").toString(),
                "public_id", uploadFile.get("public_id").toString()
        );
    }

    @Override
    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

}
