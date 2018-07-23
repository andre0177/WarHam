
package ConstantModules;

import acm.graphics.GImage;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import GUIModules.Animation;
import MapModules.Hexagon;
import UnitModules.MageUnit;
import UnitModules.Player;
import UnitModules.PoisonEffect;
import UnitModules.Projectile;
import UnitModules.RangeUnit;
import UnitModules.Spell;
import UnitModules.Unit;


public class Auxiliary {
    
    public static Unit getEmpire_Swordman(Player player1,int hex_size,int direction){
        int imgsize = hex_size*1;
        Animation anim=new Animation("images/animation/swordman",3,0,imgsize);
        String description = "Empire Swordman";
        String imagepath = "images/unit.png";
        int MeeleDmg = 3;
        int Armor = 5;
        int MovementSpeed = 2;
        int morale = 10;
        int hp = 5;
        boolean shield = true;
        Player player = player1;
        boolean playedthisround = false;
        return new Unit(description,imagepath,MeeleDmg,Armor,MovementSpeed,morale,hp,imgsize,shield,player,anim,direction);
    }

    
    public static MageUnit getEmpire_Sorcerer(Player player1,int hex_size){
        String description = "Empire Sorcerer";
        String imagepath = "images/unitM.png";
        int MeeleDmg = 3;
        int Armor = 5;
        int MovementSpeed = 2;
        int morale = 10;
        int hp = 5;
        int imgsize = hex_size*8/10;
        boolean shield = true;
        Player player = player1;
        boolean playedthisround = false;
        int range = 8;
        int  cooldown = 1;
        Spell spell = getmagicBlast();
        //return new MageUnit(range,cooldown,spell,description,imagepath,MeeleDmg,Armor,MovementSpeed,morale,hp,imgsize,shield,player);
        return null;
    }
    
    public static void main(String args[]){
        
        //Animation(String filepath,int framesPerMeeleAttack,int framesPerRangeAttack){
        //Animation anim=new Animation("images/animation/swordman",3,0);
    }
    
    public static Spell getmagicBlast(){
        int direct_damage=3,aoe_area=0,chain_links=0,movement_debuff=0,debuff_turns=0,dmg_debuff=0,armor_debuff=0,range_debuff=0;
        String desc="magic blast";
        PoisonEffect poison=null;
        return new Spell(direct_damage,aoe_area,chain_links,movement_debuff,debuff_turns,dmg_debuff,armor_debuff,range_debuff,desc,poison);
    }
    
    public static boolean contains(int[] movementButtonsPlayer1, int button) {
    	for(int obj:movementButtonsPlayer1) {
    		if(obj==button) return true;
    	}
    	return false;
    }
    
    public static RangeUnit getEmpire_CrossbowMan(Player player1,int hex_size,int direction){
        String description = "Empire Crossbowman";
        String imagepath = "images/unitR.png";
        Animation anim=new Animation("images/animation/crossbowman",0,3,hex_size);
        int MeeleDmg = 3;
        int Armor = 5;
        int MovementSpeed = 3;
        int morale = 10;
        int hp = 5;
        int imgsize = hex_size*8/10;
        boolean shield = true;
        Player player = player1;
        boolean playedthisround = false;
        int range=7,ranged_dmg=1,CriticalRange=0;
        double accuracy=0.9;
        boolean fmg=false;
        //int range, int ranged_dmg, double accuracy, boolean fmg, Projectile projectile, String description, String imagepath, int MeeleDmg, int Armor, int MovementSpeed, int morale, int stamina, int imgsize, boolean shield, Player Player
        Projectile projectile=Arrow(hex_size,ranged_dmg);
        return new RangeUnit(range,ranged_dmg,accuracy,fmg,projectile,description,imagepath,MeeleDmg,Armor,MovementSpeed,morale,hp,imgsize,shield,player,anim,direction);
    }
    
    public static Projectile Arrow(int hex_size,int dmg){
        //int speed, int damage, int sizeX, int sizeY, boolean fmg, GImage image
        //hex_size
        return new Projectile(10,dmg,hex_size/5,hex_size/5,false,new GImage("images/proj.png"));
    }
    
    public static GImage rotateImageby90Degrees(GImage image,String x){

        int pixel_array[][]=image.getPixelArray().clone();//so not to change the initial pixel array
        
        int N1=pixel_array.length,N2=pixel_array[0].length;
        
        for(int i=0;i<N1-2;i++){
            for(int j=i+1;j<N2-2;j++){
                int temp=pixel_array[i][j];
                pixel_array[i][j]=pixel_array[j][i];
                pixel_array[j][i]=temp;
            }
        }
        
        return new GImage(pixel_array);
    }
    
    /**
     * @param hex_target
     * @param distance
     * @param u
     * @return true if the range attack hit the target
     */
    public static boolean RangeAttack(Hexagon hex_target,int distance,RangeUnit u){
        if(u.getRange()<distance){
            System.err.println("Auxiliary:RangeAttack: range<distance");
            return false;
        }else if(hex_target.hasUnit()==false){
            System.err.println("Auxiliary:RangeAttack: hex_target.hasUnit()==false");
            return false;
        }else if(u.getAccuracy()<=0||u.getAccuracy()>1){
            System.err.println("Auxiliary:RangeAttack: accuracy<0||accuracy>1");
            return false;
        }
        double base_chance;
        if(distance<0.25*u.getRange()) base_chance=1.00;
        else if(distance<0.5*u.getRange()) base_chance=0.85;
        else  base_chance=0.6;
        Unit target=hex_target.getUnit();
        //if target==huge then base=1
        
        base_chance=base_chance*u.getAccuracy();
        
        int luck = new Random().nextInt(100);
        if(luck>base_chance*100){
            return true;
        }else{//missed
            return false;
        }
    }
    
    public static int RangeDamage(int dmg,Hexagon hex){
        int armor=hex.getUnit().getArmor();//1-100

        int luck = new Random().nextInt(100);
        if(hex.getUnit().hasShield()){
            if(luck<=hex.getUnit().getShieldRating()){
                System.out.println("Range attack blocked");
                return 0;
            }     
        }
        //if(luck<armor)
        return dmg;
        
    }
    
    public static int MeeleDamage(int dmg,Hexagon hex){
        //int armor=hex.getUnit().getArmor();
        Unit u=hex.getUnit();
        if(u==null){
            System.err.println("Auxiliary:MeeleDamage:Null unit inside hex:"+hex);
            return 0;
        }
        int def=u.getDefense();//0-100
        //
        int luck = new Random().nextInt(100);
        if(luck>def) return dmg;
        else return 0;
    }
    
       public static GImage RotatedImage(GImage image,int deegres){
       Image img=image.getImage();
       BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
       AffineTransform at = new AffineTransform();
       // 4. translate it to the center of the component
        at.translate(bimage.getWidth() / 2, bimage.getHeight() / 2);

        // 3. do the actual rotation
        at.rotate(Math.PI/4);

        // 2. just a scale because this image is big
        at.scale(0.5, 0.5);
        // 1. translate the object so that you rotate it around the 
        //    center (easier :))
        at.translate(-bimage.getWidth()/2, -bimage.getHeight()/2);
        return new GImage(bimage);
    }
    
    
}
