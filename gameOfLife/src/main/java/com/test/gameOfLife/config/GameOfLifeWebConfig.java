package com.test.gameOfLife.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

@Configuration
@EnableWebMvc
public class GameOfLifeWebConfig extends WebMvcConfigurerAdapter {
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.mediaType("json", MediaType.APPLICATION_JSON); 
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}
	
	@Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
 
        resolvers.add(jsonViewResolver());
         
        resolver.setViewResolvers(resolvers);
        return resolver;
    }
	
	@Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }
}
