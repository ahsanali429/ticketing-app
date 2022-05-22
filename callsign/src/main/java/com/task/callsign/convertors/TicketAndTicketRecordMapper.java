package com.task.callsign.convertors;

import com.task.callsign.models.dto.TicketRecordDTO;
import com.task.callsign.models.entity.Ticket;

/**
 * Helper class responsible for conversion between Ticket Data Object and Ticket Business Object
 */
public class TicketAndTicketRecordMapper {
  /**
   * Converts the given data layer representation of a Ticket to the business layer representation.
   *
   * @param ticket entity to convert
   * @return {@link TicketRecordDTO} business layer representation of a Ticket
   */
  public static TicketRecordDTO toTicketRecord(Ticket ticket) {
    return TicketRecordDTO
        .builder()
        .ticketId(ticket.getTicketId())
        .reason(ticket.getReasonType())
        .ticketPriority(ticket.getTicketPriority())
        .deliveryRecord(DeliveryAndDeliveryRecordMapper.toDeliveryRecord(ticket.getDelivery()))
        .build();
  }
}
