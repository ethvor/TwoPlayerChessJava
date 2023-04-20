package gamer;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class Legality {

    public static double getMoveSlope(Move.moveSquares moveSquares) {
        SquareClass newSquare = moveSquares.getNewSquare();
        SquareClass oldSquare = moveSquares.getOldSquare();


        //System.out.println("oldSquare: " + oldSquare.getSquareName());
        //System.out.println("newSquare: " + newSquare.getSquareName());

        double rowDifference = newSquare.getSquareRow() - oldSquare.getSquareRow();
        double columnDifference = newSquare.getSquareColumn() - oldSquare.getSquareColumn();

        System.out.println("rowDifference: " + rowDifference);
        System.out.println("columnDifference: " + columnDifference);

        double slope = 0;

        if (columnDifference != 0 && rowDifference != 0) {
            slope = rowDifference / columnDifference;
        } else {

            if (columnDifference == 0) {
                if (rowDifference > 0) {
                    slope = 100;
                } else if (rowDifference < 0) {
                    slope = -100;
                }
            } else if (rowDifference == 0) {
                if (columnDifference > 0) {
                    slope = 50;
                } else if (columnDifference < 0) {
                    slope = -50;
                }
            }

        }
        return slope;
    }

    public static double getMoveDistance(Move.moveSquares moveSquares) {
        SquareClass newSquare = moveSquares.getNewSquare();
        SquareClass oldSquare = moveSquares.getOldSquare();

        double rowDifference = newSquare.getSquareRow() - oldSquare.getSquareRow();
        double columnDifference = newSquare.getSquareColumn() - oldSquare.getSquareColumn();

        double distance = sqrt((rowDifference * rowDifference) + (columnDifference * columnDifference));

        return distance;
    }

    public static Boolean isLegalDistance(Move.moveSquares moveSquares) {
        Boolean isLegalDistance = false;
        double distance = getMoveDistance(moveSquares);
        Piece.pieceType piece = moveSquares.getOldSquare().getPiece();

        if (distance == 0) {
            isLegalDistance = true;
        }

        if (piece.type == "Pawn") {
            if (distance == 1 || distance == sqrt(2) || (distance == 2&& piece.hasMoved == false)) {
                isLegalDistance = true;
            }
        } else if (piece.type == "Knight") {
            if (distance == sqrt(5)) {
                isLegalDistance = true;
            }

        } else if (piece.type == "King") {
            if (distance == 1 || distance == sqrt(2) || distance == 2) {
                isLegalDistance = true;
            }
        } else if (piece.type == "Rook" || piece.type == "Queen" || piece.type == "Bishop") {
            isLegalDistance = true;
        }

        return isLegalDistance;
    }


    public static Boolean isLegal(ChessBoard oldBoard, ChessBoard newBoard, Game.ChessPlayer whitePlayer, Game.ChessPlayer blackPlayer) {
        //isLegal has to check for the following:
        //1. is the piece moving in a legal shape
        //2. is the piece moving in a legal distance
        //3. is the piece moving to a square that is occupied by a piece of the same color
        //4. is the piece moving through a friendly piece

        Boolean isLegal = false;

        Move.moveSquares moveSquares = Move.harvestMoveData(oldBoard, newBoard);
        System.out.println("oldSquare Piece: " + moveSquares.getOldSquare().getPiece().color + " " + moveSquares.getOldSquare().getPiece().type);
        System.out.println("newSquare Piece: " + moveSquares.getNewSquare().getPiece().color + " " + moveSquares.getNewSquare().getPiece().type);



        Piece.pieceType movePiece = moveSquares.getOldSquare().getPiece();
        String movePieceColor = movePiece.color;
        System.out.println("movePieceColor: " + movePieceColor);
        System.out.println("whitePlayer.turn " + whitePlayer.turn);
        System.out.println("blackPlayer.turn " + blackPlayer.turn);


        Boolean isTurn = false;
        if (whitePlayer.color == movePieceColor && whitePlayer.turn) {
            isTurn = true;
        } else if (blackPlayer.color == movePieceColor && blackPlayer.turn) {
            isTurn = true;
        }

        if (isTurn == false) {
            System.out.println("isTurn: " + isTurn);
            return isLegal;
        }


            Boolean isGoodShape = isGoodShape(moveSquares);
        System.out.println("isGoodShape: " + isGoodShape);
        if (isGoodShape == false) {
            return isLegal;
        }


        Boolean isLegalDistance = isLegalDistance(moveSquares);
        System.out.println("isLegalDistance: " + isLegalDistance);
        if (isLegalDistance == false) {
            return isLegal;
        }

        List<SquareClass> path = getPath(oldBoard, newBoard);



        List<SquareClass> intercepts = getIntercepts(path);
        if (intercepts.size() > 0) {
            System.out.println("intercepts.size() > 0");
            return isLegal;
        }



        String oldSquareName = moveSquares.getOldSquare().getSquareName();
        String newSquareName = moveSquares.getNewSquare().getSquareName();
        System.out.println("oldSquareName: " + oldSquareName);
        System.out.println("newSquareName: " + newSquareName);

        movePiece.hasMoved = true;

        moveSquares.destinationSquare.deletePiece();

        isLegal = true;
        System.out.println("end of isLegal");
        return isLegal;
    }


    public static List<SquareClass> getIntercepts(List<SquareClass> path) {
        List<SquareClass> intercepts = new ArrayList<SquareClass>();
        for (SquareClass square : path) {
            if (square.getPiece().type != "NONE") {
                intercepts.add(square);
            }
        }
        return intercepts;
    }

    public static List<SquareClass> getPath(ChessBoard oldBoard, ChessBoard newBoard) {

        Move.moveSquares moveSquares = Move.harvestMoveData(oldBoard, newBoard);

        List<SquareClass> path = new ArrayList<SquareClass>();

        SquareClass[] oldSquares = oldBoard.getSquares();

        Boolean isNotBlocked = false;
        double slope = getMoveSlope(moveSquares);
        double distance = getMoveDistance(moveSquares);
        SquareClass oldSquare = moveSquares.getOldSquare();
        SquareClass newSquare = moveSquares.getNewSquare();
        double rowDifference = newSquare.getSquareRow() - oldSquare.getSquareRow();
        double columnDifference = newSquare.getSquareColumn() - oldSquare.getSquareColumn();
        Piece.pieceType piece = oldSquare.getPiece();
        int destinationIndex = newSquare.getSquareIndex(); //this is the index of the destination square

        if (slope == 100) {
            //iterate upwards
            System.out.println("iterating upwards");
            for (int i = 1; i < distance; i++) {
                int row = oldSquare.getSquareRow() + i;
                int column = oldSquare.getSquareColumn();
                SquareClass square = oldSquares[row * 8 + column];
                path.add(square);
            }
        }
        if (slope == -100) {
            //iterate downwards
            System.out.println("iterating downwards");
            for (int i = 1; i < distance; i++) {
                int row = oldSquare.getSquareRow() - i;
                int column = oldSquare.getSquareColumn();
                SquareClass square = oldSquares[row * 8 + column];
                path.add(square);
            }
        }
        if (slope == 50) {
            //iterate right
            System.out.println("iterating right");
            for (int i = 1; i < distance; i++) {
                int row = oldSquare.getSquareRow();
                int column = oldSquare.getSquareColumn() + i;
                SquareClass square = oldSquares[row * 8 + column];
                path.add(square);
            }
        }
        if (slope == -50) {
            //iterate left
            System.out.println("iterating left");
            for (int i = 1; i < distance; i++) {
                int row = oldSquare.getSquareRow();
                int column = oldSquare.getSquareColumn() - i;
                SquareClass square = oldSquares[row * 8 + column];
                path.add(square);
            }
        }

        if (slope == 1) {

            //iterate up and right
            if (rowDifference > 0 && columnDifference > 0) {
                System.out.println("iterating up and right");

                for (int i = 1; i < 8; i++) {
                    int row = oldSquare.getSquareRow() + i;
                    int column = oldSquare.getSquareColumn() + i;
                    System.out.println("row: " + row);
                    System.out.println("column: " + column);

                    int thisIndex = row * 8 + column;
                    if (thisIndex >= destinationIndex) {
                        break;
                    }
                    System.out.println("thisIndex: " + thisIndex);
                    SquareClass square = oldSquares[thisIndex];
                    path.add(square);
                }

            }
            //iterate down and left
            else if (rowDifference < 0 && columnDifference < 0) {
                System.out.println("iterating down and left");
                for (int i = 1; i < 8; i++) {
                    int row = oldSquare.getSquareRow() + i;
                    int column = oldSquare.getSquareColumn() - i;
                    int thisIndex = row * 8 + column;
                    if (thisIndex >= destinationIndex) {
                        break;
                    }
                    System.out.println("thisIndex: " + thisIndex);
                    SquareClass square = oldSquares[thisIndex];
                    path.add(square);
                }
            }
        }



        if (slope == -1) {
            //iterate down and right
            if (rowDifference < 0 && columnDifference > 0) {
                //iterate down and right
                System.out.println("iterating down and right");
                for (int i = 1; i < 8; i++) {
                    int row = oldSquare.getSquareRow() - i;
                    int column = oldSquare.getSquareColumn() + i;
                    int thisIndex = row * 8 + column;
                    if (thisIndex <= destinationIndex) {

                        break;
                    }
                    System.out.println("thisIndex: " + thisIndex);
                    SquareClass square = oldSquares[thisIndex];
                    path.add(square);
                }



            } else if (rowDifference > 0 && columnDifference < 0) {
                //iterate up and left
                System.out.println("iterating up and left");
                for (int i = 1; i < 8; i++) {
                    int row = oldSquare.getSquareRow() + i;
                    int column = oldSquare.getSquareColumn() - i;
                    int thisIndex = row * 8 + column;
                    if (thisIndex >= destinationIndex) {
                        break;
                    }
                    System.out.println("thisIndex: " + thisIndex);
                    SquareClass square = oldSquares[thisIndex];
                    path.add(square);
                }
            }

        }

        if (path.size() == 0)
        {
            System.out.println("                    path is empty");
        }
        else {
            for (SquareClass square : path) {
                System.out.println("intercept on square: " + square.squarename + " at index: " + square.getSquareIndex() + " --> " + square.getPiece().color + " " + square.getPiece().type);
            }
        }
        return path;

    }


    public static Boolean isGoodShape(Move.moveSquares moveSquares)
    {
        Boolean isGoodShape = false;
        double slope = getMoveSlope(moveSquares);

        SquareClass oldSquare = moveSquares.getOldSquare();
        SquareClass newSquare = moveSquares.getNewSquare();
        Piece.pieceType piece = oldSquare.getPiece();

        System.out.println("slope: " + slope);
        System.out.println("piece: " + piece.type);

        if (slope == 0) {
            isGoodShape = true;
        }

        if(piece.type == "Pawn")
        {
            if(slope == 100 || slope == -100 || slope == 1 || slope == -1)
            {
                isGoodShape = true;
            }
        }
        else if(piece.type == "Rook")
        {
            if(slope == 50 || slope == -50 || slope == 100 || slope == -100)
            {
                isGoodShape = true;
            }
        }
        else if(piece.type == "Knight")
        {
            if(slope == 2 || slope == -2 || slope == 0.5 || slope == -0.5)
            {
                isGoodShape = true;
            }
        }
        else if(piece.type == "Bishop")
        {
            if(slope == 1 || slope == -1)
            {
                isGoodShape = true;
            }
        }
        else if(piece.type == "Queen")
        {
            if(slope == 50 || slope == -50 || slope == 100 || slope == -100 || slope == 1 || slope == -1)
            {
                isGoodShape = true;
            }
        }
        else if(piece.type == "King")
        {
            if(slope == 50 || slope == -50 || slope == 100 || slope == -100 || slope == 1 || slope == -1)
            {
                isGoodShape = true;
            }
        }
        else
        {
            System.out.println("piece type not recognized");
        }


        return isGoodShape;
    }
}