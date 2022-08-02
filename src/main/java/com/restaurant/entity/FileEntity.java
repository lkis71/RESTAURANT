package com.restaurant.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "file")
public class FileEntity {
    
    @Id @GeneratedValue
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
    public static FileEntity createFile(Map<String, Object> fileMap, Restaurant restaurant, Menu menu) {

        FileEntity file = new FileEntity();
        file.setFileNm(String.valueOf(fileMap.get("fileNm")));
        file.setPath(String.valueOf(fileMap.get("path")));
        file.setSize(Long.parseLong(String.valueOf(fileMap.get("size"))));
        file.setExtension(String.valueOf(fileMap.get("extension")));
        file.setFileType(String.valueOf(fileMap.get("fileType")));
        file.setRestaurant(restaurant);
        file.setMenu(menu);

        return file;
    }
}
