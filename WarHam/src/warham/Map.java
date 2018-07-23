
package warham;

import acm.graphics.GImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Map {
    
    private Hexagon[][] hex;
    private Unit[] UnitsA,UnitsB;
    private int height,width;
    private static int size;
    private String map_skin;
    
    public Hexagon getHex(int i,int j){
        if(hex==null||hex[0]==null||i>=hex.length||j>=hex[0].length||i<0||j<0){
            System.err.println("Error Map:getHex: invalid input, i:"+i+" ,j:"+j+
                    " ,HL:"+hex.length+" ,HoL:"+hex[0].length);
            return null;
        }
        return hex[i][j];
    }
    
    public boolean readMapCSVFile(String filename){
        Map out=null;
        BufferedReader in = null;
        int tempA,tempB,tempC,tempD;
        hex=null;
        try {
            in = new BufferedReader(new FileReader(filename));
            String line;
            line = in.readLine();//Header Info
            getHeaderInfo(line);//Create the array
            System.out.println("Read Header successfully");
            for(int j=0;j<hex.length;j++){
                line = in.readLine();
                String temp[]=line.split(",");//prepei sizeofCandidates=sizeOftemp
                for(int i=0;i<temp.length;i++){
                    tempA=Integer.parseInt(temp[i]);
                    hex[j][i]=new Hexagon(tempA,j,i,map_skin);
                }
            }
            addAdjacenthextoMapHex();
            System.out.println("Read Map File successfully");            
        } catch (IOException ex) {
            System.err.println("Error could not read Map File");
            return false;
        }
        
        
        try {
            in.close();
            System.out.println("BufferedReader closed successfully");
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    //header: 35#12#map_skin . 
    public void getHeaderInfo(String line){
        String[] info=line.split("#");
        height=Integer.parseInt(info[0]);
        width=Integer.parseInt(info[1]);
        map_skin=info[2];
        System.out.println("map_skin:"+map_skin);
        hex=new Hexagon[height][width];
    }
    
    public void addAdjacenthextoMapHex(){
     Hexagon temp;
            int count;
            for(int i=0;i<hex.length;i++){//adding the adjacent hexagons
                for(int j=0;j<hex[0].length;j++){
                    count=0;
                    temp=hex[i][j];
                    if(j>0){ count++; temp.addAdjacentHex(hex[i][j-1]); }//de3io
                    if(i>0){ count++; temp.addAdjacentHex(hex[i-1][j]); }//panw
                    if(i<hex.length-1){ count++; temp.addAdjacentHex(hex[i+1][j]);}//katw
                    if(j<hex[0].length-1){ count++; temp.addAdjacentHex(hex[i][j+1]); }//aristero
                    
                    if(i%2==0){
                        if(i>0&&j>0){ count++; temp.addAdjacentHex(hex[i-1][j-1]); }//panwB
                        if(i<hex.length-2&&j>0){ count++; temp.addAdjacentHex(hex[i+1][j-1]); }//katwB
                    }else{
                        if(i>0&&j<hex[0].length-2){ count++; temp.addAdjacentHex(hex[i-1][j+1]); }//panwB
                        if(i<hex.length-2&&j<hex[0].length-2){ count++; temp.addAdjacentHex(hex[i+1][j+1]); }//katwB
                    }
                    if(count==0){
                        System.err.println("Error Map:readMapCSVFile: must have at least 2 adjacent hexes,i,j="+i+","+j);
                    }
                }
            }
    }
    
    public ReportMessage moveUnitFromTo(Hexagon A,Hexagon B,Player player){
    	
    	ReportMessage out=new ReportMessage(true,"");
    	String message;
        if(A!=null&&B!=null){
            
            if(A.getUnit().getPlayer()!=player){
            	message="Player has not unit at this hex";
                out.setSuccessful(false);
                out.setMessage(message);
            }else if(A.getUnit().hasMovedThisRound()){
            	 message="This unit has moved already";
                 out.setSuccessful(false);
                 out.setMessage(message);
            }
            
            ReportMessage b=A.canDisUnitHereMoveTo(B);
            if(b.isSuccessful()==false) {
            	out.setSuccessful(false);
                out.setMessage(b.getMessage());
            }
                
        }else{
        	message="Map:moveUnitFromTo:SelectedA or SelectedB == null";
        	out.setSuccessful(false);
            out.setMessage(message);
        }
        return out;
    }
    /*
    public boolean moveUnitFromTo(Hexagon A,Hexagon B,Player player){
    	
        if(A!=null&&B!=null){
            
            if(A.getUnit().getPlayer()!=player){
                System.err.println("Map:UnitRangeAttackFromTo:Player has not unit at this hex");
                return false;
            }else if(A.getUnit().hasMovedThisRound()){
                 System.err.println("Map:UnitRangeAttackFromTo:This unit has played already");
                 return false;
            }
            
            if(A.canDisUnitHereMoveTo(B)){
                B.setUnit(A.getUnit());//unit goes to B
                A.UnitLeft();//unit leaves A
                B.getUnit().PlayedThisRound();//unit cannot play this round anymore
                return true;
            }else{
                return false;
            }
        }else{
            System.err.println("Map:moveUnitFromTo:SelectedA or SelectedB == null");
            return false;
        }
    }
    */
    
    /**
     * checks if A has unit played this round,if the units are from different player and calls Unit.canDisUnitHereShootTo
     * If positive calls PlayedThisRound() for the unit
     * @param A
     * @param B
     * @param player
     * @return result of canDisUnitHereShootTo
     */
    public boolean UnitRangeAttackFromTo(Hexagon A,Hexagon B,Player player){
        if(A!=null&&B!=null&&A.hasUnit()&&B.hasUnit()){
            if(A.getUnit().getPlayer()!=player){
                System.err.println("Map:UnitRangeAttackFromTo:Player has not unit at this hex");
            }else if(A.getUnit().hasMovedThisRound()){
                 System.err.println("Map:UnitRangeAttackFromTo:This unit has played already");
            }else if(B.getUnit().getPlayer()==player){
                 System.err.println("Map:UnitRangeAttackFromTo:This target is from the same faction");
            }else{
                boolean ans=A.canDisUnitHereShootTo(B);
                if(ans){
                    A.getUnit().PlayedThisRound();//unit cannot play this round anymore
                    return true;
                }
                return false;
            } 
        }else{
            System.err.println("Map:UnitRangeAttackFromTo:SelectedA or SelectedB == null");
            return false;
        }
        return false;
    }
    
    public ReportMessage UnitMeeleAttackFromTo(Hexagon A,Hexagon B,Player player){
    	ReportMessage ans=new ReportMessage(true,"");
        if(A!=null&&B!=null){
            if(A.getUnit().getPlayer()!=player){
            	ans.setMessage(":Player has not unit at this hex");
                ans.setSuccessful(false);
                return ans;
            }else if(A.getUnit().hasHitThisRound()){
            	ans.setMessage("This Unit has hit this round");
                ans.setSuccessful(false);
                return ans;
            }
            ReportMessage BR=A.canDisUnitHereMeeleAttack(B);
            if(BR.isSuccessful()==false) {
            	ans.setMessage(BR.getMessage());
            	ans.setSuccessful(false);
            	return ans;
            }
        }else{
            System.err.println("Map:UnitMeeleAttackFromTo:SelectedA or SelectedB == null");
            ans.setMessage("null hex");
            ans.setSuccessful(false);
        }
        return ans;
    }
    
    public static int getDirectionFromTo(Hexagon A,Hexagon B){
        int direction=0;
        int Ai=A.getI(), Aj=A.getJ();
        int Bi=B.getI(), Bj=B.getJ();
        int diffi=Math.abs(Ai-Bi),diffj=Math.abs(Aj-Bj);
        if(diffi>=diffj){
            if(Ai-Bi<0) return 2;
            else return 0;
        }else{
            if(Aj-Bj<0) return 1;
            else return 3;
        }
    }
    /**
     * 
     * @param A
     * @param B
     * @return integer from 0-3 ,which indicates to which direction B is from the point of A
     */
    public static int turnHead(Hexagon A,Hexagon B){
        int Ai=A.getI(), Aj=A.getJ();
        int Bi=B.getI(), Bj=B.getJ();
        if(Aj>Bj) return 0;
        else if(Aj<Bj) return 2;
        else if(Ai<Bi) return 1;
        else if(Ai>Bi) return 3;
        return -1;
    }
    
    /**
    *Flag based game:each Unit which is at a flag hexagon ,it gives a point at its team.
    *Hexagon can be a flag.It will be initialized by aux info at the text file.
    *Returns the points p1 will get ,if <0 it is p2 points.
    */
    /*
    public int getFlagPoints(Player p1,Player p2){
    	int len=hex.length;
    	int heig=hex[0].length;
    	int countP1=0,countP2=0;
    	Hexagon hexag;
    	for(int i=0;i<len;i++){
    			for(int j=0;j<heig;j++){
    					hexag=hex[i][j];
    					if(hexag.hasUnit()&&hexag.isFlag()){
    						if(hexag.getUnit().getPlayer()==p1){
    							countP1++;
    						}else{
    							countP2++;
    						}
    					}
    			}
    	}
    	return p1-p2;
    }

    
    */
    
    public Hexagon getHexFromKeyboard(int code,Hexagon currentHex) {
    	Hexagon hex=null;
    	int x=currentHex.getI();
    	int y=currentHex.getJ();
    	if(code==Config.MOVEMENT_BUTTONS_PLAYER1[0]||code==Config.MOVEMENT_BUTTONS_PLAYER2[0]) {//up
    		x--;
    		if(y<0) return null;
    	}else if(code==Config.MOVEMENT_BUTTONS_PLAYER1[1]||code==Config.MOVEMENT_BUTTONS_PLAYER2[1]) {//right
    		y++;
    		if(x>=width) return null;
    	}else if(code==Config.MOVEMENT_BUTTONS_PLAYER1[2]||code==Config.MOVEMENT_BUTTONS_PLAYER2[2]) {//down
    		x++;
    		if(y>=height) return null;
    	}else if(code==Config.MOVEMENT_BUTTONS_PLAYER1[3]||code==Config.MOVEMENT_BUTTONS_PLAYER2[3]) {//left
    		y--;
    		if(x<0) return null;
    	}else {
    		return null;
    	}
    	return getHex(x,y);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static int getHexSize() {
        return size;
    }

    public static void setHexSize(int size) {
        Map.size = size;
    }
    
    public Hexagon getHexfromUnit(Unit e) {
    	for (int i=0;i<hex.length;i++) {
    		for (int j=0;j<hex[0].length;j++) {
    			if(hex[i][j].hasUnit() && hex[i][j].getUnit() ==e ) {
    				return hex[i][j];
    			}
    		}
    	}
    	return null;
    }
    
    
}
