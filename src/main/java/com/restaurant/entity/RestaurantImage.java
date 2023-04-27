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
public class RestaurantImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_file_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileMaster fileMaster;

    @Setter
    @Enumerated(EnumType.STRING)
    private UseType useType;

    public static RestaurantImage createRestaurantImage(Restaurant restaurant, FileMaster fileMaster) {

        RestaurantImage restaurantImage = new RestaurantImage();
        restaurantImage.restaurant = restaurant;
        restaurantImage.fileMaster = fileMaster;
        restaurantImage.useType = UseType.USE;

        return restaurantImage;
    }

    public void delete() {
        this.useType = UseType.REMOVE;
        this.fileMaster.delete();
    }
}
