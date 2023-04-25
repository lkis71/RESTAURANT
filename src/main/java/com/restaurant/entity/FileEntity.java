package com.restaurant.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "file")
public class FileEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileId")
    private Long id;

    private String fileNm;

    private String path;

    private Long size;

    private String extension;

    @Setter
    @Enumerated(EnumType.STRING)
    private UseType useType;

    @Builder
    public FileEntity(String fileNm, String path, Long size, String extension) {
        this.fileNm = fileNm;
        this.path = path;
        this.size = size;
        this.extension = extension;
        this.useType = UseType.USE;
    }

    public void setFile(String fileNm, String path, Long size, String extension) {
        this.fileNm = fileNm;
        this.path = path;
        this.size = size;
        this.extension = extension;
    }
}
