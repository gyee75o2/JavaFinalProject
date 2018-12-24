package bullet;

import logger.Recorder;
import battle.BattleField;
import creature.Creature;
import javafx.scene.canvas.GraphicsContext;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BulletManager {
    private final List<Bullet> bullets = new LinkedList<>();
    private final BattleField battleField;

    public BulletManager(BattleField battleField){
        this.battleField = battleField;
    }

    public void addBullet(Bullet bullet){
        synchronized (bullets){
            bullets.add(bullet);
        }
    }

    /**
     *
     * @param millisec time gap since last move
     */
    public void move(int millisec){
        synchronized (bullets) {
            Iterator<Bullet> iter = bullets.iterator();
            while(iter.hasNext()){
                Bullet bullet = iter.next();
                bullet.move(millisec);

                if(bullet instanceof TrackingBullet && bullet.target!=null && bullet.target.isDead()){
                    iter.remove();
                    continue;
                }

                if(bullet.x > 800 || bullet.x < 0 || bullet.y < 0 || bullet.y > 600){
                    iter.remove();
                    continue;
                }

                Creature creature = battleField.getCreatureAt(bullet.x, bullet.y);

                //healing bullet hit a friend
                if(bullet.damage < 0){
                    if(creature!=null &&  creature==bullet.target&&!creature.isDead()) {
                        creature.getHurt(bullet.damage);
                        iter.remove();
                    }
                    continue;
                }

                //hit an enemy
                if (creature != null && !creature.isDead()
                        &&creature.getClass().getSuperclass() != bullet.shooter.getClass().getSuperclass()) {
                    creature.getHurt(bullet.damage);
                    iter.remove();
                }
            }
        }
    }

    public void display(GraphicsContext context, Recorder recorder){
        synchronized (bullets) {
            for (Bullet bullet : bullets) {
                recorder.write(bullet);
                context.drawImage(bullet.image, bullet.x, bullet.y, 30, 30);
            }
        }
    }

    public void clear(){
        synchronized (bullets) {
            bullets.clear();
        }
    }
}
