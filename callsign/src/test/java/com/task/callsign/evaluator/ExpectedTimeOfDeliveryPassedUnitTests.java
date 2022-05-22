package com.task.callsign.evaluator;

import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.tickets.ExpectedTimeOfDeliveryPassedTicketConditionEvaluator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class ExpectedTimeOfDeliveryPassedUnitTests {

    private static ExpectedTimeOfDeliveryPassedTicketConditionEvaluator evaluator;

    @Before
    public void setup() {
        evaluator = new ExpectedTimeOfDeliveryPassedTicketConditionEvaluator();
    }

    @Test
    public void testExpectedTimeOfDeliveryNotPassed() {

        // Mocking
        DeliveryRecordDTO deliveryRecordDTO = DeliveryRecordDTO.builder()
            .expectedDeliveryTime(LocalDateTime.now().plusSeconds(10))
            .build();

        // Invocation + Assertions
        Assert.assertFalse(evaluator.evaluate(deliveryRecordDTO));
    }

    @Test
    public void testExpectedTimeOfDeliveryPassed() {
        // Mocking
        DeliveryRecordDTO deliveryRecordDTO = DeliveryRecordDTO.builder()
            .expectedDeliveryTime(LocalDateTime.now().minusSeconds(10))
            .build();

        // Invocation + Assertions
        Assert.assertTrue(evaluator.evaluate(deliveryRecordDTO));
    }
}
