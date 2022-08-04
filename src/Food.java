
public class Food {
    public int x;
    public int y;
    private Snake snake;
    public Food(Snake snake){
        this.snake= snake;
    }
    public void spawn(){
        this.x = (int)Math.floor(Math.random()*Main.boardSize);
        this.y =  (int)Math.floor(Math.random()*Main.boardSize);
        final int finalX = this.x;
        final int finalY = this.y;
        snake.body.forEach(e -> {
            if(e.x == finalX && e.y == finalY) spawn();
        });
    }
}
