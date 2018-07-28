package GUIModules;

import acm.graphics.GImage;

public class Spell_Animation {
	
	public int FPS=15,frames,unitsize;
    private GImage frame[];
    
    
    public Spell_Animation(String filename,int frames,int unitsize) {
    	this.frames=frames;
    	this.unitsize=unitsize/2;
    	readImageFile(filename);
    }
    
    public Spell_Animation(String filename,int FPS,int frames,int unitsize) {
    	this.FPS=FPS;
    	this.frames=frames;
    	this.unitsize=unitsize;
    	readImageFile(filename);
    }
    
    public void readImageFile(String filename) {
    	String filepath=filename+"/";
    	frame=new GImage[frames];
    	for(int i=0;i<frames;i++) {
    		GImage temp=new GImage(filepath+"SpellAnimation"+i+".png");
    		temp.setSize(unitsize,unitsize);
    		frame[i]=temp;
    	}
    }
    
    
    public GImage getAnimationFrame(int i) {
    	if(i<0||i>=frames) {
    		return null;
    	}else {
    		return frame[i];
    	}
    }
    
	public int getFPS() {
		return FPS;
	}
	public void setFPS(int fPS) {
		FPS = fPS;
	}
	public GImage[] getFrame() {
		return frame;
	}
	public void setFrame(GImage[] frame) {
		this.frame = frame;
	}
	public int getFrames() {
		return frames;
	}
	public void setFrames(int frames) {
		this.frames = frames;
	}
    
    

}
