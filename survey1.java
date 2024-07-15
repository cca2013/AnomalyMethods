package loop;

/**
This class sets the observation horizon
 */

public class survey1 {


//Tsoulis distances:30 - 300 - 3000
double zOffset=15000.0D;
int j_terminal=100025;
int init_value=-100025;
int step=1000;
//Observation points of profile
int MOpoints=(((j_terminal-init_value)/step)+1);
//Observation points of canvas
int obs_points=10;


double y_value=15.0D;

int xpoints=5;
int ypoints=5;

    public int getXpoints() {
        return xpoints;
    }

    public int getYpoints() {
        return ypoints;
    }
int stepx=1;int stepy=1;
//int MOpoints=xpoints*ypoints;
//canvas array 
//vector[] Obs=new vector[MOpoints];

int start_xypoints=0;
//profile array
vector[] Obs=new vector[MOpoints];

    public double getzOffset() {
        return zOffset;
    }

    public void setzOffset(double zOffset) {
        this.zOffset = zOffset;
    }

    public int getMOpoints() {
        return MOpoints;
    }

    public void setMOpoints(int MOpoints) {
        this.MOpoints = MOpoints;
    }

    public vector[] getObs() {
        return Obs;
    }

    public void setObs(vector[] Obs) {
        this.Obs = Obs;
    }

 int i=0;
 
 
 //canvas survey
/**
public survey() {
for (int x=start_xypoints;x<xpoints;x++)
  {
     for (int y=start_xypoints;y<ypoints;y++) 
     {  
      Obs[i]= new vector(x+stepx,y+stepy,zOffset);
      //increase index for next obs point
      if (i<MOpoints)i++;
  }
  }

}
 */

 //profile survey
public survey1() {
    
 int counter=init_value;
 int i=0;

 for (i=0;i<getMOpoints();i++)
  {
    //horizontal track
    Obs[i]= new vector(Math.pow(10,i+1),Math.pow(10,i+1),zOffset);
    //inclined track
    //Obs[i]= new vector(Math.pow(10,i+1),Math.pow(10,i+1),Math.pow(10,i+1));
  }
 
while (counter <= j_terminal) 
{
   // System.out.println(counter);
      double c=counter;
     Obs[i]= new vector(c,y_value,zOffset);
 // System.out.println
        //  (counter);
      if (i<MOpoints)
          i++;
           counter+=step;
}
//for (int j=init_value;j<=j_terminal;j+=10)
  
     
    //Horizontal Profile 
//{ Obs[i]= new vector(Double.valueOf(j),y_value,zOffset);
  // if (i<=MOpoints/10)
    //   i++;

//}
    
             
}
 
public vector getObs(int index)
{return Obs[index];}
public int getNumberObs(){return MOpoints;}
public vector[] getAllObs(){return Obs;}




}
