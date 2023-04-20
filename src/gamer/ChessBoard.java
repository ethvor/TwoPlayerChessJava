package gamer;


    public class ChessBoard implements Cloneable{
        SquareClass[] squares;

        public SquareClass[] getSquares() {
            return squares;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException{ // this is a deep copy

                ChessBoard deepCopy = (ChessBoard) super.clone();
                deepCopy.setSquares((SquareClass[]) deepCopy.getSquares().clone());
                return deepCopy;

        }

        public void setSquares(SquareClass[] squaresToSet) {
            this.squares = squaresToSet;
        }

        public void modifyBoard(Move.moveSquares moveSquares) {
            SquareClass oldSquare = moveSquares.getOldSquare();
            SquareClass newSquare = moveSquares.getNewSquare();

            newSquare.setPiece(oldSquare.getPiece());
            oldSquare.deletePiece();
        }



        public ChessBoard() {
            this.squares = SquareClass.getSquareList();

            this.squares[0].setPiece(new Piece.pieceType("Rook", 5, "White", false));
            this.squares[1].setPiece(new Piece.pieceType("Knight", 3, "White", false));
            this.squares[2].setPiece(new Piece.pieceType("Bishop", 3, "White", false));
            this.squares[3].setPiece(new Piece.pieceType("Queen", 9, "White", false));
            this.squares[4].setPiece(new Piece.pieceType("King", 0, "White", false));
            this.squares[5].setPiece(new Piece.pieceType("Bishop", 3, "White", false));
            this.squares[6].setPiece(new Piece.pieceType("Knight", 3, "White", false));
            this.squares[7].setPiece(new Piece.pieceType("Rook", 5, "White", false));

            for (int i = 8; i < 16; i++) {
                this.squares[i].setPiece(new Piece.pieceType("Pawn", 1, "White", false));
            }

            for (int i = 48; i < 56; i++) {
                this.squares[i].setPiece(new Piece.pieceType("Pawn", 1, "Black", false));
           }

            this.squares[56].setPiece(new Piece.pieceType("Rook", 5, "Black", false));
            this.squares[57].setPiece(new Piece.pieceType("Knight", 3, "Black", false));
            this.squares[58].setPiece(new Piece.pieceType("Bishop", 3, "Black", false));
            this.squares[59].setPiece(new Piece.pieceType("Queen", 9, "Black", false));
            this.squares[60].setPiece(new Piece.pieceType("King", 0, "Black", false));
            this.squares[61].setPiece(new Piece.pieceType("Bishop", 3, "Black", false));
            this.squares[62].setPiece(new Piece.pieceType("Knight", 3, "Black", false));
            this.squares[63].setPiece(new Piece.pieceType("Rook", 5, "Black", false));

            // this section initializes the board with the pieces in their starting positions










        }
    }

