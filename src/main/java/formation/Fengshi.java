package formation;

import battle.BattleField;
import common.AuthorAnno;
import creature.Creature;

@AuthorAnno(author = "何峰彬")
public class Fengshi implements Formation<Creature> {
    public Fengshi(){
        leaderX = 8;
        leaderY = 5;
    }

    public void arrange(BattleField bf, Creature... creatures){
        bf.setCreatureAt(leaderY,leaderX, creatures[0]);
        int count = 1;
        for(int i = 1; i < 5; i ++){
            bf.setCreatureAt(leaderY,leaderX+i,creatures[count++]);
        }
        for(int i = 1; i < 4; i ++){
            bf.setCreatureAt(leaderY+i,leaderX+i,creatures[count++]);
            bf.setCreatureAt(leaderY-i,leaderX+i,creatures[count++]);
        }
    }

    private int leaderX, leaderY;
}
