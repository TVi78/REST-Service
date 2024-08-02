package ru.home.webservice1.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

/**
 * Таблица Coordinator
 **/
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"coordinator_id", "coordinator_name"})
public class Coordinator {
    /**
     * Поле идентификатор координатора
     **/
    private int coordinator_id;
    /**
     * Поле имя координатора
     **/
    @NonNull
    private String coordinator_name;
}
