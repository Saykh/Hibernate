package com.dmdev;


import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.Birthday;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
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

        User user = User.builder()
                .username("isaeva_au@mail.ru")
                .firstname("Aska")
                .lastname("Isaeva")
                .birthDate(new Birthday(LocalDate.of(1963,11,8)))
                .role(Role.ADMIN)
                .info("""
                            {
                                "name": "Aska",
                                "nick": "Tiger"
                            }
                            """)
                .build();
        log.info("User entity is in transient static, object: {}", user);


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();

            try(session1) {

                Transaction transaction = session1.beginTransaction();
                log.trace("Transaction is created, {}", transaction);


                session1.saveOrUpdate(user);
                log.trace("User is in persistence state: {}, session {}", user, session1);

                session1.getTransaction().commit();
            }
            log.warn("User is in detached state: {}, session is closed {}", user, session1);

        } catch (Exception exception) {
            log.error("Exception occurred", exception);
            throw exception;
        }

    }
}
