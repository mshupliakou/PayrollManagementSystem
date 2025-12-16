package com.project_agh.payrollmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "status_wyplaty")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatus {
    @Id
    @Column(name = "id_status_wyplaty", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nazwa", unique = true)
    String name;

    @Column(name = "opis")
    String description;


}
