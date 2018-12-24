package formation;

import battle.BattleField;
import creature.Creature;

public class Yanxing implements Formation<Creature> {
    public Yanxing(){
        leaderX = 11;
        leaderY = 5;
    }

    public void arrange(BattleField bf, Creature... creatures){
        bf.setCreatureAt(leaderY,leaderX,creatures[0]);
        int count = 1;
        for(int i = 1; i < 4; i ++){
            bf.setCreatureAt(leaderY + i,leaderX + i,creatures[count ++]);
            bf.setCreatureAt(leaderY - i,leaderX - i,creatures[count ++]);
        }
    }

    private int leaderX, leaderY;
}
