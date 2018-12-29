package formation;

import battle.BattleField;
import common.AuthorAnno;
import creature.Creature;

@AuthorAnno(author = "何峰彬")
public class Yanyue implements Formation<Creature> {
    public Yanyue(){
        leaderX = 9;
        leaderY = 5;
    }

    public void arrange(BattleField bf, Creature... creatures){
        bf.setCreatureAt(leaderY, leaderX, creatures[0]);
        int count = 1;
        for(int i = 1; i < 5; i ++){
            bf.setCreatureAt(leaderY+i,leaderX+i,creatures[count++]);
            bf.setCreatureAt(leaderY+i,leaderX+i+1,creatures[count++]);
            bf.setCreatureAt(leaderY-i,leaderX+i,creatures[count++]);
            bf.setCreatureAt(leaderY-i,leaderX+i+1,creatures[count++]);
        }
        bf.setCreatureAt(leaderY+1,leaderX,creatures[count++]);
        bf.setCreatureAt(leaderY-1,leaderX,creatures[count++]);
        bf.setCreatureAt(leaderY,leaderX+1,creatures[count++]);
        bf.setCreatureAt(leaderY,leaderX+2,creatures[count++]);
    }

    private int leaderX, leaderY;
}
