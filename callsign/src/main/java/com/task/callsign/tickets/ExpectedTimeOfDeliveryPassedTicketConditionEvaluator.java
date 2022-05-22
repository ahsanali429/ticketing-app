package com.task.callsign.tickets;


import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.enums.TicketPriority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExpectedTimeOfDeliveryPassedTicketConditionEvaluator implements TicketConditionEvaluator {
  @Override
  public String getReasonId() {
    return "EXPECTED_TIME_OF_DELIVERY_PASSED";
  }

  @Override
  public boolean evaluate(DeliveryRecordDTO delivery) {
    return LocalDateTime.now().isAfter(delivery.getExpectedDeliveryTime());
  }

  @Override
  public TicketPriority getDefaultTicketPriority() {
    return TicketPriority.HIGH;
  }
}
