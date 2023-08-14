package com.example.othello.models;

import java.util.ArrayList;

public class OthelloGame {

    public static OthelloConfig b;

    //create array for board
    public int[][] mBoard;
    public int[][] mSpaces;
    public static int userWins, compWins, ties = 0;
    private int userTurn;
    private int compTurn;
    private int turn;
    private int notTurn;

    private ArrayList<Integer> tempAL;

//TODO: build the rest of the model
    //constructor
    public OthelloGame(){
        mBoard= b.getBoard();
        mSpaces =b.getSpaces();

    }

    public void SetUserColorSelection(int turn){
        b.setTurn(turn);
    }

    public void updateBoard(){

    }


}
