package com.task.callsign.tickets;

import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.enums.DeliveryStatus;
import com.task.callsign.models.enums.TicketPriority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DeliveryStatusNotChangedFromReceivedFor10Minutes implements TicketConditionEvaluator {

  @Override
  public String getReasonId() {
    return "DELIVERY_STATUS_NOT_CHANGED_FROM_RECEIVED_FOR_10_MINUTES";
  }

  @Override
  public boolean evaluate(DeliveryRecordDTO delivery) {
    return LocalDateTime.now().isAfter(delivery.getCreatedAt().plusMinutes(10)) &&
        delivery.getDeliveryStatus().equals(DeliveryStatus.RECEIVED);
  }

  @Override
  public TicketPriority getDefaultTicketPriority() {
    return TicketPriority.LOW;
  }
}
