import java.util.Random;

public class Point {

    private int type;
    private Point next;
    private boolean moved;
    private int speed;
    private final static double p = 0.2;
    private final static Random random = new Random();


    public int getType()
    {
        return this.type;
    }

    public void move() {

        if (this.type ==1 && this.moved == false)
        {
            if (this.speed < 5)
            {
                if (this.speed > 1 && random.nextInt((int) (1 / p)) == 1)
                {
                this.speed --;
                }
                else
                {
                    this.speed ++;
                }
            }
            Point current = this;
            for (int i = speed-1; i >= 0; i--)
            {
                if (current.next.type == 1)
                {
                    this.speed = i;
                    break;
                }
                current = current.next;
            }
            this.type = 0;
            this.moved = true;
            current.type = 1;
            current.moved = true;
            current.speed = this.speed;
            this.speed = 0;

        }
    }

    public void setMoved(boolean x)
    {
        this.moved = x;
    }

    public void setNext(Point point)
    {
        this.next = point;
    }

    public void clicked() {
        this.type = 1;
    }

    public void clear() {
        this.type = 0;
    }
}

