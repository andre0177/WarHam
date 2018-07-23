
package UnitModules;

import GUIModules.Animation;

public class RangeUnit extends Unit{
    
    private int range,ranged_dmg,CriticalRange=0;
    private double accuracy;
    private boolean fmg;
    private Projectile projectile;

    public RangeUnit(int range, int ranged_dmg, double accuracy, boolean fmg, Projectile projectile, String description, String imagepath, int MeeleDmg, int Armor, int MovementSpeed, int morale, int stamina, int imgsize, boolean shield, Player Player,Animation anim,int dir) {
        super(description, imagepath, MeeleDmg, Armor, MovementSpeed, morale, stamina, imgsize, shield, Player,anim,dir);
        this.range = range;
        this.ranged_dmg = ranged_dmg;
        this.accuracy = accuracy;
        this.fmg = fmg;
        this.projectile = projectile;
        createInfoPlus();
    }
    
    public void createInfoPlus(){
        info[3]="Range Attack:"+ranged_dmg;
        info[4]="Range:"+range;
        info[5]="Movement Speed:"+MovementSpeed;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getRanged_dmg() {
        return ranged_dmg;
    }

    public void setRanged_dmg(int ranged_dmg) {
        this.ranged_dmg = ranged_dmg;
    }

    public int getCriticalRange() {
        return CriticalRange;
    }

    public void setCriticalRange(int CriticalRange) {
        this.CriticalRange = CriticalRange;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public boolean isFmg() {
        return fmg;
    }

    public void setFmg(boolean fmg) {
        this.fmg = fmg;
    }
    
    
    
}
