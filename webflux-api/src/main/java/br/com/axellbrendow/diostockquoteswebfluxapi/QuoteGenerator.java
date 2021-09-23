package br.com.axellbrendow.diostockquoteswebfluxapi;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class QuoteGenerator implements ApplicationRunner {
    @Autowired
    private QuoteRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting data insertion");

        var quote = Quote.builder()
            .openValue(0.2)
            .closeValue(0.2)
            .symbol("TESTE")
            .timestamp(LocalDateTime.now())
            .build();

        repository.save(quote)
            .subscribe(
                f -> repository.findAll()
                    .subscribe(
                        q -> log.info("Quote {}", q),
                        e -> log.error("findAll Error: {}", e.getMessage())
                    ),
                e -> log.error("save Error: {}", e.getMessage())
            );
    }
}
