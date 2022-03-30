import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points = new Point[][]{};
    private int size = 25;
    public int editType = 0;


    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }


    public void calculateDistances(int x, int y)
    {
        //0 - lewy pas, 1 - prawy
        if (y == 2)
        {
            points[x][y].lane = 0;
        }
        if (y == 3)
        {
            points[x][y].lane = 1;
        }
        int a, b;
        if (points[x][y].lane == 0)
        {
            a = x+1;
            b = y;
        }
        else
        {
            a = x+1;
            b = y -1;
        }
        int dist = 0;
        if (a >= points.length)
        {
            a = 0;
        }
        while (a != x && points[a][b].type != 1 && points[a][b].type != 2 && points[a][b].type != 3)
        {
            a += 1;
            dist += 1;
            if (a >= points.length)
            {
                a = 0;
            }
        }
        points[x][y].distance_next_left = dist;
        dist = 0;
        a = x - 1;
        if (a <= -1)
        {
            a = points.length-1;
        }
        while (a != x && points[a][b].type != 1 && points[a][b].type != 2 && points[a][b].type != 3)
        {
            a -= 1;
            dist += 1;
            if (a <= -1)
            {
                a = points.length-1;
            }
        }
        points[x][y].distance_prev_left = dist;
        dist = 0;

        y += 1;

        a = x+1;
        if (a >= points.length)
        {
            a = 0;
        }
        while (a != x && points[a][b].type != 1 && points[a][b].type != 2 && points[a][b].type != 3)
        {
            a += 1;
            dist += 1;
            if (a >= points.length)
            {
                a = 0;
            }
        }
        points[x][y].distance_next_right = dist;
        dist = 0;
        a = x - 1;
        if (a <= -1)
        {
            a = points.length-1;
        }
        while (a != x && points[a][b].type != 1 && points[a][b].type != 2 && points[a][b].type != 3)
        {
            a -= 1;
            dist += 1;
            if (a <= -1)
            {
                a = points.length-1;
            }
        }
        points[x][y].distance_prev_right = dist;
    }

    public boolean backConditionsMet(int x, int y)
    {
        if (y == 2)
        {
            boolean cond1 = points[x][y].distance_prev_right >= points[x][y].getMaxSpeed();
            boolean cond2 = points[x][y].distance_prev_left <= points[x][y].getMaxSpeed();
            boolean cond3 = points[x][y].distance_next_right >= points[x][y].getSpeed();
            return cond1 && cond2 && cond3;
        }
        else
        {
            return false;
        }
    }


    public boolean bypassingConditionsMet(int x, int y)
    {
        if (y == 3)
        {
        boolean cond1 = points[x][y].getSpeed() < points[x][y].getMaxSpeed();
        boolean cond2 = points[x][y].distance_prev_left >= points[x][y].getMaxSpeed();
        boolean cond3 = points[x][y].distance_prev_right >= points[x][y].getMaxSpeed();
        boolean cond4 = points[x][y].distance_next_left >= points[x][y].getSpeed();
        return cond1 && cond2 && cond3 && cond4;
        }
        else
        {
            return false;
        }
    }


    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
                if (y == 0 || y == 1 || y == 4 || y == 5)
                {
                    points[x][y].type = 5;
                }
            }
        }
        for (int x = 0; x < points.length; ++x)
        {
            for (int y = 0; y < points[x].length; ++y)
            {
               if (x == points.length-1)
                {
                    points[x][y].setNext(points[0][y]);
                }
                else
                {
                points[x][y].setNext(points[x+1][y]);
                }
                if (x == 0)
                {
                    points[x][y].setPrevious(points[points.length-1][y]);
                }
                else
                {
                    points[x][y].setNext(points[x-1][y]);
                }
            }
        }
    }

    public void iteration() {

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y)
            {
                points[x][y].setMoved(false);
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y)
            {
                if (points[x][y].getType() == 1 || points[x][y].getType() == 2 || points[x][y].getType() == 3)
                {
                    calculateDistances(x, y);
                }
            }}

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y)
            {
                if (points[x][y].getType() == 1 || points[x][y].getType() == 2 || points[x][y].getType() == 3)
                {
                    int new_x_coord = x + points[x][y].getSpeed();
                    if (new_x_coord >= points.length)
                    {
                        new_x_coord = new_x_coord - points.length;
                    }
                    if (new_x_coord == -1)
                    {
                        new_x_coord = points.length - 1;
                    }
                    if (backConditionsMet(x, y))
                    {
                        points[x][y].setNewPosition(points[new_x_coord][y+1]);
                    }
                    else if (bypassingConditionsMet(x, y))
                    {
                        points[x][y].setNewPosition(points[new_x_coord][y-1]);
                    }
                    else
                    {
                        points[x][y].setNewPosition(points[new_x_coord][y]);
                    }

                }
            }
        }
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y)
            {
                if (points[x][y].type == 1 || points[x][y].type == 2 || points[x][y].type == 3){
                points[x][y].move();}
            }
        }

        this.repaint();
    }



    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
            }
        this.repaint();
    }


    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                float a = 1.0F;
                if (points[x][y].getType() == 0)
                {
                    g.setColor(new Color(255, 255, 255));
                }
                else if (points[x][y].getType() == 1)
                {
                g.setColor(new Color(255, 255, 0));
                }
                else if (points[x][y].getType() == 2)
                {
                    g.setColor(new Color(0, 0, 255));
                }
                else if (points[x][y].getType() == 3)
                {
                    g.setColor(new Color(255, 0, 0));
                }
                else if (points[x][y].getType() == 5)
                {
                    g.setColor(new Color(0, 255, 0));
                }
                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if(editType==0){
                points[x][y].clicked();
            }
            else {
                points[x][y].type= editType;
                points[x][y].setMaxSpeed();
            } this.repaint();
        }
    }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if(editType==0){
                points[x][y].clicked();
            }
            else {
                points[x][y].type= editType;
                points[x][y].setMaxSpeed();
            } this.repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
