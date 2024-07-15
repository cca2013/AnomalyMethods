
/**
 * <p>Title: Gravity anomaly calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: K.P.Anastasiadis Ph.D</p>
 * @author Costas
 * @version 1.0
 */

package loop;
import java.io.*;
import java.text.DecimalFormat;


public class utility {

  /* 2.TEST MODES                   mode
 * for all printouts                   1
 * int testmode=1;
 * only Line method                    2
 * int testmode=2
 * All obs totals                      3
 * int testmode=3
 * obs totals for Vertex               4
 * int testmode=4
 * obs totals for Line                 5
 * int testmode=5
 *obs totals for  Surface              6
 validate magnetic fields              7
Method totals per edge                 8
 */


 int testmode=3;
   //My printouts
  final String H1="Standard model calculation for gravity potential, gravity field, magnetic field";
  final String H2="using Vertex - Line - Surface methods";
  final String Svut  ="vector unit tangent  tij  vut    ";
  final String Svh   ="vector unit horizontal hij vh    ";
  final String Svn   ="vector unit normal  nij un[facet]";
  final String Sr1   ="vector r1        Vr1             ";
  final String Sr2   ="vector r2        Vr2             ";
  final String Svr1  ="r1 projection      on hij,ni,tij ";
  final String Svr2  ="r2 projection      on hij,ni,tij ";
  final String Sldiff="l2-l1,   Edge Length,      Lambda";
  final String Slog  ="r2+l2,r1+l1,r0                   ";
  final String fa    ="Facet Area                       ";
  final String strVt ="vector tangent    vt             ";
  static final String ZEROES ="000000000000";
  static final String BLANKS ="            ";

  //using 50 decimal digits.
DecimalFormat df=new DecimalFormat("000000000000000.000000000000000000000000000000");
File file;
PrintWriter out;
int A[];
static int call=0;

  public utility(PrintWriter file) {
    out = file;

  }//abc(0,0,1)
public utility() {
    

  }//abc(0,0,1)
//methods for additional functions
//method for calculating maximum
  public  double max(double a,double b)
  {
  if (a>b)
  return a;
  else if
  (b>a)
  return b;
  else
  return 0;
  }

//method for calculating the sign of a double number
 public  int sign(double x)
  {
  if (x>0) return 1;
  else if (x<0) return -1;
  else return 0;
  }

  public  void  printHeadings()
  {
  System.out.println();System.out.println();System.out.println();
  System.out.println(H1);
  System.out.println(H2);
  out.println();out.println();out.println();
  out.println(H1);
  out.println(H2);
  }//abc(0,0,10)

  public  void printHeadingsObs(vector obsPoint)
  {
  System.out.println();
  System.out.println();
  System.out.println();
  System.out.println();
  out.println();
  out.println();
  out.println();
  out.println();
  System.out.println("********************************************");
  System.out.println("observation point =  "+String.valueOf(obsPoint.x)+"    "+String.valueOf(obsPoint.y)+"  "+String.valueOf(obsPoint.z));
  System.out.println("********************************************");
  out.println("***************************************************");
  out.println("observation point =  "+String.valueOf(obsPoint.x)+"    "+String.valueOf(obsPoint.y)+"  "+String.valueOf(obsPoint.z));
  out.println("*********************************************************");
  }

  public void printHeadingsFacet(int facet)
  {
  for (int i=0;i<3;i++){System.out.println();}
  for (int i=0;i<3;i++){out.println();}
  out.println("                       facet number   "+String.valueOf(facet+1));
  out.println("*****************************************");
  System.out.println("                       facet number   "+String.valueOf(facet+1));
  System.out.println("*****************************************");
 }//abc(0,8,12)
  public void printHeadingsEdge(int edge)
  {
  out.println();
  out.println("edge number   "+String.valueOf(edge));
  out.println("*******************");
  System.out.println();
  System.out.println("edge number   "+String.valueOf(edge));
  System.out.println("*******************");
 }

  public  void printVector(vector a,String s)
  { //call++;
    if (call==1)
   { out.print("\t"+"                                     "+"x"+"                             ");
    out.print("\t"+"                                     "+"y"+"                             ");
    out.println("\t"+"                                "+"z"+"                             ");}
    out.print(s);
    out.print("\t"+df.format(a.x)+"\t");
    out.print("\t"+df.format(a.y)+"\t");
    out.println("\t"+df.format(a.z)+"\t");
 if (call==1)
   { System.out.print("\t"+"                             "+"x"+"                     ");
    System.out.print("\t"+"                           "+"y"+"                  ");
    System.out.println("\t"+"                       "+"z"+"                   ");}
    //System.out.print(s);
    System.out.print(s+"\t"+df.format(a.x)+"\t");
    System.out.print("\t"+df.format(a.y)+"\t");
    System.out.println("\t"+df.format(a.z)+"\t");

  }//abc(0,30,19)
public  void printDouble(double d,String s)
{
  out.print(s);
  out.println("\t"+df.format(d)+"\t");
  System.out.println(s+"\t"+df.format(d)+"\t");
}//abc(0,5,5)
  public  void print_edge_results(double a,double b,double c,double d,double e,double f)
  {
  out.println();
  out.println("Edge contribution using Vertex              :    "+df.format(a));
  out.println("Edge contribution using Line                :    "+df.format(b));
  out.println("Vertex Max term                             :    "+df.format(c));
  out.println("Line Max term                               :    "+df.format(d));
  out.println("Vertex accumulation so far                  :    "+df.format(e));
  out.println("Line accumulation so far                    :    "+df.format(f));

  System.out.println();
  System.out.println("Edge contribution using Vertex method:    "+df.format(a));
  System.out.println("Edge contribution for the Line method:    "+df.format(b));
  System.out.println("Max term    Vertex                   :    "+df.format(c));
  System.out.println("Max term    line                     :    "+df.format(d));
  System.out.println("Edge contribution for Vertex so far  :    "+df.format(e));
  System.out.println("Edge contribution for Line so far    :    "+df.format(f));
}

  public  void printar1(int ar[][])
  {
    System.out.println("printing Facets array");
    out.println("printing Facets array");
  for(int i=0;i<A.length;i++)
    for(int j=0;j<A[i]+1;j++)
    {System.out.println("i= "+i+"j="+j+"  "+"ar["+i+"]["+j+"]= "+ar[i][j] );
    out.println("i= "+i+"j="+j+"  "+"ar["+i+"]["+j+"]= "+ar[i][j] );}
  }
  public void printar2(vector br[])
  {System.out.println("printing Vertex array");
  out.println("printing Vertex array");
    for(int i=0;i<br.length;i++)
      {System.out.println(String.valueOf(br[i].x)+String.valueOf(br[i].y)+String.valueOf(br[i].z));
      out.println(String.valueOf(br[i].x)+String.valueOf(br[i].y)+String.valueOf(br[i].z));}
 }
  public int getTestMode()
 {return testmode;}
 public void setTestMode(int mod)
 {testmode=mod;}
public  void printEdgeResults(double[]results)
{
   System.out.println("Vertex potential    :  "+String.valueOf(results[1]));
   System.out.println("Line potential      :  "+String.valueOf(results[3]));
   System.out.println("Surface potential   :  "+String.valueOf(results[5]));
   out.println("Vertex potential    :  "+String.valueOf(results[1]));
   out.println("Line potential      :  "+String.valueOf(results[3]));
   out.println("Surface potential   :  "+String.valueOf(results[5]));
}
}
