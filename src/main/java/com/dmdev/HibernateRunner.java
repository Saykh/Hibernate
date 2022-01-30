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

public class HibernateRunner {

    public static void main(String[] args) {


        Configuration configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(User.class);

        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        configuration.addAttributeConverter(BirthdayConverter.class, true);

        configuration.registerTypeOverride(new JsonBinaryType());

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = User.builder()
                    .username("edilova_at@mail.ru")
                    .firstname("Albina")
                    .lastname("Edilova")
                    .birthDate(new Birthday(LocalDate.of(1993,4,8)))
                    .role(Role.ADMIN)
                    .info("""
                            {   
                                "name": "Albina",
                                "nick": "Khedar"
                            }
                            """)
                    .build();


            User userGet = session.get(User.class, 2L);

            session.getTransaction().commit();

        }

    }
}
