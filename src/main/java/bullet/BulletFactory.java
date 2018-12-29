package bullet;

import common.AuthorAnno;
import creature.Creature;

@AuthorAnno(author = "何峰彬")
public interface BulletFactory<T extends Bullet> {
    T getBullet(Creature shooter, Creature target);
}
