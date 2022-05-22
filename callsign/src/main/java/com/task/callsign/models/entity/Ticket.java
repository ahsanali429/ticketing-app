package com.task.callsign.models.entity;

import com.task.callsign.models.enums.TicketPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "reason_type")
    private String reasonType;

    @Column(name = "ticket_priority", columnDefinition = "ENUM('HIGH', 'MEDIUM', 'LOW')")
    @Enumerated(EnumType.STRING)
    private TicketPriority ticketPriority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id")
    private Delivery delivery;

    @Column(name = "created_at" , insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at" , insertable = false, updatable = false)
    private Timestamp updatedAt;


}
