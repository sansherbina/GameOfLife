package com.test.gameOfLife.service;

import org.mockito.Spy;

import com.test.gameOfLife.dto.InitialGameStateDto;
import com.test.gameOfLife.exception.GameProccessException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameOfLifeServiceTest {
	@Spy
	private GameOfLifeService gameOfLifeService;
	
	@Test(expected=GameProccessException.class)
	public void createGameTest(){
		gameOfLifeService.createGame(new InitialGameStateDto());
	}
}
