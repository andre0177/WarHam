
package warham;

import java.util.ArrayList;


public class Player {
    
    protected String name;
    protected ArrayList<Unit> army=new ArrayList();

    public Player(String name) {
        this.name = name;
    }

    public ArrayList<Unit> getArmy() {
        return army;
    }

    public void setArmy(ArrayList<Unit> army) {
        this.army = army;
    }
    
    public void addUnit(Unit e){
        if(e.getPlayer()==this) army.add(e);
        else System.err.println("Player:addUnit unit setted to player but has another played setted to it");
    }
    
    public boolean hasUnit(Unit e){
        for(Unit u:army){
            if(e==u) return true;
        }
        return false;
    }
    
    public boolean removeUnit(Unit e){
        return army.remove(e);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
