package com.rungroup.web.dto;

import com.rungroup.web.models.Club;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    @NotEmpty(message = "Name of cannot be empty")
    private String name;
    @NotNull(message = "Start Time cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") //to tell Model in what format to expect date and time data
    private LocalDateTime startTime;
    @NotNull(message = "End Time cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;
    private String type;
    @NotEmpty(message = "PhotoURL cannot be empty")
    private String photoUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Club club;
}
