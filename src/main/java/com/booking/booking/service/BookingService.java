package com.booking.booking.service;

import com.booking.booking.domain.Booking;
import com.booking.booking.domain.Client;
import com.booking.booking.domain.Room;
import com.booking.booking.repository.BookingRepository;
import com.booking.booking.repository.ClientRepository;
import com.booking.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    RoomRepository roomRepository;


    public List<Room> getAvailableRooms(LocalDate dateFrom, LocalDate dateTo){
        List<Long> ExcludedIds = roomRepository.getAllRomsBookedBetween(dateFrom, dateTo);

        if(ExcludedIds.isEmpty()) //jeżeli wszystkie pokoje wolne
            return roomRepository.findAll(); //wypisz wszystkie
        else
            return roomRepository.findAllByIdNotIn(ExcludedIds); //wypisz takie, których nie ma w zajętych, czyli wolne
    }

    public Optional<Room> findRoomById(Long id){
        return roomRepository.findById(id);
    }

    public float CalculateBookingPrice(float RoomPrice, LocalDate dateFrom, LocalDate dateTo){
        long amountOfDays = dateFrom.until(dateTo, ChronoUnit.DAYS);

        return amountOfDays * RoomPrice;
    }

    public Booking createBooking(String ClientName, LocalDate dateFrom, LocalDate dateTo, Long RoomId){
        Booking b = new Booking();

        Client c = new Client();
        c.setName(ClientName);
        c = clientRepository.save(c);

        Optional<Room> room = roomRepository.findById(RoomId);

        b.setClient(c);
        b.setDateFrom(dateFrom);
        b.setDateTo(dateTo);
        b.setPrice(CalculateBookingPrice(room.get().PricePerDay, dateFrom, dateTo));
        b.setRoom(room.get());

        return bookingRepository.save(b);
    }

    public Optional<Booking> getBookingById(Long Id){
        return bookingRepository.findById(Id);
    }

    @PostConstruct
    public String createData(){
        Room r1 = new Room(0L, 101, 250);
        Room r2 = new Room(0L, 102, 120);
        Room r3 = new Room(0L, 113, 230);
        Room r4 = new Room(0L, 215, 400);

        Client c1 = new Client(0L, "Lukasz Szukasz");
        Client c2 = new Client(0L, "Andrzej Bob");

        r1 = roomRepository.save(r1);
        r2 = roomRepository.save(r2);
        r3 = roomRepository.save(r3);
        r4 = roomRepository.save(r4);

        c1 = clientRepository.save(c1);
        c2 = clientRepository.save(c2);

        Booking b1 = new Booking(0L, LocalDate.parse("2021-06-22"), LocalDate.parse("2021-06-30"), 700, r1, c1);
        Booking b2 = new Booking(0L, LocalDate.parse("2021-07-01"), LocalDate.parse("2021-07-12"), 1200, r3, c2);

        bookingRepository.saveAll(Arrays.asList(b1, b2));

        return "index";
    }
}