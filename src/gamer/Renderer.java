package gamer;

public class Renderer {

    public SquareClass[] mirrorRowsInBoard(ChessBoard board){
        SquareClass[] squares = board.getSquares();
        SquareClass[] flippedSquares = new SquareClass[64];
        //make a copy of the squares array
        //next, flip each row: 7 6 5 4 3 2 1 0 becomes 0 1 2 3 4 5 6 7


        SquareClass[] thisRow = new SquareClass[8];
        SquareClass[] flippedRow = new SquareClass[8];

        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                thisRow[j] = squares[i*8 + j];
            }
            for(int j = 0; j < 8; j++)
            {
                flippedRow[j] = thisRow[7-j];
            }
            for(int j = 0; j < 8; j++)
            {
                flippedSquares[i*8 + j] = flippedRow[j];
            }
        }

        return flippedSquares;
    }
    public void renderBoard(ChessBoard board) {

        //Square.SquareClass[] squares = board.squares;
        SquareClass[] squares = mirrorRowsInBoard(board);



        for (int i = 63; i >= 0; i--) {
            if (i % 8 == 7) {
                System.out.print(" " + (i / 8 + 1) + " ");
            }
            if (squares[i].getPiece().type == "NONE") {
                System.out.print("[  ]");
            } else {
                String colorStr = squares[i].getPiece().color;
                String typeStr = squares[i].getPiece().type;
                Character colorChar = colorStr.charAt(0);
                if (colorChar == 'W') {
                    colorChar = 'w';
                } else {
                    colorChar = 'b';
                }
                Character typeChar = typeStr.charAt(0);
                if(typeStr == "Knight")
                {
                    typeChar = 'N';
                }
                String pieceStr = '[' + colorChar.toString() + typeChar.toString() + ']';
                System.out.print(pieceStr);
            }
            if (i % 8 == 0) {
                System.out.println();
            }
        }
        System.out.print("     a   b   c   d   e   f   g   h");
    }
}