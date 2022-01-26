package com.booking.booking.repository;

import com.booking.booking.domain.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {

    @Query("select b.services.id from Booking b where b.Date = ?1 ")
    public List<Long> getAllRomsBookedBetween(LocalDate dateFrom);

    public List<Services> findAllByIdNotIn(List<Long> ids);
}
