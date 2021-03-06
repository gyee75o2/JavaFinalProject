package creature;

import battle.BattleField;
import common.AuthorAnno;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

@AuthorAnno(author = "何峰彬")
public class HuLuWa extends Justice implements Comparable<HuLuWa>{
    private final Color color;
    private final Seniority seniority;
    private static int count = 0;

    public int compareTo(HuLuWa tar){
        if(tar.color.ordinal() < this.color.ordinal()){
            return -1;
        }
        else if(tar.color.ordinal() > this.color.ordinal())
            return 1;
        else
            return 0;
    }

    public HuLuWa(BattleField battleField){
        super(battleField);
        this.color = Color.values()[count];
        this.seniority = Seniority.values()[count];
        name = seniority.getName();
        image = new Image(Integer.toString(count+1)+".png");
        count++;

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

enum Color{
    RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE;
}

enum Seniority{
    ELDEST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    FIFTH("5"),
    SIXTH("6"),
    YOUNGEST("7");

    private String name;

    Seniority(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}