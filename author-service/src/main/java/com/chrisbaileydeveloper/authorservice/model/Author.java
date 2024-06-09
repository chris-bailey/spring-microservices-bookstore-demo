package com.chrisbaileydeveloper.authorservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("t_authors")
public class Author {

    @Id
    private Long id;
    private String name;
    private LocalDate birthDate;
}
