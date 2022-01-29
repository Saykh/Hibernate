package com.dmdev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    private String username;

    private String firstname;

    private String lastname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;


}
