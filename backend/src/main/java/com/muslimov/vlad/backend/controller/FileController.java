package com.muslimov.vlad.backend.controller;

import com.muslimov.vlad.backend.dto.ResponseMessage;
import com.muslimov.vlad.backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/files")
public class FileController {

    private final FileService fileService;

    @PostMapping()
    public HttpEntity<?> uploadFile(@RequestParam MultipartFile file) {

        try {
            final var savedFile = fileService.save(file);
            return ResponseEntity.ok(savedFile);
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                .body(new ResponseMessage("Could not upload the file: " + file.getOriginalFilename()));
        }
    }

    @GetMapping()
    public HttpEntity<byte[]> getFile(@RequestParam String fileName) {

        final var file = fileService.getFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @DeleteMapping
    public HttpEntity<ResponseMessage> deleteFile(@RequestParam String fileName) {

        fileService.delete(fileName);
        return ResponseEntity.ok(
            new ResponseMessage("File deleted successfully")
        );
    }
}
