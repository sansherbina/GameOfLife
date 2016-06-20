package com.test.gameOfLife.exception;

public class GameProccessException extends RuntimeException{
	private static final long serialVersionUID = -3116288550197586248L;

	public GameProccessException(String message){
		super(message);
	}
	
	public GameProccessException(String message, Throwable e){
		super(message, e);
	}
}
