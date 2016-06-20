package com.test.gameOfLife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.gameOfLife.dto.GameStateDto;
import com.test.gameOfLife.dto.InitialGameStateDto;
import com.test.gameOfLife.dto.StartGameResponseDto;
import com.test.gameOfLife.dto.StepsCountDto;
import com.test.gameOfLife.service.GameOfLifeService;

@Controller
@RequestMapping(value = "/game")
public class GameOfLifeController {
	@Autowired
	private GameOfLifeService gameOfLifeService;
	
	@RequestMapping(value="/", method = RequestMethod.PUT)
	public StartGameResponseDto startGame(@RequestBody InitialGameStateDto initialGameStateDto) {
		return gameOfLifeService.createGame(initialGameStateDto);
	}
	
	@RequestMapping(value="/{gameId}", method = RequestMethod.GET)
	public GameStateDto getGameState(@PathVariable int gameId){
		return gameOfLifeService.getGameState(gameId);
	}
	
	@RequestMapping(value="/{gameId}", method = RequestMethod.POST)
	public GameStateDto makeSteps(@PathVariable int gameId, @RequestBody StepsCountDto stepsCount){
		return gameOfLifeService.makeSteps(gameId, stepsCount);
	}
}
