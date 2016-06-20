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
	
	protected boolean[][] buildBoard(List<CellDto> liveCells, int size){
		boolean[][] currentBoard = new boolean[size][size];
		for(CellDto liveCell:liveCells){
			currentBoard[liveCell.getY()][liveCell.getX()] = true;
		}
		return currentBoard;
	}
	
	public void makeStepInGame(GameStateDto gameState){
		List<CellDto> liveCells = gameState.getBoardStates().get(gameState.getBoardStates().size()-1);
		boolean[][] currentBoard = buildBoard(liveCells, gameState.getBoardSize());
		
		List<CellDto> nextState = new ArrayList<>();
		for(int i=0;i<gameState.getBoardSize();i++){
			for(int j=0;j<gameState.getBoardSize();j++){
				if(isCellWillLive(i, j, currentBoard)){
					nextState.add(new CellDto(j,i));
				}
			}
		}
		
		boolean isRepeat = gameState.getBoardStates().contains(nextState);
		
		if(isRepeat){
			gameState.setStatus(GameStatus.FINISHED);
		}else{
			gameState.getBoardStates().add(nextState);
		}
	}
	
	protected int calculateLiveNeighboad(int x, int y, boolean[][] board){
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
		return liveNeighboard;
	}
	
	protected boolean isCellWillLive(int x, int y, boolean[][] board){
		int liveNeighboard = calculateLiveNeighboad(x, y, board);
		
		if(board[y][x] && liveNeighboard>=2 && liveNeighboard<=3){
			return true;
		}
		
		if(!board[y][x] && liveNeighboard==3){
			return true;
		}
		return false;
	}
}
