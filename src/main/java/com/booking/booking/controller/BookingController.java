package com.booking.booking.controller;

import com.booking.booking.domain.Booking;
import com.booking.booking.domain.Services;
import com.booking.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class BookingController {

    @Autowired
    BookingService bookingService;


    @GetMapping("/")
    public String home(
            @RequestParam(value = "datefrom", required = false) String DateFromString,
            @RequestParam(value = "dateto", required = false) String DateToString,
            Model model
    ){

        if(DateFromString != null && DateToString != null) {
            LocalDate DateFrom = LocalDate.parse(DateFromString);
            LocalDate DateTo = LocalDate.parse(DateToString);

            model.addAttribute("datefrom", DateFrom.toString());
            model.addAttribute("dateto", DateTo.toString());

            List<Services> rooms = bookingService.getAvailableRooms(DateFrom, DateTo);

            model.addAttribute("rooms", rooms);
        }

        return "index";
    }


    @GetMapping("/book-room/{id}")
    public String bookRoom(
            @PathVariable Long id,
            @RequestParam(value = "datefrom", required = false) String DateFromString,
            @RequestParam(value = "dateto", required = false) String DateToString,
            Model model
    ){

        model.addAttribute("datefrom", DateFromString);
        model.addAttribute("dateto", DateToString);
        model.addAttribute("roomid", id);


        Optional<Services> room = bookingService.findRoomById(id);

        if(room.isPresent()){
            float bookingPrice = bookingService.CalculateBookingPrice(
                    room.get().PricePerDay,
                    LocalDate.parse(DateFromString),
                    LocalDate.parse(DateToString)
            );
            model.addAttribute("bookingprice", bookingPrice);
            model.addAttribute("roomnumber", room.get().serviceName);

            return "booking";
        }


        return "redirect:/";
    }

    @PostMapping("/book-confirm")
    public String bookConfirm(
            @RequestParam("client_name") String ClientName,
            @RequestParam("datefrom") String DateFromString,
            @RequestParam("dateto") String DateToString,
            @RequestParam("roomid") Long RoomId
    ) {
        LocalDate DateFrom = LocalDate.parse(DateFromString);
        LocalDate DateTo = LocalDate.parse(DateToString);

        Booking booking = bookingService.createBooking(ClientName, DateFrom, DateTo, RoomId);

        return "redirect:/book-confirm/"+booking.getId();
    }


    @GetMapping("/book-confirm/{id}")
    public String bookConfirmPage(@PathVariable Long id, Model model){
        Optional<Booking> booking = bookingService.getBookingById(id);
        if(booking.isPresent()){
            model.addAttribute("booking", booking.get());
            return "book-confirm";
        }
        return "redirect:/";
    }


}
