/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loop;

/**
 * <p>Title: Gravity anomaly calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: K.P.Anastasiadis Ph.D</p>
 * @author Costas
 * @version 1.0
 */
import java.io.*;
public class TargetValidation {
     PrintWriter out;
     static String target="";
    // static Control c;
Data data;

  public TargetValidation(String model) {
    int rcflag=1;target=model;


vector area=new vector(0.0,0.0,0.0);vector tarea=new vector(0.0,0.0,0.0);
vector zero=new vector(0.0,0.0,0.0);

 LineUndirectedEdgeLoop_volume m = new LineUndirectedEdgeLoop_volume();
    m.Facet_Loop();data=m.getData();

vector[] areas=new vector[data.getPolyFacets()]; 
vector[] normals=new vector[data.getPolyFacets()];
areas=data.getAreas();normals=data.getNormals();
 //open a file
 try{
  File file=new File("TargetValidation.txt");
  out=new PrintWriter(new FileWriter(file));
  }
  catch(IOException e){}

 for (int i=0;i<data.getPolyFacets();i++)
 {tarea = vector.addVec(vector.mulScalar(normals[i],vector.magnitude(areas[i])),tarea);}
 if (tarea.equalVectors(tarea,zero)){
   System.out.println("Target validated");
 out.println("Target validated");}
 else {
   System.out.println("Target is not a valid target ");
   System.out.println("because  "+tarea+ "  = Target area(should be [0.0,0.0,0.0]");
   out.println("because  "+tarea+ "  = Target area(should be [0.0,0.0,0.0]");}

out.close();

  }
  public static void main(String[] args) {
    TargetValidation targetValidation1 = new TargetValidation("");




  }

}

