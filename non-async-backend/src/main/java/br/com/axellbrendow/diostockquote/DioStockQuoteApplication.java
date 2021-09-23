package br.com.axellbrendow.diostockquote;

import java.util.Date;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableScheduling
@SpringBootApplication
public class DioStockQuoteApplication {

	@Autowired
	private QuoteRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DioStockQuoteApplication.class, args);
	}

	@Scheduled(fixedDelay = 1000)
	public void generateData() {
		log.info("Generating quote x");
		log.info(
			repository
				.findFirstBySymbolOrderByTimestampDesc("TESTE")
				.map(this::generateNewData)
				.orElseGet(this::initializeData)
		);
	}

	private Quote generateNewData(Quote quote) {
		Quote quoteToSave = Quote.builder()
			.symbol(quote.getSymbol())
			.openValue(quote.getOpenValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
			.closeValue(quote.getCloseValue() + new RandomDataGenerator().nextUniform(-0.1, 0.1))
			.timestamp(quote.getTimestamp())
			.build();
		return repository.save(quoteToSave);
	}

	private Quote initializeData() {
		Quote quoteToSave = Quote.builder()
			.symbol("TESTE")
			.openValue(0.2222222)
			.closeValue(0.2222222)
			.timestamp(new Date())
			.build();
		return repository.save(quoteToSave);
	}
}
