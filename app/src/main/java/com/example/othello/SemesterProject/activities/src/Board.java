//import java.util.ArrayList;
import java.util.Scanner;


//NOTE TO SELF, ALL INPUTS BEING FED TO METHODS ALREADY HAVE 1 SUBTRACTED

//NOTE TO SELF all indexes are accurate, all ranges are accurate, DO NOT CHANGE

public class Board {
    private boolean legalMove = true;
    private boolean playersTurn = true;
    Scanner keyboard = new Scanner(System.in);

    private int compOptimalY, compOptimalX;

    public void playerPieceChoice(String x){
        if (!(x.equalsIgnoreCase(whitePiece)||x.equalsIgnoreCase(blackPiece))){
            System.out.println("that isn't a valid choice\ntry again:  ");
            boolean validPlayerChoice = false;
            while (!validPlayerChoice){
                x = keyboard.nextLine();
                if (x.equalsIgnoreCase(whitePiece)||x.equalsIgnoreCase(blackPiece)){
                    validPlayerChoice = true;
                }
            }
        }
        if (x.equalsIgnoreCase(whitePiece)){
            playerIsWhite = true;
            playersPiece = whitePiece;
            computersPiece = blackPiece;
            playersTurn = true;
        }else {
            playerIsWhite = false;
            playersPiece = blackPiece;
            computersPiece = whitePiece;
            playersTurn = false;
        }
//        return x;
    }
    private boolean playerIsWhite;
    private String playersPiece;
    private String computersPiece;
    private String whitePiece = "X";
    private String blackPiece = "O";
    private String emptyPiece = " ";
    public String getWhitePiece(){
        return whitePiece;
    }
    public String getBlackPiece(){
        return blackPiece;
    }

    // use static vars to track score

    private static int gamesPlayed = 0;
    private static int wins = 0;
    private static int losses = 0;
    private static int ties = 0;

    private static void setWins(){
        wins++;
    }
    private static void setLosses(){
        losses++;
    }
    private static void setTies(){
        ties++;
    }
    private static void setGamesPlayed(){
        gamesPlayed++;
    }
    public void gameResults(){
        int white = 0;
        int black = 0;
        setGamesPlayed();
            //don't flip the index here, this one is correct*****
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (spaces[i][j].positionStatus.equals(whitePiece)){
                        white +=1;
                    } else if (spaces[i][j].positionStatus.equals(blackPiece)) {
                        black +=1;
                    }
                }
            }
            System.out.println("final tally:\nWhite:  "+ white +"\nBlack:  "+black);
            if (playerIsWhite) {
                if (white > black) {
                    System.out.println("You Win");
                    setWins();
                } else if (black > white) {
                    System.out.println("You Lose");
                    setLosses();
                } else {
                    System.out.println("Tie");
                    setTies();
                }
            }else {
                if (white < black) {
                    System.out.println("You Win");
                    setWins();
                } else if (black < white) {
                    System.out.println("You Lose");
                    setLosses();
                } else {
                    System.out.println("Tie");
                    setTies();
                }
            }
            // display win rates:
        System.out.println("You have played " + gamesPlayed + "games");
        if (wins>0){
            double tempVar = wins;
            System.out.println("you have won " + (100*(tempVar/gamesPlayed)) + "% of games");
        }
        if (losses>0){
            double tempVar = losses;
            System.out.println("You have lost " + (100*(tempVar/gamesPlayed)) + "% of games");
        }
        if (ties>0){
            double tempVar = ties;
            System.out.println("You have tied "+ (100*(tempVar/gamesPlayed)) + "% of games");
        }
    }

    public void forfeitGameResult(){
        setGamesPlayed();
        setLosses();
        System.out.println("You have played " + gamesPlayed + "games");
        if (wins>0){
            double tempVar = wins;
            System.out.println("you have won " + (100*(tempVar/gamesPlayed)) + "% of games");
        }
        if (losses>0){
            double tempVar = losses;
            System.out.println("You have lost " + (100*(tempVar/gamesPlayed)) + "% of games");
        }
        if (ties>0){
            double tempVar = ties;
            System.out.println("You have tied "+ (100*(tempVar/gamesPlayed)) + "% of games");
        }
    }

    public boolean getPlayersTurn() {
        return playersTurn;
    }

    private class Space {
        private boolean occupied = false;
        private String positionStatus;
    }

    public boolean getLegalMove() {
        return legalMove;
    }


//method to display the board
    public void displayBoard() {
        //don't flip the index here, this one is correct*****
        System.out.println("X   1   2   3   4   5   6   7   8");
        for (int i = 0; i < 8; i++) {
            System.out.println("   ---------------------------------");
            System.out.print((i + 1) + " |");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + spaces[i][j].positionStatus + " |");
            }
            System.out.println();
        }
        System.out.println("   ---------------------------------");
    }

    private Space[][] spaces = new Space[8][8];

    public void setBoard() {
        legalMove = true;
        //don't flip these indexes either (also accurate)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (((i == 3) && (j == 3)) || ((i == 4) && (j == 4))) {
                    Space x = new Space();
                    x.occupied = true;
                    x.positionStatus = whitePiece;
                    spaces[i][j] = x;
                } else if (((i == 3) && (j == 4)) || ((i == 4) && (j == 3))) {
                    Space x = new Space();
                    x.occupied = true;
                    x.positionStatus = blackPiece;
                    spaces[i][j] = x;
                } else {
                    Space x = new Space();
                    x.positionStatus = emptyPiece;
                    x.occupied=false;
                    spaces[i][j] = x;
                }
            }
        }
        if (playerIsWhite) {
            playersTurn = true;
        }else {
            playersTurn = false;
        }
    }

    //note to self, pass all methods args in order x, y, all direct indexes in order [y][x]

    //input validation
    public boolean forceValidNumericInput(String x){
        if (x == null){
            return false;
        }
        else if (x.equalsIgnoreCase("quit")){
            return true;
        }
        try {
            Double.parseDouble(x);
        } catch (NumberFormatException e) {
            return false;
        }try {
            Integer.parseInt(x);
        }catch (NumberFormatException exception){
            return false;
        }
        int y = Integer.parseInt(x);
        if (y>=1 && y<=8){
            return true;
        }
        return false;
    }

    public boolean CheckValidMove(int x, int y){

         boolean nPoints = testN(x,y);
         boolean sPoints = testS(x,y);
         boolean ePoints=testE(x,y);
         boolean wPoints=testW(x,y);
         boolean nePoints=testNE(x,y);
         boolean nwPoints=testNW(x,y);
         boolean sePoints=testSE(x,y);
         boolean swPoints=testSW(x,y);
         boolean givePoints = (moveResults(x,y)>0);

        if ((nPoints||sPoints||ePoints||wPoints||nePoints||nwPoints||sePoints||swPoints) && (!(spaces[y][x].occupied)) && givePoints){
             return true;
         } else {
            return false;
        }
     }

//directional test methods:
    //north:
    private boolean testN(int x, int y){

        boolean canGeneratePoints = false;
        boolean hasAnEmptySpace=false;

        if (x<8 && y>=1 && x>=0 && y<8){
            canGeneratePoints=true;
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y-1][x].occupied){
                int i = y-1;
                if (playersTurn){
                    while (i>=0 && continueTesting) {
                        if ((spaces[i][x].positionStatus.equals(computersPiece))) {
                            if (i==0){
                                continueTesting = false;
                            }
                        } else if (!(spaces[i][x].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[i][x].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                            //confirms piece on each end of the row, meaning it should return points
                        }
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (i>=0 && continueTesting) {
                        if ((spaces[i][x].positionStatus.equals(playersPiece))){
                            if (i==0){
                                continueTesting = false;
                            }
                        } else if (!(spaces[i][x].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[i][x].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
                if (!pieceOnBothEnds){
                    canGeneratePoints = false;
                }
            }
        }
        return canGeneratePoints;
    }

    //South
    private boolean testS(int x, int y){

        boolean canGeneratePoints = false;
        boolean hasAnEmptySpace=false;

        if (x<8 && y>=0 && x>=0 && y<7){
            canGeneratePoints=true;
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y+1][x].occupied){
                int i = y+1;
                if (playersTurn){
                    while (i<8 && continueTesting) {
                        if ((spaces[i][x].positionStatus.equals(computersPiece))){
                            if (i==7){
                                continueTesting = false;
                            }
                        } else if (!(spaces[i][x].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[i][x].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {

                    while (i<8 &&  continueTesting) {
                        if ((spaces[i][x].positionStatus.equals(playersPiece))){
                            if (i==7){
                                continueTesting = false;
                            }
                        } else if (!(spaces[i][x].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[i][x].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
                if (!pieceOnBothEnds){
                    canGeneratePoints = false;
                }
            }
        }
        return canGeneratePoints;
    }

    //east:
    private boolean testE(int x, int y){
        boolean canGeneratePoints = false;
        boolean hasAnEmptySpace=false;
        boolean continueTesting = true;


        if (x<7 && y>=0 && x>=0 && y<8){
            canGeneratePoints=true;
            boolean pieceOnBothEnds = false;
            if (spaces[y][x+1].occupied){
                int i = x+1;
                if (playersTurn){
                    while (i<8&& continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(computersPiece))){
                            if (i==7){
                                continueTesting = false;
                            }
                        } else if (!(spaces[y][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (i<8&& continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(playersPiece))){
                            if (i==7){
                                continueTesting = false;
                            }
                        } else if (!(spaces[y][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
                if (!pieceOnBothEnds){
                    canGeneratePoints = false;
                }
            }
        }
        return canGeneratePoints;
    }

    //West
    private boolean testW(int x, int y){

        boolean canGeneratePoints = false;
        boolean hasAnEmptySpace=false;
        boolean continueTesting = true;

        if (x<8 && y>=0 && x>=1 && y<8){
            canGeneratePoints=true;
            boolean pieceOnBothEnds = false;
            if (spaces[y][x-1].occupied){
                int i = x-1;
                if (playersTurn){
                    while (i>=0&& continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(computersPiece))){
                            if (i==0){
                                continueTesting = false;
                            }
                        } else if (!(spaces[y][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (i>=0&& continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(playersPiece))){
                            if (i==0){
                                continueTesting = false;
                            }
                        } else if (!(spaces[y][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting=false;
                        }
                    }
                }
                if (!pieceOnBothEnds){
                    canGeneratePoints = false;
                }
            }
        }
        return canGeneratePoints;
    }

    // now diagonal, increment/decrement x and y values together

    //to switch between N and S switch the y variables, between E and W the X

    // North-East
    private boolean testNE(int x, int y){
        boolean canGeneratePoints = false;
        boolean hasAnEmptySpace=false;
        boolean continueTesting = true;

        if (x<7 && y>=1 && x>=0 && y<8){
            canGeneratePoints=true;
            boolean pieceOnBothEnds = false;
            if (spaces[y-1][x+1].occupied){
                int i = x+1;
                int j = y-1;
                if (playersTurn){
                    while (i<8&&j>=0&& continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (i==7||j==0){
                                continueTesting = false;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j>=0 && i<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            if (i==7||j==0){
                                continueTesting = false;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting=false;
                        }
                    }
                }
                if (!pieceOnBothEnds){
                    canGeneratePoints = false;
                }
            }
        }
        return canGeneratePoints;
    }

    //North-West
    private boolean testNW(int x, int y){
        boolean canGeneratePoints = false;
        boolean hasAnEmptySpace=false;
        boolean continueTesting = true;

        if (x<8 && y>=1 && x>=1 && y<8){
            canGeneratePoints=true;
            boolean pieceOnBothEnds = false;
            if (spaces[y-1][x-1].occupied){
                int i = x-1;
                int j = y-1;
                if (playersTurn){
                    while (i>=0&&j>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (i==0||j==0){
                                continueTesting = false;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j>=0 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            if (i==0||j==0){
                                continueTesting = false;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
                if (!pieceOnBothEnds){
                    canGeneratePoints = false;
                }
            }
        }
        return canGeneratePoints;
    }

    //South-East
    private boolean testSE(int x, int y){

        boolean canGeneratePoints = false;
        boolean hasAnEmptySpace=false;
        boolean continueTesting = true;

        if (x<7 && y>=0 && x>=0 && y<7){
            canGeneratePoints=true;
            boolean pieceOnBothEnds = false;
            if (spaces[y+1][x+1].occupied){
                int i = x+1;
                int j = y+1;
                if (playersTurn){
                    while (i<8&&j<8&& continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (i==7||j==7){
                                continueTesting = false;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting=false;
                        }
                    }
                }else {
                    while (j<8 && i<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            if (i==7||j==7){
                                continueTesting = false;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting=false;
                        }
                    }
                }
                if (!pieceOnBothEnds){
                    canGeneratePoints = false;
                }
            }
        }
        return canGeneratePoints;
    }

    //South-West
    private boolean testSW(int x, int y){

        boolean canGeneratePoints = false;
        boolean hasAnEmptySpace=false;
        boolean continueTesting = true;

        if (x<8 && y>=0 && x>=1 && y<7){
            canGeneratePoints=true;
            boolean pieceOnBothEnds = false;
            if (spaces[y+1][x-1].occupied){
                int i = x-1;
                int j = y+1;
                if (playersTurn){
                    while (i>=0&&j<8&& continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (i==0||j==7){
                                continueTesting = false;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting=false;
                        }
                    }
                }else {
                    while (j<8 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            if (i==0||j==7){
                                continueTesting = false;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting=false;
                        }
                    }
                }
                if (!pieceOnBothEnds){
                    canGeneratePoints = false;
                }
            }
        }
        return canGeneratePoints;
    }
    //end of individual test methods

    //NOTE: could add recursive functionality to flip pieces that would flip as secondary changes, but official rules say not to
    // (only flip pieces directly as a result of piece placed this turn)

    //North
    private int countN(int x, int y){
        int tempPointAccumulator=0;
        boolean hasAnEmptySpace=false;
        boolean continueTesting = true;

        if (x<8 && x>=0 && y<8 && y>=1){
            boolean pieceOnBothEnds = false;
            if (spaces[y-1][x].occupied){
                int i = x;
                int j = y-1;
                if (playersTurn){
                    while (i>=0 && j>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (j == 0) {
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        }else if (!spaces[j][i].occupied){
                            tempPointAccumulator=0;
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j>=0 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))) {
                            if (j == 0) {
                                continueTesting = false;
                            } else {
                                tempPointAccumulator++;
                            }
                        }else if (!spaces[j][i].occupied){
                            tempPointAccumulator=0;
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
        return tempPointAccumulator;
    }
    //South
    private int countS(int x, int y){
        int tempPointAccumulator=0;
        boolean hasAnEmptySpace=false;

        if (x<8 && x>=0 && y>=0 && y<7){
            boolean continueTesting = true;
            boolean pieceOnBothEnds = false;
            if (spaces[y+1][x].occupied){
                int i = x;
                int j = y+1;
                if (playersTurn){
                    while (i>=0 && j<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (j==7){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j<8 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            if (j==7){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
        return tempPointAccumulator;
    }

    //East
    private int countE(int x, int y){
        int tempPointAccumulator=0;
        boolean hasAnEmptySpace=false;

        if (x<7 && x>=0 && y<8 && y>=0){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y][x+1].occupied){
                int i = x+1;
                if (playersTurn){
                    while (i<8 && continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(computersPiece))){
                            if (i==7){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[y][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (i<8 && continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(playersPiece))){
                            if (i==7){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[y][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
        return tempPointAccumulator;
    }

    //West
    private int countW(int x, int y){
        int tempPointAccumulator=0;
        boolean hasAnEmptySpace=false;

        if (x>=1 && x<8 && y>=0 && y<8){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y][x-1].occupied){
                int i = x-1;
                if (playersTurn){
                    while (i>=0 && continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(computersPiece))){
                            if (i==0){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[y][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (i>=0 && continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(playersPiece))){
                            if (i==0){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[y][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
        return tempPointAccumulator;
    }

    //diagonals:

    //North-East
    private int countNE(int x, int y){
        int tempPointAccumulator=0;
        boolean hasAnEmptySpace=false;

        if (x<7 && x>=0 && y<8 && y>=1){
            boolean pieceOnBothEnds = false;
            boolean continueTesting =true;
            if (spaces[y-1][x+1].occupied){
                int i = x+1;
                int j = y-1;
                if (playersTurn){
                    while (i<8 && j>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (i==7||j==0){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j>=0 && i<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            if (i==7||j==0){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
        return tempPointAccumulator;
    }

    //North-West
    private int countNW(int x, int y){
        int tempPointAccumulator=0;
        boolean hasAnEmptySpace=false;

        if (x<8 && x>=1 && y<8 && y>=1){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y-1][x-1].occupied){
                int i = x-1;
                int j = y-1;
                if (playersTurn){
                    while (i>=0&&j>=0&& continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (i==0||j==0){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j>=0 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            if (i==0||j==0){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
        return tempPointAccumulator;
    }

    //South-East
    private int countSE(int x, int y){
        int tempPointAccumulator=0;
        boolean hasAnEmptySpace=false;

        if (x<7 && x>=0 && y>=0 && y<7){
            boolean pieceOnBothEnds = false;
            boolean continueTesting =true;
            if (spaces[y+1][x+1].occupied){
                int i = x+1;
                int j = y+1;
                if (playersTurn){
                    while (i<8 && j<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (i==7||j==7){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j<8 && i<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            if (i==7||j==7){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
        return tempPointAccumulator;
    }

    //South-West
    private int countSW(int x, int y){
        int tempPointAccumulator=0;
        boolean hasAnEmptySpace=false;

        if (x>=1 && x<8 && y>=0 && y<7){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y+1][x-1].occupied){
                int i = x-1;
                int j = y+1;
                if (playersTurn){
                    while (i>=0 && j<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            if (i==0||j==7){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j<8 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            if (i==0||j==7){
                                continueTesting = false;
                            }else {
                                tempPointAccumulator++;
                            }
                        } else if (!(spaces[j][i].occupied)) {
                            tempPointAccumulator=0;
                                hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
        return tempPointAccumulator;
    }

    //flip methods
    //North
    private void flipN(int x, int y){
        boolean hasAnEmptySpace=false;

        if (x<8 && x>=0 && y<8 && y>=1){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y-1][x].occupied){
                int i = x;
                int j = y-1;
                if (playersTurn){
                    while (i>=0 && j<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            spaces[j][i].positionStatus=playersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j<8 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            spaces[j][i].positionStatus=computersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
    }

    //South
    private void flipS(int x, int y){
        boolean hasAnEmptySpace=false;

        if (x<8 && x>=0 && y>=0 && y<7){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y+1][x].occupied){
                int i = x;
                int j = y+1;
                if (playersTurn){
                    while (i>=0 && j<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            spaces[j][i].positionStatus=playersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j<8 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            spaces[j][i].positionStatus=computersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
    }

    //East
    private void flipE(int x, int y){
        boolean hasAnEmptySpace=false;

        if (x<7 && x>=0 && y<8 && y>=0){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y][x+1].occupied){
                int i = x+1;
                if (playersTurn){
                    while (i<8 && continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(computersPiece))){
                            spaces[y][i].positionStatus=playersPiece;
                        } else if (!(spaces[y][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (i<8 && continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(playersPiece))){
                            spaces[y][i].positionStatus=computersPiece;
                        } else if (!(spaces[y][i].occupied)) {
                                hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
    }

    //West
    private void flipW(int x, int y){
        boolean hasAnEmptySpace=false;

        if (x>=1 && x<8 && y>=0 && y<8){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y][x-1].occupied){
                if (playersTurn){
                    int i = x-1;
                    while (i>=0 && continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(computersPiece))) {
                            spaces[y][i].positionStatus = playersPiece;
                        } else if (!(spaces[y][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    int i = x-1;
                    while (i>=0 && continueTesting) {
                        if ((spaces[y][i].positionStatus.equals(playersPiece))){
                            spaces[y][i].positionStatus=computersPiece;
                        } else if (!(spaces[y][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[y][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
    }

    //North-East
    private void flipNE(int x, int y){
        boolean hasAnEmptySpace=false;

        if (x<7 && x>=0 && y<8 && y>=1){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y-1][x+1].occupied){
                int i = x+1;
                int j = y-1;
                if (playersTurn){
                    while (i<8 && j>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            spaces[j][i].positionStatus=playersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j>=0 && i<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            spaces[j][i].positionStatus=computersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
    }

    //North-West
    private void flipNW(int x, int y){
        boolean hasAnEmptySpace=false;

        if (x<8 && x>=1 && y<8 && y>=1){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y-1][x-1].occupied){
                int i = x-1;
                int j = y-1;
                if (playersTurn){
                    while (i>=0 && j>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            spaces[j][i].positionStatus=playersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        j--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j>=0 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            spaces[j][i].positionStatus=computersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j--;
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
    }

    //South-East
    private void flipSE(int x, int y){
        boolean hasAnEmptySpace=false;

        if (x<7 && x>=0 && y>=0 && y<7){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y+1][x+1].occupied){
                int i = x+1;
                int j = y+1;
                if (playersTurn){
                    while (i<8 && j<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            spaces[j][i].positionStatus=playersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i++;
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j<8 && i<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            spaces[j][i].positionStatus=computersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        i++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
    }

    //South-West
    private void flipSW(int x, int y){
        boolean hasAnEmptySpace=false;

        if (x>=1 && x<8 && y>=0 && y<7){
            boolean pieceOnBothEnds = false;
            boolean continueTesting = true;
            if (spaces[y+1][x-1].occupied){
                int i = x-1;
                int j = y+1;
                if (playersTurn){
                    while (i>=0 && j<8 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(computersPiece))){
                            spaces[j][i].positionStatus=playersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(playersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        i--;
                        j++;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }else {
                    while (j<8 && i>=0 && continueTesting) {
                        if ((spaces[j][i].positionStatus.equals(playersPiece))){
                            spaces[j][i].positionStatus=computersPiece;
                        } else if (!(spaces[j][i].occupied)) {
                            hasAnEmptySpace=true;

                        } else if (spaces[j][i].positionStatus.equals(computersPiece)) {
                            pieceOnBothEnds = true;
                        }
                        j++;
                        i--;
                        if (pieceOnBothEnds||hasAnEmptySpace){
                            continueTesting = false;
                        }
                    }
                }
            }
        }
    }

    public int moveResults(int x, int y){

        //values to indicate which directions will give points (controlling which methods to call to test amounts)

        boolean nPoints = testN(x,y);
        boolean sPoints = testS(x, y);
        boolean ePoints=testE(x, y);
        boolean wPoints=testW(x, y);
        boolean nePoints=testNE(x, y);
        boolean nwPoints=testNW(x, y);
        boolean sePoints=testSE(x, y);
        boolean swPoints=testSW(x, y);

        //initialize accumulator for directional point counter methods
        int pointTotal = 0;

        if (nPoints){
            pointTotal+=countN(x,y);
        }
        if (sPoints){
            pointTotal+=countS(x,y);
        }
        if (ePoints){
            pointTotal+=countE(x,y);
        }
        if (wPoints){
            pointTotal+=countW(x,y);
        }
        if (nePoints){
            pointTotal+=countNE(x,y);
        }
        if (nwPoints){
            pointTotal+=countNW(x,y);
        }
        if (sePoints){
            pointTotal+=countSE(x,y);
        }
        if (swPoints){
            pointTotal+=countSW(x,y);
        }

            return pointTotal;
    }

    public boolean setLegalMove() {
        //switch turns before testing, but before the end of the while loop that contains each turn
        switchTurns();

        boolean possiblePoints = false;
        int highestPossibleScore = 0;

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                boolean nPoints = testN(i, j);
                boolean sPoints = testS(i, j);
                boolean ePoints = testE(i, j);
                boolean wPoints = testW(i, j);
                boolean nePoints = testNE(i, j);
                boolean nwPoints = testNW(i, j);
                boolean sePoints = testSE(i, j);
                boolean swPoints = testSW(i, j);
                boolean givePoints = (moveResults(i, j) > 0);
                if ((nPoints || sPoints || ePoints || wPoints || nePoints || nwPoints || sePoints || swPoints) && (!spaces[j][i].occupied) && givePoints) {
                    possiblePoints = true;
                }
                if (possiblePoints) {
                    int pointTotal = 0;

                    if (nPoints) {
                        pointTotal += countN(i, j);
                    }
                    if (sPoints) {
                        pointTotal += countS(i, j);
                    }
                    if (ePoints) {
                        pointTotal += countE(i, j);
                    }
                    if (wPoints) {
                        pointTotal += countW(i, j);
                    }
                    if (nePoints) {
                        pointTotal += countNE(i, j);
                    }
                    if (nwPoints) {
                        pointTotal += countNW(i, j);
                    }
                    if (sePoints) {
                        pointTotal += countSE(i, j);
                    }
                    if (swPoints) {
                        pointTotal += countSW(i, j);
                    }
                    if (pointTotal > highestPossibleScore && !spaces[j][i].occupied) {
                        highestPossibleScore = pointTotal;
                    }
                }
            }
            }
        if (highestPossibleScore == 0){
            possiblePoints = false;
        }
        legalMove = possiblePoints;
            return possiblePoints;
        }

    //this places the actual piece
    //only use after confirming that the move is allowed
    public void placePiece(int x, int y){
        spaces[y][x].occupied=true;
        if (playersTurn){
            spaces[y][x].positionStatus=playersPiece;
        }else {
            spaces[y][x].positionStatus=computersPiece;
        }
    }


    //this method is to be used after the move is checked for validity
    public void flipPieces(int x, int y){
    // don't flip the values here, they get flipped when passed to method, only flip at bottom level methods, or when creating new values
        boolean nPoints = testN(x,y);
        boolean sPoints = testS(x, y);
        boolean ePoints=testE(x, y);
        boolean wPoints=testW(x, y);
        boolean nePoints=testNE(x, y);
        boolean nwPoints=testNW(x, y);
        boolean sePoints=testSE(x, y);
        boolean swPoints=testSW(x, y);

        if (nPoints){
            flipN(x,y);
        }
        if (sPoints){
            flipS(x,y);
        }
        if (ePoints){
            flipE(x,y);
        }
        if (wPoints){
            flipW(x,y);
        }
        if (nePoints){
            flipNE(x,y);
        }
        if (nwPoints){
            flipNW(x,y);
        }
        if (sePoints){
            flipSE(x, y);
        }
        if (swPoints){
            flipSW(x, y);
        }
    }

    //method to loop through the board using above method to test for optimal move
    public void compOptimalMoveTest(){
        int highestPossibleScore=0;
        boolean possiblePoints = false;
        compOptimalX = 0;
        compOptimalY = 0;
        for (int j = 0; j<8; j++){
            for (int i = 0; i<8; i++){
                boolean nPoints = testN(i,j);
                boolean sPoints = testS(i,j);
                boolean ePoints=testE(i, j);
                boolean wPoints=testW(i, j);
                boolean nePoints=testNE(i, j);
                boolean nwPoints=testNW(i, j);
                boolean sePoints=testSE(i, j);
                boolean swPoints=testSW(i, j);
                boolean givePoints = (moveResults(i,j)>0);
                if ((nPoints||sPoints||ePoints||wPoints||nePoints||nwPoints||sePoints||swPoints) && !spaces[j][i].occupied && givePoints){
                    possiblePoints = true;
                }
                if (possiblePoints){
                    int pointTotal = 0;

                    if (nPoints){
                        pointTotal+=countN(i,j);
                    }
                    if (sPoints){
                        pointTotal+=countS(i,j);
                    }
                    if (ePoints){
                        pointTotal+=countE(i,j);
                    }
                    if (wPoints){
                        pointTotal+=countW(i,j);
                    }
                    if (nePoints){
                        pointTotal+=countNE(i,j);
                    }
                    if (nwPoints){
                        pointTotal+=countNW(i,j);
                    }
                    if (sePoints){
                        pointTotal+=countSE(i,j);
                    }
                    if (swPoints){
                        pointTotal+=countSW(i,j);
                    }
                    if (pointTotal>highestPossibleScore && !spaces[j][i].occupied){
                        highestPossibleScore=pointTotal;
                        compOptimalY=j;
                        compOptimalX=i;
                    }
                }
            }
        }
    }

    public int getCompOptimalX() {
        return compOptimalX;
    }

    public int getCompOptimalY() {
        return compOptimalY;
    }

    //switch sides/turns
    private void switchTurns(){
        if (playersTurn){
            playersTurn = false;
        } else {
            playersTurn =true;
        }
    }
}