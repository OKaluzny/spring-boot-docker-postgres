package com.kaluzny.demo.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(name = "Automobile", description = "Data object for an automobile", oneOf = Automobile.class)
public class Automobile {

    @Schema(description = "Unique identifier of the Automobile.", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Schema(description = "Name of the Automobile.", example = "Volvo", required = true)
    @Size(max = 50)
    private String name;

    @Schema(description = "Color of the Automobile.", example = "Red", required = true)
    @Size(max = 50)
    private String color;

    @Column(name = "original_color")
    private Boolean originalColor = Boolean.TRUE;

    public void checkColor(Automobile automobile) {
        if (automobile.color != null && !automobile.color.equals(this.color)) {
            this.originalColor = false;
        }
    }
}
