package com.browarna.railwaycarsserving.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TimeNorm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long normId;
    @Column(nullable = false)
    private Date relevanceDate;
    @Column(nullable = false)
    private Double norm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private TimeNormType normType;
}
