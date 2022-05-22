package com.task.callsign.models.dto;

import com.task.callsign.models.enums.CustomerType;
import com.task.callsign.models.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeliveryRecordDTO {
    private Long deliveryId;
    private CustomerType customerType;
    private DeliveryStatus deliveryStatus;
    private Integer currentDistanceFromDestinationInMetres;
    private LocalDateTime expectedDeliveryTime;
    private Integer timeToReachDestinationInSeconds;
    private LocalDateTime createdAt;
    private int restaurantsMeanTimetoPrepareFood;
    private List<TicketRecordDTO> tickets = new ArrayList<>();

}
