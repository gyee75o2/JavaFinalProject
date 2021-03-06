package bullet;

import common.AuthorAnno;
import creature.Creature;

@AuthorAnno(author = "何峰彬")
public class HorizontalBulletFactory implements BulletFactory<HorizontalBullet> {
    public HorizontalBullet getBullet(Creature shooter, Creature target){
        HorizontalBullet bullet = new HorizontalBullet(shooter);
        bullet.setToLeft(target.getPosition().getJ() - shooter.getPosition().getJ() < 0);
        bullet.setDamage(shooter.getAttack());
        return bullet;
    }
}
