package com.task.callsign.tickets;

import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.enums.TicketPriority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EstimatedTimeOFDeliveryGreaterThanExpectedTime implements TicketConditionEvaluator {
  @Override
  public String getReasonId() {
    return "ESTIMATED_TIME_OF_DELIVERY_GREATER_THAN_EXPECTED_TIME";
  }

  @Override
  public boolean evaluate(DeliveryRecordDTO delivery) {
    int estimatedTimeRequiredInSeconds = delivery.getRestaurantsMeanTimetoPrepareFood() +
        delivery.getTimeToReachDestinationInSeconds();
    LocalDateTime estimatedDeliveryTime = delivery.getCreatedAt().plusSeconds(estimatedTimeRequiredInSeconds);
    return estimatedDeliveryTime.isAfter(delivery.getExpectedDeliveryTime());
  }

  @Override
  public TicketPriority getDefaultTicketPriority() {
    return TicketPriority.MEDIUM;
  }
}
