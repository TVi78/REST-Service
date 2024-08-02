package ru.home.webservice1.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

/**
 * Таблица Course
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "course_name"})
public class Course {
    /**
     * Идентификатор курса
     */
    private int id;
    /**
     * Название курса
     */
    @NonNull
    private String course_name;
}
