/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;

import java.io.*;
import static java.lang.Double.parseDouble;
import java.nio.charset.Charset;

/**
 *
 * @author Administrator
 */
public class construct_coords_array {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Charset inputCharset = Charset.forName("ISO-8859-7");
          // The name of the file to open.
        String fileName = "K:\\ΔΙΔΑΚΤΟΡΙΚΟ-ΑΠΘ\\bennu\\bennu_coords.txt";

        // This will reference one line at a time
        String line = null;

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
             
                StringBuilder sbStr = new StringBuilder(line);
                // sbStr.deleteCharAt(0); 
                line=sbStr.toString();
               // String removeSpaces=line;
                //String removeSpaces = line.replaceAll("\\s+",",");
                line=line.replaceAll("\\s+",",");
                line=line.substring(1);
                
                //Split string to x,y,z and Convert km to meters
                  
                String[] lineArray = line.split(",");
                double x=parseDouble(lineArray[0]);x=x*1000;
                double y=parseDouble(lineArray[1]);y=y*1000;
                double z=parseDouble(lineArray[2]);z=z*1000;
                lineArray[0]=Double.toString(x);lineArray[1]=Double.toString(y);lineArray[2]=Double.toString(z);
                line=lineArray[0]+","+lineArray[1]+","+lineArray[2];
                
                //line=removeSpaces.replace(' ',',');
                            //  line=line.replaceFirst(",","");
                        
                //line=removeSpaces;
               // line="{"+3+","+line+"},";
               line="new vector("+line;
               line=line+"),";
               
                n++;
                //System.out.print(n);
                System.out.println(line);
             }
            
                 //System.out.println(n);
               
             System.out.println("};");
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
