package com.booking.booking.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class StacjaDiagnostyczna {


    @Id
    @NotNull
    private Long  IdStacji;

    @NotNull
    private String NazwaStacji;

    @NotNull
    private String Kontakt;

    @NotNull
    private boolean Status;

}
