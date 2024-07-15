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
public class construct_topology_array {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Charset inputCharset = Charset.forName("ISO-8859-7");
          // The name of the file to open.
        String fileName = "K:\\ΔΙΔΑΚΤΟΡΙΚΟ-ΑΠΘ\\eros433\\Didymos_Topology.txt";

        // This will reference one line at a time
        String line = null;
        String[] tokens ; 
        int a,b,c,d;
        int [][] Faces=new int[2692][4];
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);
            BufferedReader bufferedReader= new BufferedReader(
           new InputStreamReader(
                      new FileInputStream(fileName), inputCharset));
                System.out.println("{");
            int n=0;
            while((line = bufferedReader.readLine()) != null ) {
                if ( line.trim().length() == 0)continue;
             
                //StringBuilder sbStr = new StringBuilder(line);
                 //sbStr.deleteCharAt(0); 
                //line=sbStr.toString();
                
                String removeSpaces = line.replaceAll("\\s+",",");
               // line=removeSpaces.replaceAll("\\s+","");
                //line=removeSpaces.replace(' ',',');
                //line=line.replaceFirst(",","");
                
                
                line=removeSpaces;
                //System.out.println(line);
                tokens = line.split(",");
                //line="{"+3+","+line+"},";
               a=Integer.parseInt(tokens[0]);
               b=Integer.parseInt(tokens[1]);
               c=Integer.parseInt(tokens[2]);
               Faces[n][0]=3;
               Faces[n][1]=a-1;Faces[n][2]=b-1;Faces[n][3]=c-1;
              // System.out.print(Faces[n][0]+"    ");System.out.print(Faces[n][1]+"   ");System.out.print(Faces[n][2]+"   ");System.out.println(Faces[n][3]);
               
               // System.out.println(line);
                System.out.print(Faces[n][1]+"  ");System.out.print(Faces[n][2]+"  ");System.out.println(Faces[n][3]);
             n++; 
            }
            
                 //System.out.println(n);
               
            // System.out.println("};");
System.out.println(n);
            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        
   
        
    }
}
