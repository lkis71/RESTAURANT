package com.restaurant.entity;

import javax.persistence.*;

import com.restaurant.entity.type.UseType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "file_master")
public class FileMaster {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String fileName;

    private String path;

    private long size;

    private String extension;

    @Setter
    @Enumerated(EnumType.STRING)
    private UseType useType;

    @Builder
    public FileMaster(String fileName, String path, Long size, String extension) {
        this.fileName = fileName;
        this.path = path;
        this.size = size;
        this.extension = extension;
        this.useType = UseType.USE;
    }

    public static FileMaster transferTo(MultipartFile file) {

        if (file == null) {
            return new FileMaster();
        }

        FileMaster fileMaster = new FileMaster();
        fileMaster.fileName = file.getOriginalFilename();
        fileMaster.path = "upload\\";
        fileMaster.size = file.getSize();
        fileMaster.extension = fileMaster.fileName.substring(fileMaster.fileName.indexOf(".")+1);

        File fileFolder = new File(fileMaster.path);

        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }

        try {
            String savePath = "C:\\IdeaProject\\restaurant\\src\\main\\resources\\static\\upload\\";
            File uploadFile = new File(savePath+ fileMaster.fileName);
            file.transferTo(uploadFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return fileMaster;
    }

    public boolean isEmpty() {
        return size > 0 ? true : false;
    }

    public void delete() {
        this.useType = UseType.REMOVE;
    }
}
