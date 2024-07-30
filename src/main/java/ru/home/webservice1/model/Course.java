package ru.home.webservice1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Course {
    @JsonIgnore
    private int id;
    @NonNull
    private String course_name;
}
