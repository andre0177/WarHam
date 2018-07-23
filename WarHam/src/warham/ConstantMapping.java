package warham;

import java.util.Random;

import acm.graphics.GImage;

public class ConstantMapping {
	
	private static Random rand = new Random();
	
	public static GImage getImagefromIntCode(int code,String map_skin) {

		
		if (map_skin==null) {
			System.err.println("Error in getImagefromIntCode: null map_skin");
			return null;
		}
		
		GImage out=null;
		String Imagefilename="images/map/";
		switch (code) {
        case 1:  Imagefilename =Imagefilename + getPassable(code,map_skin);
                 break;
        case 20:  Imagefilename =Imagefilename + getNotPassable(code,map_skin);
        		break;
        case 30:  Imagefilename =Imagefilename + getWall(code,map_skin);
				break;
		}
		if (!Imagefilename.equals("")) out=new GImage(Imagefilename);
		return out;
	}
	
	public static String getWall(int code,String map_skin) {
		String res="";
		int random;
		if (map_skin==null) {
			System.err.println("Error in getWall: null map_skin");
			return "";
		}
		if(map_skin.equals("Forest")) {
			 random=getRandomNumber(4);
			 if(random==1) {
				 res="pinetree1.png";
			 }else if (random==2) {
				 res="pinetree2.png";
			 }else if (random==2) {
				 res="pinetree3.png";
			 }else {
				 res="pinetree4.png";
			 }
		}
		return res;
	}
	
	public static String getNotPassable(int code,String map_skin) {
		String res="";
		int random;
		if (map_skin==null) {
			System.err.println("Error in getNotPassable: null map_skin");
			return "";
		}
		if(map_skin.equals("Forest")) {
			 random=getRandomNumber(9);
			 if(random==1||random==2) {
				 res="lake_water1.png";
			 }else if (random==3) {
				 res="lake_water2.png";
			 }else if (random==4) {
				 res="lake_water3.png";
			 }else if (random==5) {
				 res="lake_water4.png";
			 }else if (random==6||random==7) {
				 res="lake_water5.png";
			 }else if (random==8) {
				 res="lake_water6.png";
			 }else {
				 res="lake_water7.png";
			 }
		}
		return res;
	}
	
	public static String getPassable(int code,String map_skin) {
		String res="";
		int random;
		if (map_skin==null) {
			System.err.println("Error in getPassable: null map_skin");
			return "";
		}
		if(map_skin.equals("Forest")) {
			 random=getRandomNumber(5);
			 if(random==1) {
				 res="forest_land1.png";
			 }else if (random==2) {
				 res="forest_land2.png";
			 }else if (random==3) {
				 res="forest_land3.png";
			 }else if (random==4) {
				 res="forest_land4.png";
			 }else {
				 res="forest_land5.png";
			 }
		}
		return res;
	}
	
	public static int getRandomNumber(int i) {
		
        int  n = rand.nextInt(i);
        return n;
	}
	
	

}
