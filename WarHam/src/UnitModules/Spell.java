
package UnitModules;

import java.util.Random;
import GUIModules.ReportMessage;

/**
 *
 * @author Arthas
 */
public class Spell {
    
    
    protected int direct_damage,aoe_area,chain_links,movement_debuff,debuff_turns,dmg_debuff,armor_debuff,range_debuff;
    protected String desc;
    protected PoisonEffect poison;

    public Spell(){
        this.direct_damage = 0;//direct damage if > 0 then it is heal!
        this.aoe_area = 0;
        this.chain_links = 0;
        this.movement_debuff = 0;
        this.debuff_turns = 0;
        this.dmg_debuff = 0;
        this.armor_debuff = 0;
        this.range_debuff = 0;
    }
    
    public Spell(int direct_damage, int aoe_damage, int chain_damage, int movement_debuff, int debuff_turns, int dmg_debuff, int armor_debuff, int range_debuff) {
        this.direct_damage = direct_damage;
        this.aoe_area = aoe_damage;
        this.chain_links = chain_damage;
        this.movement_debuff = movement_debuff;
        this.debuff_turns = debuff_turns;
        this.dmg_debuff = dmg_debuff;
        this.armor_debuff = armor_debuff;
        this.range_debuff = range_debuff;
    }

    public Spell(int direct_damage, int aoe_area, int chain_links, int movement_debuff, int debuff_turns, int dmg_debuff, int armor_debuff, int range_debuff, String desc, PoisonEffect poison) {
        this.direct_damage = direct_damage;
        this.aoe_area = aoe_area;
        this.chain_links = chain_links;
        this.movement_debuff = movement_debuff;
        this.debuff_turns = debuff_turns;
        this.dmg_debuff = dmg_debuff;
        this.armor_debuff = armor_debuff;
        this.range_debuff = range_debuff;
        this.desc = desc;
        this.poison = poison;
    }
    
    /**
     * activates the buffs or de_buffs at the target unit based on the accuracy
     * @return the damage which must be inflicted to the unit if any, it can be heal
     */
    public ReportMessage activateSpellEffect(Unit e){
        int dmg=direct_damage;
        if(e==null) return null;
        String message;
        if(getMagicHit(e)){
            if(armor_debuff!=0){
                e.setArmor(e.getArmor()+armor_debuff);
            }
            if(movement_debuff!=0){
                e.setMovementSpeed(e.getMovementSpeed()+movement_debuff);
            }
            if(dmg_debuff!=0){
                e.setMeeleDmg(e.getMeeleDmg()+dmg_debuff);
            }
            if(range_debuff!=0&&e instanceof RangeUnit){
                RangeUnit tmp=(RangeUnit)e;
                tmp.setRange(tmp.getRange()+range_debuff);
            }
            return new ReportMessage(true,"The spell was cast successfully.",dmg);
        }
        return new ReportMessage(false,"The enemy's magic resistance was too strong.",0);
    }
    /**
     * 
     * @param e
     * @return true if the luck>mr
     */
    public boolean getMagicHit(Unit e){
        int luck = new Random().nextInt(100);
        int mr=e.getMagic_resistance();
        if(mr>luck){
            return false;
        }else{
            return true;
        }
    }
    
    public boolean hasEffect(){
        if(movement_debuff==0&&armor_debuff==0&&dmg_debuff==0&&range_debuff==0) return false;
        return true;
    }
    
    public int getDirect_damage() {
        return direct_damage;
    }

    public void setDirect_damage(int direct_damage) {
        this.direct_damage = direct_damage;
    }

    public int getAoeArea() {
        return aoe_area;
    }

    public void setAoeArea(int aoe_damage) {
        this.aoe_area = aoe_damage;
    }

    public int getChain_damage() {
        return chain_links;
    }

    public void setChain_damage(int chain_damage) {
        this.chain_links = chain_damage;
    }

    public int getMovement_debuff() {
        return movement_debuff;
    }

    public void setMovement_debuff(int movement_debuff) {
        this.movement_debuff = movement_debuff;
    }

    public int getDebuff_turns() {
        return debuff_turns;
    }

    public void setDebuff_turns(int debuff_turns) {
        this.debuff_turns = debuff_turns;
    }

    public int getDmg_debuff() {
        return dmg_debuff;
    }

    public void setDmg_debuff(int dmg_debuff) {
        this.dmg_debuff = dmg_debuff;
    }

    public int getArmor_debuff() {
        return armor_debuff;
    }

    public void setArmor_debuff(int armor_debuff) {
        this.armor_debuff = armor_debuff;
    }

    public int getRange_debuff() {
        return range_debuff;
    }

    public void setRange_debuff(int range_debuff) {
        this.range_debuff = range_debuff;
    }
    
    
    
}
