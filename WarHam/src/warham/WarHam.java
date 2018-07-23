
package warham;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.SwingUtilities;

import ConstantModules.Auxiliary;
import ConstantModules.Config;
import ConstantModules.DistanceMatrix;
import GUIModules.Animation;
import GUIModules.GUILogger;
import GUIModules.Portrait;
import GUIModules.ReportMessage;
import MapModules.Hexagon;
import UnitModules.MageUnit;
import UnitModules.Player;
import UnitModules.Projectile;
import UnitModules.RangeUnit;
import UnitModules.Spell;
import UnitModules.Unit;

public class WarHam extends GraphicsProgram {

    private Map map;
    protected static final String map_filename="files/testHexagonMap3_empty.txt";
    protected int height,width,clockX,clockY,pauseX,pauseY;
    protected double BorderAX,BorderAY,BorderBX,BorderBY,phase_labelX,phase_labelY;
    protected Hexagon selectedA,selectedB,selectedHoverP1,selectedHoverP2;
    protected GImage SelectedA,SelectedB,SelectedC,SelectedD,SelectedHoverP1,SelectedHoverP2,img_end_turn,Pause_icon,Resume_icon;
    protected Player player1,player2;
    protected boolean user_end_turn,animation_activated=false,paused=false,keyboard_input_mode=true;
    protected boolean b_player_phase[]=new boolean[8];//p1_mvm,p2_mvm,p1_range,p2_range,p1_magic,p2_magic,p1_combat,p2_combat
    protected GLabel Title,clock,phase_label;
    protected GUILogger Logger=new GUILogger();
    protected Portrait por1,por2;
    
    public static void main(String[] args) {
       new WarHam().start();
    }
    
    @Override
    public void init(){
        initialize();
        game();
    }
    

    
    public void game(){
        while(true){
            Phase(0,"Movement Phase");
            //Phase(2,"Range Phase");//check if anyone has range units
            //Phase(4,"Mage Phase");//check if anyone has range units
            Phase(6,"Meele Phase");
            endOfRoundJobs();
        }
    }
    
    public void endOfRoundJobs(){
        ResetPlayedThisRoundVar();
        refillMana();
        remove(SelectedHoverP1);
        remove(SelectedHoverP2);
        selectedHoverP1=null;
        selectedHoverP2=null;
    }
    
    //TODO:implement
    /*
    public void check_for_capture_the_flag_vitcory(){
    	int p1_points=map.getFlagPoints(p1,p2);
    	if(p1_points>0){
    		p1_flag_points=+p1_points;
    	}else if(p1_points<0){
    		p2_flag_points=+p1_points;
    	}
    	if(p1_points!=0) update_flag_point_Label();
    }
    */
    /**
     * refills the mana of all mages
     */
    public void refillMana(){
        ArrayList<Unit> arm=player1.getArmy();
        for(Unit u:arm){
            if(u instanceof MageUnit){
                MageUnit mg=(MageUnit)u;
                mg.TurnPassed();
            }
        }
        arm=player2.getArmy();
        for(Unit u:arm){
            if(u instanceof MageUnit){
                MageUnit mg=(MageUnit)u;
                mg.TurnPassed();
            }
        }
    }
    
    public void initialize(){
        Preinitialize();
        showMap();
        Proinitialize();
    }
    /**
     * before
     * reads map from txt file,set width,height of window,initializes the state of the phase
     */
    protected void Preinitialize(){
        map=new Map();
        boolean an=map.readMapCSVFile(map_filename);
        DistanceMatrix.setHW(map.getHeight(),map.getWidth());
        int width=1400;
        int height=750;
        GImage bck=new GImage("images/background.png");
        bck.setSize(width, 1100);
        add(bck,0,0);
        setSize(width,height);
        this.width=getWidth();
        this.height=getHeight();
        for(int i=0;i<8;i++) b_player_phase[i]=false;
        phase_labelX=((double)(width))/5.0;
        phase_labelY=((double)(height))/25.0;
        print("initilization completed,width:"+width+" ,height"+height+" phase_labelX:"+phase_labelX+" phase_labelY:"+phase_labelY);
    }
    /**
     * after
     * initializes the players ,add the logger,the portraits, the clock ,clockImg and the units,also enables the event listeners
     */
    protected void Proinitialize(){
    	
    	int size=(height)/map.getHeight();//stepWidth,StepHeight
        SelectedA=new GImage("images/selected.png");
        SelectedA.setSize(size*110/100, size*110/100);
        SelectedB=new GImage("images/selectedB.png");
        SelectedB.setSize(size*110/100, size*110/100);
        if(!keyboard_input_mode)  SelectedC=new GImage("images/selected2.png");
        else SelectedC=new GImage("images/selected.png");
        SelectedC.setSize(size*110/100, size*110/100);
        if(!keyboard_input_mode)  SelectedD=new GImage("images/selectedB2.png");
        else SelectedD=new GImage("images/selectedB.png");
        SelectedD.setSize(size*110/100, size*110/100);
        
        SelectedHoverP1=new GImage("images/selectedHoverP1.png");
        SelectedHoverP1.setSize(size*110/100, size*110/100);
        SelectedHoverP2=new GImage("images/selectedHoverP2.png");
        SelectedHoverP2.setSize(size*110/100, size*110/100);
        
        img_end_turn=new GImage("images/End_Turn.png");
        img_end_turn.setSize(width/8,height/12);
        player1=new Player("Player 1");
        player2=new Player("Player 2");
        add(img_end_turn,width/20,8*height/10);
        double Bordex=BorderBX+BorderBX/30;
        Pause_icon=new GImage("images/pause.png");
        Resume_icon=new GImage("images/play.png");
        int pause_width=width/35;
        pauseX= (int)(width-2.5*pause_width);
        pauseY=(int)height/15;
        Pause_icon.setSize(pause_width,pause_width);
        Resume_icon.setSize(pause_width,pause_width);
        add(Pause_icon,pauseX,pauseY);
        int stepY=height/40;
        int max_chars=30,rows=10;
        Logger=new GUILogger(rows,max_chars,(int) Bordex,2*height/3,stepY);
        int GUILogWidth=9*max_chars;
        int GUILogHeight=rows*30;
        Logger.setImage(new GImage("images/GUILogger.png"));
        Logger.getImage().setSize(GUILogWidth, GUILogHeight);
        add(Logger.getImage(),Logger.getX(),Logger.getY()-height/25);
        clockX=(int) Bordex;clockY=height/10;
        GImage clockGI=new GImage("images/clock.png");
        clockGI.setSize(width/25,width/25);
        add(clockGI,clockX-width/65,clockY-height/24);
        //****************************************************
        int startX=width/30;int startY=height*5/28;
        int sizeXX=(int) (BorderAX-startX-map.getHexSize()/2);
        por1=new Portrait(new GImage("images/background_panel.png"),startX,startY,sizeXX,height*4/9);
        add(por1.getBackground(),startX,startY);
        por2=new Portrait(new GImage("images/background_panel.png"),(int) Bordex,startY,sizeXX,height*4/9);
        add(por2.getBackground(),Bordex,startY);
        
        //test add **********************************
        testUnitAdd();
        addMouseListeners();
        addKeyListeners();
    }
    
    /**
     * initialization method for showing the hexagons of the map and some depended variables
     */
    protected void showMap(){
        int h=map.getHeight();
        int w=map.getWidth();
        //8elw border kai map diafora 50 pixel
        int startmapHeight=height/14;
        int startmapWidth=width/5;//(width-height)/2;
        BorderAX=startmapWidth;
        BorderAY=startmapHeight;
        //ta pixel pou exoume dia8esima / poses seires exoume
        double size=(height)/map.getHeight();//stepWidth,StepHeight
        System.out.println("BorderAX:"+BorderAX+" ,BorderAY:"+BorderAY+" ,size:"+size+" config,"+Config.step_width);
        map.setHexSize((int) size);
        double counterH=startmapHeight;
        double counterW=startmapWidth;
        Hexagon temp;
        
        for(int i=0;i<h;i++){
            if(i%2==1) startmapWidth=(int) (startmapWidth+size/2);
            else if(i%2==0&&i>0) startmapWidth=(int) (startmapWidth-size/2);
            counterW=startmapWidth;
            for(int j=0;j<w;j++){
                temp=map.getHex(i, j);
                GImage tempGI=temp.getCoreImage();
                tempGI.setSize(size, size);
                add(tempGI,counterW,counterH);
                //add(new GLabel((i+1)+" "+(j+1),counterW+5,counterH+2*size/5));//********************* TEST
                temp.setPosX((int)counterW);
                temp.setPosY((int)counterH);
                counterW=counterW+size+size*1/20;
            }
            counterH=counterH+(int)(size*4/5);
        }
        BorderBY=counterH;//because last counterH++ will not be accepted to the loop
        BorderBX=counterW;
    }
    
    /**
     * checks if a Image is out of bounds of the map
     * @return true if is  out of bounds
     */
    protected boolean out_of_bounds(GImage x) {
    	if(x.getX()<BorderAX||x.getY()<BorderAY||x.getX()>BorderBX||x.getY()>BorderBY) return true;
    	return false;
    }
    
    /**
     * Phase of turn
     * @param phase
     * @param description_phase 
     */
    public void Phase(int phase,String description_phase){
        //add GLabel me to desc
        //add pote paizei o kauenas
        //maybe bale ka8usterhsh h timer
        if(phase<0||phase>7||phase%2==1){
            print("WarHam:Phase Error");
            return;
        }
        boolean skip_playP1=skipPhase(phase,player1);
        boolean skip_playP2=skipPhase(phase,player2);
        Random rand = new Random();
        int  n = rand.nextInt(2);
        user_end_turn=false;
        print(description_phase+"!!!");
        if(n==0){
        	if(skip_playP1) {
        		addToLogger(player1.getName()+" has not units to play "+description_phase+" turn.");
        	}else {
        		update_phase_label(description_phase,player1);
        		addToLogger(player1.getName()+" "+description_phase+" turn.");
                b_player_phase[phase]=true;
                loop_turn(phase);
                b_player_phase[phase]=false;
        	}
        	if(skip_playP2) {
        		addToLogger(player2.getName()+" has not units to play "+description_phase+" turn.");
        	}else {
        		update_phase_label(description_phase,player2);
        		addToLogger(player2.getName()+" "+description_phase+" turn.");
                b_player_phase[phase+1]=true;
                loop_turn(phase);
                b_player_phase[phase+1]=false;
        	}
        }else{
        	if(skip_playP2) {
        		addToLogger(player2.getName()+" has not units to play "+description_phase+" turn.");
        	}else {
        		update_phase_label(description_phase,player2);
        		addToLogger(player2.getName()+" "+description_phase+" turn.");
                b_player_phase[phase+1]=true;
                loop_turn(phase);
                b_player_phase[phase+1]=false;
        	}
        	if(skip_playP1) {
        		addToLogger(player1.getName()+" has not units to play "+description_phase+" turn.");
        	}else {
        		update_phase_label(description_phase,player1);
        		addToLogger(player1.getName()+" "+description_phase+" turn.");
                b_player_phase[phase]=true;
                loop_turn(phase);
                b_player_phase[phase]=false;
        	}
        }
    }
    
    public void update_phase_label(String phase,Player p) {
    	if(phase_label!=null) remove(phase_label);
    	if(phase==null||p==null) return;
    	phase_label=new GLabel(p.getName()+ "'s "+phase);
    	phase_label.setColor(Config.FONT_COLOR_PHASE_LABEL);
    	double font_size=Config.FONT_SIZE_PHASE_LABEL*(float)width;
    	//TODO: set max and min font size!!!
    	print("font_size"+font_size);
    	phase_label.setFont("*-BOLD-"+(int)font_size);
    	add(phase_label,phase_labelX,phase_labelY);
    	print("updated phase label");
    }
    
    public boolean skipPhase(int phase,Player p) {
    	if(phase==0) return false;
    	ArrayList<Unit> units = p.getArmy();
    	if(phase==2) {
    		for(Unit e:units) {
    			if(e instanceof RangeUnit && e.hasMovedThisRound()==false) {
    				return false;
    			}
    		}
    	}else if (phase==4) {
    		for(Unit e:units) {
    			if(e instanceof MageUnit ) {
    				return false;
    			}
    		}
    	}else if (phase==6) {
    		Hexagon temphex;
    		ArrayList<Hexagon> HexagonsAdj;
    		for(Unit e:units) {
    			temphex=map.getHexfromUnit(e);
    			HexagonsAdj=temphex.getAdjacentHexList();
    			for(Hexagon hx:HexagonsAdj) {
    				if (hx.hasUnit() && hx.getUnit().getPlayer()!=p) {
    					return false;
    				}
    			}
    		}
    	}
    	return true;
    }
    
    public void ResetPlayedThisRoundVar(){
        for(Unit u:player1.getArmy()){
            u.setMovedthisround(false);
            u.setHitthisround(false);
        }
        for(Unit u:player2.getArmy()){
            u.setMovedthisround(false);
            u.setHitthisround(false);
        }
    }
    /**
     * This method waits for a player to end his turn or the time to end to end the turn
     * @param phase 
     */
    public void loop_turn(int phase){
        int counter=0,wait_time=Config.c_time_per_round/100;
        int seconds=Config.c_time_per_round/1000;//40 seconds
        refreshClock(seconds);
        double second_clock=0;
        while(!user_end_turn&&counter<100){
        	pause(wait_time);
        	if(paused==false) {
                second_clock=second_clock+(double)wait_time/1000.0;
                counter++;
                if(second_clock>=1){//a second passed
                    second_clock=second_clock-1;
                    seconds--;
                    refreshClock(seconds);
                }
                if(animation_activated) animation(phase);
        	}
        }
        remove(clock);
        user_end_turn=false;
        clear_all_for_next_turn();
        clearPortrait(true);clearPortrait(false);//clear portraits
    }
    
    public void refreshClock(int seconds){
        if(clock!=null) remove(clock);
        clock=new GLabel(""+seconds);//TO DO: WASTING RESOURCES (?)
        clock.setColor(Color.RED);
        clock.setFont("*-BOLD-20");
        add(clock,clockX,clockY);
    }

    
    public void animation(int phase){
        animation_activated=false;
        Player p;
        int direction=map.getDirectionFromTo(selectedA,selectedB);
        if(phase%2==0) p=player1;
        else  p=player2;
        if(phase==2||phase==3){//projectile
        	activateAnimation(selectedA,false,direction);
            activateProjectile(p,selectedA,selectedB);//projectile
        }else if(phase==4||phase==5){//magic
            activateSpell(selectedA,selectedB);
        }else if(phase==6||phase==7){//combat
        	print("dir:"+direction);
        	move_unit_to_dir(selectedA, direction,false);
        	
            activateAnimation(selectedA,true,direction);
            activateMeeleHit(selectedA,selectedB);
            move_unit_to_opposite_dir(selectedA, direction,true);
        }
    }
    
    public void activateMeeleHit(Hexagon source,Hexagon target){
        int dmgTaken=Auxiliary.MeeleDamage(source.getUnit().getMeeleDmg(), target);
        source.getUnit().HitThisRound();
        updatePortrait(source,true);
        if(dmgTaken==0){
            addToLogger(target.getUnit().getDescription()+" has blocked the hit");
        }else{
            takeDamage(dmgTaken,target);
        }
    }
    
    protected boolean ClickedInsideTheHexMap(int x,int y){
        if(x>BorderAX-map.getHexSize()&&x<BorderBX+map.getHexSize()&&y>BorderAY&&y<BorderBY) return true;
        return false;
    }
    
    protected Hexagon getClickedHex(int x,int y){
        for(int i=0;i<map.getHeight();i++){
            for(int j=0;j<map.getHeight();j++){
                if(map.getHex(i, j).getCoreImage().contains(x, y)) return map.getHex(i, j);
            }
        }
        return null;
    }
    
    public void clicked_at_pause_button() {
    	paused=(!paused);
    	GImage x;
    	if(paused==true) {
    		x=Resume_icon;
    	}else {
    		x=Pause_icon;
    	}
    	add(x,pauseX,pauseY);
    }
    
    public void mouseReleased(MouseEvent e) {
        int eX=e.getX(),eY=e.getY();
        int phase=PresentPhase();
        if(phase!=-1){
            Player p;
            if(phase%2==0) p=player1;
            else p=player2;
            if(paused==false&&ClickedInsideTheHexMap(eX,eY)){
                Hexagon hex=getClickedHex(eX,eY);
                boolean leftclick=SwingUtilities.isLeftMouseButton(e);
                if(hex!=null) SelectHex(hex,leftclick,p);
            }else if(paused==false&&img_end_turn.getBounds().contains(eX, eY)){//pressed end turn
                user_end_turn=true;
                //TODO: grafika gia button
            }else{
            	GImage pauseB;
            	if(paused) pauseB=Resume_icon;
            	else pauseB=Pause_icon;
            	if(pauseB.getBounds().contains(eX, eY)) {//pressed pause button
            		clicked_at_pause_button();
            	}
            }
        }
        
    }
    /**
     * 
     * @return the number of the phase the game currently is
     */
    public int PresentPhase(){
        for(int i=0;i<b_player_phase.length;i++){
            if(b_player_phase[i]) return i;
        }
        return -1;
    }
    
	/**
	 * adds the correct Selected Outline Image at the target Hex based on the player and the click.
	 * Also gives the correct value to teh selectedHex variables(selectedA,selectedB);
	 * @param e
	 * @param leftclick
	 * @param pl
	 */
    public void SelectHex(Hexagon e,boolean leftclick,Player pl){
        int posX=e.getPosX();
        int posY=e.getPosY();
        int size=map.getHexSize();
        //select process
        GImage leftclick_select_image,rightclick_select_image;
        if(pl==player1){
            leftclick_select_image=SelectedA;
            rightclick_select_image=SelectedB;
        }else{ 
            leftclick_select_image=SelectedC;
            rightclick_select_image=SelectedD;
        }
        if(leftclick){
            selectedA=e;
            selectedB=null;
            remove(rightclick_select_image);
            add(leftclick_select_image,e.getPosX()-size/15,e.getPosY()-size/15);
            updatePortrait(e,true);
        }else{
            if(selectedA!=null&&selectedA!=e){ 
                add(rightclick_select_image,e.getPosX()-size/15,e.getPosY()-size/15);
                selectedB=e;
                updatePortrait(e,false);
            }
        }
    }
    
    public boolean addUnitToMap(Unit u,int i,int j){
        Hexagon hex=map.getHex(i, j);
        if(hex.hasUnit()){
            return false;
        } else{
            hex.setUnit(u);
            //int direction=0;
            //if(u.getPlayer()==player2) direction=3;
            GImage img=u.getImage();
            if(img==null) print("Error null img at add unit to map");
            img.setSize(u.getImgsize(),u.getImgsize());
            int sizeHex=hex.getSize()*1/10;
            add(img,hex.getPosX()+sizeHex,hex.getPosY()+sizeHex);
        }
        return true;
    }

    //Return true if unit is eligible to move to the selected hex
    public boolean RequestForUnitMoveFromTo(Player player){
        if(selectedA==null||selectedB==null){
        	addToLogger(" You have not selected any hexes");
            return false;
        }
        ReportMessage ans = map.moveUnitFromTo(selectedA,selectedB, player);
        if(ans.isSuccessful()==false) addToLogger(ans.getMessage());
        
        return ans.isSuccessful();
    }
    
    public void moveSelectedUnittoSelectedHex() {
    	
    	if(selectedA==null||selectedB==null) {
    		return;
    	}
        //GUI
    	GImage img=selectedA.getUnit().getImage();
        Player p=selectedA.getUnit().getPlayer();
        Unit u=selectedA.getUnit();
        if(img==null) System.err.println("moveSelectedUnittoSelectedHex: img is null");
        remove(img);
        int dir=map.getDirectionFromTo(selectedA, selectedB);
        img=u.getAnimation().getDefaultFrame(dir);
        img.setSize(u.getImgsize(),u.getImgsize());///why must I do it every time????
        u.setImage(img);
        add(img,selectedB.getPosX(),selectedB.getPosY());
        updatePortrait(selectedB,true);
        //background
    	Hexagon A=selectedA;
    	Hexagon B=selectedB;
    	B.setUnit(A.getUnit());//unit goes to B
        A.UnitLeft();//unit leaves A
        B.getUnit().PlayedThisRound();//unit cannot play this round anymore
}
    
    public boolean RequestForUnitMeeleFromTo(Player player){
        if(selectedA==null||selectedB==null){
        	addToLogger("You have not selected any hexes");
            return false;
        }else if(!selectedA.hasUnit()||!selectedB.hasUnit()){
        	addToLogger("One of the hexes has no unit");
            return false;
        }
        ReportMessage ans = map.UnitMeeleAttackFromTo(selectedA,selectedB, player);
        if(ans.isSuccessful()==false) addToLogger(ans.getMessage());
        
        return ans.isSuccessful();
    }
    
    public boolean RequestForUnitShootFromTo(Player player){
        if(selectedA==null||selectedB==null){
            System.err.println("WarHam:RequestForUnitShootFromTo: You have not selected any hexes");
            return false;
        }else if(!(selectedA.getUnit() instanceof RangeUnit)){
            System.err.println("WarHam:RequestForUnitShootFromTo: this Unit has no ranged abilities");
            return false;
        }
        GImage img=selectedA.getUnit().getImage();
        boolean ans = map.UnitRangeAttackFromTo(selectedA,selectedB, player);
        return ans;
    }
    
    
    public void testUnitAdd(){
        Unit u;
        int unit_size=map.getHexSize();//katw
        for(int i=5;i<7;i++){//katw
            u=Auxiliary.getEmpire_Swordman(player1,map.getHexSize(),0);
            if (i==9) u=Auxiliary.getEmpire_CrossbowMan(player1,map.getHexSize(),0);
            if(u!=null){
                addUnitToMap(u,6,i);
                player1.addUnit(u);
            }else{
                print("ERROR aux send null unit");
            }
            
        }
        //getEmpire_CrossbowMan

        for(int i=5;i<8;i++){//panw
            u=Auxiliary.getEmpire_Swordman(player2,map.getHexSize(),2);
            if (i==6) u=Auxiliary.getEmpire_CrossbowMan(player2,map.getHexSize(),2);
            if(u!=null){
                addUnitToMap(u,4,i);
                player2.addUnit(u);
            }else{
                print("ERROR aux send null unit");
            }
        }

    }
    

    public void print(String x){
        System.out.println(x);
    }
    
    public void clear_all_for_next_turn(){
        //remove select outlines
        user_end_turn=false;
        remove(SelectedA);
        remove(SelectedB);
        remove(SelectedC);
        remove(SelectedD);
        selectedA=null;
        selectedB=null;
    }
    
    /**
     * pauses the game.Spawns a projectile (if it is possible) from shooter which is aimed at the target.Animates the projectile 
     * movement and if it hits something it calls takeDamage() method
     * @param shooter
     * @param source
     * @param target 
     */
    public void activateProjectile(Player shooter,Hexagon  source,Hexagon target){
        Projectile proj=((RangeUnit)(source.getUnit())).getProjectile();
        GImage img=proj.getImage();
        int center=map.getHexSize()/4;//why though?
        double sX=source.getPosX()+center,tX=target.getPosX()+center;
        double sY=source.getPosY()+center,tY=target.getPosY()+center;
        img.setSize(proj.getSizeX(),proj.getSizeY());
        add(img,sX+center,sY+center);//why though?
        double speed=proj.getSpeed();
        double diffX=Math.abs(sX-tX);//aux
        double diffY=Math.abs(tY-sY);//aux
        double speedX=diffX/(diffX+diffY)*speed;
        double speedY=diffY/(diffX+diffY)*speed;
        if(tX<sX) speedX=-1*speedX;
        if(tY<sY) speedY=-1*speedY;
        double posX,posY;
        ArrayList<Hexagon> fmg_dmg_dealt=new ArrayList<>();
        while(true){
            img.move(speedX, speedY);//Î¹ÏƒÏ‰Ï‚ ÏƒÏ„Ï�Î¿Î³Î³Ï…Î»Î¿Ï€Î¿Î¹Î·ÏƒÎ· ÏƒÏ„Î¿ 0
            pause(Config.time_per_frame);
            posX=(int)img.getX()+img.getWidth()/2;
            posY=(int)img.getY()+img.getHeight()/2;
            Hexagon hex=getClickedHex((int)posX,(int)posY);
            if(hex==null){//does not mean it is out of bounds because there are spaces between the hexagons
            	if(out_of_bounds(img)) {
            		print("Warham:activateProjectile: Projectile got out of bounds");
            		break;
            	}
            }else if(hex.isWall()){
                print("Warham:activateProjectile: Projectile hit a Wall(Trump Intensifies)");
                break;
            }else if(hex.hasUnit()&&hex!=source&&!fmg_dmg_dealt.contains(hex)){//friendly fire?
                print("Warham:activateProjectile: Projectile hit something");
                int distance=DistanceMatrix.getShootDistance(source,hex);//RAW DISTANCE
                boolean didithit=Auxiliary.RangeAttack(hex,distance,(RangeUnit)source.getUnit());
                if(didithit){
                    print("damage");
                    takeDamage(proj.getDamage(),hex);
                }else {
                	show_dmg_label(0,hex);
                	print("missed");
                }
                if(!proj.isFmg()) {
                	print("no damage");
                	break;//penetration
                }
                else{
                    fmg_dmg_dealt.add(hex);
                }
            }
        }
        updatePortrait(source,true);
        remove(img);
    }

    public void keyPressed(KeyEvent e) {
    	Player p;
        if(e.getKeyCode()==KeyEvent.VK_SPACE&&keyboard_input_mode==false){
            int phase=PresentPhase();
            if(phase%2==0) p=player1;
            else p=player2;
            Action(phase,p);
        }else if(keyboard_input_mode==true){
        	int button =e.getKeyCode();
        	int phase=PresentPhase();
            if(phase%2==0) p=player1;
            else p=player2;
            Hexagon tempH;
            //hex leftclick p
            if(button==Config.ACTION_BUTTON_PLAYER1&&p==player1) {
            	Action(phase,p);
            }else if(button==Config.ACTION_BUTTON_PLAYER2&&p==player2) {
            	Action(phase,p);
            }else if(Auxiliary.contains(Config.MOVEMENT_BUTTONS_PLAYER1,button)&&p==player1) {
            	if(selectedHoverP1==null) {
            		SelectHoverHex(map.getHex(0, 0),p);
            	}else {
            		tempH=map.getHexFromKeyboard(button, selectedHoverP1);
            		if(tempH!=null) SelectHoverHex(tempH,p);
            	}
            }else if(Auxiliary.contains(Config.MOVEMENT_BUTTONS_PLAYER2,button)&&p==player2) {
            	if(selectedHoverP2==null) {
            		SelectHoverHex(map.getHex(0, 0),p);
            	}else {
            		tempH=map.getHexFromKeyboard(button, selectedHoverP2);
            		if(tempH!=null) SelectHoverHex(tempH,p);
            	}
            }else if(button==Config.TARGET_BUTTON_PLAYER1&&p==player1&&selectedHoverP1!=null) {
            	SelectHex(selectedHoverP1,false,p);
            }else if(button==Config.TARGET_BUTTON_PLAYER2&&p==player2&&selectedHoverP2!=null) {
            	SelectHex(selectedHoverP2,false,p);
            }else if(button==Config.SELECT_BUTTON_PLAYER1&&p==player1&&selectedHoverP1!=null) {
            	SelectHex(selectedHoverP1,true,p);
            }else if(button==Config.SELECT_BUTTON_PLAYER2&&p==player2&&selectedHoverP2!=null) {
            	SelectHex(selectedHoverP2,true,p);
            }
        	
        }
        
    }
    /**
     * removes the old selectedHover image,defines the new selectedHover hexagon,adds the new selectedHover image
     * @param e
     * @param p
     */
    public void SelectHoverHex(Hexagon e,Player p) {
    	if(player1==p) {
    		remove(SelectedHoverP1);
    		selectedHoverP1=e;
    		add(SelectedHoverP1,e.getPosX(),e.getPosY());
    	}else{
    		remove(SelectedHoverP2);
    		selectedHoverP2=e;
    		add(SelectedHoverP2,e.getPosX(),e.getPosY());
    	}
    }
    
    public void Action(int phase,Player p) {
    	if(phase==0||phase==1){//movement phase
            if(selectedA!=null&&selectedB!=null){
                boolean ans=RequestForUnitMoveFromTo(p);
                if(ans==true) {
                	moveSelectedUnittoSelectedHex();
                }
            }
        }else if(phase==2||phase==3){//range phase
            if(selectedA!=null&&selectedB!=null){
                boolean ans=RequestForUnitShootFromTo(p);
                if(ans){
                    animation_activated=true;
                }
            }
        }else if(phase==4||phase==5){//magic phase
            if(selectedA!=null&&selectedB!=null){
                boolean ans=RequestForUnitToCastSpell(p);
                if(ans){
                    animation_activated=true;
                }
            }
        }else if(phase==6||phase==7){//combat phase
            if(selectedA!=null&&selectedB!=null){
                boolean ans=RequestForUnitMeeleFromTo(p);
                if(ans){
                    animation_activated=true;
                }
            }
        }
    }
    
    
    
    /**
     * adds a new message to the GUI Logger and show all the messages
     */
    public void addToLogger(String x){
    	if(x==null) return;
    	ArrayList<GLabel> lbl;
    	lbl=Logger.addString(x);//lbl is the Glabel that should be deleted
        if(lbl!=null) {
        	for(GLabel lab:lbl) {
        		remove(lab);
        	}
        }
        showLog();
    }
    
    
    
    /**
     * used mainly by addToLogger() method
     * shows the messages of the GUI LOgger
     */
    public void showLog(){
		if(Logger.size()>0&&Logger.Hasbeenupdated()==true){
			clearLog();
			Logger.setHasbeenupdated(false);//must be inserted something new
			double X=Logger.getX();
			double Y=Logger.getY();
			double stepY=Logger.getStepY();
			ArrayList<GLabel> labels=Logger.getMessages();
	                for(int i=Logger.size()-1;i>=0;i--){
	                    add(labels.get(i),X+width/95,Y);
	                    Y=Y+stepY;
	                }
		}
    }
    
    /**
     * used by showLog() method
     * removes all messages from the GUI Logger
     */
    public void clearLog(){
        if(Logger.size()>0){
            ArrayList<GLabel> labels=Logger.getMessages();
            for(GLabel lbl:labels){
                remove(lbl);
            }
        }
    }
    
    public void clearPortrait(boolean left){
        Portrait using=por1;
        if(left==false) using=por2;
        
        if(using.getImg()!=null) remove(using.getImg());  
        if(using.getCan_hit_icon()!=null) remove(using.getCan_hit_icon());
        if(using.getCan_range_icon()!=null) remove(using.getCan_range_icon());
        if(using.getCan_move_icon()!=null) remove(using.getCan_move_icon());
        
        if(using.getLabels()!=null){
            for(GLabel l:using.getLabels()){
                remove(l);
            }
        }
        
    }
    
    public void updatePortrait(Hexagon hex,boolean left){
        clearPortrait(left);
        Portrait using=por1;
        if(left==false) using=por2;
        if(using.getImg()!=null){//remove old img
            remove(using.getImg());
        }
        if(hex.hasUnit()){
            //add img of unit
            Unit tu=hex.getUnit();
            GImage newimg=tu.getPorimg();
            
            using.setImg(newimg);//add new img
            newimg.setSize(using.getUnitSize(), using.getUnitSize());
            add(newimg,using.getImgX(),using.getImgY());
            Unit u=hex.getUnit();
            if(u!=null&&left){//icons only for left portrait
                int phase=PresentPhase();
                double posX=using.getImgX()+using.getBackground().getWidth()*3/5;
                double posY=using.getImgY()+6/5*using.getUnitSize();
                if(phase==0||phase==1){
                    if(!u.hasMovedThisRound()){
                        add(using.getCan_move_icon(),posX,posY);
                    }
                }
                else if(phase>=2&&phase<=5&&(u instanceof RangeUnit||u instanceof MageUnit)){
                    if(!u.hasMovedThisRound()){
                        add(using.getCan_range_icon(),posX,posY);
                    }
                }
                else if(phase==6||phase==7){
                    if(!u.hasHitThisRound()){
                        add(using.getCan_hit_icon(),posX,posY);
                    }
                }
            }
            //add info of unit
            addStats(hex,using);
        }else{
            //String desc=hex.toString();
            //addStats(hex,using);
        }
    }
    
    public void addStats(Hexagon hex,Portrait using){
        String[] info=hex.getUnit().getInfo();
        if(info==null) return;
        GLabel tmp;
        int step=0;
        for(String txt:info){
            tmp=new GLabel(txt);
            tmp.setColor(Color.gray);
            tmp.setFont("*-bold-20");
            using.addGLabel(tmp);
            add(tmp,using.getLabelX(),using.getLabelY()+step);
            step=step+using.getStepY();
        }
    }
    
    public boolean RequestForUnitToCastSpell(Player p){
        Hexagon source=selectedA;
        Hexagon target=selectedB;
        if(target==null||source==null){
            return false;
        }
        
        if(!source.hasUnit()){
            addToLogger("Player has no unit here");
            return false;
        }
        
        if(source.getUnit().getPlayer()!=p){
            addToLogger("This unit does not belong to the player");
            return false;
        }
        
        if(source==target){
            addToLogger("Source and target must be different");
            return false;
        }
        Unit e=source.getUnit();
        if(!(e instanceof MageUnit)){
            addToLogger("This unit is not a mage");
            return false;
        }
        if(e.hasMovedThisRound()){
            addToLogger("This unit has played this round");
            return false;
        }
        
        MageUnit mag=(MageUnit)e;
        int distance=DistanceMatrix.getShootDistance(source, target);
        
        if(!mag.hasMana()){
            addToLogger("This mage has no mana");
            return false;
        }
        
        if(mag.getRange()<distance){
            addToLogger("This unit has not enough range");
            return false;
        }
        return true;

    }
    
    public void activateSpell(Hexagon source,Hexagon target){
        MageUnit mag=(MageUnit)source.getUnit();
        mag.SpellCast();
        int area=mag.getSpell().getAoeArea();
        if(target.hasUnit()){
            Spell spl=mag.getSpell();
            ReportMessage rpt=spl.activateSpellEffect(target.getUnit());
            addToLogger(rpt.getMessage());
            takeDamage(rpt.getValue(),target);
        }
        if(area>0){
            if(area>=1){
                for(Hexagon hex:target.getAdjacentHexList()){
                    if(hex.hasUnit()){
                        Spell spl=mag.getSpell();
                        ReportMessage rpt=spl.activateSpellEffect(hex.getUnit());
                        takeDamage(rpt.getValue(),target);
                    }
                }
            }
            if(area==2){
                for(Hexagon hex:target.getAdjacentHexList()){
                    for(Hexagon hexInside:hex.getAdjacentHexList()){
                        if(!hexInside.isAdjacenthex(target)&&hexInside.hasUnit()){
                            Spell spl=mag.getSpell();
                            ReportMessage rpt=spl.activateSpellEffect(hexInside.getUnit());
                            takeDamage(rpt.getValue(),target);
                        }
                    }
                }
            }
        }
    }
    
    public void takeDamage(int dmg,Hexagon h){
        if(dmg==0) {
        	show_dmg_label(dmg,h);
        	return;
        }
        animation_hit_impact(h,map.getDirectionFromTo(selectedA,selectedB),false);//animation of the hit impact
        show_dmg_label(dmg,h);//animation dmg label
        Unit target=h.getUnit();
        int hp=target.getHp();
        hp=hp-dmg;
        target.setHp(hp);
        Player p=target.getPlayer();
        if(hp<=0){
            p.removeUnit(target);
            remove(target.getImage());
            h.UnitLeft();
            add(target.getAnimation().getDeadImg(),h.getPosX(),h.getPosY());
            addToLogger(target.getDescription()+" has been killed.");
            clearPortrait(false);//clear right portrait
        }else{
            addToLogger(target.getDescription()+" has taken "+dmg+" damage.");
            updatePortrait(h,false);//update right portrait
        }
    }
    /**
     * Shows the damage which is being inflicted to the target Unit in the Hexagon.If it is 
     * zero then it shows the word missed.
     * @param damage
     * @param hex
     */
    public void show_dmg_label(int damage,Hexagon hex) {
    	if(hex==null||hex.hasUnit()==false) return;
    	GLabel label;
    	if(damage<=0) label=new GLabel("Missed");
    	else label=new GLabel("-"+damage);
    	label.setColor(Color.RED);
    	label.setFont("*-BOLD-20");
    	add(label,hex.getPosX()+hex.getSize()/4,hex.getPosY()+hex.getSize()/4);
    	int step=hex.getSize()/20;
    	if(step==0) step=1;
    	for(int i=0;i<Config.FRAMES_PER_DMG_INDICATOR;i++) {
    		label.move(0,-step);
    		pause(Config.time_per_frame);
    	}
    	remove(label);
    }
    
    /**
    * Animation:executes the aniamtion of the impact of a hit(meele or range) to the unit of the specific hexagon.
      hit_direction:direction of the enemy unit which executes the hit.If a range attack is used, hit_direction is still the direction of the enemy unit.
    */
    public void animation_hit_impact(Hexagon hex,int hit_direction,boolean change_dir){
    	move_unit_to_dir(hex,hit_direction,change_dir);
    	move_unit_to_opposite_dir(hex,hit_direction,change_dir);
    };
    /**
    * Animation:moves unit towards the opposite of the input direction at the half length of its own size.
    */
    public void move_unit_to_opposite_dir(Hexagon hex,int hit_direction,boolean change_dir){
    	move_unit_to_dir(hex,(hit_direction+2)%4,change_dir);
    }
    /**
    * Animation:moves unit towards the input direction at the half length of its own size.
    */
    public void move_unit_to_dir(Hexagon hex,int direction,boolean change_dir){
    	if(hex.hasUnit()==false||direction>3||direction<0) return;
    	Unit u=hex.getUnit(); 
    	if(change_dir){
    		double x=u.getImage().getX();
        	double y=u.getImage().getY();
        	remove(u.getImage());
        	u.setImage(u.getAnimation().getDefaultFrame((direction+2)%4));
        	add(u.getImage(),x,y);
    	}
    	double stepX,stepY;
    	int step=u.getImgsize() / (4*Config.FRAMES_PER_MOVE_ANIMATION);//30
    	if(step==0) step=1;
    	int TimePerFrame=Config.time_per_frame/10;
    	if(direction==0){
    		stepX=0;
    		stepY=-step;
    	}else if (direction==1){
    		stepY=0;
    		stepX=step;
    	}else if (direction==2){
    		stepX=0;
    		stepY=+step;
    	}else{//direction==3
    		stepY=0;
    		stepX=-step;
    	}
    	//print("move_unit_to_dir: 2*Config.FRAMES_PER_MOVE_ANIMATION:"+2*Config.FRAMES_PER_MOVE_ANIMATION+" step:"+step+"+ stepX:"+stepX+" stepY:"+stepY+" time pf:"+Config.time_per_frame+".");
    	for(int i=0;i<Config.FRAMES_PER_MOVE_ANIMATION;i++){
    		u.getImage().move(stepX,stepY);
    		pause(TimePerFrame);
    	}
    }
    
    /**
     * Activates the animation based on direction ,and based on the type of phase it is(meele or ranged)-->(Mage casting spell is considered range)
     * must be cast when request for action is true.
     */
    public void activateAnimation(Hexagon hex,boolean meele,int direction){
        if(direction>3||direction<0||hex.hasUnit()==false||hex==null){
            print("WarHam:activateAnimation: grave Error");
        }
        boolean over=false;
        Unit u=hex.getUnit();
        Animation anim=u.getAnimation();
        int frames=anim.getFramesPerMeeleAttack();
        if(!meele) frames=anim.getFramesPerRangeAttack();
        
        GImage  temp=u.getImage();
        remove(temp);//remove the current Image
        double x=u.getImage().getX();
        double y=u.getImage().getY();
        GImage frame=null;//lme
        for(int i=0;i<frames;i++){
            if(frame!=null) remove(frame);
            frame=anim.getNextAnimationFrame(meele, direction, i);
            add(frame,x,y);
            pause(Config.time_per_frame);
        }
        remove(frame);
        temp=anim.getDefaultFrame(direction);
        u.setImage(temp);
        add(temp,x,y);
    }
    
    
    
    
}
