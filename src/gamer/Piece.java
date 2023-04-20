package gamer;

public class Piece {
    public static class pieceType implements Cloneable{

        String type = "NONE";
        int value = 0;

        String color = "NONE";

        
        Boolean hasMoved = false;
        
        
        
        
        
        public pieceType(String type, int value, String color, Boolean hasMoved) {
            this.hasMoved = hasMoved;
            this.type = type;
            this.value = value;
            this.color = color;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
                return super.clone();

        }
    }
}
