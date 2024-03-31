package com.muslimov.vlad.backend.repository;

import com.muslimov.vlad.backend.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileDB, Long> {

    Optional<FileDB> findByName(String name);
}
