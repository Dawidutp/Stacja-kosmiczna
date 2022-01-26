package com.booking.booking.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Services {

    @Id
    @GeneratedValue
    private Long id;

    public String serviceName;
    public float Price;
}
