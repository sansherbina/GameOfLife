package com.test.gameOfLife.dto;

public class StartGameResponseDto {
	private int gameId;
	
	public StartGameResponseDto() {
	}

	public StartGameResponseDto(int gameId) {
		this.gameId = gameId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
}
