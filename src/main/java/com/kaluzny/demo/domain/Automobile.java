package com.kaluzny.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate = LocalDateTime.now();

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateDate = LocalDateTime.now();

    @Column(name = "original_color")
    private Boolean originalColor = Boolean.TRUE;

    private Boolean deleted = Boolean.FALSE;

    public void checkColor(Automobile automobile) {
        if (automobile.color != null && !automobile.color.equals(this.color)) {
            this.originalColor = false;
        }
    }
}
