package com.task.callsign.convertors;

import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.dto.TicketRecordDTO;
import com.task.callsign.models.entity.Delivery;
import com.task.callsign.models.entity.Ticket;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class responsible for conversion between Delivery Data Object and Delivery Business Object
 */
public class DeliveryAndDeliveryRecordMapper {
  /**
   * Converts the given data layer representation of a Delivery to the business layer representation.
   *
   * @param delivery entity to convert
   * @return {@link DeliveryRecordDTO} business layer representation of a Delivery
   */
  public static DeliveryRecordDTO toDeliveryRecord(Delivery delivery) {
    return DeliveryRecordDTO
        .builder()
        .deliveryId(delivery.getDeliveryId())
        .customerType(delivery.getCustomerType())
        .deliveryStatus(delivery.getDeliveryStatus())
        .currentDistanceFromDestinationInMetres(delivery.getCurrentDistanceFromDestinationInMetres())
        .expectedDeliveryTime(delivery.getExpectedDeliveryTime())
        .restaurantsMeanTimetoPrepareFood(delivery.getRestaurant().getMeanTimeToPrepareFoodInSeconds())
        .timeToReachDestinationInSeconds(delivery.getTimeToReachDestinationInSeconds())
        .tickets(getTicketRecords(delivery.getTickets()))
    .build();
  }

  private static List<TicketRecordDTO> getTicketRecords(List<Ticket> tickets) {
    return tickets
        .stream()
        .map(ticket -> new TicketRecordDTO(ticket.getTicketId(), ticket.getReasonType(), ticket.getTicketPriority())).
        collect(Collectors.toList());
  }
}
