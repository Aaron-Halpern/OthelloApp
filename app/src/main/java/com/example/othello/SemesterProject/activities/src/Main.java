import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        Board gameBoard = new Board();
        gameBoard.setBoard();
        gameBoard.displayBoard();
        System.out.println("would you like to play as white ("+gameBoard.getWhitePiece()+") or black ("+gameBoard.getBlackPiece()+")? "+gameBoard.getBlackPiece()+"/"+gameBoard.getWhitePiece());
        String playerPieceSelection = keyboard.nextLine();
        gameBoard.playerPieceChoice(playerPieceSelection);
        System.out.println("White goes 1st");

        //get move
        boolean keepGoing = true;
        boolean quit = false;
        while (keepGoing) {
            boolean gameContinue = gameBoard.getLegalMove();
            while (gameContinue) {

                //initialize variables
                int downSelection = 0;
                int rightSelection = 0;
                int userSelection;


                //check whose turn it is
                if (gameBoard.getPlayersTurn()) {
                    System.out.println("type quit to forfeit the game");
                    System.out.println("where would you like to go?\ninput how many spaces to the right, then how many down\nRight:");

                    //input validation
                    String userInput = keyboard.nextLine();
                    boolean validDataType = gameBoard.forceValidNumericInput(userInput);
                    while (!validDataType) {
                        System.out.println("That isn't a valid selection\ntry again, Right:");
                        userInput = keyboard.nextLine();
                        validDataType = gameBoard.forceValidNumericInput(userInput);
                    }
                    if (userInput.equalsIgnoreCase("quit")) {
                        quit = true;
                    } else {
                        userSelection = Integer.parseInt(userInput);
                        rightSelection = userSelection - 1;

                        System.out.println("Down:");
                        userInput = keyboard.nextLine();
                        validDataType = gameBoard.forceValidNumericInput(userInput);
                        while (!validDataType) {
                            System.out.println("That isn't a valid selection\ntry again, Down");
                            userInput = keyboard.nextLine();
                            validDataType = gameBoard.forceValidNumericInput(userInput);
                        }
                        if (userInput.equalsIgnoreCase("quit")) {
                            quit = true;
                        } else {
                            userSelection = Integer.parseInt(userInput);
                            downSelection = userSelection - 1;

                            boolean validMove = gameBoard.CheckValidMove(rightSelection, downSelection);
                            while (!validMove) {
                                System.out.println("That is not a valid selection\nTry again");
                                System.out.println("how many spaces to the right?");

                                userInput = keyboard.nextLine();
                                validDataType = gameBoard.forceValidNumericInput(userInput);
                                while (!validDataType) {
                                    System.out.println("That isn't a valid selection\ntry again, Right:");
                                    userInput = keyboard.nextLine();
                                    validDataType = gameBoard.forceValidNumericInput(userInput);
                                }
                                if (userInput.equalsIgnoreCase("quit")) {
                                    quit = true;
                                } else {
                                    userSelection = Integer.parseInt(userInput);
                                    rightSelection = userSelection - 1;
                                    System.out.println("How many spaces down?");

                                    userInput = keyboard.nextLine();
                                    validDataType = gameBoard.forceValidNumericInput(userInput);
                                    while (!validDataType) {
                                        System.out.println("That isn't a valid selection\ntry again, Down:");
                                        userInput = keyboard.nextLine();
                                        validDataType = gameBoard.forceValidNumericInput(userInput);
                                    }
                                    if (userInput.equalsIgnoreCase("quit")) {
                                        quit = true;
                                    } else {
                                        userSelection = Integer.parseInt(userInput);
                                        downSelection = userSelection - 1;
                                        validMove = gameBoard.CheckValidMove(rightSelection, downSelection);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("\nComputer's turn\n");
                    gameBoard.compOptimalMoveTest();
                    rightSelection = gameBoard.getCompOptimalX();
                    downSelection = gameBoard.getCompOptimalY();
                    System.out.println("computer is going " + (rightSelection + 1) + " to the right and " + (downSelection + 1) + " down");
                }


                //need to place piece, then flip resulting pieces, then test if game is over
                //if not, switch turn
                if (quit) {
                    gameContinue = false;
                }
                if (gameContinue) {
                    gameBoard.placePiece(rightSelection, downSelection);
                    gameBoard.flipPieces(rightSelection, downSelection);

                    gameBoard.displayBoard();

                    //test if the next turn has any legal moves, it also switches the turn tracker
                    gameContinue = gameBoard.setLegalMove();
                }

            }


            //display results
            System.out.println("game over");
            if (!quit) {
                gameBoard.gameResults();
            } else {
                System.out.println("you forfeited the game");
                gameBoard.forfeitGameResult();
            }
                //check if user wants to play another game

                System.out.println("do you want to play another game? y/n");
                String playAnotherGame = keyboard.nextLine();
                while (!(playAnotherGame.equalsIgnoreCase("y")) && !(playAnotherGame.equalsIgnoreCase("n"))) {
                    System.out.println("that isn't a valid selection, try again");
                    playAnotherGame = keyboard.nextLine();
                }
                if (playAnotherGame.equalsIgnoreCase("n")) {
                    keepGoing = false;
                } else if (playAnotherGame.equalsIgnoreCase("y")) {
                    System.out.println("What color would you like to play as? ("+gameBoard.getWhitePiece()+"/"+gameBoard.getBlackPiece()+")  ");
                    playerPieceSelection = keyboard.nextLine();
                    gameBoard.playerPieceChoice(playerPieceSelection);

                    gameBoard.setBoard();
                    gameBoard.displayBoard();
                    System.out.println("Hit enter to start the next game");
                    keyboard.nextLine();
                }

            }
        }
    }
