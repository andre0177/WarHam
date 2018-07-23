
package warham;

import acm.graphics.GImage;
import java.util.ArrayList;


public class Hexagon {

    private GImage core_image,image_portrait;
    private ArrayList<Hexagon> adjacentHex=new ArrayList();
    private Bonus[] bonus=null;
    private boolean passable,visited=false,wall=false;
    private int posX,posY,i,j,size,code;//pixel coordinates
    private Unit unit=null;

    

	public Hexagon(int code,int i,int j,String map_skin){
        this.code=code;
        this.i=i;
        this.j=j;
        core_image=ConstantMapping.getImagefromIntCode(code,map_skin);
        if(code<=10){
            passable=true;
        }else if(code<=20){
            passable=false;
        }else if(code<=30){
            passable=false;
            wall=true;
        }
    }
    
    
    @Override
    public String toString(){
        String out="Hexagon:"+i+" ,"+j;
        if(wall) out=out+" wall";
        if(!passable) out=out+" ,not passable";
        return out;
    } 
    
    public boolean hasUnit(){
        if(unit==null) return false;
        else return true;
    }

    public GImage getCoreImage() {
        return core_image;
    }

    public void setCoreImage(GImage image) {
        this.core_image = image;
    }
    
    public ReportMessage canDisUnitHereMoveTo(Hexagon e){
    	ReportMessage ans=new ReportMessage(true,"");
        if(!hasUnit()){
            ans.setMessage("This hex has no unit");
            ans.setSuccessful(false);
            return ans;
        }else if(e.hasUnit()){
            ans.setMessage("Target hexagon hex has unit");
            ans.setSuccessful(false);
            return ans;
        }else if(e==this){
            ans.setMessage("Cannot move to the same hexagon");
            ans.setSuccessful(false);
            return ans;
        }else if(e.isPassable()==false){
            ans.setMessage("Target hexagon is not Passable");
            ans.setSuccessful(false);
            return ans;
        }
        int dist=DistanceMatrix.getMoveDistance(this,e);
        
        if(dist>unit.getMovementSpeed()) {
            ans.setMessage("This unit has not enough movement speed.");
            ans.setSuccessful(false);
        }
        return ans;
    }
    /**
     * checks if this hex has a unit, if the target and source are the same and if distance < range,
     * @param e
     * @return 
     */
    public boolean canDisUnitHereShootTo(Hexagon e){
        
        if(!hasUnit()){
            System.err.println("Hexagon:canDisUnitHereMoveTo:This hex has no unit");
            return false;
        }else if(e==this){
            System.err.println("Hexagon:canDisUnitHereMoveTo:Cannot shoot to the same hexagon");
            return false;
        }

        int dist=DistanceMatrix.getShootDistance(this,e);
        RangeUnit t=(RangeUnit)getUnit();
        if(dist<=t.getRange()) return true;
        else{
            System.err.println("Hexagon:canDisUnitHereShootTo:This unit has not enought range"
                    + " range required:"+dist+" range unit has:"+t.getRange());
            return false;
        }
    }
    
    public ReportMessage canDisUnitHereMeeleAttack(Hexagon e){
    	ReportMessage ans=new ReportMessage(true,"");
        if(!hasUnit()){
        	ans.setMessage("This hex has no unit");
        	ans.setSuccessful(false);
        }else if(e==this){
        	ans.setMessage("Cannot hit to the same hexagon");
            ans.setSuccessful(false);
        }
        boolean close=isAdjacenthex(e);
        if(!close){
        	ans.setMessage("The target hex is too far");
        	ans.setSuccessful(false);
            
        }
        return ans;
    }

    
    public int getNumberOfAdj(){
        return adjacentHex.size();
    }

    public int getPosX() {
        return posX;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        //Bonus stuff
        this.unit = unit;
    }
    
    public void UnitLeft() {
        //Bonus stuff
        this.unit = null;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Hexagon(boolean passable) {
        this.passable = passable;
    }


    public ArrayList<Hexagon> getAdjacentHexList() {
        return adjacentHex;
    }

    public void addAdjacentHex(Hexagon adjacentHex) {
        if(this.adjacentHex.size()<6){
            this.adjacentHex.add(adjacentHex);
        }else{
            System.err.println("Hexagon:addAdjacentHex: cannot add 7th adjcacent hex");
        }
    }
    /**
     * Returns true if e belongs to the adjacent hex list of this hex
     * @param e
     * @return 
     */
    public boolean isAdjacenthex(Hexagon e){
        
        for(int i=0;i<adjacentHex.size();i++){
            if(adjacentHex.get(i)==e) return true;
        }
        return false;
    }

    public Bonus[] getBonus() {
        return bonus;
    }

    public void setBonus(Bonus[] bonus) {
        this.bonus = bonus;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    
}
