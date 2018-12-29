package creature;

import battle.BattleField;
import common.AuthorAnno;
import javafx.scene.image.Image;

import java.util.List;

@AuthorAnno(author = "何峰彬")
public class Snake extends Monster{
    public Snake(BattleField battleField){
        super(battleField);
        name = "snake";
        image = new Image("snake.png");

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
