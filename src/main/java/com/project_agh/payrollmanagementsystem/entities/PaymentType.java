package com.project_agh.payrollmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typ_wyplaty")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentType {
    @Id
    @Column(name = "id_typ_wyplaty", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nazwa", unique = true)
    String name;

    @Column(name = "opis")
    String description;


}
