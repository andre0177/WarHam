
package UnitModules;

import GUIModules.Animation;

public class MageUnit  extends Unit{
    
    private int range,cooldown,counter_cd;//counter_cd=turns that must pass so that he can cast a spell
    private Spell spell;

    public MageUnit(int range, int cooldown, Spell spell, String description, String imagepath, int MeeleDmg, int Armor, int MovementSpeed, int morale, int hp, int imgsize, boolean shield, Player Player,Animation anim,int dir) {
        super(description, imagepath, MeeleDmg, Armor, MovementSpeed, morale, hp, imgsize, shield, Player,anim,dir);
        this.range = range;
        this.cooldown = cooldown;
        this.spell = spell;
        counter_cd=0;
    }

    
    //must be called every turn
    public void TurnPassed(){
        if(counter_cd>0) counter_cd--;
    }
    //must be called every time a spell is cast.Return the spell if spell can be cast ,and sets the counter_cd else
    // retuns null.
    public Spell SpellCast(){
        if(hasMana()){
            counter_cd=cooldown;
            return spell;
        }else{
            return null;
        }
    }
    
    public boolean hasMana(){
        return counter_cd==0;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }
    
    
}
