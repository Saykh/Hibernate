package com.dmdev.entity;

import com.dmdev.converter.BirthdayConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@TypeDef(name = "dmdev", typeClass = JsonBinaryType.class)
public class User {

    @Id
    private String username;

    private String firstname;

    private String lastname;

    @Convert(converter = BirthdayConverter.class)
    @Column(name = "birth_date")
    private Birthday birthDate;


    @Enumerated(EnumType.STRING)
    private Role role;


    @Type(type = "dmdev")
    private String info;


}
