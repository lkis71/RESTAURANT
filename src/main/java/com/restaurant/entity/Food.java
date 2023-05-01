package com.restaurant.entity;

import com.restaurant.controller.dto.FoodDto;
import com.restaurant.entity.common.Content;
import com.restaurant.entity.type.FoodType;
import com.restaurant.entity.type.UseType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Food {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    private String foodName;

    private int price;

    private Content content;

    private FoodType foodType;

    @Setter
    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food")
    private List<FoodFile> foodFiles = new ArrayList<>();

    private UseType useType;

    @Builder
    public Food(String foodName, int price, Content content, FoodType foodType) {
        this.foodName = foodName;
        this.price = price;
        this.content = content;
        this.foodType = foodType;
        this.useType = UseType.USE;
    }

    public void update(FoodDto foodDto) {

        this.foodName = foodDto.getFoodName();
        this.price = foodDto.getPrice();
        this.foodType = foodDto.getFoodType();

        Content updateContent = new Content(foodDto.getSimpleContents(), foodDto.getDetailContents());
        this.content = updateContent;
    }

    public void delete() {
        this.useType = UseType.REMOVE;

        // 첨부파일 삭제
        for (FoodFile foodFile : foodFiles) {
            foodFile.delete();
        }
    }
}
