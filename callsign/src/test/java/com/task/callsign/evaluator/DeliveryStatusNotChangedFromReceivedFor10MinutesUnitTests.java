package com.task.callsign.evaluator;

import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.enums.DeliveryStatus;
import com.task.callsign.tickets.DeliveryStatusNotChangedFromReceivedFor10Minutes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryStatusNotChangedFromReceivedFor10MinutesUnitTests {

    private DeliveryStatusNotChangedFromReceivedFor10Minutes evaluator;

    @Before
    public void setup() {
        evaluator = new DeliveryStatusNotChangedFromReceivedFor10Minutes();
    }


    @Test
    public void testWithDeliveryStatusNotReceivedButCreatedAtGreaterThan10Minutes(){

        // Mocking
        DeliveryRecordDTO deliveryRecordDTO = DeliveryRecordDTO.builder()
            .createdAt(LocalDateTime.now().minusMinutes(10))
            .deliveryStatus(DeliveryStatus.PREPARING)
            .build();

        // Invocation + Assertions
        Assert.assertFalse(evaluator.evaluate(deliveryRecordDTO));
    }

    @Test
    public void testWithDeliverStatusReceivedButCreatedAtLessThan10Minutes(){

        // Mocking
        DeliveryRecordDTO deliveryRecordDTO = DeliveryRecordDTO.builder()
            .createdAt(LocalDateTime.now())
            .deliveryStatus(DeliveryStatus.RECEIVED)
            .build();

        // Invocation + Assertions
        Assert.assertFalse(evaluator.evaluate(deliveryRecordDTO));
    }

    @Test
    public void testWithDeliveryStatusReceivedAndCreatedAtGreaterThan10Minutes(){

        // Mocking
        DeliveryRecordDTO deliveryRecordDTO = DeliveryRecordDTO.builder()
            .createdAt(LocalDateTime.now().minusMinutes(11))
            .deliveryStatus(DeliveryStatus.RECEIVED)
            .build();

        // Invocation + Assertions
        Assert.assertTrue(evaluator.evaluate(deliveryRecordDTO));
    }
}
