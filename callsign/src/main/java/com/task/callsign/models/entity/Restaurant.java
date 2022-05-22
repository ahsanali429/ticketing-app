package com.task.callsign.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "mean_time_to_prepare_food_in_seconds")
    private Integer meanTimeToPrepareFoodInSeconds;

    @Column(name = "created_at" , insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at" , insertable = false, updatable = false)
    private Timestamp updatedAt;

}
