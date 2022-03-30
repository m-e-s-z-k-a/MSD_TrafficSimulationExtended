import java.util.Random;

public class Point {

    public int type;
    private Point next;
    private Point previous;
    private boolean moved;
    private int speed;
    private int maxSpeed;
    private Point newPosition;
    public int lane;
    public int distance_next_left;
    public int distance_next_right;
    public int distance_prev_left;
    public int distance_prev_right;
    public static Integer[] types = {0, 1, 2, 3, 5};
    private final static double p = 0.2;
    private final static Random random = new Random();


    public int getType()
    {
        return this.type;
    }

    public void move() {

        if ((this.type ==1 || this.type == 2 || this.type == 3) && !this.moved && this.newPosition != null && this.newPosition.type == 0)
        {
            if (this.speed < 5)
            {
                    this.speed ++;
            }
            if (this.lane == 0 && this.speed >= this.distance_next_left)
            {
                this.speed = this.distance_next_left - 1;
            }
            else if (this.lane == 1 && this.speed >= this.distance_next_right)
            {
                this.speed = this.distance_next_right - 1;
            }
            if (!this.newPosition.equals(this))
            {
                this.newPosition.type = this.type;
                this.type = 0;
                this.newPosition.speed = this.speed;
                this.speed = 0;
                this.newPosition.moved = true;
            }
            this.moved = true;
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

    public void setPrevious(Point point)
    {
        this.previous = point;
    }

    public void clicked() {
        this.type = 0;
    }

    public void clear() {
        this.type = 0;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void setMaxSpeed()
    {
        if (this.type == 1)
        {
            this.maxSpeed = 3;
            this.speed = this.maxSpeed;
        }
        else if (this.type == 2)
        {
            this.maxSpeed = 5;
            this.speed = this.maxSpeed;
        }
        else if (this.type == 3)
        {
            this.maxSpeed = 7;
            this.speed = this.maxSpeed;
        }
    }

    public int getMaxSpeed()
    {
        return this.maxSpeed;
    }

    public void setNewPosition(Point a)
    {
        this.newPosition = a;
    }



    public int getSpeed()
    {
        return this.speed;
    }

}

