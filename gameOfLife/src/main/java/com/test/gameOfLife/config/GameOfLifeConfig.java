package com.test.gameOfLife.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GameOfLifeWebConfig.class)
@ComponentScan({"com.test.gameOfLife.controller", 
	"com.test.gameOfLife.service"})
public class GameOfLifeConfig {

}
