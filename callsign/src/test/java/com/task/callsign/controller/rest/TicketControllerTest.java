package com.task.callsign.controller.rest;

import com.task.callsign.controllers.rest.TicketController;
import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.dto.TicketRecordDTO;
import com.task.callsign.models.dto.TicketResponseDTO;
import com.task.callsign.models.enums.TicketPriority;
import com.task.callsign.services.TicketService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @Test
    public void withNoTickets(){

        when(ticketService.getAllTicketsByPriority()).thenReturn(Collections.emptyList());

        ResponseEntity<List<TicketResponseDTO>> response = new TicketController(ticketService).getAllTicketsSorted();

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(0, response.getBody().size());
    }

    @Test
    public void withTwoTicketsFetchedSuccessfully(){

        // Mocking
        final String reason1 = "Reason 1";
        final String reason2 = "Reason 2";

        DeliveryRecordDTO deliveryRecordDTO = DeliveryRecordDTO.builder().deliveryId(1001L).build();
        TicketRecordDTO ticketRecordDTO1 = new TicketRecordDTO(101L, reason1, TicketPriority.HIGH, deliveryRecordDTO);
        TicketRecordDTO ticketRecordDTO2 = new TicketRecordDTO(102L, reason2, TicketPriority.LOW, deliveryRecordDTO);

        when(ticketService.getAllTicketsByPriority()).thenReturn(Arrays.asList(ticketRecordDTO1, ticketRecordDTO2));

        // Invocation
        ResponseEntity<List<TicketResponseDTO>> response = new TicketController(ticketService).getAllTicketsSorted();
        List<TicketResponseDTO> result =  response.getBody();

        // Assertions
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(2, result.size());

        TicketResponseDTO one = result.get(0);
        Assert.assertEquals(Long.valueOf(101L), one.getTicketId());
        Assert.assertEquals(Long.valueOf(1001L), one.getDeliveryId());
        Assert.assertEquals(TicketPriority.HIGH, one.getTicketPriority());
        Assert.assertEquals(reason1, one.getReason());

        TicketResponseDTO two = result.get(1);
        Assert.assertEquals(Long.valueOf(102L), two.getTicketId());
        Assert.assertEquals(Long.valueOf(1001L), two.getDeliveryId());
        Assert.assertEquals(TicketPriority.LOW, two.getTicketPriority());
        Assert.assertEquals(reason2, two.getReason());
    }
}
