package gamer;

public class SquareClass implements Cloneable{
    String row;
    String col;
    Piece.pieceType piece;

    String squarename;

    public SquareClass(String row, String col) {
        this.row = row;
        this.col = col;
        this.squarename = col + row;
        this.piece = new Piece.pieceType("NONE", 0, "NONE", false);
    }

    public void setPiece(Piece.pieceType piece) {
        this.piece = piece;
    }

    public void deletePiece() {
        this.piece = new Piece.pieceType("NONE", 0, "NONE", false);
    }

    public Piece.pieceType getPiece() {
        return this.piece;
    }

    public Boolean isEmpty()
    {
        if (this.piece.type == "NONE")
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static SquareClass[] getSquareList() {
        String[] colnames = getColNames();
        String[] rownames = getRowNames();
        SquareClass[] squareList = new SquareClass[64];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String thisString = rownames[i] + colnames[j];
                squareList[j + i * 8] = new SquareClass(rownames[i], colnames[j]);

            }
        }
        return squareList;

    }

    public static String[] getColNames() {
        String[] colnames = {"a", "b", "c", "d", "e", "f", "g", "h"};
        return colnames;
    }

    public static String[] getRowNames() {
        String[] rownames = {"1", "2", "3", "4", "5", "6", "7", "8"};
        return rownames;
    }

    public String getSquareName() {
        return squarename;
    }

    public int getSquareRow() {
        String[] rowArray = getRowNames();
        for (int i = 0; i < 8; i++) {
            if (rowArray[i] == this.row) {
                return i;
            }
        }
        return 0;
    }

    public int getSquareColumn() {
        String[] colArray = getColNames();
        for (int i = 0; i < 8; i++) {
            if (colArray[i] == this.col) {
                return i;
            }
        }
        return 0;
    }

    public int getSquareIndex() {
        int row = this.getSquareRow();
        int col = this.getSquareColumn();
        return col + row * 8;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
            SquareClass clone = (SquareClass) super.clone();
            clone.setPiece((Piece.pieceType)clone.getPiece().clone());
            return clone;


    }
    
    public String getRowName() {
    	return row;
    }
    
    public String getColName() {
    	return col;
    }
    
    
}