package com.test.gameOfLife.service;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import com.test.gameOfLife.dto.CellDto;
import com.test.gameOfLife.dto.GameStateDto;
import com.test.gameOfLife.dto.GameStatus;
import com.test.gameOfLife.dto.InitialGameStateDto;
import com.test.gameOfLife.dto.StartGameResponseDto;
import com.test.gameOfLife.dto.StepsCountDto;
import com.test.gameOfLife.exception.GameProccessException;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameOfLifeServiceTest {
	@InjectMocks
	@Spy
	private GameOfLifeService gameOfLifeService;
	
	@Mock
	private GameRepository gameRepository;
	
	@Mock
	private GameProcessService gameProcessService;
	
	@Captor
	private ArgumentCaptor<GameStateDto> gameCaptor;
	
	@Test(expected=GameProccessException.class)
	public void validateInputStateWrongBoardSizeTest(){
		gameOfLifeService.createGame(new InitialGameStateDto());
	}
	
	@Test(expected=GameProccessException.class)
	public void validateInputStateWrongCellXTest(){
		gameOfLifeService.createGame(createGameWithCellAndSize(1, 2, 0));
	}
	
	@Test(expected=GameProccessException.class)
	public void validateInputStateWrongCellYTest(){
		gameOfLifeService.createGame(createGameWithCellAndSize(1, 0, -1));
	}
	
	@Test
	public void createGameTest(){
		int id = 123;
		List<CellDto> sortedCells = Mockito.mock(List.class);
		int boardSize = 2;
		InitialGameStateDto initialGameStateDto = createGameWithCellAndSize(boardSize, 1, 1);
		Mockito.when(gameProcessService.sortCells(initialGameStateDto.getLiveCells())).thenReturn(sortedCells);
		Mockito.when(gameRepository.saveNewGame(Mockito.any(GameStateDto.class))).thenReturn(id);
		
		StartGameResponseDto response = gameOfLifeService.createGame(initialGameStateDto);
		
		Mockito.verify(gameRepository).saveNewGame(gameCaptor.capture());
		
		Assert.assertThat("Response contains correct Id", response.getGameId(), Is.is(id));
		Assert.assertThat("Stored in repository sorted list", gameCaptor.getValue().getBoardStates().get(0), Is.is(sortedCells));
		Assert.assertThat("Stored in repository correct boadr size", gameCaptor.getValue().getBoardSize(), Is.is(boardSize));
		Assert.assertThat("Stored in repository game in correct status", gameCaptor.getValue().getStatus(), Is.is(GameStatus.ACTIVE));
	}
	
	@Test(expected = GameProccessException.class)
	public void makeStepsWrongCountTest(){
		gameOfLifeService.makeSteps(1, new StepsCountDto());
	}
	
	@Test
	public void makeStepsTest(){
		int gameId = 1;
		int count = 5;
		GameStateDto game = new GameStateDto();
		game.setStatus(GameStatus.ACTIVE);
		
		StepsCountDto stepsCountDto = new StepsCountDto();
		stepsCountDto.setStepsCount(5);
		
		Mockito.doReturn(game).when(gameRepository).getGame(gameId);
		
		gameOfLifeService.makeSteps(gameId, stepsCountDto);
		Mockito.verify(gameProcessService, Mockito.times(count)).makeStepInGame(game);
	}
	
	@Test
	public void makeStepsFinishTest(){
		int gameId = 1;
		GameStateDto game = new GameStateDto();
		game.setStatus(GameStatus.FINISHED);
		
		StepsCountDto stepsCountDto = new StepsCountDto();
		stepsCountDto.setStepsCount(5);
		
		Mockito.doReturn(game).when(gameRepository).getGame(gameId);
		
		gameOfLifeService.makeSteps(gameId, stepsCountDto);
		Mockito.verify(gameProcessService, Mockito.times(0)).makeStepInGame(game);
	}
	
	@Test
	public void getGameTest(){
		int id = 5;
		GameStateDto game = Mockito.mock(GameStateDto.class);
		Mockito.when(gameRepository.getGame(id)).thenReturn(game);
		GameStateDto returnedGame = gameOfLifeService.getGameState(id);
		Assert.assertThat("Correct game returned", returnedGame, Is.is(game));
	}
	
	private InitialGameStateDto createGameWithCellAndSize(int size, int x, int y){
		InitialGameStateDto initialGameState = new InitialGameStateDto();
		initialGameState.setBoardSize(size);
		List<CellDto> cells = new ArrayList<>();
		cells.add(new CellDto(x, y));
		initialGameState.setLiveCells(cells);
		return initialGameState;
	}
}
