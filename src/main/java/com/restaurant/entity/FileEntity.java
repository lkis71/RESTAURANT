package com.restaurant.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    public static FileEntity upload(MultipartFile file) {

        if (file == null) {
            return new FileEntity();
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.fileNm = file.getOriginalFilename();
        fileEntity.path = "upload\\";
        fileEntity.size = file.getSize();
        fileEntity.extension = fileEntity.fileNm.substring(fileEntity.fileNm.indexOf(".")+1);

        File fileFolder = new File(fileEntity.path);

        if(!fileFolder.exists()) {
            fileFolder.mkdirs();
        }

        try {
            String savePath = "C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\";
            File uploadFile = new File(savePath+fileEntity.fileNm);
            file.transferTo(uploadFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return fileEntity;
    }
}
