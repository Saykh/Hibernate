package com.dmdev.entity;

import com.dmdev.converter.BirthdayConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
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
@Access(AccessType.FIELD)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;


    @Type(type = "dmdev")
    private String info;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
