/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;

import java.io.*;
import java.nio.charset.Charset;

/**
 *
 * @author Administrator
 */
public class compute_CG {
int i=0;
   static Data data=new Data();

   public static void main(String[] args) {
      int total_vertices=data.getVertices();
       Eros433Vertices vertices=new Eros433Vertices();
        double totalx=0.0;double totaly=0.0;double totalz=0.0;double cg=0;double total=0;
        
        for(int k=0;k<vertices.Vertex.length;k++)
    
   {
      totalx=vertices.Vertex[k].x;
              totaly=vertices.Vertex[k].y;
                      totalz=vertices.Vertex[k].z;
       

   }
        
         totalx=totalx/total_vertices*1000;
         totaly=totaly/total_vertices*1000;
         totalz=totalz/total_vertices*1000;
         total=totalx+totaly+totalz;
         System.out.print("totalx:"+totalx+"   "); System.out.print("totaly:"+totaly+"   "); System.out.println("totalz:"+totalz);
         cg=Math.pow(totalx,2)+Math.pow(totaly,2)+Math.pow(totalz, 2);
         cg=Math.sqrt(cg);
         // System.out.println(cg/856);
         System.out.println(cg);
   }
     
}
