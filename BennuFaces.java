/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class BennuFaces {

    public int[][] getFaces() {
        return Faces;
    }

    public void setFaces(int[][] Faces) {
        this.Faces = Faces;
    }

    public String getFileName() {
        return fileName;
    }

//      Data data=new Data();
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
     
     public int [][] Faces=new int[2692][4];
    
      String fileName = "K:\\ΔΙΔΑΚΤΟΡΙΚΟ-ΑΠΘ\\bennu\\bennu_topology.txt";
      
      public int getVertexIndex(int facet, int index) {
        return Faces[facet][index];
    }
    private static final Logger LOG = Logger.getLogger(BennuFaces.class.getName());
      
   BennuFaces()
   {
  
    for (int n=0;n<Faces.length;n++)
    { 
        for (int j=0;j<4;j++)
    {
        Faces[n][j]=0;
    }//1st for rows
    }//2nd for columns
    
     
    Charset inputCharset = Charset.forName("ISO-8859-7");
        String line = null;
        String[] tokens ; 
        int a,b,c,d=3;
       
       try {
            int n=0;
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);
            BufferedReader bufferedReader= new BufferedReader(
           new InputStreamReader(
                      new FileInputStream(fileName), inputCharset));
               // System.out.println("{");
            n=0;
            while((line = bufferedReader.readLine()) != null ) 
            { 
                if ( line.trim().length() == 0)continue;
             
                //StringBuilder sbStr = new StringBuilder(line);
                //sbStr.deleteCharAt(0); 
                //line=sbStr.toString();
                
                String removeSpaces = line.replaceAll("\\s+",",");
               // line=removeSpaces.replaceAll("\\s+","");
                //line=removeSpaces.replace(' ',',');
                //line=line.replaceFirst(",","");
                
                
                line=removeSpaces;
                tokens = line.split(",");
                //line="{"+3+","+line+"},";
               a=Integer.parseInt(tokens[0]);
               b=Integer.parseInt(tokens[1]);
               c=Integer.parseInt(tokens[2]);
               Faces[n][0]=d;Faces[n][1]=a-1;Faces[n][2]=b-1;Faces[n][3]=c-1;
              // System.out.print(Faces[n][0]+"    ");System.out.print(Faces[n][1]+"   ");System.out.print(Faces[n][2]+"   ");System.out.println(Faces[n][3]);
                n++;
             }

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
        
   
   }//constructor
   public void printFaces()
   {
   for (int n=0;n<Faces.length;n++)
      System.out.println(Faces[n][0]+" "+Faces[n][1]+" "+Faces[n][2]+" "+Faces[n][3]);
   }
   
   }//class

   
 


