package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.service.impl.NCPStorageServiceImpl;
import org.springframework.web.multipart.MultipartFile;

public interface NCPStorageService {

    String uploadImage(MultipartFile file, NCPStorageServiceImpl.DirectoryPath directory);

    void deleteImageByUrl(String fileUrl);

}
