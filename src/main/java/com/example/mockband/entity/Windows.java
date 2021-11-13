package com.example.mockband.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = "windows")
public class Windows {
    @Id
    @Column(name = "auth")
    private int auth;

    @Column(name = "value")
    private String value;
}
