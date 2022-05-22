package com.task.callsign.models.dto;

import com.task.callsign.models.enums.TicketPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TicketResponseDTO {
    private Long ticketId;
    private Long deliveryId;
    private String reason;
    private TicketPriority ticketPriority;

}
