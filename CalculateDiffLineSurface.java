/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kostas
 */
public class CalculateDiffLineSurface {

    private static boolean y;
    double zOffset=12.0D;
int xpoints=18;
int ypoints=10;
int stepx=10;int stepy=10;
vector[] Obs=new vector[xpoints*ypoints];
  static int  obs_points=0;
  BufferedReader LineFile,SurfaceFile;
FileWriter diffLineSurface;
Data data=new Data();
double[] lineValues=new double[data.getSurveydata().getNumberObs()];
double[] surfaceValues=new double[data.getSurveydata().getNumberObs()];
double[] diffValues=new double[data.getSurveydata().getNumberObs()];

int start_xypoints=0;


public CalculateDiffLineSurface()
{
  

obs_points=data.getSurveydata().getNumberObs();
try{
   
    LineFile= new BufferedReader(new FileReader("C:/users/user/Documents/MATLAB/Examples/R2022a/matlab/GS2DAnd3DPlotsExample/line.txt"));
    SurfaceFile= new BufferedReader(new FileReader("C:/users/user/Documents/MATLAB/Examples/R2022a/matlab/GS2DAnd3DPlotsExample/surface.txt"));
diffLineSurface = new FileWriter("C:/users/user/Documents/MATLAB/Examples/R2022a/matlab/GS2DAnd3DPlotsExample/DiffLineSurface.txt");
}
    
      catch(IOException e){}
      
  
  

}




    public static void main(String[] args)
{
  CalculateDiffLineSurface diff=new CalculateDiffLineSurface();
  
   double[] numArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
      


       // System.out.format("Standard Deviation = %.6f", SD);
  //System.out.println(obs_points);
  diff.calcDiff(100);



int i=0;


}
    public void calcDiff(double obs)
    {
        double diff = 0.0;int i=0;
      //loop for obs
        try {
            //read a line by line the 2 files with one scan
            //put values to arrays
            //creae difference
            //put difference in array
    
             
            while (i<obs_points){
                 String line=LineFile.readLine();
                 lineValues[i]=Double.valueOf(line);
                 String line1=SurfaceFile.readLine();
                 surfaceValues[i]=Double.valueOf(line1);
                 
                 diff=Math.abs(Double.valueOf(line))-Math.abs(Double.valueOf(line1));
                 diffValues[i]=diff;
                 
                 
            System.out.print("Line:   "+line+"  Surface:    "+line1);
            System.out.println("    Difference: "+diff);
            diffLineSurface.write(line+" "+'\n');
            i++;
            }
            //read a line of the surface file
            double mean=findMean(diffValues);
            double sd=findSD(diffValues);
            double min=Math.abs(findMin(diffValues));
            double max=findMax(diffValues);
            System.out.println("   min:  "+min+"   max:  "+max);
            System.out.println("Mean value; "+mean+" Standard deviation: "+sd);
            
            //calculate difference
            //write to text file diffLineSurface
            
            LineFile.close();SurfaceFile.close();diffLineSurface.close();
            
            //end loop
        } catch (IOException ex) {
            Logger.getLogger(CalculateDiffLineSurface.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public double MachineEpsilon()
    {
    double epsilon=0;
    
    float machEps = 1.0f;

do
    machEps /= 2.0f;
while ( (1.0 + (machEps / 2.0)) != 1.0);

System.out.println( machEps);
    
    
    return epsilon;
    }
    
    
     public double findSD(double[] values){
  
  	int n=0;double sum=0;double mean;

	
	for(int i=0;i<values.length;i++) 
	{
		
		sum=sum+values[i];
	}
       	mean=sum/values.length;
      
	sum=0;  
	for(int i=0;i<values.length;i++) 
	{
		sum+=Math.pow((values[i]-mean),2);
	}
	
	double deviation=Math.sqrt(sum);
        
        return deviation;
  }
  
   public double findMean(double[] values){
  
  	int n=0;double sum=0;double mean;
	
	
	for(int i=0;i<values.length;i++) 
	{
		
		sum=sum+values[i];
	}
       	mean=sum/values.length;
      
	
	
        
        return mean;
  }
  
   double max=0,min=0,sd=0,mean=0,max1=0,min1=0,mean1=0,sd1=0;
   double altitude=data.getSurveydata().zOffset;          
//    xcoord=data.getSurveydata().j_terminal*2                      
   //       ,ycoord=data.getSurveydata().y_value
     //     ,targetdim=data.getTargetVolume(),step=data.getSurveydata().step;
  double[] cij_array=new double[data.getNumberObs()];
  double[] point_mass_array=new double[data.getNumberObs()];
 public double findMax(double[] values)
 {int maxIdx = 0;

        for (int i = 0; i < values.length; ++i) {
            if (values[i] > values[maxIdx]) {
                maxIdx = i;
            }
        }
        return values[maxIdx];
    }
   public double findMin(double[] values)
 {int minIdx = 0;

        for (int i = 0; i < values.length; ++i) {
            if (values[i] < values[minIdx]) {
                minIdx = i;
            }
        }
        return values[minIdx];
    }
    
    
}
