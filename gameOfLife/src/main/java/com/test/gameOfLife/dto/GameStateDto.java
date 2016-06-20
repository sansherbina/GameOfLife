package com.test.gameOfLife.dto;

import java.util.List;

public class GameStateDto{
	private int boardSize;
	private GameStatus status;
	private int id;
	private List<List<CellDto>> boardStates;
	
	public GameStatus getStatus() {
		return status;
	}
	public void setStatus(GameStatus status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoardSize() {
		return boardSize;
	}
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}
	public List<List<CellDto>> getBoardStates() {
		return boardStates;
	}
	public void setBoardStates(List<List<CellDto>> boardStates) {
		this.boardStates = boardStates;
	}
	
	
}
