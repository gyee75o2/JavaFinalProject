package bullet;

import common.AuthorAnno;
import creature.Creature;

@AuthorAnno(author = "何峰彬")
public class HealingTrackingBulletFactory implements BulletFactory<TrackingBullet> {
    public TrackingBullet getBullet(Creature shooter, Creature target){
        TrackingBullet bullet = new TrackingBullet(shooter, target);
        bullet.damage = -shooter.getAttack();
        bullet.speed = 5;
        return bullet;
    }
}
