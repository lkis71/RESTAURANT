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
public class FoodFile {

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

    public static FoodFile createFoodFile(Food food, FileMaster fileMaster) {
        FoodFile foodFile = new FoodFile();
        foodFile.food = food;
        foodFile.fileMaster = fileMaster;
        foodFile.useType = UseType.USE;
        return foodFile;
    }

    public void delete() {
        this.useType = UseType.REMOVE;
        this.fileMaster.delete();
    }
}
