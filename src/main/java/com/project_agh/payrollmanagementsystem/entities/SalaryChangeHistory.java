package com.project_agh.payrollmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "historia_zmian_wynagrodzen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryChangeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zmiany_wynagrodzenia")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pracownik")
    private User user;

    @Column(name = "stare_wynagr")
    private BigDecimal oldSalary;

    @Column(name = "nowe_wynagr")
    private BigDecimal newSalary;

    @Column(name = "data")
    private LocalDate date;

    @Column(name = "opis")
    private String description;


}