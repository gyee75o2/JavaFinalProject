package creature;

import battle.BattleField;
import common.AuthorAnno;

@AuthorAnno(author = "何峰彬")
public abstract class Justice extends Creature {
    public Justice(BattleField battleField){
        super(battleField);
    }
}
