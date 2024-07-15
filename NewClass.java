/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loop;

/**
 *
 * @author Kostas
 */
public class NewClass {

    private static boolean y;
    public static void main(String[] args)
{
  
  
   double[] numArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double SD = calculateSD(numArray);

       // System.out.format("Standard Deviation = %.6f", SD);
  
double zOffset=12.0D;
int xpoints=18;
int ypoints=10;
int stepx=10;int stepy=10;
vector[] Obs=new vector[xpoints*ypoints];

int start_xypoints=0;


int i=0;

for (int x=start_xypoints;x<xpoints;x++)
  
     for (int y =start_xypoints; y<ypoints; y++) 
     
     {  Obs[i]= new vector(x+stepx,y+stepy,zOffset);
      if (i<xpoints*ypoints)i++;
    //   System.out.print("x=    "+x+"   y=  "); System.out.println(y);
     
     
     }
  
for (i=0;i<Obs.length;i++){System.out.println(Obs[i]);}


}
    public static double calculateSD(double numArray[])
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }
}
