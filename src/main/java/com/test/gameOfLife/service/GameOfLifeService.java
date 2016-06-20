package com.test.gameOfLife.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.gameOfLife.dto.CellDto;
import com.test.gameOfLife.dto.GameStateDto;
import com.test.gameOfLife.dto.GameStatus;
import com.test.gameOfLife.dto.InitialGameStateDto;
import com.test.gameOfLife.dto.StartGameResponseDto;
import com.test.gameOfLife.dto.StepsCountDto;
import com.test.gameOfLife.exception.GameProccessException;

@Service
public class GameOfLifeService {
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private GameProcessService gameProcessService;
	
	public StartGameResponseDto createGame(
			InitialGameStateDto initialGameStateDto) {
		validateInputState(initialGameStateDto);
		
		GameStateDto gameState = new GameStateDto();
		gameState.setBoardSize(initialGameStateDto.getBoardSize());
		List<List<CellDto>> boardsStates = new ArrayList<>();
		if(initialGameStateDto.getLiveCells() != null){
			boardsStates.add(gameProcessService.sortCells(initialGameStateDto.getLiveCells()));
		}else{
			boardsStates.add( new ArrayList<CellDto>());
		}
		
		gameState.setBoardStates(boardsStates);
		
		gameState.setStatus(GameStatus.ACTIVE);
		
		int gameId = gameRepository.saveNewGame(gameState);
		
		return new StartGameResponseDto(gameId);
	}
	
	protected void validateInputState(InitialGameStateDto initialGameStateDto){
		if (initialGameStateDto.getBoardSize() < 1) {
			throw new GameProccessException(String.format(
					"Wrong board size %d. Board size must be greater then 0",
					initialGameStateDto.getBoardSize()));
		}
		
		for(CellDto cell:initialGameStateDto.getLiveCells()){
			if(cell.getX()>=initialGameStateDto.getBoardSize() || cell.getX()<0){
				throw new GameProccessException(String.format("Wrong cell with coordinate x %d, y %d", cell.getX(), cell.getY()));
			}
			
			if(cell.getY()>=initialGameStateDto.getBoardSize() || cell.getY()<0){
				throw new GameProccessException(String.format("Wrong cell with coordinate x %d, y %d", cell.getX(), cell.getY()));
			}
		}
	}
	
	public GameStateDto makeSteps(int gameId, StepsCountDto stepsCount){
		if(stepsCount.getStepsCount()<1){
			throw new GameProccessException(String.format("Steps count must be greater then 0, but actual %d", stepsCount.getStepsCount()));
		}
		GameStateDto gameState = getGameState(gameId);
		
		synchronized (gameState) {
			for(int i=0;i<stepsCount.getStepsCount();i++){
				if(gameState.getStatus() == GameStatus.FINISHED){
					break;
				}
				gameProcessService.makeStepInGame(gameState);
			}
		}
		return gameState;
	}
	
	public GameStateDto getGameState(int gameId){
		return gameRepository.getGame(gameId);
	}
}
