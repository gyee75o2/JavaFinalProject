package creature;

import battle.BattleField;

import java.util.List;

public class Nobody extends Monster{
    public Nobody(BattleField battleField){
        super(battleField);
        name = "nobody";

        setAttributes();
    }

    @Override
    public boolean shoot() {
        List<Creature> targets = getTargets();
        for(Creature target : targets){
            battleField.addBullet(bulletFactory.getBullet(this, target));
        }
        return !targets.isEmpty();
    }
}
