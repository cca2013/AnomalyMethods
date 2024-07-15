package loop;


/**
 * <p>Title: Gravity anomally calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: C.P.Anastasiades Ph.D</p>
 * @author Costas
 * @version 1.0
 */

public class survey {

double zOffset=12.0D;
int MOpoints=1;
int y=10;
vector[] Obs=new vector[MOpoints];



public survey() {
for (int i=0;i<MOpoints;i++)
  {
    //horizontal track
      //KB
      //3446,4102.5,-6052
      //MAX
    // 17600,8431.6,6013
    Obs[i]= new vector(Math.pow(10,i+1),y,zOffset);
    //inclined track
    //Obs[i]= new vector(Math.pow(10,i+1),Math.pow(10,i+1),Math.pow(10,i+1));
  }
}




public vector getObs(int index)
{return Obs[index];}
public int getNumberObs(){return MOpoints;}
public vector[] getAllObs(){return Obs;}




}
