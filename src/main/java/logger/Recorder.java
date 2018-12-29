package logger;

import bullet.Bullet;
import common.AuthorAnno;
import creature.Creature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@AuthorAnno(author = "何峰彬")
public class Recorder {
    private BufferedWriter logWriter;
    private boolean win = true;

    public Recorder(int rate){
        try {
            logWriter = new BufferedWriter(new FileWriter(new File("default.mylog")));
            logWriter.write(rate+"\n");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void write(Creature c){
        try {
            StringBuilder line = new StringBuilder();
            if(c.isDead())
                line.append("rip");
            else
                line.append(c.toString());
            line.append(":");
            line.append(c.getPosition().getX());
            line.append(",");
            line.append(c.getPosition().getY());
            if(!c.isDead()) {
                line.append(",");
                line.append(c.getHealth() / c.getMaxHealth());
            }
            line.append("\n");
            logWriter.write(line.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void write(Bullet bullet){
        try {
            StringBuilder line = new StringBuilder();
            line.append(bullet.getShooterName());
            line.append("bullet");
            line.append(":");
            line.append(bullet.getX());
            line.append(",");
            line.append(bullet.getY());
            line.append("\n");
            logWriter.write(line.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeStop(){
        try{
            logWriter.write("stop"+"\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeWinner(boolean win){
        this.win = win;
    }

    public void finish(){
        try {

            if(win){
                logWriter.write("win");
            }
            else
                logWriter.write("lose");
            logWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
