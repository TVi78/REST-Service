package ru.home.webservice1.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@JsonPropertyOrder({"id", "name", "coordinator_id"})
public class Student {
    @JsonIgnore
    private int id;
    @NonNull
    private String name;
    @NonNull
    private int coordinator_id;

}
