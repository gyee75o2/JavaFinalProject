package formation;
import battle.BattleField;

public interface Formation<T>{
    void arrange(BattleField bf, T... objs);
}
