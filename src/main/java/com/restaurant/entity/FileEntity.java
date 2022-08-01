package com.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
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
    public static FileEntity createFile(String fileNm, String path, Long size, String extension, String fileType, Restaurant restaurant, Menu menu) {

        FileEntity file = new FileEntity();
        file.setFileNm(fileNm);
        file.setPath(path);
        file.setSize(size);
        file.setExtension(extension);
        file.setFileType(fileType);
        file.setRestaurant(restaurant);
        file.setMenu(menu);

        return file;
    }
}
