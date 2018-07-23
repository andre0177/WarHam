
package GUIModules;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;


public class GUILogger {
    
    public ArrayList<GLabel> messages=new ArrayList();
    public final int maxMessagesSize,maxChars,pixelperchar=5;
    public int font_size;
    protected boolean hasbeenupdated=false;
    protected double X=0,Y=0,stepY=10;
    protected GImage image;
    
    public GUILogger(){maxMessagesSize=5; maxChars=30;font_size=20;}

    public GUILogger(int maxMessagesSize,int maxChars,int X,int Y,int stepY) {
        this.maxMessagesSize=maxMessagesSize;
        this.maxChars=maxChars;
        this.X=X;
        this.Y=Y;
        this.stepY=stepY;
        font_size=14;
    }

    private ArrayList<GLabel> addMessage(String message){
    	ArrayList<GLabel> list=new ArrayList();
    	GLabel lbl=new GLabel(message);
        lbl.setFont("*-BOLD-"+font_size);
        messages.add(lbl);
        if(messages.size()>maxMessagesSize){
        	list.add(messages.remove(0));
            return list;
        }    
        return null;
    }
    //TODO:what if there are two deleted GLABELs from the insert of one???
    /**
     * @param line
     * @return the deleted GLabel(if any) from the insert ,so it may be deleted from the Canvas
     */
    public ArrayList<GLabel> addString(String line){//TODO:fix it
    	ArrayList<GLabel> list=new ArrayList();
        hasbeenupdated=true;
        if(line.length()<=maxChars){
            return addMessage(line);
        }else{//TODO:must go in with reverse 
        	Stack<String> stack=new Stack();
            GLabel templ,ret=null;
            String temp;
            while(line.length()>maxChars){
                temp=line.substring(0, maxChars);
                stack.push(temp);
                //templ=stack.push(temp);
                //if(templ!=null) ret=templ;
                line=line.substring(maxChars);
            }
            addMessage(line);
            while(!stack.isEmpty()) {
            	addMessage(stack.pop());
            }
            if(messages.size()>maxMessagesSize) {
            	//ret=messages.get(maxMessagesSize+1);
            	//messages.remove(maxMessagesSize+1);
            	ret=messages.get(0);
            	messages.remove(0);
            	int loop=messages.size();
            	for(int i=0;i<loop;i++) {
            		list.add(messages.remove(0));
            	}
            }
            return list;
        }
    }
    
    public int size(){
        return messages.size();
    }

    public ArrayList<GLabel> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<GLabel> messages) {
        this.messages = messages;
    }
    
    public boolean Hasbeenupdated() {
        return hasbeenupdated;
    }

    public void setHasbeenupdated(boolean hasbeenupdated) {
        this.hasbeenupdated = hasbeenupdated;
    }

    public double getX() {
        return X;
    }

    public void setX(double X) {
        this.X = X;
    }

    public double getY() {
        return Y;
    }

    public void setY(double Y) {
        this.Y = Y;
    }

    public double getStepY() {
        return stepY;
    }

    public void setStepY(double stepY) {
        this.stepY = stepY;
    }

    public GImage getImage() {
        return image;
    }

    public void setImage(GImage image) {
        this.image = image;
        image.setSize(maxChars*pixelperchar, (maxMessagesSize+1)*stepY);
    }

    
    
}
