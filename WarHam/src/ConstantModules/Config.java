package ConstantModules;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Config {
	
	public final static int c_time_per_round=40000;
	
	public final static int time_per_frame=50;
	
	public final static int FRAMES_PER_MOVE_ANIMATION=15;
	
	public final static int step_height=20;
	
	public final static double step_width=4/5;
	
	public final static int FRAMES_PER_DMG_INDICATOR=20;
	
	public final static double FONT_SIZE_PHASE_LABEL=10.0/400.0;//% of width(why not height??)
	
	public final static Color FONT_COLOR_PHASE_LABEL=Color.RED;
	
	public final static int ACTION_BUTTON_PLAYER1 = KeyEvent.VK_SPACE;
	
	public final static int ACTION_BUTTON_PLAYER2 = KeyEvent.VK_ENTER;
	
	public final static int SELECT_BUTTON_PLAYER1 = KeyEvent.VK_Q;
	
	public final static int SELECT_BUTTON_PLAYER2 = KeyEvent.VK_NUMPAD1 ;
	
	public final static int TARGET_BUTTON_PLAYER1 = KeyEvent.VK_E;
	
	public final static int TARGET_BUTTON_PLAYER2 = KeyEvent.VK_NUMPAD2 ;
	
	public final static int[] MOVEMENT_BUTTONS_PLAYER1= { KeyEvent.VK_W, KeyEvent.VK_D , KeyEvent.VK_S , KeyEvent.VK_A}; 
	
	public final static int[] MOVEMENT_BUTTONS_PLAYER2= { KeyEvent.VK_UP, KeyEvent.VK_RIGHT , KeyEvent.VK_DOWN , KeyEvent.VK_LEFT}; 
	

	

}
