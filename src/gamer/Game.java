package gamer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gamer.Piece.pieceType;

public class Game {
   

	
	
	
	
        public static class ChessPlayer {
            String color;
            Boolean turn;

            public ChessPlayer(String color, Boolean turn) {
                this.color = color;
                this.turn = turn;
            }
        }

        public static class gameOperations{
        	
        	Renderer r = new Renderer();
        	
        	
        	
            public void startGameLoop(ChessGame game) throws CloneNotSupportedException {

                ChessPlayer whitePlayer = game.whitePlayer;
                ChessPlayer blackPlayer = game.blackPlayer;

                System.out.println("Initial Render");
                r.renderBoard(game.getThisBoard());


                //Main Game Loop
                while (victoryConditions(game) == "NONE")
                {
                    //get user input for move
                    ChessBoard newBoard = userInput.getMove(game.getThisBoard(), whitePlayer, blackPlayer);

                    //check if move is legal
                    Boolean Legal = Legality.isLegal(game.getThisBoard(), newBoard, whitePlayer, blackPlayer);
                    System.out.println("Legal: " + Legal);
                    
                    System.out.println("Old board: " + game.getThisBoard());
                    System.out.println("New board: " + newBoard);
                    


                    if(Legal)
                    {
                        System.out.println("Legal move!");
                      //  game.setThisBoard(newBoard);
                        r.renderBoard(game.getThisBoard());
                        updateTurns(whitePlayer, blackPlayer);

                    }

                    else
                    {
                    	game.setThisBoard(newBoard);
                        System.out.println("Illegal move, try again");
                        r.renderBoard(game.getThisBoard());

                    }

                }
                // End of Main Game Loop



                String winner = victoryConditions(game);
                if (winner == "WHITE") {
                    System.out.println("\n\nWhite wins!");
                } else if (winner == "BLACK") {
                    System.out.println("\n\nBlack wins!");
                }
            }

            private void updateTurns(ChessPlayer whitePlayer, ChessPlayer blackPlayer) {
                if (whitePlayer.turn) {
                    whitePlayer.turn = false;
                    blackPlayer.turn = true;
                } else if (blackPlayer.turn) {
                    whitePlayer.turn = true;
                    blackPlayer.turn = false;
                }
            }

            public String victoryConditions(ChessGame game) {
                //check if king exists
                ChessBoard thisBoard = game.thisBoard;
                List<Piece.pieceType> kingList = new ArrayList<>();

                SquareClass[] squares = thisBoard.getSquares();

                for (int i = 0; i < 64; i++) {
                    if (squares[i].getPiece().type == "King") {
                        kingList.add(squares[i].getPiece());
                    }
                }

                if (kingList.size() == 2) {
                    return "NONE";
                } else {
                    for (int i = 0; i < kingList.size(); i++) {
                        Piece.pieceType King = kingList.get(i);
                        if (King.color == "White") {
                            return "WHITE";
                        } else if (King.color == "Black") {
                            return "BLACK";
                        }
                    }
                }

                return "NONE";
            }


            public static class userInput {
                public static ChessBoard getMove(ChessBoard board, ChessPlayer whitePlayer, ChessPlayer blackPlayer) throws CloneNotSupportedException {

                	
                	
                	
                	
                    Scanner scanner = new Scanner(System.in);

                    String move = "NONE";
                    if (whitePlayer.turn) {
                        //wait for white player to make a move
                        //return the new board
                        System.out.println("\nWhite player, please make a move by entering the coordinates of the piece you want to move, followed by the coordinates of the square you want to move it to. For example, to move the piece at a2 to a4, type 'a2a4'.");
                        move = scanner.nextLine();
                    } else {
                        //wait for black player to make a move
                        //return the new board
                        System.out.println("\nBlack player, please make a move by entering the coordinates of the piece you want to move, followed by the coordinates of the square you want to move it to. For example, to move the piece at a2 to a4, type 'a2a4'.");
                        move = scanner.nextLine();
                    }

                    String oldSquareStr = move.substring(0, 2);
                    String newSquareStr = move.substring(2, 4);

                    int oldIndex = getIndex(oldSquareStr);
                    int newIndex = getIndex(newSquareStr);
                    System.out.println(oldIndex);
                    System.out.println(newIndex);

                    SquareClass[] squares = board.getSquares();
                    SquareClass oldSquare = squares[oldIndex];
                    SquareClass newSquare = squares[newIndex];
                    SquareClass destinationSquare = squares[newIndex];

                    System.out.println("Old Square: " + oldSquare.getPiece().color + " " + oldSquare.getPiece().type + " at " + oldSquare.getSquareName());
                    System.out.println("New Square: " + newSquare.getPiece().color + " " + newSquare.getPiece().type + " at " + newSquare.getSquareName());

                    Move.moveSquares moveSquares = new Move.moveSquares(oldSquare, newSquare, destinationSquare);

                    
                    
                    
                    
                    
                   // ChessBoard boardClone = (ChessBoard) board.clone();
                    
                   ChessBoard boardClone = new ChessBoard();
                   squares = board.getSquares();
                   SquareClass[] squaresCopy = boardClone.getSquares();
                   
                   
                   for (int i = 0; i < squares.length; i++) {
                	   squaresCopy[i] = new SquareClass(squares[i].getRowName(), squares[i].getColName());
                	   
                	   
                	  // Piece np = new 
                	   
                	 //  squaresCopy[i].setPiece();
                	   
                	   pieceType old = squares[i].getPiece();
                	   
                	   pieceType pt = new pieceType(old.type, old.value, old.color, old.hasMoved);
                	   squaresCopy[i].setPiece(pt);
                	   
                	   
                   }
                   
                   boardClone.setSquares(squaresCopy);
                   

                   
                   
                   boardClone.modifyBoard(moveSquares);
                   
                   
                    
                  //  boardClone.modifyBoard(moveSquares);

                    
                    System.out.println("Old Square: " + squares[oldIndex].getPiece().color + " " + squares[oldIndex].getPiece().type + " at " + squares[oldIndex].getSquareName());
                    System.out.println("New Square: " + squares[newIndex].getPiece().color + " " + squares[newIndex].getPiece().type + " at " + squares[newIndex].getSquareName());
                    
                    
                    
                    System.out.println("oldboard: " + board);
                    System.out.println("newboard: " + boardClone);
                    
                    
                    
                    System.out.println(board.getSquares()[8].getPiece().type);
                    System.out.println(boardClone.getSquares()[8].getPiece().type);
                    
                    
                    return boardClone;
                }

                public static int getIndex(String square) {
                    List<String> letters = new ArrayList<String>();
                    letters.add("a");
                    letters.add("b");
                    letters.add("c");
                    letters.add("d");
                    letters.add("e");
                    letters.add("f");
                    letters.add("g");
                    letters.add("h");

                    List<String> numbers = new ArrayList<String>();
                    numbers.add("1");
                    numbers.add("2");
                    numbers.add("3");
                    numbers.add("4");
                    numbers.add("5");
                    numbers.add("6");
                    numbers.add("7");
                    numbers.add("8");

                    String letter = square.substring(0, 1);
                    String number = square.substring(1, 2);
                    int letterIndex = letters.indexOf(letter);
                    int numberIndex = numbers.indexOf(number);
                    int index = numberIndex * 8 + letterIndex;
                    return index;
                }

            }
        }
}
