package com.project_agh.payrollmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typ_pracy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkType {
    @Id
    @Column(name = "id_typ_pracy", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nazwa", unique = true)
    private String name;

    @Column(name = "opis")
    private String description;
}
