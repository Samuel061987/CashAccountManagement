package com.springboot.cashmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private String type; // "CREDIT" or "DEBIT"

    @ManyToOne
    @JsonBackReference
    private Account account;
    @CreationTimestamp
    private LocalDateTime creationDate;

}

