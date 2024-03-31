package com.muslimov.vlad.backend.controller;

import com.muslimov.vlad.backend.BaseIntegrationTest;
import com.muslimov.vlad.backend.model.FileDB;
import com.muslimov.vlad.backend.repository.FileRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FileControllerTest extends BaseIntegrationTest {

    private MockMultipartFile multipartFile;

    @Autowired
    FileRepository fileRepository;

    @BeforeEach
    public void createFile() {
        multipartFile = new MockMultipartFile("file", "test.txt",
            "text/plain", "Spring Framework".getBytes());
    }

    @Test
    @Transactional
    @DisplayName("Сохранение файла")
    void uploadFile() throws Exception {

        String responseMessage = "{\"name\":\"%s\",\"type\":\"%s\"}"
            .formatted(multipartFile.getOriginalFilename(), multipartFile.getContentType());

        mockMvc.perform(multipart("/v1/files").file(multipartFile))
            .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().string(responseMessage)
            );
    }


    @Test
    @Transactional
    @DisplayName("Поиск файла по имени")
    void getFile() throws Exception {

        final var savedFile = savingFileToDB();

        mockMvc.perform(get("/v1/files").param("fileName", savedFile.getName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
            .andExpect(header()
                .string("Content-Disposition",
                    "form-data; name=\"attachment\"; filename=\"" + savedFile.getName() + "\"")
            )
            .andExpect(content().bytes(savedFile.getData()));
    }


    @Test
    @Transactional
    @DisplayName("Удаление файла по имени")
    void deleteFile() throws Exception {

        final var savedFile = savingFileToDB();
        String responseMessage = "{\"message\":\"File deleted successfully\"}";

        mockMvc.perform(delete("/v1/files").param("fileName", savedFile.getName()))
            .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().string(responseMessage)
            );
    }

    private FileDB savingFileToDB() throws IOException {
        return fileRepository.save(FileDB.builder()
            .name(multipartFile.getOriginalFilename())
            .type(multipartFile.getContentType())
            .data(multipartFile.getBytes())
            .build()
        );
    }
}