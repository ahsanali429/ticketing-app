package com.task.callsign.tickets;


import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.enums.TicketPriority;

/**
 * Responsible for evaluating if a delivery record qualifies the need for a ticket to be created
 */
public interface TicketConditionEvaluator {
  /**
   * Gives a unique id to the reason this evaluator is associated to. This should be unique across all evaluators.
   *
   * @return String-based unique identifier for the reason
   */
  String getReasonId();

  /**
   * Evaluate if the given {@link DeliveryRecordDTO} record satisfies the ticket creation conditions.
   *
   * @param delivery the delivery record to evaluate
   * @return boolean result of evalute
   */
  boolean evaluate(DeliveryRecordDTO delivery);

  /**
   * Fetches the default priority associated with this ticket creation condition
   *
   * @return {@link TicketPriority} default priority
   */
  TicketPriority getDefaultTicketPriority();
}
