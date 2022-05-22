package com.task.callsign.services;

import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.entity.Delivery;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DeliveryService {
    /**
     * Fetches N incomplete Delivery records and obtains update lock over each.
     * This method should always be called within a {@link org.hibernate.Transaction} context or else will error out.
     *
     * @param numOfRecordsToFetch number of Delivery records to fetch
     * @return {@link Set <DeliveryRecordDTO>} Delivery records qualifying criteria
     */
    List<DeliveryRecordDTO> getUnDeliveredDeliveryRecordsWithUpdateLock(int numOfRecordsToFetch);

    /**
     * Fetches Delivery record for the given internal id.
     *
     * @param id identifier of the delivery record
     * @return {@link Optional <Delivery>}
     */
    Optional<Delivery> getById(long id);
}
