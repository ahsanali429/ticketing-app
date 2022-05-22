package com.task.callsign.evaluator;

import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.tickets.DeliveryStatusNotChangedFromReceivedFor10Minutes;
import com.task.callsign.tickets.EstimatedTimeOFDeliveryGreaterThanExpectedTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class EstimatedTimeOfDeliveryGreaterThanExpectedTimeUnitTests {

    private EstimatedTimeOFDeliveryGreaterThanExpectedTime evaluator;

    @Before
    public void setup() {
        evaluator = new EstimatedTimeOFDeliveryGreaterThanExpectedTime();
    }

    @Test
    public void testEstimatedAndExpectedTimesAreEqual(){

        // Mocking
        DeliveryRecordDTO deliveryRecordDTO = DeliveryRecordDTO.builder()
            .restaurantsMeanTimetoPrepareFood(100)
            .createdAt(LocalDateTime.now())
            .timeToReachDestinationInSeconds(100)
            .expectedDeliveryTime(LocalDateTime.now().plusSeconds(100 + 100))
            .build();

        // Invocation + Assertions
        Assert.assertFalse(evaluator.evaluate(deliveryRecordDTO));
    }

    @Test
    public void testEstimatedTimeLessThanExpectedTime(){

        // Mocking
        DeliveryRecordDTO deliveryRecordDTO = DeliveryRecordDTO.builder()
            .restaurantsMeanTimetoPrepareFood(100)
            .createdAt(LocalDateTime.now())
            .timeToReachDestinationInSeconds(100)
            .expectedDeliveryTime(LocalDateTime.now().plusSeconds(250))
            .build();

        // Invocation + Assertions
        Assert.assertFalse(evaluator.evaluate(deliveryRecordDTO));
    }

    @Test
    public void testEstimatedTimeGreaterThanExpectedTime(){

        // Mocking
        DeliveryRecordDTO deliveryRecordDTO = DeliveryRecordDTO.builder()
            .restaurantsMeanTimetoPrepareFood(100)
            .createdAt(LocalDateTime.now())
            .timeToReachDestinationInSeconds(100)
            .expectedDeliveryTime(LocalDateTime.now().plusSeconds(100))
            .build();

        // Invocation + Assertions
        Assert.assertTrue(evaluator.evaluate(deliveryRecordDTO));
    }
}
