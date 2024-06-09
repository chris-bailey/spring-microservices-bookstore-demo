package com.chrisbaileydeveloper.authorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
    private String id;
    private String name;
    private LocalDate birthDate;
}
