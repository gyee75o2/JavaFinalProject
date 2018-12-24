package formation;
import battle.BattleField;
import creature.Creature;

public class Heyi implements Formation<Creature> {
    public Heyi(){
        leaderX = 10;
        leaderY = 5;
    }

    public void arrange(BattleField bf,Creature... creatures){
        bf.setCreatureAt(leaderY, leaderX, creatures[0]);
        int count = 1;
        for(int i = 1; i < 4; i ++){
            bf.setCreatureAt(leaderY + i,leaderX + i, creatures[count++]);
            bf.setCreatureAt(leaderY - i,leaderX + i, creatures[count++]);
        }
    }

    private static int leaderX, leaderY;
}
