package com.health.yogiodigym.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Override
    public String saveImage(String saveFileName, MultipartFile profile) {
        if (profile.isEmpty()) {
            log.warn("업로드된 파일이 없습니다.");
            return null;
        }

        String uploadDirectory = "src/main/resources/static/images/profile";
        String originName = profile.getOriginalFilename();
        String fileExtension = originName.substring(originName.lastIndexOf("."));
        String fileName = saveFileName + fileExtension;

        removeDuplicate(saveFileName, uploadDirectory);

        Path path = Paths.get(uploadDirectory, fileName);
        try {
            Files.copy(profile.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("파일 저장 경로: " + path.toString());

        return "/images/profile/" + fileName;
    }

    @Override
    public void removeDuplicate(String saveFileName, String uploadDirectory) {
        try {
            Stream<Path> files = Files.list(Paths.get(uploadDirectory));
            List<Path> existingFiles = files.filter(file -> file.getFileName().toString().startsWith(saveFileName + ".")).toList();

            for (Path existingFile : existingFiles) {
                Files.delete(existingFile);
                log.info("기존 파일 삭제: {}", existingFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
