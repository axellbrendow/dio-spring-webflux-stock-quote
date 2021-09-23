package br.com.axellbrendow.diostockquoteswebfluxapi;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("quotes")
public class Quote {
    @Id
    private Long id;
    private String symbol;
    private Double openValue;
    private Double closeValue;
    private LocalDateTime timestamp;
}
