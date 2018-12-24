package bullet;

import creature.Creature;
import javafx.geometry.Point2D;

public class TrackingBullet extends Bullet {
    public TrackingBullet(Creature shooter, Creature target){
        super(shooter, target);
        assert(target != null);
    }

    @Override
    public void move(int millisec) {
        Point2D vector = new Point2D(target.getPosition().getX()+IMAGE_SIZE*0.5 - x, target.getPosition().getY()+IMAGE_SIZE*0.5 - y);
        vector = vector.normalize();
        vector = vector.multiply(speed*IMAGE_SIZE*millisec/1000.0);
        x+=vector.getX();
        y+=vector.getY();
    }
}
