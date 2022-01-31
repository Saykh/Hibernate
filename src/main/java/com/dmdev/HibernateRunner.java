package com.dmdev;


import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.*;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;


import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.Birthday;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.util.HibernateUtil;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {

        Company company = Company.builder()
                .name("Google")
                .build();


        User user = User.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname("Abdul")
                        .lastname("Edilov")
                        .birthDate(new Birthday(LocalDate.of(1960, 2, 23)))
                        .build())
                .username("edilov_as@mail.ru")
                .role(Role.ADMIN)
                .info("""
                        {
                            "name": "Abdul",
                            "nick": "Tura"
                        }
                        """)
                .company(company)
                .build();


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();

            try (session1) {

                Transaction transaction = session1.beginTransaction();


                session1.save(company);
                session1.save(user);

                session1.getTransaction().commit();
            }

        }
    }
}
