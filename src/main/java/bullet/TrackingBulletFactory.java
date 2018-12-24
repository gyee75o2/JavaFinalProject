package bullet;

import creature.Creature;

public class TrackingBulletFactory implements BulletFactory<TrackingBullet> {
    public TrackingBullet getBullet(Creature shooter, Creature target){
        TrackingBullet bullet =  new TrackingBullet(shooter, target);
        bullet.setDamage(shooter.getAttack());
        return bullet;
    }
}