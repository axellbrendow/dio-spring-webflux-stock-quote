package br.com.axellbrendow.diostockquoteswebfluxapi;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class WebConfig {
    @Bean
    public RouterFunction<ServerResponse> routeQuotes(QuoteHandler quoteHandler) {
        return route(GET("/quotes"), quoteHandler::getAll);
    }
}
