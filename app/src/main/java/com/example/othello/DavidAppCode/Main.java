package com.example.othello.DavidAppCode;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
	public static OthelloConfig b;
	private static Scanner scan = new Scanner(System.in);
	public static int userWins, compWins, ties = 0;
	
	public static void main(String[] args) {
		boolean newGame = true;
		boolean gameOver, pendingAnswer;
		String input;
		
		while(newGame) {
			//Game setup:
			b = new OthelloConfig();
			pendingAnswer = true;
			System.out.println("Welcome to Othello!\nWould you like to be "+
					"white (Enter \'W\') or black (Enter \'B\')?");
			input = scan.nextLine();
			while(pendingAnswer) {
				if(input.equals("W")) {
					b.setTurn(1);
					pendingAnswer = false;
				}	
				else if(input.equals("B")) {
					b.setTurn(2);
					pendingAnswer = false;
				}
				else {
					System.out.println("Please enter a proper response.");
					input = scan.nextLine();
				}	
			}
	
			//Game start:
			printBoard();
			if(b.getCompTurn()==2) {
				System.out.println("Computer goes first!");
				b.changeTurn(b.getCompTurn());
				b.compMove();
				printBoard();
			}
			else
				System.out.println("You go first!");
			
			//Game loop:
			gameOver = false;
			while(!gameOver) {
				b.changeTurn(b.getUserTurn());
				if(b.fullBoard() || !b.movesLeft()) {
					evaluateWinner();
					gameOver = true;
				}
				else {
					userTurn();
					printBoard();
					b.changeTurn(b.getCompTurn());
					if(b.fullBoard() || !b.movesLeft()) {
						evaluateWinner();
						gameOver = true;
					}
					else {
						System.out.println("Computer's turn!");
						b.compMove();
						printBoard();
					}
				}
			}
			
			//Ask to play again:
			pendingAnswer = true;
			System.out.println("Would you like to play again? (Enter \"Y\" or \"N\")");
			input = scan.nextLine();
			while(pendingAnswer) {
				if(input.equals("N")) {
					newGame = false;
					pendingAnswer = false;
				}	
				else if(input.equals("Y")) {
					pendingAnswer = false;
				}
				else {
					System.out.println("Please enter a proper response.");
					input = scan.nextLine();
				}
			}
		}
			
		//Report data of all games played:
		System.out.println();
		System.out.println("Printing all game data:");
		int total = userWins + compWins + ties;
		double userWinsPercent = (double)userWins / total * 100;
		double compWinsPercent = (double)compWins / total * 100;
		double tiesPercent = (double)ties / total * 100;
		System.out.println("Total games played: " + total);
		System.out.println("Games you won: " + userWins);
		System.out.println("Percentage of games you won: " + userWinsPercent + "%");
		System.out.println("Games Computer won: " + compWins);
		System.out.println("Percentage of games Computer won: " + compWinsPercent + "%");
		System.out.println("Tied games: " + ties);
		System.out.println("Percentage of games tied: " + tiesPercent + "%");
		
	}


	public static void userTurn() {
		int x, y;
		boolean invalidMove = false;
		while(!invalidMove) {
			System.out.println("You're up!");
			System.out.print("Please enter the row # of your move: ");
			x = scan.nextInt();
			scan.nextLine();
			System.out.print("Please enter the column # of your move: ");
			y = scan.nextInt();
			scan.nextLine();
			if (b.userMove(x, y)) {
				invalidMove = true;
			}
			else {
				System.out.println("Invalid move.");
			}
		}
	}
	
	public static void evaluateWinner() {
		int[] score = b.getScore();
		if(score[0] > score[1]) {
			System.out.println("Game over! You Win!");
			userWins++;
		}	
		else if(score[1] > score[0]) {
			System.out.println("Game over! Computer Wins!");
			compWins++;
		}
		else {
			System.out.println("Game over! Tie game!");
			ties++;
		}	
	}
	
	public static void printBoard() {
		int [][] board = b.getBoard();
		String [][] p = new String[8][8];
		for(int x=0; x <= 7; x++)
			for(int y=0; y <= 7; y++)
				if(board[x][y] == 0)
					p[x][y] = " ";
				else if(board[x][y] == 1)
					p[x][y] = "○";
				else
					p[x][y] = "●";
		System.out.println();
		System.out.println("     0   1   2   3   4   5   6   7  ");
		for(int x = 0; x <= 7; x++) {
			System.out.println("   +---+---+---+---+---+---+---+---+");
			System.out.println(x+"  | "+p[x][0]+" | "+p[x][1]+" | "+p[x][2]+
					" | "+p[x][3]+" | "+p[x][4]+" | "+p[x][5]+" | "+p[x][6]+
					" | "+p[x][7]+" |");
		}
		System.out.println("   +---+---+---+---+---+---+---+---+");
		int[] score = b.getScore();
		String userColor, compColor;
		if (b.getUserTurn() == 1) {
			userColor = "WHITE";
			compColor = "BLACK";
		}
		else {
			userColor = "BLACK";
			compColor = "WHITE";
		}
		System.out.println(" ‣ YOU      (" + userColor + "): " + score[0]);
		System.out.println(" ‣ COMPUTER (" + compColor + "): " + score[1]);
		System.out.println();
	}


}