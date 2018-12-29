package formation;
import battle.BattleField;
import common.AuthorAnno;

@AuthorAnno(author = "何峰彬")
public interface Formation<T>{
    void arrange(BattleField bf, T... objs);
}
