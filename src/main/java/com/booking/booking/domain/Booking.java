package com.booking.booking.domain;

import lombok.*;

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

    private LocalDate Date;
    private float Price;

    @ManyToOne
    private Services services;

    @ManyToOne
    private Client client;

}
