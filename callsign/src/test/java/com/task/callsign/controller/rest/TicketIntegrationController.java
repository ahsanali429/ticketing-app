package com.task.callsign.controller.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.callsign.controllers.rest.LoginController;
import com.task.callsign.models.dto.AuthenticationRequestDTO;
import com.task.callsign.models.dto.TicketResponseDTO;
import com.task.callsign.models.enums.TicketPriority;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts =   {
            "classpath:sql/insert-into-userinfo-table.sql",
            "classpath:sql/insert-into-restaurant-table.sql",
            "classpath:sql/insert-into-delivery-table.sql"
    }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/truncate-db-tables.sql")
})
public class TicketIntegrationController extends BaseIntegrationTest{

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private LoginController loginController;

    private static final String GET_TICKETS = "/tickets";

    @Test
    public void testResponseWithZeroTickets() throws Exception {
        // Mocking
        // Insert in sql mentioned above

        // Invocation
        List<TicketResponseDTO> responses = requestAndGetResponse();

        //Assertions
        Assert.assertEquals(0, responses.size());
    }

    @Test
    @Sql("classpath:sql/insert-into-ticket-table-sorted.sql")
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    public void testResponseWithMultipleTicketsSorted() throws Exception {

        // Mocking
        // Insert in sql mentioned above

        // Invocation
        List<TicketResponseDTO> responses = requestAndGetResponse();

        //Assertions
        assertEquals(4, responses.size());
        assertEquals(TicketPriority.HIGH, responses.get(0).getTicketPriority());
        assertEquals(TicketPriority.HIGH, responses.get(1).getTicketPriority());
        assertEquals(TicketPriority.MEDIUM, responses.get(2).getTicketPriority());
        assertEquals(TicketPriority.LOW, responses.get(3).getTicketPriority());
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql("classpath:sql/insert-into-ticket-table.sql")
    public void testResponseWithMultipleTickets() throws Exception {
        // Mocking
        // Insert in sql mentioned above

        // Invocation
        List<TicketResponseDTO> responses = requestAndGetResponse();

        //Assertions
        Assert.assertEquals(3, responses.size());

        TicketResponseDTO ticketOne = responses.get(0);
        assertEquals(1L, ticketOne.getTicketId());
        assertEquals(1001L, ticketOne.getDeliveryId());
        assertEquals(TicketPriority.HIGH, ticketOne.getTicketPriority());

        TicketResponseDTO ticketTwo = responses.get(1);
        assertEquals(2L, ticketTwo.getTicketId());
        assertEquals(1001L, ticketTwo.getDeliveryId());
        assertEquals(TicketPriority.MEDIUM, ticketTwo.getTicketPriority());

        TicketResponseDTO ticketThree = responses.get(2);
        assertEquals(3L, ticketThree.getTicketId());
        assertEquals(1001L, ticketThree.getDeliveryId());
        assertEquals(TicketPriority.LOW, ticketThree.getTicketPriority());
    }



    private List<TicketResponseDTO> requestAndGetResponse() throws Exception {
        String authenticatedJwtToken = loginController.createAuthToken(
            new AuthenticationRequestDTO("ahsan", "avbBYA8y4GWNnEmzTQmmea4ubZRFGHGJ")
        ).getBody().getToken();

        MvcResult result = mvc.perform(
            get(GET_TICKETS)
                .header("Authorization", "Bearer " + authenticatedJwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        return OBJECT_MAPPER.readValue(
            result.getResponse().getContentAsString(), new TypeReference<List<TicketResponseDTO>>() {});
    }
}
