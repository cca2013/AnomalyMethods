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
public class testingLoop {
  int MOpoints=200;
  double zOffset=300.0D;

int j_terminal=1500;
int init_value=-1000;
double y_value=15.0D;
  vector[] Obs=new vector[MOpoints];
    public testingLoop()
    {
      int init_value=-1000;
      int counter=init_value;
 int i=0;
 int step=10;
 
 
while (counter < j_terminal-step) 
{
      double c=counter;
  System.out.println(c);
      //if (i<MOpoints/step)
        //  i++;
           counter+=step;

        
}

for (i=-10; i<=10;i++){
System.out.println(i);
}
    }
    
    
    public static void main(String[] args)
{

testingLoop testing=new testingLoop();
 
  
}
    
}
