package battle;

import common.AuthorAnno;
import logger.Recorder;
import bullet.*;
import creature.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import position.Position;
import javafx.scene.image.Image;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@AuthorAnno(author ="何峰彬")
public class BattleField {
    private static final int M=12, N = 16;
    private static final int IMAGE_SIZE = 50;
    private static final int NR_MONSTER = 20;
    private ExecutorService pools;
    public boolean isBattle = false;

    final Position[][] positions = new Position[M][N];
    private List<HuLuWa> huLuwas;
    private List<Monster> monsters = new ArrayList<>();
    final BulletManager bulletManager = new BulletManager(this);
    Creature snake = new Snake(this);
    Creature grandPa = new GrandPa(this);

    public BattleField(){
        for(int i = 0; i < M; i ++){
            for(int j = 0; j <N; j ++){
                positions[i][j] = new Position(i,j);
            }
        }

        huLuwas = new ArrayList<>();
        for(int i = 0; i < 7; i ++){
            HuLuWa huluwa = new HuLuWa(this);
            huLuwas.add(huluwa);
        }

        //monster.get(0) is scorpion as leader
        Scorpion scorpion = new Scorpion(this);
        monsters.add(scorpion);
        Image monster = new Image("nobody.png");
        for(int i = 0; i < NR_MONSTER;i ++){
            Nobody nobody = new Nobody(this);
            nobody.setImage(monster);
            monsters.add(nobody);
        }

        initFormation();
    }

    public void setCreatureAt(int x, int y, Creature c){
        if(!validPosition(x, y))
            return;
        Position position = positions[x][y];
        position.setObject(c);
        if(c!=null)
            c.setPosition(position);
    }

    public Creature getCreatureAt(int i, int j){
        if(!validPosition(i, j))
            return null;
        return positions[i][j].getObject();
    }

    public void cheerMonsters(){
        for(int i = 0; i < monsters.size(); i ++){
            monsters.get(i).Wryyyyy();
        }
    }

    public void cheerHuLuWa(){
        for(int i = 0; i < huLuwas.size(); i ++){
            huLuwas.get(i).Wryyyyy();
        }
    }

    public Creature getCreatureAt(double x, double y){
        int j = (int)(x/IMAGE_SIZE), i = (int)(y/IMAGE_SIZE);
        if(!validPosition(i, j))
            return null;
        return positions[i][j].getObject();
    }

    public Position getPosition(int x, int y){
        if(!validPosition(x, y))
            return null;
        return positions[x][y];
    }

    public void addBullet(Bullet bullet){
        this.bulletManager.addBullet(bullet);
    }

    void clearMonsters(){
        for(int i = 0 ; i < M; i ++){
            for(int j = 0; j < N; j ++){
                Creature creature = positions[i][j].getObject();
                if(creature != null) {
                    positions[i][j].clear();
                    creature.setPosition(null);
                }
            }
        }
    }

    void initFormation(){
        setCreatureAt(5,1,grandPa);
        setCreatureAt(5,14,snake);
        changShe(3, 2);
    }

    // Formation for Huluwas
    private void changShe(int leaderX, int leaderY){
        for(int i = 0; i < huLuwas.size(); i ++){
            setCreatureAt(leaderY+i, leaderX, huLuwas.get(i));
        }
    }

    void display(GraphicsContext context, Recorder recorder){
        synchronized (this) {
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    Creature creature = positions[i][j].getObject();
                    if (creature != null) {
                        if(recorder != null)
                            recorder.write(creature);
                        context.drawImage(creature.getImage(), j * IMAGE_SIZE, i * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);

                        if (!creature.isDead()) {
                            context.setFill(Color.RED);
                            context.fillRoundRect(positions[i][j].getX() + 3, positions[i][j].getY(), 46, 5, 10, 10);
                            context.setFill(Color.color(0.3, 1.0, 0.69));
                            double ratio = creature.getHealth() / creature.getMaxHealth();
                            context.fillRoundRect(positions[i][j].getX() + 3, positions[i][j].getY(), 46 * ratio, 5, 10, 10);
                        }
                    }
                }
            }
        }
    }

    void restart(){
        bulletManager.clear();
        try {
            pools.shutdownNow();
            pools.awaitTermination(50, TimeUnit.MILLISECONDS);
            System.out.println("All dead!");
        }catch (Exception e){
            e.printStackTrace();
        }
        snake.reborn();
        grandPa.reborn();
        for(HuLuWa huLuWa: huLuwas){
            huLuWa.reborn();
        }
        for(Monster monster:monsters){
            monster.reborn();
        }
    }

    void battle(){
        isBattle = true;
        pools = Executors.newCachedThreadPool();
        pools.execute(grandPa);
        pools.execute(snake);
        for (HuLuWa huLuWa : huLuwas) {
            pools.execute(huLuWa);
        }
        for (Monster monster : monsters) {
            if (monster.getPosition() != null) {
                pools.execute(monster);
            }
        }
        pools.shutdown();
    }

    public String toString(){
        String str = "";
        for(int i = 0; i < M; i ++){
            for(int j = 0; j < N; j ++){
                str+=positions[i][j];
            }
            str+="\n";
        }
        return str;
    }

    boolean isBattle(GraphicsContext context, Recorder recorder){
        boolean monsterAllDead = snake.isDead();
        boolean justiceAllDead = grandPa.isDead();
        if(monsterAllDead){
            for(int i = 0; i < monsters.size(); i ++){
                if (!monsters.get(i).isDead() && monsters.get(i).getPosition()!=null){
                    monsterAllDead = false;
                    break;
                }
            }
        }
        if(justiceAllDead) {
            for(int i = 0; i < huLuwas.size(); i ++){
                if(!huLuwas.get(i).isDead()){
                    justiceAllDead = false;
                    break;
                }
            }
        }
        if(justiceAllDead){
            recorder.writeWinner(false);
            context.drawImage(new Image("lose.png"), 100, 25, 600, 450);
        }else if(monsterAllDead){
            recorder.writeWinner(true);
            context.drawImage(new Image("win.png"), 0, 50,800,331);
        }
        isBattle = !justiceAllDead && !monsterAllDead;
        return isBattle;
    }

    List<Monster> getMonsters(){
        return monsters;
    }

    private boolean validPosition(int i, int j){
        if(i >= M || i < 0 || j >= N || j < 0)
            return false;
        else
            return true;
    }
}
