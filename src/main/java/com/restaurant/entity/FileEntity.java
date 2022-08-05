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

import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String fileType;

    @JoinColumn(name = "restaurantId")
    @OneToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
    
    @JoinColumn(name = "menuId")
    @OneToOne(fetch = FetchType.LAZY)
    private Menu menu;

    //생성메서드
    public static FileEntity createFile(FileEntity fileInfo, Restaurant restaurant, Menu menu) {

        FileEntity file = new FileEntity();
        file.setFile(fileInfo.getFileNm(), fileInfo.getPath(), fileInfo.getSize(), fileInfo.getExtension(), fileInfo.getFileType());
        file.setFileJoinEntity(restaurant, menu);

        return file;
    }

    public void setFile(String fileNm, String path, Long size, String extension, String fileType) {
        this.fileNm = fileNm;
        this.path = path;
        this.size = size;
        this.extension = extension;
        this.fileType = fileType;
    }
    
    public void setFileJoinEntity(Restaurant restaurant, Menu menu) {
        this.restaurant = restaurant;
        this.menu = menu;
    }
}
