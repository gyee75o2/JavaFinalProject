package bullet;

import creature.Creature;

public class StraightBulletFactory implements BulletFactory<StraightBullet> {
    public StraightBullet getBullet(Creature shooter, Creature target){
        StraightBullet bullet = new StraightBullet(shooter, target);
        bullet.setDamage(shooter.getAttack());
        return bullet;
    }
}
