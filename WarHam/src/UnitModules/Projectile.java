
package UnitModules;

import acm.graphics.GImage;


public class Projectile {
    
    private int speed,damage,sizeX,sizeY;
    private boolean fmg;
    private GImage image;

    public Projectile(int speed, int damage, int sizeX, int sizeY, boolean fmg, GImage image) {
        this.speed = speed;
        this.damage = damage;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.fmg = fmg;
        this.image = image;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isFmg() {
        return fmg;
    }

    public void setFmg(boolean fmg) {
        this.fmg = fmg;
    }

    public GImage getImage() {
        return image;
    }

    public void setImage(GImage image) {
        this.image = image;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
    
    
    
}
