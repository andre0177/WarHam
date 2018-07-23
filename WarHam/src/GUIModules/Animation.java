
package GUIModules;

import acm.graphics.GImage;


public class Animation {
    
    public static int FPS;
    private GImage frame[];
    private int length,unitsize;
    private int framesPerMeeleAttack,framesPerRangeAttack;
    
    public Animation(String filepath,int framesPerMeeleAttack,int framesPerRangeAttack,int unitsize){
        this.framesPerMeeleAttack=framesPerMeeleAttack;
        this.framesPerRangeAttack=framesPerRangeAttack;
        this.unitsize=unitsize;
        readImageFileClassic(filepath,framesPerMeeleAttack,framesPerRangeAttack);
    }

    
    public void readImageFileClassic(String filepath,int framesPerMeeleAttack,int framesPerRangeAttack){
        frame=new GImage[4+framesPerMeeleAttack*4+framesPerRangeAttack*4+1];
        filepath=filepath+"/";
        int counter=0;
        //def0,def1,def2,def3
        for(int i=0;i<4;i++){
            frame[i]=new GImage(filepath+"def"+i+".png");
            frame[counter].setSize(unitsize,unitsize);
            counter++;
        }
        //meele00,meele01,....,meele43,meele51
        for(int i=0;i<framesPerMeeleAttack;i++){
            for(int k=0;k<4;k++){
                frame[counter]=new GImage(filepath+"Meele"+i+""+k+".png");
                frame[counter].setSize(unitsize,unitsize);
                counter++;
            }
            
        }
        //range00,range01,....,range43,range51
        for(int i=0;i<framesPerRangeAttack;i++){
             for(int k=0;k<4;k++){
                frame[counter]=new GImage(filepath+"range"+i+""+k+".png");
                frame[counter].setSize(unitsize,unitsize);
                counter++;
            }
        }
        frame[counter]=new GImage(filepath+"dead.png");
        frame[counter].setSize(unitsize,unitsize);
        length=counter;
    }

    public GImage getNextAnimationFrame(boolean Meele,int direction,int frame){
        if(direction<0||direction>3){
            System.out.println("Animation:getNextAnimationFrame frame>0||frame>3 direction:"+direction);
            return null;
        }else if(frame>8){
            System.out.println("Animation:getNextAnimationFrame frame>8 direction:"+frame);
            return null;
        }else if(Meele&&frame>4){
            System.out.println("Animation:getNextAnimationFrame Meele&&frame>4 direction:"+frame);
            return null;
        }
        int counter;
        if(Meele) counter=4;
        else counter=4+framesPerMeeleAttack*4;
        counter=counter+frame*4+direction;
        return this.frame[counter];
    }
    
    public GImage getDeadImg(){
        return this.frame[length];
    }
 
    public GImage clone(GImage x){
        int[][] pixel=x.getPixelArray();
        return new GImage(pixel);
    }

    public static int getFPS() {
        return FPS;
    }

    public static void setFPS(int FPS) {
        Animation.FPS = FPS;
    }

    public GImage[] getFrame() {
        return frame;
    }

    public void setFrame(GImage[] frame) {
        this.frame = frame;
    }
    
    public GImage getDefaultFrame(int direction){
        if(direction<0||direction>3){
            System.out.println("Animation:getDefaultFrame frame>0||frame>3 direction:"+direction);
            return null;
        }
        return frame[direction];
    }
    
    public void setDefaultFrame(GImage img,int direction){
        if(direction<0||direction>3){
            System.out.println("Animation:getDefaultFrame frame>0||frame>3 direction:"+direction);
        }else{
            frame[direction]=img;
        }
        
    }

    public int getFramesPerMeeleAttack() {
        return framesPerMeeleAttack;
    }

    public void setFramesPerMeeleAttack(int framesPerMeeleAttack) {
        this.framesPerMeeleAttack = framesPerMeeleAttack;
    }

    public int getFramesPerRangeAttack() {
        return framesPerRangeAttack;
    }

    public void setFramesPerRangeAttack(int framesPerRangeAttack) {
        this.framesPerRangeAttack = framesPerRangeAttack;
    }
    
    
}
