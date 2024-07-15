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
public class make_vectors {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Charset inputCharset = Charset.forName("ISO-8859-7");
          // The name of the file to open.
        String fileName = "E:\\ΔΙΔΑΚΤΟΡΙΚΟ-ΑΠΘ\\eros433\\Eros_Topology.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);
            BufferedReader bufferedReader= new BufferedReader(
           new InputStreamReader(
                      new FileInputStream(fileName), inputCharset));

            // Always wrap FileReader in BufferedReader.
           // BufferedReader bufferedReader = 
                     
               // new BufferedReader(fileReader);
              // String charToRemove = "v";
                System.out.println("{");
            int n=0;
            while((line = bufferedReader.readLine()) != null ) {
                if ( line.trim().length() == 0)continue;
             if (n>=856){
                StringBuilder sbStr = new StringBuilder(line);
                 sbStr.deleteCharAt(0); 
                line=sbStr.toString();
                String removeSpaces = line.replaceAll("\\s+",",");
              //  line=removeSpaces.replaceAll("\\s+","");
                line=removeSpaces.replace(' ',',');
                              line=line.replaceFirst(",","");
                        
                //line=removeSpaces;
                line="{"+3+","+line+"},";
               
               
                n+=1;
                //System.out.print(n);
                System.out.println(line);
             }
             else   n+=1;continue;
                 //System.out.println(n);
            }   
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
