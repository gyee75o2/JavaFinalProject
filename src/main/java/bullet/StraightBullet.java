package bullet;

import creature.Creature;
import javafx.geometry.Point2D;

public class StraightBullet extends Bullet {
    private Point2D direction;

    public StraightBullet(Creature shooter, Creature target){
        super(shooter, target);
        assert(target != null);
        direction = new Point2D(target.getPosition().getX()+IMAGE_SIZE*0.5 - x, target.getPosition().getY()+IMAGE_SIZE*0.5 - y);
        direction = direction.normalize();
    }

    @Override
    public void move(int millisec) {
        Point2D vector = direction.multiply(speed*IMAGE_SIZE*millisec/1000.0);
        x+=vector.getX();
        y+=vector.getY();
    }
}
