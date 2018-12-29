package bullet;

import common.AuthorAnno;
import creature.Creature;

@AuthorAnno(author = "何峰彬")
public class StraightBulletFactory implements BulletFactory<StraightBullet> {
    public StraightBullet getBullet(Creature shooter, Creature target){
        StraightBullet bullet = new StraightBullet(shooter, target);
        bullet.setDamage(shooter.getAttack());
        return bullet;
    }
}
