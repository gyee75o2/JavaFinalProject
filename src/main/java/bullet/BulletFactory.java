package bullet;

import creature.Creature;

public interface BulletFactory<T extends Bullet> {
    T getBullet(Creature shooter, Creature target);
}
