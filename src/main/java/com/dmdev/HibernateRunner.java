package com.dmdev;


import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {


        Configuration configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(User.class);

        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = User.builder()
                    .username("edilov_st@mail.ru")
                    .firstname("Saykhan")
                    .lastname("Edilov")
                    .birthDate(LocalDate.of(1994,7,23))
                    .age(27)
                    .role(Role.ADMIN)
                    .build();


            session.save(user);

            session.getTransaction().commit();

        }

    }
}
