package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
public class VehicleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String model;
    
    @Column(nullable = false)
    private String brand;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private Boolean sold = false;
    
    @CreationTimestamp
    @Column(name = "created", updatable = false)
    private LocalDateTime created;
    
    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;
}