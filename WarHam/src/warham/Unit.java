
package warham;

import acm.graphics.GImage;


public class Unit {
    
    protected String description;
    protected int MeeleDmg,Armor,MovementSpeed,morale,hp,imgsize,defense,current_hp,magic_resistance;
    protected boolean shield,movedthisround=false,hitthisround=false;
    protected GImage image,porimg;//porimg=backupimg for the portrait;
    protected Player player;
    protected double shieldRating;//1-100
    protected String[] info;
    protected Spell under_spell=null;
    protected Animation Animation;

    public Unit(String description, String Portrait_Image_path, int MeeleDmg, int Armor, int MovementSpeed, int morale, int hp, int imgsize, boolean shield, Player Player,Animation animation,int dir) {
        this.description = description;
        this.MeeleDmg = MeeleDmg;
        this.Armor = Armor;
        this.MovementSpeed = MovementSpeed;
        this.morale = morale;
        this.hp = hp;
        this.current_hp=hp;
        this.imgsize = imgsize;
        this.shield = shield;
        this.player = Player;
        this.Animation=animation;
        this.image=Animation.getDefaultFrame(dir);
        if(image==null){
            System.out.println("Null img at unit con:dir="+dir);
            if(Animation==null) System.out.println("Animation=null");
        }
        porimg=new GImage(Portrait_Image_path);
        createInfo();
    }
    
    public void createInfo(){
        info=new String[6];
        info[0]=description;
        info[1]="Meele Attack:"+MeeleDmg;
        info[2]="HP:"+hp+"/"+current_hp;
        info[3]="Armor:"+Armor;
        info[4]="Movement Speed:"+MovementSpeed;
        info[5]="";
    }
    
    public String[] getInfo(){
        return info;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        info[2]="HP:"+hp+"/"+current_hp;
    }

    public int getCurrent_hp() {
        return current_hp;
    }

    public void setCurrent_hp(int current_hp) {
        this.current_hp = current_hp;
    }
    

    public String getDescription() {
        return description;
    }

    public int getImgsize() {
        return imgsize;
    }
    
    public void PlayedThisRound(){
        movedthisround=true;
    }
    
    public void HitThisRound(){
        hitthisround=true;
    }

    public void setImgsize(int imgsize) {
        this.imgsize = imgsize;
    }
    
    
    
    public int getMagic_resistance() {
        return magic_resistance;
    }

    public void setMagic_resistance(int magic_resistance) {
        this.magic_resistance = magic_resistance;
    }

    public GImage getImage() {
        return image;
    }

    public void setImage(GImage image) {
        this.image = image;
    }

    public GImage getPorimg() {
        return porimg;
    }

    public void setPorimg(GImage porimg) {
        this.porimg = porimg;
    }

    public double getShieldRating() {
        return shieldRating;
    }

    public void setShieldRating(double shieldRating) {
        this.shieldRating = shieldRating;
    }


    public boolean hasMovedThisRound() {
        return movedthisround;
    }

    public void setMovedthisround(boolean playedthisround) {
        this.movedthisround = playedthisround;
    }

    
    public boolean hasHitThisRound() {
        return hitthisround;
    }

    public void setHitthisround(boolean playedthisround) {
        this.hitthisround = playedthisround;
    }
    

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMeeleDmg() {
        return MeeleDmg;
    }

    public void setMeeleDmg(int MeeleDmg) {
        this.MeeleDmg = MeeleDmg;
    }

    public int getArmor() {
        return Armor;
    }

    public void setArmor(int Armor) {
        this.Armor = Armor;
    }

    public int getMovementSpeed() {
        return MovementSpeed;
    }

    public void setMovementSpeed(int MovementSpeed) {
        this.MovementSpeed = MovementSpeed;
    }


    public int getMorale() {
        return morale;
    }

    public void setMorale(int morale) {
        this.morale = morale;
    }

    public int getStamina() {
        return hp;
    }

    public void setStamina(int stamina) {
        this.hp = stamina;
    }

    public boolean hasShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public Player getPlayer() {
        return player;
    }

    public Spell getUnder_spell() {
        return under_spell;
    }

    public void setUnder_spell(Spell under_spell) {
        this.under_spell = under_spell;
    }

    public Animation getAnimation() {
        return Animation;
    }

    public void setAnimation(Animation Animation) {
        this.Animation = Animation;
    }
    
    
}
