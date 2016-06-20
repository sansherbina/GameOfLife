package com.test.gameOfLife.dto;

import java.util.List;

public class InitialGameStateDto {
	private int boardSize;
	
	private List<CellDto> liveCells;
	
	public int getBoardSize() {
		return boardSize;
	}
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}
	public List<CellDto> getLiveCells() {
		return liveCells;
	}
	public void setLiveCells(List<CellDto> liveCells) {
		this.liveCells = liveCells;
	}
	
	
}
