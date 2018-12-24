package logger;

import java.io.File;
import java.io.FileInputStream;
public class Reviewer{
    private String[] frames;
    private int rate;
    private boolean isReviewing;
    private int currentFrame = 0;

    public Reviewer(File file){
        isReviewing = true;
        try {
            Long length = file.length();
            byte[] content = new byte[length.intValue()];
            try {
                FileInputStream in = new FileInputStream(file);
                in.read(content);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String[] lines = new String(content).split("\n", 2);
            rate = Integer.parseInt(lines[0]);
            frames = lines[1].split("stop");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getOneFrame(){
        if(currentFrame > frames.length - 1) {
            isReviewing = false;
            return null;
        }
        else
            return frames[currentFrame++];
    }

    public boolean isReviewing(){
        return isReviewing;
    }

    public int getRate(){
        return rate;
    }
}
