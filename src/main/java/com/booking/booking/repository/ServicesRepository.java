package com.booking.booking.repository;

import com.booking.booking.domain.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {

    @Query("select b.service.id from Booking b where b.DateFrom between ?1 and ?2 or b.DateTo between ?1 and ?2")
    public List<Long> getAllRomsBookedBetween(LocalDate dateFrom, LocalDate dateTo);

    public List<Services> findAllByIdNotIn(List<Long> ids);
}
