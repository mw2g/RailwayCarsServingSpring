package com.browarna.railwaycarsserving.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Wagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wagonId;
    @Size(min = 8, max = 8, message = "Wagon number length must be 8 characters.")
    @Column(nullable = false, unique = true)
    private String wagonNumber;
}
