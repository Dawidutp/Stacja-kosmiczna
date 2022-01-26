package com.booking.booking.service;

import com.booking.booking.domain.Booking;
import com.booking.booking.domain.Raport;
import com.booking.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaportService {


    @Autowired
    BookingRepository bookingRepository;


    public List<Raport> getAllRaports(){
        List<Booking> bookings = bookingRepository.findAll();

        return bookings
                .stream()
                .map(booking -> new Raport(booking.getServices().serviceName, booking.getDate()))
                .collect(Collectors.toList());
    }
}
