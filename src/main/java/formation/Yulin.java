package formation;

import battle.BattleField;
import creature.Creature;

public class Yulin implements Formation<Creature>{
    public Yulin(){
        leaderX = 8;
        leaderY = 5;
    }

    public void arrange(BattleField bf, Creature... creatures){
        bf.setCreatureAt(leaderY, leaderX, creatures[0]);
        int count = 1;
        bf.setCreatureAt(leaderY,leaderX+1,creatures[count ++]);
        bf.setCreatureAt(leaderY-1,leaderX+1,creatures[count ++]);
        bf.setCreatureAt(leaderY+1,leaderX+1,creatures[count ++]);
        for(int i = 0; i < 5; i ++){
            bf.setCreatureAt(leaderY+2-i,leaderX+2,creatures[count ++]);
        }
        for(int i = 0; i < 7; i ++){
            bf.setCreatureAt(leaderY+3-i,leaderX+3,creatures[count++]);
        }
        bf.setCreatureAt(leaderY,leaderX+4,creatures[count++]);
    }
    
    private int leaderX, leaderY;
}
