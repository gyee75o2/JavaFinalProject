package bullet;

import creature.Creature;
import javafx.geometry.Point2D;

public class HorizontalBullet extends Bullet {
    HorizontalBullet(Creature shooter){
        super(shooter, null);
    }

    @Override
    public void move(int millisec){
        Point2D vector = new Point2D(1, 0);
        if(toLeft)
            vector = vector.multiply(-1);
        vector = vector.multiply(millisec*IMAGE_SIZE*speed / 1000.0);
        x+=vector.getX();
        y+=vector.getY();
    }
}
