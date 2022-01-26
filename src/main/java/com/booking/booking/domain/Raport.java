package com.booking.booking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Raport {
    private int RoomNumber;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
