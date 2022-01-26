package com.booking.booking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate DateFrom;
    private LocalDate DateTo;
    private float Price;

    @ManyToOne
    private Services service;

    @ManyToOne
    private Client client;

}
