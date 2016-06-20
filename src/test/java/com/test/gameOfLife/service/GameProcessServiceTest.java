package com.test.gameOfLife.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;

import org.mockito.runners.MockitoJUnitRunner;

import com.test.gameOfLife.dto.CellDto;
import com.test.gameOfLife.dto.GameStateDto;

@RunWith(MockitoJUnitRunner.class)
public class GameProcessServiceTest {
	@Spy
	private GameProcessService gameProcessService;
	
	@Test
	public void sortCellsTest(){
		List<CellDto> cells = new ArrayList<>();
		cells.add(new CellDto(3, 3));
		cells.add(new CellDto(1, 3));
		cells.add(new CellDto(4, 1));
		cells.add(new CellDto(2, 1));
		
		cells = gameProcessService.sortCells(cells);
		
		Assert.assertThat("Correct order", cells.get(0), Is.is(new CellDto(2, 1)));
		Assert.assertThat("Correct order", cells.get(1), Is.is(new CellDto(4, 1)));
		Assert.assertThat("Correct order", cells.get(2), Is.is(new CellDto(1, 3)));
		Assert.assertThat("Correct order", cells.get(3), Is.is(new CellDto(3, 3)));
	}
	
	@Test
	public void makeStepInGameTest(){
		GameStateDto game = new GameStateDto();
		int boardSize = 3;
		game.setBoardSize(boardSize);
		List<List<CellDto>> states = new ArrayList<>();
		List<CellDto> cells = new ArrayList<>();
		states.add(cells);
		game.setBoardStates(states);
		
		boolean[][] board = new boolean[boardSize][boardSize];
		
		Mockito.doReturn(board).when(gameProcessService).buildBoard(cells, boardSize);
		
		Mockito.doReturn(true).when(gameProcessService).isCellWillLive(1, 1, board);
				
		gameProcessService.makeStepInGame(game);
		
		Assert.assertThat("Correct board state", game.getBoardStates().get(1).get(0), Is.is(new CellDto(1, 1)));
		Assert.assertThat("Correct board state", game.getBoardStates().get(1).size(), Is.is(1));
	}
	
	@Test
	public void isCellWillLiveTest(){
		boolean[][] board = new boolean[2][2];
		board[0][0] = true;
		board[0][1] = true;
		
		Mockito.doReturn(2).when(gameProcessService).calculateLiveNeighboad(0, 0, board);
		Mockito.doReturn(3).when(gameProcessService).calculateLiveNeighboad(1, 1, board);
		Mockito.doReturn(4).when(gameProcessService).calculateLiveNeighboad(0, 1, board);
		
		boolean isLive = gameProcessService.isCellWillLive(0, 0, board);
		
		Assert.assertThat("Cell must be live", isLive, Is.is(true));
		
		isLive = gameProcessService.isCellWillLive(1, 1, board);
		
		Assert.assertThat("Cell must be live", isLive, Is.is(true));
		
		isLive = gameProcessService.isCellWillLive(0, 1, board);
		
		Assert.assertThat("Cell must be live", isLive, Is.is(false));
	}
	
	@Test
	public void calculateLiveNeighboadTest(){
		boolean[][] board = new boolean[3][3];
		Arrays.fill(board[0], true);
		Arrays.fill(board[1], true);
		Arrays.fill(board[2], true);
		
		Assert.assertThat(gameProcessService.calculateLiveNeighboad(0, 0, board), Is.is(3));
		Assert.assertThat(gameProcessService.calculateLiveNeighboad(0, 1, board), Is.is(5));
		Assert.assertThat(gameProcessService.calculateLiveNeighboad(0, 2, board), Is.is(3));
		Assert.assertThat(gameProcessService.calculateLiveNeighboad(1, 0, board), Is.is(5));
		Assert.assertThat(gameProcessService.calculateLiveNeighboad(1, 1, board), Is.is(8));
		Assert.assertThat(gameProcessService.calculateLiveNeighboad(1, 2, board), Is.is(5));
		Assert.assertThat(gameProcessService.calculateLiveNeighboad(2, 0, board), Is.is(3));
		Assert.assertThat(gameProcessService.calculateLiveNeighboad(2, 1, board), Is.is(5));
		Assert.assertThat(gameProcessService.calculateLiveNeighboad(2, 2, board), Is.is(3));
	}

	@Test
	public void buildBoardTest(){
		List<CellDto> cells = new ArrayList<>();
		cells.add(new CellDto(0, 0));
		cells.add(new CellDto(0, 1));
		boolean[][] board = gameProcessService.buildBoard(cells, 2);
		Assert.assertThat("Cell is live", board[0][0], Is.is(true));
		Assert.assertThat("Cell is live", board[1][0], Is.is(true));
		Assert.assertThat("Cell is live", board[0][1], Is.is(false));
		Assert.assertThat("Cell is live", board[1][1], Is.is(false));
	}
}
