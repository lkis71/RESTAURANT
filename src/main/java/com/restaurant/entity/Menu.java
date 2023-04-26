package com.restaurant.entity;

import com.restaurant.controller.dto.MenuDto;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.MenuType;
import com.restaurant.entity.type.UseType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Menu {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuId")
    private Long id;

    private String menuName;

    private int price;

    private Content content;

    private MenuType menuType;

    @Setter
    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @Setter
    @JoinColumn(name = "file_id")
    @OneToOne(fetch = FetchType.LAZY)
    private FileEntity file;

    private UseType useType;

    @Builder
    public Menu(String menuName, int price, Content content, MenuType menuType) {
        this.menuName = menuName;
        this.price = price;
        this.content = content;
        this.menuType = menuType;
        this.useType = UseType.USE;
    }

    public void update(MenuDto menuDto) {

        this.menuName = menuDto.getMenuName();
        this.price = menuDto.getPrice();
        this.menuType = menuDto.getMenuType();

        Content updateContent = new Content(menuDto.getSimpleContents(), menuDto.getDetailContents());
        this.content = updateContent;
    }

    public void delete() {
        this.useType = UseType.REMOVE;
    }
}
