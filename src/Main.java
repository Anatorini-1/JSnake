public class Main {
    public static int boardSize = 10;
    public static boolean isOver = false;
    public static void main(String[] args) throws InterruptedException {
        var snake = new Snake();
        var food = new Food(snake);
        var board = new Board(snake,food);
        snake.food = food;

        while(true){
            if(!isOver && board.hasFocus()){
                snake.move();
            }
            board.repaint();

            Thread.sleep(200 - snake.body.size());
        }
    }
}