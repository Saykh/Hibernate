package com.dmdev.util;

import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.Company;
import com.dmdev.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.configure();

        configuration.addAnnotatedClass(User.class);

        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        configuration.addAttributeConverter(BirthdayConverter.class, true);

        configuration.registerTypeOverride(new JsonBinaryType());

        configuration.addAnnotatedClass(Company.class);

        return  configuration.buildSessionFactory();
    }
}


