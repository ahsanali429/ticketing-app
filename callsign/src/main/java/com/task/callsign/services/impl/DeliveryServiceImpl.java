package com.task.callsign.services.impl;

import com.task.callsign.convertors.DeliveryAndDeliveryRecordMapper;
import com.task.callsign.models.dto.DeliveryRecordDTO;
import com.task.callsign.models.entity.Delivery;
import com.task.callsign.models.enums.DeliveryStatus;
import com.task.callsign.repository.DeliveryRepository;
import com.task.callsign.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public List<DeliveryRecordDTO> getUnDeliveredDeliveryRecordsWithUpdateLock(int numOfRecordsToFetch) {
        Set<Delivery> deliveries = deliveryRepository.fetchAllDeliveriesWithStatusNotEqual(
            DeliveryStatus.DELIVERED.name(), numOfRecordsToFetch);
        return deliveries.stream().map(DeliveryAndDeliveryRecordMapper::toDeliveryRecord).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Delivery> getById(long id) {
        return deliveryRepository.findById(id);
    }
}
