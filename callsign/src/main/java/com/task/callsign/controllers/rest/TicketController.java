package com.task.callsign.controllers.rest;

import com.task.callsign.convertors.TicketRecordAndTicketResponseMapper;
import com.task.callsign.models.dto.TicketResponseDTO;
import com.task.callsign.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible for providing end-users with end-points to access the Ticket resource
 */
@RestController
public class TicketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Fetches and returns the list of all available tickets sorted in descending order by priority.
     *
     * @return {@link List <TicketResponseDTO>} list of all sorted tickets
     */
    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public ResponseEntity<List<TicketResponseDTO>> getAllTicketsSorted() {
        List<TicketResponseDTO> ticketResponses =
            ticketService
                .getAllTicketsByPriority()
                .stream()
                .map(TicketRecordAndTicketResponseMapper::toTicketResponse)
                .collect(Collectors.toList());
        LOGGER.debug("Successfully fetched {} tickets.", ticketResponses.size());
        return new ResponseEntity<>(ticketResponses, HttpStatus.OK);
    }
}

