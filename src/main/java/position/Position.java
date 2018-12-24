package position;

import creature.Creature;

/** A type represents a fix point in Cartesian coordinates*/
public class Position {
    private static final int IMAGE_SIZE = 50;
    private final int x, y;

    private Creature creature = null;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Creature getObject(){
        return creature;
    }

    public void setObject(Creature obj){
        this.creature = obj;
    }

    public void clear(){
        this.creature = null;
    }

    public double getX(){ return y*IMAGE_SIZE;}

    public double getY(){ return x*IMAGE_SIZE;}

    public int getI(){ return x;}

    public int getJ(){ return y;}

    public String toString(){
        if(creature == null)
            return "**";
        return creature.toString();
    }
}
