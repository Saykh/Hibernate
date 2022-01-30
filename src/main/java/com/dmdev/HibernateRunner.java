package com.dmdev;


import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.Birthday;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
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

public class HibernateRunner {

    private static final Logger logger = LoggerFactory.getLogger(HibernateRunner.class);

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
        logger.info("User entity is in transient static, object: {}", user);


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();

            try(session1) {

                Transaction transaction = session1.beginTransaction();
                logger.trace("Transaction is created, {}", transaction);


                session1.saveOrUpdate(user);
                logger.trace("User is in persistence state: {}, session {}", user, session1);

                session1.getTransaction().commit();
            }
                logger.warn("User is in detached state: {}, session is closed {}", user, session1);

        } catch (Exception exception) {
            logger.error("Exception occurred", exception);
            throw exception;
        }

    }
}
