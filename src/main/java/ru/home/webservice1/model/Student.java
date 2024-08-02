package ru.home.webservice1.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;


/**
 * Cоздание таблицы Student
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@JsonPropertyOrder({"id", "name", "coordinator_id"})
public class Student {
    /**
     * Идентификатор студента
     */
    private int id;
    /**
     * Имя студента
     */
    @NonNull
    private String name;

    /**
     * Идентификатор координатора
     */
    @NonNull
    private int coordinator_id;

}
