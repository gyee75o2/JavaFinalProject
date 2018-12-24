package creature;

import battle.BattleField;
import bullet.*;
import javafx.scene.image.Image;
import position.Position;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class Creature implements Runnable{
    String name;
    Image image;
    private final static Image deadImg = new Image("rip.png");

    private final static int HEALTH = 100;
    private final static int ATTACK = 10;
    private final static int FREQUENCY = 30;
    private final static int RANGE = 5;
    private final static double SPEED = 1.0;
    private static Properties attributes;
    static{
        try{
            attributes = new Properties();
            InputStream in = Creature.class.getClassLoader().getResourceAsStream("character.properties");
            attributes.load(in);
        }catch (Exception e){
            e.printStackTrace();
            attributes=null;
        }
    }

    //some character attributes
    private int range = 5;
    private double maxHealth = 100;
    private double health = 100;
    private int attack = 10;
    private static final int minAttackFrequency = 10;
    private int attackFrequency = 30;
    private double speed = 1.0;
    private Position position;

    BulletFactory bulletFactory;
    final BattleField battleField;

    private static final Random random = new Random(System.currentTimeMillis());
    private final int M = 12, N = 16;
    private boolean isDead = false;

    public Creature(BattleField battleField){
        name = null;
        this.battleField = battleField;
    }

    public void Wryyyyy(){
        speed *= 5;
        if(bulletFactory instanceof HorizontalBulletFactory) {
            bulletFactory = new StraightBulletFactory();
            range = 10;
        }
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }

    public Image getImage(){
        if(isDead)
            return deadImg;
        return image;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public String toString() {
        return name;
    }

    private void randomWalk(){
        if(position == null)
            return;
        int x = position.getI(), y = position.getJ();
        int newX = x, newY = y;
        if(random.nextInt(2) == 0){
            newX+=(random.nextInt(2) == 0)? -1: 1;
        }else{
            newY+=(random.nextInt(2) == 0)?1:-1;
        }

        if (validPosition(newX, newY) && battleField.getCreatureAt(newX, newY) == null) {
            battleField.setCreatureAt(x, y, null);
            battleField.setCreatureAt(newX, newY, this);
            position = battleField.getPosition(newX, newY);
        }
    }

    public void getHurt(int damage){
        health -= damage;
        if(health > maxHealth)
            health = maxHealth;
        isDead = health<=0;
    }

    public void reborn(){
        isDead = false;
        setAttributes();
    }

    public double getHealth(){
        return health;
    }

    public double getMaxHealth(){
        return maxHealth;
    }

    public int getAttack(){
        return attack;
    }

    public boolean isDead(){
        return isDead;
    }

    public boolean shoot(){
        return false;
    }

    @Override
    public void run(){

        while(true){
            int timeGap = (int)(500/speed);
            try{
                TimeUnit.MILLISECONDS.sleep(timeGap);
                synchronized (battleField) {
                    //一觉醒来发现仗打完了 or 自己挂了
                    if (isDead || !battleField.isBattle)
                        break;
                    if (random.nextInt(attackFrequency) < minAttackFrequency) {
                        if (!shoot())
                            randomWalk();
                    } else
                        randomWalk();
                }
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }

        if(this instanceof GrandPa)
            battleField.cheerHuLuWa();
        else if(this instanceof Snake)
            battleField.cheerMonsters();
    }

    final List<Creature> getTargets(){
        if(bulletFactory instanceof HorizontalBulletFactory)
            return captureRowEnemies();
        else if(bulletFactory instanceof HealingTrackingBulletFactory)
            return captureNearFriends();
        else
            return captureNearEnemies();
    }

    private List<Creature> captureRowEnemies(){
        List<Creature> targets = new ArrayList<>();

        Creature target = null;
        int i = position.getI();
        synchronized (battleField){
            for(int j = 0; j < N; j ++){
                target = battleField.getCreatureAt(i, j);
                if(target!=null && !target.isDead()
                        &&target.getClass().getSuperclass() != this.getClass().getSuperclass()){
                    targets.add(target);
                    return targets;
                }
            }
        }
        return targets;
    }

    private List<Creature> captureNearEnemies(){
        List<Creature> targets = new ArrayList<>();

        Creature target = null;
        synchronized (battleField) {
            for (int i = position.getI() - range; i <= position.getI() + range; i++) {
                for (int j = getPosition().getJ() - range; j <= position.getJ() + range; j++) {
                    target = battleField.getCreatureAt(i, j);
                    if (target != null && !target.isDead()
                            && target.getClass().getSuperclass() != this.getClass().getSuperclass()) {
                        targets.add(target);
                    }
                }
            }
        }

        return targets;
    }

    private List<Creature> captureNearFriends(){
        List<Creature> targets = new ArrayList<>();

        Creature target = null;
        synchronized (battleField) {
            for (int i = position.getI() - range; i <= position.getI() + range; i++) {
                for (int j = getPosition().getJ() - range; j <= position.getJ() + range; j++) {
                    if(i == position.getI() && j == position.getJ())
                        continue;
                    target = battleField.getCreatureAt(i, j);
                    if (target != null && !target.isDead() && target.getHealth()!=target.getMaxHealth()
                            && target.getClass().getSuperclass() == this.getClass().getSuperclass()) {
                        targets.add(target);
                    }
                }
            }
        }
        return targets;
    }

    final void setAttributes(){
        if(attributes!=null){
            int bulletType = Integer.parseInt(attributes.getProperty(name + "bullet", "1"));
            switch (bulletType){
                case 1: bulletFactory = new HorizontalBulletFactory();break;
                case 2: bulletFactory = new TrackingBulletFactory(); break;
                case 3: bulletFactory = new HealingTrackingBulletFactory(); break;
                case 4: bulletFactory = new StraightBulletFactory();break;
            }

            speed = Double.parseDouble(attributes.getProperty(name+"speed", Double.toString(SPEED)));
            range = Integer.parseInt(attributes.getProperty(name + "range", Integer.toString(RANGE)));
            attack = Integer.parseInt(attributes.getProperty(name+"attack", Integer.toString(ATTACK)));
            attackFrequency = Integer.parseInt(attributes.getProperty(name + "fre", Integer.toString(FREQUENCY)));
            health = Double.parseDouble(attributes.getProperty(name+"health", Integer.toString(HEALTH)));
            maxHealth = health;
        }
    }

    private boolean validPosition(int x, int y){
        return (x >= 0 && x < M && y >= 0 && y < N);
    }
}
