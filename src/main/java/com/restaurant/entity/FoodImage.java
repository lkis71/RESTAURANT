package com.restaurant.entity;

import com.restaurant.entity.type.UseType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileMaster fileMaster;

    @Setter
    @Enumerated(EnumType.STRING)
    private UseType useType;

    public static FoodImage createfoodImage(Food food, FileMaster fileMaster) {
        FoodImage foodImage = new FoodImage();
        foodImage.food = food;
        foodImage.fileMaster = fileMaster;
        foodImage.useType = UseType.USE;
        return foodImage;
    }

    public void delete() {
        this.useType = UseType.REMOVE;
        this.fileMaster.delete();
    }
}
