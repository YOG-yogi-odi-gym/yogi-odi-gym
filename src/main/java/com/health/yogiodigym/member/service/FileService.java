package com.health.yogiodigym.member.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveImage(String saveFileName, MultipartFile profile);

    void removeDuplicate(String saveFileName, String uploadDirectory);
}
