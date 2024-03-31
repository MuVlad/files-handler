package com.muslimov.vlad.backend.service;

import com.muslimov.vlad.backend.dto.FileDto;
import com.muslimov.vlad.backend.exception.model.NotFoundException;
import com.muslimov.vlad.backend.model.FileDB;
import com.muslimov.vlad.backend.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public FileDto save(MultipartFile file) throws IOException {

        FileDB fileDB = FileDB.builder()
            .name(file.getOriginalFilename())
            .type(file.getContentType())
            .data(file.getBytes())
            .build();

        final var savedFile = fileRepository.save(fileDB);

        return new FileDto(savedFile.getName(), savedFile.getType());
    }

    @Transactional
    public byte[] getFile(String fileName) {

        final var file = fileRepository.findByName(fileName);

        if (file.isPresent()) {
            return file.get().getData();
        } else {
            throw new NotFoundException("File not found");
        }
    }

    public void delete(String fileName) {

        final var file = fileRepository.findByName(fileName);

        if (file.isPresent()) {
            fileRepository.delete(file.get());
        } else {
            throw new NotFoundException("File not found");
        }
    }
}
