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
            @RequestParam(value = "date", required = false) String DateString,
            Model model
    ){

        if(DateString != null ) {
            LocalDate DateFrom = LocalDate.parse(DateString);

            model.addAttribute("date", DateFrom.toString());

            //List<Services> rooms = bookingService.listAllServices();

            model.addAttribute("rooms", bookingService.listAllServices());
        }

        return "index";
    }
    @GetMapping("/reservations")
    public String home2(Model model) {

        model.addAttribute("reservations", bookingService.listAllBookings());

        return"reservations";
    }

    @GetMapping("/book-room/{id}")
    public String bookRoom(
            @PathVariable Long id,
            @RequestParam(value = "date", required = false) String DateString,
            Model model
    ){

        model.addAttribute("date", DateString);
        model.addAttribute("serviceid", id);


        Optional<Services> room = bookingService.findRoomById(id);

        if(room.isPresent()){
            float bookingPrice = bookingService.CalculateBookingPrice(room.get().Price);
            model.addAttribute("bookingprice", bookingPrice);
            model.addAttribute("roomnumber", room.get().serviceName);

            return "booking";
        }


        return "redirect:/";
    }

    @GetMapping("/book-confirm")
    public String bookConfirm(
            @RequestParam("client_name") String ClientName,
            @RequestParam("date") String DateFromString,
            @RequestParam("serviceid") Long RoomId
    ) {
        LocalDate Date = LocalDate.parse(DateFromString);

        Booking booking = bookingService.createBooking(ClientName, Date, RoomId);

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
