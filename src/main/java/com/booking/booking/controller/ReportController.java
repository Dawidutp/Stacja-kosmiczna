package com.booking.booking.controller;

import com.booking.booking.domain.Raport;
import com.booking.booking.service.RaportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ReportController {

    @Autowired
    RaportService raportService;

    @GetMapping("/raport")
    public List<Raport> getRaports() {
        return raportService.getAllRaports();
    }
}
