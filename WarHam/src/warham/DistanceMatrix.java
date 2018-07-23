
package warham;


public class DistanceMatrix {
    
    public static int w=-1,h=-1;
    /*
    public static int geDistance(int i1,int j1,int i2,int j2){
        
        if(w==-1||h==-1||i1>h||i1<0||i2>h||i2<0||j1>w||j1<0||j2>w||j2<0) return -1;
        //32 43
        int diffi=Math.abs(i1-i2);
        int diffj=Math.abs(j1-j2);

        if(diffi>0&&diffj==0){
            return (int)diffi;
        }else if(diffj>0&&diffi==0){
            return (int)diffj;
        }else if(diffj>0&&diffi>0){
            //a0=i1,b0=j1 , a1=i2,b1=j2
            int x0 = i1-(int)Math.floor(j1/2);
            int y0 = j1;
            int x1 = i2-(int)Math.floor(j2/2);
            int y1 = j2;
            int dx = x1 - x0;
            int dy = y1 - y0;
            int dist = Math.max(Math.abs(dx), Math.abs(dy));
            dist = Math.max(dist, Math.abs(dx+dy));
            return dist;
        }else{
           return 0; 
        }
       
    }
    */
    //
    public static int getMoveDistance(Hexagon a,Hexagon target){
        
        if(a.isAdjacenthex(target)) return 1;
        int opt=100;
        for(Hexagon b:a.getAdjacentHexList()){
            if(b.isPassable()){
                if(b.isAdjacenthex(target)) return 2;
                else{
                    for(Hexagon c:b.getAdjacentHexList()){
                        if(c.isAdjacenthex(target)) opt=3;
                    }
                }
            }
        }

        return opt;
    }
    
    public static int getShootDistance(Hexagon a,Hexagon b){
        
        int optI=Math.max(Math.abs(a.getI()-b.getI()), Math.abs(a.getJ()-b.getJ()));
        return optI;
       
    }
    
    public static void setHW(int x,int y){
        if(x>0&&y>0){
           h=x;
           w=y;
        }
    }
    
    
}

