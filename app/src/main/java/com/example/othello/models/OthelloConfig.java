package com.example.othello.models;

import android.widget.ImageButton;

import com.example.othello.R;

import java.util.ArrayList;

public class OthelloConfig {
	private int[][] board = 	 {{0,0,0,0,0,0,0,0},
			 					  {0,0,0,0,0,0,0,0},
			 					  {0,0,0,0,0,0,0,0},
			 					  {0,0,0,1,2,0,0,0},
			 					  {0,0,0,2,1,0,0,0},
			 					  {0,0,0,0,0,0,0,0},
			 					  {0,0,0,0,0,0,0,0},
			 					  {0,0,0,0,0,0,0,0}};
	//if needed: make paralell array of views
	private int[][] spaces = {{R.id.A1, R.id.A2, R.id.A3, R.id.A4, R.id.A5, R.id.A6, R.id.A7, R.id.A8},
			{R.id.B1, R.id.B2, R.id.B3, R.id.B4, R.id.B5, R.id.B6, R.id.B7, R.id.B8},
			{R.id.C1, R.id.C2, R.id.C3, R.id.C4, R.id.C5, R.id.C6, R.id.C7, R.id.C8},
			{R.id.D1, R.id.D2, R.id.D3, R.id.D4, R.id.D5, R.id.D6, R.id.D7, R.id.D8},
			{R.id.E1, R.id.E2, R.id.E3, R.id.E4, R.id.E5, R.id.E6, R.id.E7, R.id.E8},
//TODO: fill in the rest later
	};





//							}};
	private int userTurn;
	private int compTurn;
	private int turn;
	private int notTurn;
	private ArrayList<Integer> tempAL;
	
	public void setTurn(int turn) {
		userTurn = turn;
		if(turn == 1)
			compTurn = 2;
		else if(turn == 2)
			compTurn = 1;
	}
	
	public void changeTurn(int turn) {
		this.turn = turn;
		if(turn == 1)
			notTurn = 2;
		else if(turn == 2)
			notTurn = 1;
	}
	
	public int getUserTurn() {
		return userTurn;
	}
	
	public int getCompTurn() {
		return compTurn;
	}
	
	public int[][] getBoard() {
		return board;
	}
	public int[][] getSpaces() {
		return spaces;
	}
	
	public int[] getScore() {
		int userCount = 0;
		int compCount = 0;
		for(int x=0; x <= 7; x++) {
			for(int y=0; y <= 7; y++) {
				if(board[x][y] == userTurn) {
					userCount++;
				}
				else if (board[x][y] == compTurn) {
					compCount++;
				}
			}
		}
		int[] scores = {userCount, compCount};
		return scores;
	}
	
	public boolean movesLeft() {
		ArrayList<int[]> moveset = checkMoves();
		if(moveset.size() == 0)
			return false;
		return true;
	}
	
	public boolean fullBoard() {
		boolean openSpace = false;
		for(int x=0; x <= 7; x++)
			for(int y=0; y <= 7; y++)
				if(board[x][y] == 0)
					openSpace = true;
		if(openSpace)
			return false;
		return true;
	}
	
	public boolean userMove(int x, int y) {
		ArrayList<int[]> moveset = checkMoves();
		for(int[] moves : moveset) {
			if(x == moves[0] && y == moves[1]) {
				doMove(x, y, true);
				return true;
			}	
		}
		return false;
	}
	
	public boolean compMove() {
		int bestX = -1, bestY = -1, mP = -1, highestMP = -1;
		for(int x=0; x <= 7; x++) {
			for(int y=0; y <= 7; y++) {
				if(board[x][y]==0) {
					mP = doMove(x, y, false);
					if(mP > highestMP) {
						highestMP = mP;
						bestX = x;
						bestY = y;
					}
				}
			}
		}
		if(bestX != -1 && bestY != -1) {
			doMove(bestX, bestY, true);
			return true;
		}
		else
			return false;
	}
	
	private int doMove(int x, int y, boolean go) {
		ArrayList<int[]> moveset = checkMoves();
		boolean isValid = false;
		int movePower = 0;
		for(int[] move : moveset)
			if(x == move[0] && y == move[1])
				isValid = true;
		if(!isValid)
			return -1;
		ArrayList<Integer> moveType = new ArrayList<>();
		for(int[] move : moveset)
			if(x == move[0] && y == move[1])
				moveType.add(move[2]);
		for(int i : moveType) {
			if(i==101) { //Left move in row
				for(int a = y+1; a <= 7 && board[x][a] == notTurn; a++) {
					if(go)
						board[x][a] = turn;
					movePower++;
				}
			}
			if(i==102) { //Right move in row
				for(int a = y-1; a >= 0 && board[x][a] == notTurn; a--) {
					if(go)
						board[x][a] = turn;
					movePower++;
				}
			}
			if(i==103) { //Up move in col
				for(int a = x+1; a <= 7 && board[a][y] == notTurn; a++) {
					if(go)
						board[a][y] = turn;
					movePower++;
				}
			}
			if(i==104) { //Down move in col
				for(int a = x-1; a >= 0 && board[a][y] == notTurn; a--) {
					if(go)
						board[a][y] = turn;
					movePower++;
				}
			}
			if(i==105) { //Down right
				for(int a = x-1, b = y-1; a >= 0 && b >= 0 && board[a][b] == notTurn; a--, b--) {
					if(go)
						board[a][b] = turn;
					movePower++;
				}
			}
			if(i==106) { //Down left
				for(int a = x-1, b = y+1; a >= 0 && b <= 7 && board[a][b] == notTurn; a--, b++) {
					if(go)
						board[a][b] = turn;
					movePower++;
				}
			}
			if(i==107) { //Up right
				for(int a = x+1, b = y-1; a <= 7 && b >= 0 && board[a][b] == notTurn; a++, b--) {
					if(go)
						board[a][b] = turn;
					movePower++;
				}
			}
			if(i==108) { //Up left
				for(int a = x+1, b = y+1; a <= 7 && b <= 7 && board[a][b] == notTurn; a++, b++) {
					if(go)
						board[a][b] = turn;
					movePower++;
				}
			}
		}
		if(go) {
			board[x][y] = turn;
		}	
		return movePower;
	}
	
	private ArrayList<int[]> checkMoves() {
		ArrayList<int[]> moveset = new ArrayList<>();
		for(int i=0; i <= 7; i++) {
			tempAL = leftMovesInRow(i);
			for(int x : tempAL) {
				int[] coords = new int[3];
				coords[0] = i;
				coords[1] = x;
				coords[2] = 101;
				moveset.add(coords);
			}
			tempAL = rightMovesInRow(i);
			for(int x : tempAL) {
				int[] coords = new int[3];
				coords[0] = i;
				coords[1] = x;
				coords[2] = 102;
				moveset.add(coords);
			}
			tempAL = upMovesInCol(i);
			for(int x : tempAL) {
				int[] coords = new int[3];
				coords[0] = x;
				coords[1] = i;
				coords[2] = 103;
				moveset.add(coords);
			}
			tempAL = downMovesInCol(i);
			for(int x : tempAL) {
				int[] coords = new int[3];
				coords[0] = x;
				coords[1] = i;
				coords[2] = 104;
				moveset.add(coords);
			}
		}
		moveset.addAll(downRightMoves());
		moveset.addAll(downLeftMoves());
		moveset.addAll(upRightMoves());
		moveset.addAll(upLeftMoves());
		return moveset;
	}
		
	private ArrayList<Integer> leftMovesInRow(int row) {
		ArrayList<Integer> moves = new ArrayList<>();
		int x;
		for(int i = 2; i < 8; i++) {
			if(board[row][i] == turn && board[row][i-1] == notTurn) {
				for(x=i-2; x >= 0 && board[row][x] == notTurn; x--) {}
				if(x >= 0 && board[row][x] == 0)
					moves.add(x);		
			}
		}
		return moves;
	}
	
	private ArrayList<Integer> rightMovesInRow(int row) {
		ArrayList<Integer> moves = new ArrayList<>();
		int x;
		for(int i = 6; i >= 0; i--) {
			if(board[row][i] == turn && board[row][i+1] == notTurn) {
				for(x=i+2; x <= 7 && board[row][x] == notTurn; x++) {}
				if(x<=7 && board[row][x] == 0)
					moves.add(x);		
			}
		}
		return moves;
	}
	
	private ArrayList<Integer> upMovesInCol(int col) {
		ArrayList<Integer> moves = new ArrayList<>();
		int x;
		for(int i = 2; i < 8; i++) {
			if(board[i][col] == turn && board[i-1][col] == notTurn) {
				for(x=i-2; x >= 0 && board[x][col] == notTurn; x--) {}
				if(x>=0 && board[x][col] == 0)
					moves.add(x);		
			}
		}
		return moves;
	}
	
	private ArrayList<Integer> downMovesInCol(int col) {
		ArrayList<Integer> moves = new ArrayList<>();
		int x;
		for(int i = 6; i >= 0; i--) {
			if(board[i][col] == turn && board[i+1][col] == notTurn) {
				for(x=i+2; x <= 7 && board[x][col] == notTurn; x++) {}
				if(x<=7 && board[x][col] == 0)
					moves.add(x);		
			}
		}
		return moves;
	}
	
	
	private ArrayList<int[]> downRightMoves() {
		ArrayList<int[]> moves = new ArrayList<>();
		for(int x=0; x <= 5; x++) {
			for(int y=0; y <= 5; y++) {
				if(board[x][y] == turn && board[x+1][y+1] == notTurn) {
					boolean cont = true;
					int count = 1;
					while(board[x+count][y+count] == notTurn && cont) {
						count++;
						if(board[x+count][y+count] == 0) {
							int move[] = new int[3];
							move[0] = x + count;
							move[1] = y + count;
							move[2] = 105;
							moves.add(move);
							cont = false;
						}
						if(x + count == 7 || y + count == 7)
							cont = false;
					}	
				}
			}
		}
		return moves;
	}
	
	private ArrayList<int[]> downLeftMoves() {
		ArrayList<int[]> moves = new ArrayList<>();
		for(int x=0; x <= 5; x++) {
			for(int y=2; y <= 7; y++) {
				if(board[x][y] == turn && board[x+1][y-1] == notTurn) {
					boolean cont = true;
					int count = 1;
					while(board[x+count][y-count] == notTurn && cont) {
						count++;
						if(board[x+count][y-count] == 0) {
							int move[] = new int[3];
							move[0] = x + count;
							move[1] = y - count;
							move[2] = 106;
							moves.add(move);
							cont = false;
						}
						if(x + count == 7 || y - count == 0)
							cont = false;
					}	
				}
			}
		}
		return moves;
	}
	
	private ArrayList<int[]> upRightMoves() {
		ArrayList<int[]> moves = new ArrayList<>();
		for(int x=7; x >= 2; x--) {
			for(int y=0; y <= 5; y++) {
				if(board[x][y] == turn && board[x-1][y+1] == notTurn) {
					boolean cont = true;
					int count = 1;
					while(board[x-count][y+count] == notTurn && cont) {
						count++;
						if(board[x-count][y+count] == 0) {
							int move[] = new int[3];
							move[0] = x - count;
							move[1] = y + count;
							move[2] = 107;
							moves.add(move);
							cont = false;
						}
						if(x - count == 0 || y + count == 7)
							cont = false;
					}	
				}
			}
		}
		return moves;
	}
	
	private ArrayList<int[]> upLeftMoves() {
		ArrayList<int[]> moves = new ArrayList<>();
		for(int x=7; x >= 2; x--) {
			for(int y=7; y >= 2; y--) {
				if(board[x][y] == turn && board[x-1][y-1] == notTurn) {
					boolean cont = true;
					int count = 1;
					while(board[x-count][y-count] == notTurn && cont) {
						count++;
						if(board[x-count][y-count] == 0) {
							int move[] = new int[3];
							move[0] = x - count;
							move[1] = y - count;
							move[2] = 108;
							moves.add(move);
							cont = false;
						}
						if(x - count == 0 || y - count == 0)
							cont = false;
					}	
				}
			}
		}
		return moves;
	}
	
}