package battle;

import logger.Recorder;
import logger.Reviewer;
import creature.Creature;
import creature.Monster;
import formation.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class BattleController {
    private static final int IMAGE_SIZE = 50;
    private static final int BULLET_SIZE = 30;
    private static final int TIME_GAP = 10;
    private static final Image BACKGROUND = new Image("background.png");

    private BattleField battleField;
    private List<Monster> monsters;
    private Recorder recorder;
    private Reviewer reviewer;
    private List<Formation<Creature>> formations;
    private enum FORMATION{
        CHONGE,FANGYUAN,FENGSHI,HEYI,YANXING,YANYUE,YULIN
    }


    private final static Map<String, Image> images = new HashMap<>();
    static{
        for(int i = 1; i < 8; i ++){
            images.put(Integer.toString(i), new Image(i + ".png"));
            images.put(i+"bullet", new Image(i+"bullet.png"));
        }
        String[] names = {"scorpion", "snake", "grandpa", "nobody"};
        for(int i = 0; i < names.length; i ++){
            images.put(names[i], new Image(names[i] + ".png"));
            images.put(names[i] + "bullet", new Image(names[i]+"bullet.png"));
        }
        images.put("rip", new Image("rip.png"));
    }

    @FXML
    private Canvas mainCanvas;
    private GraphicsContext context;

    @FXML
    private BorderPane mainPane;

    @FXML
    public void initialize(){
        battleField = new BattleField();
        monsters = battleField.getMonsters();

        formations = new ArrayList<>();
        formations.add(new Chonge());
        formations.add(new Fangyuan());
        formations.add(new Fengshi());
        formations.add(new Heyi());
        formations.add(new Yanxing());
        formations.add(new Yanyue());
        formations.add(new Yulin());

        context = mainCanvas.getGraphicsContext2D();
        mainPane.addEventFilter(KeyEvent.KEY_PRESSED, new KeyEventHandler());
        Platform.runLater(()->mainPane.requestFocus());
        formations.get(FORMATION.YANYUE.ordinal()).arrange(battleField, monsters.toArray(new Monster[0]));
        draw();
    }

    void draw(){
        context.drawImage(BACKGROUND, 0, 0);
        battleField.display(context, recorder);
        battleField.bulletManager.display(context, recorder);
    }

    void drawFrameViaStr(String frame){
        if(frame == null || frame.equals("\n"))
            return;
        context.drawImage(BACKGROUND, 0, 0);
        String[] lines = frame.split("\n");
        for(String line : lines){
            if(line.equals(""))
                continue;
            String[] results = line.split("(:|,)");
            assert results.length >= 3;
            double x = Double.parseDouble(results[1]), y = Double.parseDouble(results[2]);

            if(results[0].contains("bullet")){
                context.drawImage(images.get(results[0]), x, y, BULLET_SIZE, BULLET_SIZE);
            } else{
                context.drawImage(images.get(results[0]), x, y, IMAGE_SIZE, IMAGE_SIZE);
                if(results[0].equals("rip"))
                    continue;
                context.setFill(Color.RED);
                context.fillRoundRect(x + 3, y, 46, 5, 10, 10);
                context.setFill(Color.color(0.3, 1.0, 0.69));
                double ratio = Double.parseDouble(results[3]);
                context.fillRoundRect(x + 3, y, 46 * ratio, 5, 10, 10);
            }
        }
    }

    private void changeFormation(){
        battleField.clearMonsters();
        battleField.initFormation();
        Random random = new Random(System.currentTimeMillis());
        int n = random.nextInt(7);
        formations.get(n).arrange(battleField, monsters.toArray(new Monster[0]));
        draw();
    }

    private void startBattle(){
        recorder = new Recorder(TIME_GAP);
        battleField.battle();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0),
                        event1 -> {
                            battleField.bulletManager.move(TIME_GAP);
                            draw();
                            recorder.writeStop();
                        }),
                new KeyFrame(Duration.millis(TIME_GAP))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (battleField.isBattle()) {
                        TimeUnit.MILLISECONDS.sleep(TIME_GAP);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                timeline.stop();
                recorder.finish();
                recorder = null;
                battleField.restart();
                battleField.clearMonsters();
                battleField.initFormation();
                formations.get(FORMATION.YANYUE.ordinal()).arrange(battleField, monsters.toArray(new Monster[0]));
            }
        }).start();
    }

    private void startReview(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Log File");
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MyLog", "*.mylog"));
        File file = fileChooser.showOpenDialog(mainCanvas.getScene().getWindow());
        if(file != null) {
            //new Thread(new Reviewer(context, file)).start();
            reviewer = new Reviewer(file);
            int rate = reviewer.getRate();
            Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,
                    event1 -> drawFrameViaStr(reviewer.getOneFrame())),
                    new KeyFrame(Duration.millis(rate))
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (reviewer.isReviewing()) {
                            TimeUnit.MILLISECONDS.sleep(TIME_GAP);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timeline.stop();
                }
            }).start();
        }
    }

    class KeyEventHandler implements EventHandler<KeyEvent> {
        public void handle(KeyEvent event){
            if(battleField.isBattle || (reviewer!=null && reviewer.isReviewing()))
                return;
            if(event.getCode() == KeyCode.F){
                changeFormation();
            }else if(event.getCode() == KeyCode.SPACE ){
                startBattle();
            }else if(event.getCode() == KeyCode.L){
                startReview();
            }
        }
    }
}
