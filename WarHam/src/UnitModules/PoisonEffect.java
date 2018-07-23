
package UnitModules;


public class PoisonEffect {
    
    int damage_per_turn,turns,dmg_increase_per_turn;
    String description;
    boolean contagious;
    

    public PoisonEffect(int damage_per_turn, int turns, String description) {
        this.damage_per_turn = damage_per_turn;
        this.turns = turns;
        this.description = description;
    }

    public int getDamage_per_turn() {
        return damage_per_turn;
    }

    public void setDamage_per_turn(int damage_per_turn) {
        this.damage_per_turn = damage_per_turn;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
