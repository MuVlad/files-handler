package com.muslimov.vlad.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "files")
public class FileDB {

    private static final String SEQ_NAME = "file_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(
        name = SEQ_NAME,
        sequenceName = SEQ_NAME,
        allocationSize = 1
    )
    private Long id;
    private String name;
    private String type;

    @Lob
    private byte[] data;
}
