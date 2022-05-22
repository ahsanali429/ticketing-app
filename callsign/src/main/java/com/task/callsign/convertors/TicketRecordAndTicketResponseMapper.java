package com.task.callsign.convertors;


import com.task.callsign.models.dto.TicketRecordDTO;
import com.task.callsign.models.dto.TicketResponseDTO;

/**
 * Helper class responsible for conversions between Ticket Business Object And Ticket Presentation Object
 */
public class TicketRecordAndTicketResponseMapper {
  /**
   * Converts the given business layer representation of a Ticket to the presentation layer representation.
   *
   * @param ticketRecord the record to convert
   * @return {@link TicketResponseDTO} presentation layer representation of a ticket
   */
  public static TicketResponseDTO toTicketResponse(TicketRecordDTO ticketRecord) {
    return TicketResponseDTO
        .builder()
        .ticketId(ticketRecord.getTicketId())
        .deliveryId(ticketRecord.getDeliveryRecord().getDeliveryId())
        .reason(ticketRecord.getReason())
        .ticketPriority(ticketRecord.getTicketPriority())
        .build();
  }
}
