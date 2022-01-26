package com.booking.booking.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Raport {
    private String serviceName;
    private LocalDate date;


}
