package com.test.gameOfLife.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.gameOfLife.dto.CellDto;
import com.test.gameOfLife.dto.GameStateDto;
import com.test.gameOfLife.dto.GameStatus;

@Service
public class GameProcessService {
	public List<CellDto> sortCells(List<CellDto> cells){
		Collections.sort(cells, new Comparator<CellDto>() {

			@Override
			public int compare(CellDto c1, CellDto c2) {
				int y = c1.getY() - c2.getY();
				if(y!=0){
					return y;
				}
				return c1.getX() - c2.getX();
			}
			
		});
		
		return cells;
	}
	
	public void makeStepInGame(GameStateDto gameState){
		boolean[][] currentBoard = new boolean[gameState.getBoardSize()][gameState.getBoardSize()];
		List<CellDto> liveCells = gameState.getBoardStates().get(gameState.getBoardStates().size()-1);
		for(CellDto liveCell:liveCells){
			currentBoard[liveCell.getY()][liveCell.getX()] = true;
		}
		
		List<CellDto> nextState = new ArrayList<>();
		for(int i=0;i<gameState.getBoardSize();i++){
			for(int j=0;j<gameState.getBoardSize();j++){
				if(isCellWillLive(i, j, currentBoard)){
					nextState.add(new CellDto(j,i));
				}
			}
		}
		
		boolean isRepeat = checkIfRepeatState(nextState, gameState);
		
		if(isRepeat){
			gameState.setStatus(GameStatus.FINISHED);
		}else{
			gameState.getBoardStates().add(nextState);
		}
	}
	
	protected boolean checkIfRepeatState(List<CellDto> nextState, GameStateDto gameStateDto){
		for(List<CellDto> prevState:gameStateDto.getBoardStates()){
			if(prevState.equals(nextState)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean isCellWillLive(int x, int y, boolean[][] board){
		int liveNeighboard = 0;
		if(y!=0){
			if(board[y-1][x]){
				liveNeighboard++;
			}
			if(x!=0){
				if(board[y-1][x-1]){
					liveNeighboard++;
				}
			}
			if(x!=board.length-1){
				if(board[y-1][x+1]){
					liveNeighboard++;
				}
			}
		}
		
		if(y!=board.length-1){
			if(board[y+1][x]){
				liveNeighboard++;
			}
			if(x!=0){
				if(board[y+1][x-1]){
					liveNeighboard++;
				}
			}
			if(x!=board.length-1){
				if(board[y+1][x+1]){
					liveNeighboard++;
				}
			}
		}
		
		if(x!=0){
			if(board[y][x-1]){
				liveNeighboard++;
			}
		}
		

		if(x!=board.length-1){
			if(board[y][x+1]){
				liveNeighboard++;
			}
		}
		
		if(board[y][x] && liveNeighboard>=2 && liveNeighboard<=3){
			return true;
		}
		
		if(!board[y][x] && liveNeighboard==3){
			return true;
		}
		return false;
	}
}
