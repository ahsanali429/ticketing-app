package com.task.callsign.repository;

import com.task.callsign.models.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

  @Transactional(propagation = Propagation.MANDATORY)
  @Query(value = "select d.* from delivery d where delivery_status <> ?1 limit ?2", nativeQuery = true)
  Set<Delivery> fetchAllDeliveriesWithStatusNotEqual(String deliveryStatus, int limit);

  @Query(value = "select d.* from delivery d where delivery_status <> ?1 limit ?2", nativeQuery = true)
  Set<Delivery> fetchAllDeliveriesWithStatusNotEqualWithoutLock(String deliveryStatus, int limit);
}
