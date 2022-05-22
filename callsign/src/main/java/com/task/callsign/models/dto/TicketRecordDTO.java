package com.task.callsign.models.dto;

import com.task.callsign.models.enums.TicketPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Builder
public class TicketRecordDTO {
    private Long ticketId;
    private String reason;
    private TicketPriority ticketPriority;
    private DeliveryRecordDTO deliveryRecord;

    public TicketRecordDTO(Long ticketId, String reason, TicketPriority ticketPriority) {
        this.ticketId = ticketId;
        this.reason = reason;
        this.ticketPriority = ticketPriority;
    }

    public TicketRecordDTO(Long ticketId, String reason, TicketPriority ticketPriority, DeliveryRecordDTO deliveryRecord) {
        this.ticketId = ticketId;
        this.reason = reason;
        this.ticketPriority = ticketPriority;
        this.deliveryRecord = deliveryRecord;
    }

}
