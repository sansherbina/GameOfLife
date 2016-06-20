package com.test.gameOfLife.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.test.gameOfLife.dto.GameStateDto;
import com.test.gameOfLife.exception.GameProccessException;

@Component
public class GameRepository {
	private int nextId;
	private Map<Integer, GameStateDto> games = new ConcurrentHashMap<>();
	
	private synchronized int getNextId(){
		nextId++;
		return nextId;
	}
	
	public int saveNewGame(GameStateDto game){
		int id = getNextId();
		game.setId(id);
		games.put(id, game);
		return id;
	}
	
	public GameStateDto getGame(int id){
		GameStateDto game = games.get(id);
		if(game==null){
			throw new GameProccessException(String.format("Game with id %d does not exist", id));
		}
		return game;
	}
}
