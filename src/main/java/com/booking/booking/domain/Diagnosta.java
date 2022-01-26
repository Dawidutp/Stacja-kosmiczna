package com.booking.booking.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Diagnosta {
    @Id
    @NotNull
    private Long IdDiagnosty;

    @NotNull
    private String Imie;

    @NotNull
    private String Nazwisko;

    @NotNull
    private String Email;

    @NotNull
    private String Password;

    @NotNull
    private Integer enabled;
    @NotNull
    private String Role;

    @NotNull
    @ManyToOne
    private StacjaDiagnostyczna Stacja;
}
