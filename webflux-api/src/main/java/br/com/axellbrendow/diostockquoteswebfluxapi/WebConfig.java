package br.com.axellbrendow.diostockquoteswebfluxapi;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .maxAge(3600);
    }

    @Bean
    public RouterFunction<ServerResponse> routeQuotes(QuoteHandler quoteHandler) {
        return route(GET("/quotes"), quoteHandler::getAll)
            .andRoute(GET("/quotes/last"), quoteHandler::getLastQuote);
    }
}
