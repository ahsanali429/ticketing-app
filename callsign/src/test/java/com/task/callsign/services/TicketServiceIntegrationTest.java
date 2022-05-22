package com.task.callsign.services;

import com.task.callsign.controller.rest.BaseIntegrationTest;
import com.task.callsign.models.dto.TicketRecordDTO;
import com.task.callsign.models.enums.TicketPriority;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:sql/insert-into-restaurant-table.sql", "classpath:sql/insert-into-delivery-table.sql"}),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/truncate-db-tables.sql")
})
public class TicketServiceIntegrationTest extends BaseIntegrationTest {

    private static final String REASON = "Reason 1";
    private static final Long DELIVERY_ID = 1001L;

    @Autowired
    TicketService ticketService;

    @Test
    public void testWithHighPriorityTicket() {

        // Mocking
        // Insert in sql mentioned above

        // Invocation
        TicketRecordDTO ticketRecord = ticketService.createTicket(REASON, TicketPriority.HIGH, DELIVERY_ID);

        // Assertions
        assertEquals(DELIVERY_ID, ticketRecord.getDeliveryRecord().getDeliveryId());
        assertEquals(REASON, ticketRecord.getReason());
        assertEquals(TicketPriority.HIGH, ticketRecord.getTicketPriority());
    }

    @Test(expected = RuntimeException.class)
    public void testWithATicketWithInvalidDeliveryId(){

        // Mocking
        // Insert in sql mentioned above

        // Invocation
        ticketService.createTicket(REASON, TicketPriority.HIGH, 2010L);

        //Assertions
        // Assertion in test method annotation
    }
}
