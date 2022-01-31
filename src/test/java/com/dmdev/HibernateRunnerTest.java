package com.dmdev;

import com.dmdev.entity.Birthday;
import com.dmdev.entity.Company;
import com.dmdev.entity.PersonalInfo;
import com.dmdev.entity.User;
import com.dmdev.util.HibernateUtil;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {


    @Test
    public void checkReflectionAPI() {

        User user = User.builder()
                .username("edilov_st@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Saykhan")
                        .lastname("Edilov")
                        .birthDate(new Birthday(LocalDate.of(1994,7,23)))
                        .build())
                .build();

        String SQL = """
                INSERT
                INTO
                %s
                (%s)
                VALUES
                (%s)
                """;


        String tableName =  Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());


        Field[] getDeclaredFields = user.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(getDeclaredFields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::name)
                .orElse(field.getName()))
                .collect(Collectors.joining(", "));


        String columnValues = Arrays.stream(getDeclaredFields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));


        System.out.println(SQL.formatted(tableName, columnNames, columnValues));

    }

    @Test
    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.getString("username");
        resultSet.getString("firstname");
        resultSet.getString("lastname");

        Class<User> clazz = User.class;
        Constructor<User> constructor = clazz.getConstructor();
        User user = constructor.newInstance();
        Field userNameField = clazz.getDeclaredField("username");
        userNameField.setAccessible(true);
        userNameField.set(user, resultSet.getString("username"));
    }


    @Test
    void oneToMany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 2);
                System.out.println(company.getUsers());

        session.getTransaction().commit();

    }



    @Test
    void addUserAtCompany() {

        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = Company.builder()
                .name("Facebook")
                .build();

        User user = User.builder().
                username("golos@mail.com")
                .build();

       // user.setCompany(company);
       // company.getUsers().add(user);

        company.addUser(user);

        session.save(company);


        System.out.println();

        session.getTransaction().commit();


    }



    @Test
    void deleteUser() {

        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.get(Company.class,2);
        session.delete(company);


        System.out.println();

        session.getTransaction().commit();

    }

    @Test
    void testLazyInitializationException() {
        Company company = null;
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            company = session.getReference(Company.class, 1);

            session.getTransaction().commit();
            var users = company.getUsers();
            System.out.println(users.size());

        }

    }
    
    @Test
    void testOrphanRemoval() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.get(Company.class, 2);

            company.getUsers().removeIf(user -> user.getId().equals(2L));

            session.getTransaction().commit();


        }
        
        
    }





}