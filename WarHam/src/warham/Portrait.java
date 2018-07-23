
package warham;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import java.util.ArrayList;


public class Portrait {
    
    protected GImage background;//
    protected int ImgSize,ImgX,ImgY,labelX,labelY,stepY;
    protected GImage img,can_move_icon,can_hit_icon,can_range_icon;
    protected ArrayList<GLabel> labels=new ArrayList();

    public Portrait(GImage background, int startX, int startY,int sizeX,int sizeY) {
        this.background = background;
        ImgX=startX+sizeX/20;//start position of new images inside background image
        ImgY=startY+sizeY/20;//start position of new images inside background image
        int min=Math.min(sizeX, sizeY);
        background.setSize(sizeX,sizeY);
        ImgSize=min/2;//Size of new images inside background image(must be 1/2 )
        labelX=ImgX;
        labelY=ImgY+ImgSize+sizeY/10;
        //Î±Ï�Î± Î¿ Ï‡Ï‰Ï�Î¿Ï‚ Ï€Î¿Ï… Î¼ÎµÎ½ÎµÎ¹ ÎµÎ¹Î½Î±Î¹ : 18/20*sizeY-ImgSize
        stepY=sizeY/10;
        init_icons();
    }
    
    public void init_icons(){
        int size=ImgSize/4;
        can_move_icon=new GImage("images/can_move_icon.png");
        can_move_icon.setSize(size, size);
        can_hit_icon=new GImage("images/can_hit_icon.png");
        can_hit_icon.setSize(size, size);
        can_range_icon=new GImage("images/can_shoot_icon.png");
        can_range_icon.setSize(size, size);
    }

    public GImage getCan_move_icon() {
        return can_move_icon;
    }

    public int getImgSize() {
        return ImgSize;
    }

    public void setImgSize(int ImgSize) {
        this.ImgSize = ImgSize;
    }

    public void setCan_move_icon(GImage can_move_icon) {
        this.can_move_icon = can_move_icon;
    }

    public GImage getCan_hit_icon() {
        return can_hit_icon;
    }

    public void setCan_hit_icon(GImage can_hit_icon) {
        this.can_hit_icon = can_hit_icon;
    }

    public ArrayList<GLabel> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<GLabel> labels) {
        this.labels = labels;
    }
    
    public void addGLabel(GLabel l){
        labels.add(l);
    }

    public GImage getCan_range_icon() {
        return can_range_icon;
    }

    public void setCan_range_icon(GImage can_range_icon) {
        this.can_range_icon = can_range_icon;
    }

    public GImage getBackground() {
        return background;
    }

    public GImage getImg() {
        return img;
    }

    public void setImg(GImage img) {
        this.img = img;
    }

    public void setBackground(GImage background) {
        this.background = background;
    }

    public int getUnitSize() {
        return ImgSize;
    }

    public void setUnitSize(int UnitSize) {
        this.ImgSize = UnitSize;
    }

    public int getImgX() {
        return ImgX;
    }

    public void setImgX(int ImgX) {
        this.ImgX = ImgX;
    }

    public int getImgY() {
        return ImgY;
    }

    public void setImgY(int ImgY) {
        this.ImgY = ImgY;
    }

    public int getLabelX() {
        return labelX;
    }

    public void setLabelX(int labelX) {
        this.labelX = labelX;
    }

    public int getLabelY() {
        return labelY;
    }

    public void setLabelY(int labelY) {
        this.labelY = labelY;
    }

    public int getStepY() {
        return stepY;
    }

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }
    
    
    
    
    
}
