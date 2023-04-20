package gamer;

import gamer.Game.ChessPlayer;

public class ChessGame {
    ChessBoard thisBoard; //board containing the current state of the game

    ChessBoard possibleBoard; //board containing the possible new state of the game
    ChessPlayer whitePlayer;
    ChessPlayer blackPlayer;

    public ChessGame() {
        thisBoard = new ChessBoard();
        whitePlayer = new ChessPlayer("White", true);
        blackPlayer = new ChessPlayer("Black", false);
    }
    public void setThisBoard(ChessBoard thisBoard) {
        this.thisBoard = thisBoard;
        //System.out.println("CALLED");
    }

    public ChessBoard getThisBoard() {
        return thisBoard;
    }

    public void setPossibleBoard(ChessBoard possibleBoard) {
        this.possibleBoard = possibleBoard;
    }

    public ChessBoard getPossibleBoard() {
        return possibleBoard;
    }
}
