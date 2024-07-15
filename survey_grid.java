package loop;


/**
 * <p>Title: Gravity anomally calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: C.P.Anastasiades Ph.D</p>
 * @author Costas
 * @version 1.0
 */

public class survey_grid {

double zOffset=12.0D;
int xpoints=18;
int ypoints=10;
int stepx=1;int stepy=1;
vector[] Obs=new vector[xpoints*ypoints];

int start_xypoints=0;


int i=0;
public survey_grid() {
for (int x=start_xypoints;x<xpoints;x++)
  {
     for (int y=start_xypoints;y<ypoints;y++) 
   
      Obs[i]= new vector(x+stepx,y+stepy,zOffset);
      if (i<xpoints*ypoints)i++;
     
  }
}




public vector getObs(int index)
{return Obs[index];}
public int getNumber_x(){return xpoints;}
public int getNumber_y(){return ypoints;}
public vector[] getAllObs(){return Obs;}


}
