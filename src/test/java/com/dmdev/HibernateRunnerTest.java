package com.dmdev;

import com.dmdev.entity.Birthday;
import com.dmdev.entity.User;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
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
                .firstname("Saykhan")
                .lastname("Edilov")
                .birthDate(new Birthday(LocalDate.of(1994,7,23)))
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

}