
package warham;

/**
 * Spell Effect Class keep track of the long lasting effects of a spell to a particular unit.
 * When the effect must ware off ,this class deactivates the effects.The method hasItEndedTurnPassed() must 
 * be called every turn from the main class.
 * @author Arthas
 */
public class SpellEffect {
    
    int rounds_remaining;
    Spell spell;
    Unit unit;
    
    public SpellEffect(Spell spell,Unit unit){
        this.spell=spell;
        this.unit=unit;
        rounds_remaining=spell.getDebuff_turns();
    }
    
    public boolean hasItEndedTurnPassed(){
        rounds_remaining--;
        if(rounds_remaining==0){
            deactiveBuffEffects();
            return true;
        }else if(rounds_remaining<0){//so not to call deactiveBuffEffects() twice
            return true;
        } else{
            return false;
        }
    }
    
    
    
    public void deactiveBuffEffects(){
        int rev;
        if(spell.getDmg_debuff()!=0){//Damage Buff
            rev=-spell.getDmg_debuff();
            unit.setMeeleDmg(unit.getMeeleDmg()+rev);
            if(unit instanceof RangeUnit){
                RangeUnit tmp=(RangeUnit)unit;
                unit.setMeeleDmg(tmp.getRanged_dmg()+rev);
            }
        }
        
        if(spell.getArmor_debuff()!=0){//Armor Buff
            rev=-spell.getArmor_debuff();
            unit.setArmor(unit.getArmor()+rev);
        }
        
        if(spell.getMovement_debuff()!=0){//MovementSpeed Buff
            rev=-spell.getMovement_debuff();
            unit.setMovementSpeed(unit.getMovementSpeed()+rev);
        }
        
        
    }
    
    
    
}
