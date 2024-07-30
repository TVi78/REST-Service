package ru.home.webservice1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Coordinator {
    @JsonIgnore
    private int coordinator_id;
    @NonNull
    private String coordinator_name;
}
