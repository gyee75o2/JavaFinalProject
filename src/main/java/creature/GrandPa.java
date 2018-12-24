package creature;

import battle.BattleField;
import javafx.scene.image.Image;

import java.util.List;

public class GrandPa extends Justice{

    public GrandPa(BattleField battleField){
        super(battleField);
        name = "grandpa";
        image = new Image("grandpa.png");

        setAttributes();
    }

    @Override
    public boolean shoot(){
        List<Creature> targets = getTargets();
        for(Creature target: targets){
            battleField.addBullet(bulletFactory.getBullet(this, target));
        }
        return !targets.isEmpty();
    }
}
