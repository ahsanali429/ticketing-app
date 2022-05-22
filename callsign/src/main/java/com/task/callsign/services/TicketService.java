package com.task.callsign.services;

import com.task.callsign.models.dto.TicketRecordDTO;
import com.task.callsign.models.enums.TicketPriority;

import java.util.List;

public interface TicketService {
    /**
     * Creates a Ticket record for the given parameters.
     *
     * @param reason         The reason for ticket creation.
     * @param ticketPriority {@link TicketPriority} priority of the ticket
     * @param deliveryId     id of the delivery for which the ticket is to be created
     * @return the created {@link TicketRecordDTO}
     * @throws IllegalArgumentException if the given delivery id is invalid
     */
    TicketRecordDTO createTicket(String reason, TicketPriority ticketPriority, Long deliveryId);

    /**
     * Fetches list of all Tickets sorted by priority from HIGH to LOW
     * @return {@link List <TicketRecordDTO>} list of ticket records
     */
    List<TicketRecordDTO> getAllTicketsByPriority();
}
