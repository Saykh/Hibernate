package com.dmdev;


import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.Birthday;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {



        User user = User.builder()
                .username("musaev_mv@mail.ru")
                .firstname("Mansur")
                .lastname("Musaev")
                .birthDate(new Birthday(LocalDate.of(1993,8,8)))
                .role(Role.ADMIN)
                .info("""
                            {
                                "name": "Mansur",
                                "nick": "Mans"
                            }
                            """)
                .build();


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            try(Session session1 = sessionFactory.openSession()) {

                session1.beginTransaction();

                session1.saveOrUpdate(user);



                session1.getTransaction().commit();
            }


            try(Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                user.setFirstname("Ramzan");
                session2.merge(user);


                session2.getTransaction().commit();
            }


        }

    }
}
