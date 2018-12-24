package formation;

import battle.BattleField;
import creature.Creature;

public class Chonge implements Formation<Creature> {
    public Chonge(){
        leaderX = 11;
        leaderY = 5;
    }

    public void arrange(BattleField bf, Creature... creatures){
        bf.setCreatureAt(leaderY, leaderX, creatures[0]);
        int count = 1;
        for(int i = 1; i < 4; i ++){
            bf.setCreatureAt(leaderY-i,leaderX + i%2,creatures[count ++]);
        }
        for(int i  = 1; i < 4; i ++){
            bf.setCreatureAt(leaderY+i,leaderX + i%2,creatures[count ++]);
        }
    }

    private int leaderX, leaderY;
}
