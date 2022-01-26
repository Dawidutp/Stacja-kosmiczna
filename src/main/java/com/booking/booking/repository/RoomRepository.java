package com.booking.booking.repository;

import com.booking.booking.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select b.room.id from Booking b where b.DateFrom between ?1 and ?2 or b.DateTo between ?1 and ?2")
    public List<Long> getAllRomsBookedBetween(LocalDate dateFrom, LocalDate dateTo);

    public List<Room> findAllByIdNotIn(List<Long> ids);
}
