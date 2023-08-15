package com.example.othello.models;

import com.example.othello.R;

import java.util.ArrayList;

public class OthelloGame {

    public static OthelloConfig b;

    //create array for board
    public int[][] mBoard;
    public int[][] mSpaces;
    private int[][] mboard = 	 {{0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,1,2,0,0,0},
                                    {0,0,0,2,1,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0}};
    //if needed: make paralell array of views
    private int[][] mspaces = {{R.id.A1, R.id.A2, R.id.A3, R.id.A4, R.id.A5, R.id.A6, R.id.A7, R.id.A8},
                                {R.id.B1, R.id.B2, R.id.B3, R.id.B4, R.id.B5, R.id.B6, R.id.B7, R.id.B8},
                                {R.id.C1, R.id.C2, R.id.C3, R.id.C4, R.id.C5, R.id.C6, R.id.C7, R.id.C8},
                                {R.id.D1, R.id.D2, R.id.D3, R.id.D4, R.id.D5, R.id.D6, R.id.D7, R.id.D8},
                                {R.id.E1, R.id.E2, R.id.E3, R.id.E4, R.id.E5, R.id.E6, R.id.E7, R.id.E8},
//TODO: fill in the rest later
    };
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
