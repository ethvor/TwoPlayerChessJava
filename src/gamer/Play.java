package gamer;



public class Play {
    public static void main(String[] args) throws CloneNotSupportedException {

    ChessGame game = new ChessGame();
    Game.gameOperations gameOps = new Game.gameOperations();
    gameOps.startGameLoop(game);

    }
}