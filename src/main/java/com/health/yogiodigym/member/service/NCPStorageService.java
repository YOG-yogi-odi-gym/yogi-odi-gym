package com.health.yogiodigym.member.service;

import com.health.yogiodigym.member.service.impl.NCPStorageServiceImpl;
import org.springframework.web.multipart.MultipartFile;

public interface NCPStorageService {

    public String uploadImage(MultipartFile file, NCPStorageServiceImpl.DirectoryPath directory);

    public void deleteImageByUrl(String fileUrl);

}
