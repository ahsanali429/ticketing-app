package com.task.callsign.services.impl;

import com.task.callsign.controllers.rest.TicketController;
import com.task.callsign.convertors.TicketAndTicketRecordMapper;
import com.task.callsign.models.dto.TicketRecordDTO;
import com.task.callsign.models.entity.Delivery;
import com.task.callsign.models.entity.Ticket;
import com.task.callsign.models.enums.TicketPriority;
import com.task.callsign.repository.TicketRepository;
import com.task.callsign.services.DeliveryService;
import com.task.callsign.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final TicketRepository ticketRepository;
    private final DeliveryService deliveryService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, DeliveryService deliveryService) {
        this.ticketRepository = ticketRepository;
        this.deliveryService = deliveryService;
    }

    @Override
    @Transactional
    public TicketRecordDTO createTicket(String reason, TicketPriority ticketPriority, Long deliveryId) {
        LOGGER.info("Inside createTicket with reason: {}, priority: {}, deliveryId: {}",
            reason, ticketPriority, deliveryId);
        Delivery delivery = deliveryService
            .getById(deliveryId).
            orElseThrow(() -> new IllegalArgumentException("No delivery record found for id = " + deliveryId));

        Ticket ticket = Ticket.builder().reasonType(reason).ticketPriority(ticketPriority).delivery(delivery).build();
        ticketRepository.save(ticket);
        LOGGER.info("Ticket created successfully, ticket: {}", ticket);

        return TicketAndTicketRecordMapper.toTicketRecord(ticket);
    }

    @Override
    public List<TicketRecordDTO> getAllTicketsByPriority() {
        LOGGER.info("Inside getAllTicketsByPriority");
        return ticketRepository
            .getAllByOrderByTicketPriority()
            .stream()
            .map(TicketAndTicketRecordMapper::toTicketRecord)
            .collect(Collectors.toList());
    }
}
