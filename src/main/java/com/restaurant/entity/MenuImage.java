package com.restaurant.entity;

import com.restaurant.entity.type.UseType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileMaster fileMaster;

    @Setter
    @Enumerated(EnumType.STRING)
    private UseType useType;

    public static MenuImage createMenuImage(Menu menu, FileMaster fileMaster) {
        MenuImage menuImage = new MenuImage();
        menuImage.menu = menu;
        menuImage.fileMaster = fileMaster;
        menuImage.useType = UseType.USE;
        return menuImage;
    }

    public void delete() {
        this.useType = UseType.REMOVE;
        this.fileMaster.delete();
    }
}
