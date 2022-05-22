package com.task.callsign.jobs;

import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.enums.CustomerType;
import com.task.callsign.models.enums.DeliveryStatus;
import com.task.callsign.models.enums.TicketPriority;
import com.task.callsign.services.DeliveryService;
import com.task.callsign.services.TicketService;
import com.task.callsign.tickets.DeliveryStatusNotChangedFromReceivedFor10Minutes;
import com.task.callsign.tickets.EstimatedTimeOFDeliveryGreaterThanExpectedTime;
import com.task.callsign.tickets.ExpectedTimeOfDeliveryPassedTicketConditionEvaluator;
import com.task.callsign.tickets.TicketConditionEvaluator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryMonitoringScheduledJobTest {

    @Mock
    private DeliveryService deliveryService;
    @Mock
    private TicketService ticketService;
    @Mock
    private ApplicationContext applicationContext;

    private DeliveryMonitoringScheduledJob deliveryMonitoringScheduledJob;

    @Before
    public void setup(){
        setApplicationContextExpectation(applicationContext);
        deliveryMonitoringScheduledJob = new DeliveryMonitoringScheduledJob(
            deliveryService,
            ticketService,
            applicationContext
        );
    }

    @Test
    public void testNoDeliveriesCreateNoTickets() {

        when(deliveryService.getUnDeliveredDeliveryRecordsWithUpdateLock(anyInt())).thenReturn(Collections.emptyList());

        deliveryMonitoringScheduledJob.runJob();

        verify(ticketService, times(0)).createTicket(anyString(), any(TicketPriority.class), anyLong());
    }


    @Test
    public void testSingleDeliveryButNotQualifiedCreateNoTickets() {

        DeliveryRecordDTO deliveryRecordDTO = getDeliveryRecord(LocalDateTime.now().plusSeconds(1000),
            CustomerType.VIP, 100);

        when(deliveryService.getUnDeliveredDeliveryRecordsWithUpdateLock(anyInt()))
            .thenReturn(Collections.singletonList(deliveryRecordDTO));

        deliveryMonitoringScheduledJob.runJob();

        verify(ticketService, times(0)).createTicket(anyString(), any(TicketPriority.class), anyLong());
    }

    @Test
    public void testSingleDeliveryButQualifiedForSingleReasonSingleTicketCreated() {
        DeliveryRecordDTO deliveryRecordDTO = getDeliveryRecord(LocalDateTime.now().plusSeconds(100),
            CustomerType.VIP, 100);

        when(deliveryService.getUnDeliveredDeliveryRecordsWithUpdateLock(anyInt()))
            .thenReturn(Collections.singletonList(deliveryRecordDTO));


        deliveryMonitoringScheduledJob.runJob();

        verify(ticketService, times(1)).createTicket(anyString(), any(TicketPriority.class), anyLong());
    }


    private DeliveryRecordDTO getDeliveryRecord(LocalDateTime expectedDeliveryTime, CustomerType customerType,
                                             int restaurantMeanTime){
        return DeliveryRecordDTO.builder()
            .deliveryId(1L)
            .customerType(customerType)
            .deliveryStatus(DeliveryStatus.RECEIVED)
            .currentDistanceFromDestinationInMetres(100)
            .timeToReachDestinationInSeconds(100)
            .restaurantsMeanTimetoPrepareFood(restaurantMeanTime)
            .expectedDeliveryTime(expectedDeliveryTime)
            .createdAt(LocalDateTime.now())
            .tickets(new ArrayList<>())
            .build();
    }


    private void setApplicationContextExpectation(ApplicationContext applicationContext) {
        Map<String, TicketConditionEvaluator> map = new LinkedHashMap<>();
        map.put(EstimatedTimeOFDeliveryGreaterThanExpectedTime.class.getName(),
            new EstimatedTimeOFDeliveryGreaterThanExpectedTime());
        map.put(DeliveryStatusNotChangedFromReceivedFor10Minutes.class.getName(),
            new DeliveryStatusNotChangedFromReceivedFor10Minutes());
        map.put(ExpectedTimeOfDeliveryPassedTicketConditionEvaluator.class.getName(),
            new ExpectedTimeOfDeliveryPassedTicketConditionEvaluator());

        when(applicationContext.getBeansOfType(TicketConditionEvaluator.class)).thenReturn(map);
    }
}
