package com.booking.booking.service;

import com.booking.booking.domain.*;
import com.booking.booking.repository.*;
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
    ServicesRepository servicesRepository;
    @Autowired
    DiagnostaRepository diagnostaRepository;

    @Autowired
    StacjaDiagnostycznaRepository stacjaDiagnostycznaRepository;

    public List<Services> listAllServices() {
        return servicesRepository.findAll();
    }
    public List<Booking> listAllBookings() {
        return bookingRepository.findAll();}
    public List<Services> getAvailableRooms(LocalDate dateFrom){
        List<Long> ExcludedIds = servicesRepository.getAllRomsBookedBetween(dateFrom);

        if(ExcludedIds.isEmpty()) //jeżeli wszystkie pokoje wolne
            return servicesRepository.findAll(); //wypisz wszystkie
        else
            return servicesRepository.findAllByIdNotIn(ExcludedIds); //wypisz takie, których nie ma w zajętych, czyli wolne
    }

    public Optional<Services> findRoomById(Long id){
        return servicesRepository.findById(id);
    }

    public float CalculateBookingPrice(float ServicePrice){
        return ServicePrice;
    }

    public Booking createBooking(String ClientName, LocalDate dateFrom, Long RoomId){
        Booking b = new Booking();

        Client c = new Client();
        c.setName(ClientName);
        c = clientRepository.save(c);

        Optional<Services> services = servicesRepository.findById(RoomId);

        b.setClient(c);
        b.setDate(dateFrom);
        b.setPrice(CalculateBookingPrice(services.get().Price));
        b.setServices(services.get());

        return bookingRepository.save(b);
    }

    public Optional<Booking> getBookingById(Long Id){
        return bookingRepository.findById(Id);
    }

    @PostConstruct
    public String createData(){
        Services r1 = new Services(0L, "Przegląd", 250);
        Services r2 = new Services(0L, "Regulacja świateł", 120);
        Services r3 = new Services(0L, "Zbieżność", 230);
        Services r4 = new Services(0L, "Złomowanie auta", 400);

        Client c1 = new Client(0L, "Lukasz Szukasz");
        Client c2 = new Client(0L, "Andrzej Bob");

        r1 = servicesRepository.save(r1);
        r2 = servicesRepository.save(r2);
        r3 = servicesRepository.save(r3);
        r4 = servicesRepository.save(r4);

        c1 = clientRepository.save(c1);
        c2 = clientRepository.save(c2);

        Booking b1 = new Booking(0L, LocalDate.parse("2021-06-22"), 700, r1, c1);
        Booking b2 = new Booking(0L, LocalDate.parse("2021-07-01"), 1200, r3, c2);
        StacjaDiagnostyczna s1 = new StacjaDiagnostyczna(0L,"123-123-123","Stacja",true);
        Diagnosta d1 = new Diagnosta(0L,"Marcin","Markiewicz","marmar@gmail.com","$2a$12$bCUwmF1fVWRNMt4sAd0AAOkh1Z1r4obb1.x.yL0nI4iu1/3j3K5Fq",1,"admin",s1);
        bookingRepository.saveAll(Arrays.asList(b1, b2));
        stacjaDiagnostycznaRepository.save(s1);
        diagnostaRepository.save(d1);

        return "index";
    }
}