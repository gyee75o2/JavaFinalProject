package bullet;

import common.AuthorAnno;
import creature.Creature;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

@AuthorAnno(author = "何峰彬")
public abstract class Bullet {
    static final int IMAGE_SIZE = 50;
    static final Map<String, Image> images = new HashMap<>();
    boolean toLeft = false;
    Creature shooter;
    Creature target;
    Image image;
    int damage = 10;

    // number of Images per second
    int speed = 10;
    double x, y;

    Bullet(Creature shooter, Creature target){
        this.shooter = shooter;
        String url = shooter.toString()+"bullet.png";
        if(!images.containsKey(url)){
            images.put(url, new Image(shooter.toString()+"bullet.png"));
        }
        image = images.get(url);
        x = shooter.getPosition().getX();
        y = shooter.getPosition().getY();
        this.target = target;
    }

    /**
     * calculate next position after millisec milliseconds
     * @param millisec time gap after last calculation
     */
    abstract public void move(int millisec);

    public void setToLeft(boolean toLeft){
        this.toLeft = toLeft;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public String getShooterName(){
        return shooter.toString();
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
