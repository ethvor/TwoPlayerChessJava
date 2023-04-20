package gamer;

public class Move {

    public static class moveSquares{
        SquareClass oldSquare;
        SquareClass newSquare;
        SquareClass destinationSquare;

        public moveSquares(SquareClass oldSquare, SquareClass newSquare, SquareClass destinationSquare)
        {
            this.oldSquare = oldSquare;
            this.newSquare = newSquare;
            this.destinationSquare = destinationSquare;
        }
      //  {
       //     this.oldSquare = oldSquare;
      //      this.newSquare = newSquare;
      //  }

        public SquareClass getOldSquare()
        {
            return this.oldSquare;
        }

        public SquareClass getNewSquare()
        {
            return this.newSquare;
        }

        public SquareClass getDestinationSquare() {return this.destinationSquare;}

        public void setOldSquare(SquareClass oldSquare)
        {
            this.oldSquare = oldSquare;
        }

        public void setNewSquare(SquareClass newSquare)
        {
            this.newSquare = newSquare;
        }
    }


    public static moveSquares harvestMoveData(ChessBoard oldBoard, ChessBoard newBoard) {
        Boolean foundOldSquare = false;
        Boolean foundNewSquare = false;

        SquareClass oldSquare = new SquareClass("z", "0");
        SquareClass newSquare = new SquareClass("z", "1");
        SquareClass destinationSquare = new SquareClass("z", "2");

        for (int i = 0; i <= 63; i++) {
            if ((oldBoard.squares[i].piece.type != newBoard.squares[i].piece.type) ||
                    (oldBoard.squares[i].piece.type == newBoard.squares[i].piece.type &&
                            oldBoard.squares[i].piece.color != newBoard.squares[i].piece.color)) {

                if (oldBoard.squares[i].isEmpty() && !newBoard.squares[i].isEmpty()) {
                    destinationSquare = oldBoard.squares[i];
                    newSquare = newBoard.squares[i];
                    foundNewSquare = true;
                }

                if (!oldBoard.squares[i].isEmpty() && newBoard.squares[i].isEmpty()) {
                    oldSquare = oldBoard.squares[i];
                    foundOldSquare = true;
                }

                if (!oldBoard.squares[i].isEmpty() && !newBoard.squares[i].isEmpty() &&
                        oldBoard.squares[i].piece.color != newBoard.squares[i].piece.color) {
                    oldSquare = oldBoard.squares[i];
                    destinationSquare = oldBoard.squares[i];
                    newSquare = newBoard.squares[i];
                    foundOldSquare = true;
                    foundNewSquare = true;
                }
            }
        }

        if (foundNewSquare && foundOldSquare) {
            return new moveSquares(oldSquare, newSquare, destinationSquare);
        } else {
            System.out.println("harvestMoveData found no difference between old and new board");
            return new moveSquares(oldBoard.squares[0], newBoard.squares[0], oldBoard.squares[0]);
        }
    }
/*
public static void main(String[] args) { //debug zone


    Board.ChessBoard initialBoard = new Board.ChessBoard();
    Board.ChessBoard nextBoard = new Board.ChessBoard();

    //oldIndex
    //newIndex
    int oldIndex = 62;
    int newIndex = 45;


    moveSquares pawnA2toA3 = new moveSquares(nextBoard.squares[oldIndex], nextBoard.squares[newIndex],initialBoard.squares[newIndex]);


    nextBoard.modifyBoard(pawnA2toA3);


    //moveType move1 = new moveType(initialBoard, nextBoard);

    moveSquares test = harvestMoveData(initialBoard, nextBoard);

    Square.SquareClass newSquare = test.getNewSquare();
    Square.SquareClass oldSquare = test.getOldSquare();


    System.out.println("oldSquare: " + oldSquare.getSquareName());
    System.out.println("newSquare: " + newSquare.getSquareName());

    Boolean isLegal = Legality.isLegal(initialBoard, nextBoard);
    System.out.println("isLegal: " + isLegal);

}
*/
}

