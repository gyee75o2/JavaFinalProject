package creature;

import battle.BattleField;
import common.AuthorAnno;

@AuthorAnno(author = "何峰彬")
public abstract class Monster extends Creature{
    public Monster(BattleField battleField){
        super(battleField);
    }
}
