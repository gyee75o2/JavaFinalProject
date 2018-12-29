package formation;

import common.AuthorAnno;
import creature.Creature;
import battle.BattleField;

@AuthorAnno(author = "何峰彬")
public class Fangyuan implements Formation<Creature>{
    public Fangyuan(){
        leaderX = 8;
        leaderY = 5;
    }

    public void arrange(BattleField bf, Creature...creatures){
        bf.setCreatureAt(leaderY,leaderX,creatures[0]);
        int count = 1;
        for(int i = 1; i < 3; i ++){
            bf.setCreatureAt(leaderY + i,leaderX+i,creatures[count++]);
            bf.setCreatureAt(leaderY - i,leaderX+i,creatures[count++]);
        }
        for(int i = 1; i < 2; i ++){
            bf.setCreatureAt(leaderY+i,leaderX+4-i,creatures[count++]);
            bf.setCreatureAt(leaderY-i,leaderX+4-i,creatures[count++]);
        }

        bf.setCreatureAt(leaderY,leaderX+4,creatures[count++]);
    }
    
    private int leaderX, leaderY;
}
