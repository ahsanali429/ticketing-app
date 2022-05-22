package com.task.callsign.models.entity;

import com.task.callsign.models.enums.CustomerType;
import com.task.callsign.models.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "customer_type", columnDefinition = "ENUM('VIP', 'LOYAL', 'NEW')")
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Column(name = "delivery_status", columnDefinition = "ENUM('RECEIVED','PREPARING', 'PICKED_UP', 'DELIVERED')")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "current_distance_from_destination_in_metres")
    private Integer currentDistanceFromDestinationInMetres;

    @Column(name = "expected_delivery_time")
    private LocalDateTime expectedDeliveryTime;

    @Column(name = "time_to_reach_destination_in_seconds")
    private Integer timeToReachDestinationInSeconds;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "delivery")
    private List<Ticket> tickets = new ArrayList<>();

    @Column(name = "created_at" , insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at" , insertable = false, updatable = false)
    private Timestamp updatedAt;

}
