package ru.home.webservice1.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Таблица связи между студентами и курсами
 */
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {
    /**
     * Идентификатор студента
     */
    private int student_id;
    /**
     * Идентификатор курса
     */
    private int course_id;
}
