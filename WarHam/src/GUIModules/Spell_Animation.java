package GUIModules;

import acm.graphics.GImage;

public class Spell_Animation {
	
	public int FPS=15,frames;
    private GImage frame[];
    
    public Spell_Animation(String filename,int frames) {
    	this.frames=frames;
    	readImageFile(filename);
    }
    
    public Spell_Animation(String filename,int FPS,int frames) {
    	this.FPS=FPS;
    	this.frames=frames;
    	readImageFile(filename);
    }
    
    public void readImageFile(String filename) {
    	String filepath=filename+"/";
    	frame=new GImage[frames];
    	for(int i=0;i<frames;i++) {
    		frame[i]=new GImage(filepath+"SpellAnimation"+i+".png");
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
