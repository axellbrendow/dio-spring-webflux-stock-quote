package br.com.axellbrendow.diostockquoteswebfluxapi;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import javax.annotation.PostConstruct;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class QuoteGenerator {
    private final QuoteRepository repository;

    @PostConstruct
    public void init() {
        // Called for each incoming Subscriber to provide the initial state
        final Callable<Quote> initialStateGenerator = () -> {
            log.info("Starting data insertion");
            return initialQuote();
        };

        final BiFunction<Quote, SynchronousSink<Object>, Quote> generator = (state, sink) -> {
            sleep(1000);
            sink.next(state); // Produces synchronously "one signal" to an underlying Subscriber.
            // sink.complete() can be used to finish the Flux for the current Subscriber.
            return createNewQuote(state);
        };

        Flux.generate(initialStateGenerator, generator)
                .delaySubscription(Duration.ofMillis(3000))
                .subscribe();
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Quote createNewQuote(Quote previousQuote) {
        var newQuote = Quote.builder()
                .symbol(previousQuote.getSymbol())
                .openValue(previousQuote.getOpenValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
                .closeValue(previousQuote.getCloseValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
                .timestamp(LocalDateTime.now())
                .build();
        repository.save(newQuote).subscribe(log::info);
        return newQuote;
    }

    private Quote initialQuote() {
        var quote = Quote.builder()
                .openValue(0.2)
                .closeValue(0.2)
                .symbol("TESTE")
                .timestamp(LocalDateTime.now())
                .build();
        repository.save(quote).subscribe(log::info);
        return quote;
    }
}
