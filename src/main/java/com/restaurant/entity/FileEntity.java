package com.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @Builder
    public FileEntity(String fileNm, String path, Long size, String extension) {
        this.fileNm = fileNm;
        this.path = path;
        this.size = size;
        this.extension = extension;
    }

    public void setFile(String fileNm, String path, Long size, String extension) {
        this.fileNm = fileNm;
        this.path = path;
        this.size = size;
        this.extension = extension;
    }
}
