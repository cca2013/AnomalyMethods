/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;

/**
 *
 * @author user
 */
public class Deduced1Faces {
    
    
   
    
    public Deduced1Faces(){
         
    
    }       
        public static void main(String[] args)
                
                
{
    
    
    DidimosFaces faces=new DidimosFaces();
    
  for(int i=0;i<faces.Faces.length;i++)
{
faces.Faces[i][1]=faces.Faces[i][1]-1;
faces.Faces[i][2]=faces.Faces[i][2]-1;
faces.Faces[i][3]=faces.Faces[i][3]-1;

}
  
  
   for(int i=0;i<faces.Faces.length;i++)
{
System.out.print("{3");System.out.print(",");
System.out.print(faces.Faces[i][1]);System.out.print(",");
System.out.print(faces.Faces[i][2]);System.out.print(",");
System.out.print(faces.Faces[i][3]);System.out.println("},");;

}
} 
    
}
