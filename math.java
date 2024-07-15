package loop;

//Angle class   --   copyright 2001, Information Disciplines, Inc.

//  This class supports operations on plane angles.


public class math {
  double  value;


//  For compatibility with the Java Math library and other software,
//  range is (-pi,pi] not [0,2pi), enforced by the following function:

  void normalize()
    {final double twoPi = Math.PI + Math.PI;
     while (value <= -Math.PI) value += twoPi;
     while (value >   Math.PI) value -= twoPi;
    }


//  Constructors
//  ------------

public  math(final double rad)  {value = rad; normalize();}
public  math()                  {this(0.0);}
public  math(final math theta) {this(theta.value);}

public  math(final int deg, //  Degrees, minutes, seconds
              final int min, //    (Signs should agree
              final int sec) //       for conventional notation.)
      {double seconds = sec + 60 * (min + 60 * deg);
              value = seconds * Math.PI / 648000.0;
              normalize();
      }
public  math(final int deg, final int min) {this(deg,min,0);}


//  Accessors
//  ---------

 public double toDegrees()  {return  180.0 * (value / Math.PI);}
 public double toRadians()  {return  value;}

//  If the following functions are used often, performance can be improved
//  by caching the results -- See IDI Date class for example.

 public short  degrees() {return  (short)(toDegrees());}
 public short  minutes()
        {long result = ((long)(Math.abs(toDegrees()) * 60.0) % 60);
              return (short) (value >=0 ? result : -result);
        }
 public short  seconds()
        {long result = ((long)(Math.round(Math.abs(toDegrees()) * 3600.0)) % 60);
              return (short) (value >=0 ? result : -result);
        }


//  Trigonometric functions (for notational consistency and to hide internal
//  -----------------------  representation.  User can use other old functions
//                           by extracting value with  toRadians() accessor)

  public double cos()          {return Math.cos (value);}
  public double sin()          {return Math.sin (value);}
  public double tan()          {return Math.tan (value);}

  public static math  arccos(final double x) {return new math(Math.acos(x));}
  public static math  arcsin(final double x) {return new math(Math.asin(x));}
  public static math  arctan(final double y, final double x)    {return new math(Math.atan2(y,x));}
  public static math  arctan(final double y) {return new math(arctan(y,1.0));}


//Math operations
//return the logarithm with base 10 of a double.
public static double log10(double x)
{return Math.log(x)/Math.log(10.0D);
//return Math.log(x)/Math.log(2.0D);

}


  //method for calculating maximum
  public  static double max(double a,double b)
  {


  if (a>b)
  return a;
  else if
  (b>a)
  return b;
  else
  return 0;
  }

//line method operations
  public static double AtanH_D(double d)throws ArctanException
       {
           double dsq = 0.0D;
           double d2 = 0.0D;
           double d3 = 0.0D;
           double d4 = 0.0D;
           int k = 0;
           if(1.0D <= Math.abs(d))
           {
               throw new ArctanException();
           }

           dsq = d*d;

           if(dsq > 0.0625D)
           {
               return Math.log((1.0D + d) / (1.0D - d)) / 2D;
           }

           if(d == 0.0D)
           {
               return 0.0D;
           }
             d4 = dsq;
             d3 = 1.0D + d4 / 3D;
             k = 3;

           for(int i = 5; 1.0D < d3; i += 2)
           {
               k = i;
               d4 *= dsq;
               d3 = 1.0D + d4 / (double)i;

           }

           d2 = dsq / (double)k;
           
           for(int j = k - 2; j >= 3; j -= 2)
           {
               d2 = (1.0D / (double)j + d2) * dsq;

           }

           return d * (1.0D + d2);

}

public static float AtanH_S(float x)
{
float xsq,res,test,term;
int n,N;
if (1<=Math.abs(x))
        {	System.out.println("ERROR:argument:  "+x+"out of range");
                return 0.0F;}
else
    {xsq=(float)Math.pow(x,2);
        if (xsq>=0.0625)
                return((float)Math.log((1+x)/(1-x))/2);
        else if
                (x==0.0F)return 0.0F;
        else
                {term=xsq;
                test=1+term/3;
                N=3;
                    for(n=5;1<test;n+=2)
                {
                N = n;
                term = term*xsq;
                test= 1 + term/n;
                }

           res= xsq/N;
        for(n=N-2;n>3;n-=2)
                res = (1/n + res)*xsq;
        return (x*(1 + res));

        }//else
   }//else

}



//  Conversion functions
//  --------------------
 public String toString()
        {final String degreeSymbol = "\370";
         return (value < 0 ? "-" : "")
              + Math.abs(degrees()) + degreeSymbol
              + Math.abs(minutes()) + '\''
              + Math.abs(seconds()) + '\"';}


//  Relational operators
//  --------------------
//  WARNING:  Floating point equality test is undependable
//            Ordering is ambiguous and not transitive, due to normalization.

 public boolean equals(final math rs)         {return value == rs.value;}

 public boolean lessThan(final math rs)       {return value  < rs.value;}
 public boolean greaterThan(final math rs)    {return value  > rs.value;}



//  Operators follow the standard additive pattern
//  ---------

 public math addSet(final math rs)
                      {value+=rs.value; normalize(); return this;}
 public math subSet (final math rs)
                      {value-=rs.value; normalize(); return this;}
 public math mpySet(final double rs)
                      {value*=rs;       normalize(); return this;}
 public math  divSet (final double rs)
                      {value/=rs;       normalize(); return this;}

 public math  minus ()                {return new math(-value);}

 public math  add(final math  rs) {return new
math(this).addSet(rs);}
 public math  sub(final math  rs) {return new
math(this).subSet(rs);}
 public math  mpy(final double rs) {return new
math(this).mpySet(rs);}
 public math  div(final double rs) {return new
math(this).divSet(rs);}
 public double div(final math rs)  {return value / rs.value;}


//  The following two functions support standard Java contracts, but are
//  not necessary, since easier-to-use equivalents appear above.

 public boolean equals(final Object rs)
                {return rs instanceof math && value ==((math) rs).value;}
 public int     compareTo(Object obj)
                {math theta = (math) obj;
                 return  lessThan(theta)  ? -1
                   :  greaterThan(theta)  ?  1
                   :                         0;
                }


public static double AtnH_D(double d)throws ArctanException
{

                      final double third=1.0D/3.0D;

                           int k = 0;
                           if(1.0D <= Math.abs(d))
                           {
                               throw new ArctanException();
                           }

                           double dsqr = d*d;
                           if(dsqr > 0.0625D)
                           {

                             return (Math.log((1.0D + d) / (1.0D - d)) / 2.0D-d)/Math.pow(d,3);
                           }
                           if(d == 0.0D)
                           {
                               return 0.0D;
                           }

                           double test = third + dsqr / 5.0D;
                           k = 5;
                          double term=dsqr;
                           for(int i = 7; third < test; i += 2)
                           {
                               k = i;
                               term=term*dsqr;
                               test= third + term/ (double)i;
                           }

                           double res=0.0D;
                           for(int j = k; j >= 5; j -= 2)
                           {
                               res = 1.0D / (double)j + res* dsqr;
                           }

                          return third+res*dsqr;

     // return (AtanH_D(d)-d)/Math.pow(d,3);
                }

public static double Atn_D(double d)throws ArctanException
                       {
                   final double third=-1.0D/3.0D;
                          int k = 0;

                          double dsqr = d*d;
                          if(dsqr > 0.0625D)
                          {
                              return (Math.atan(d)-d)/Math.pow(d,3);
                          }

                          if(d == 0.0D)
                           {
                               return 0.0D;
                           }
                          //case d <=  0.0625
                          double test = third + dsqr / 5.0D;
                          k = 5;
                          double term = dsqr;

                           for(int i = 7; third < test; i += 2)
                           {
                               k = i;
                               term=term*dsqr;
                               test= third + (term/ (double)k);
                           }
                           double res=0.0D;


                           int mod4_k=k%4;
                           int sign;
                           if (mod4_k==1)
                           sign=1;
                           else
                           sign=-1;

                           for(int j = k; j >= 5; j -= 2)
                           {
                           res =(sign* (1.0D /(double) j)) + res* dsqr;

                           sign=-sign;
                           }
                       return third+res*dsqr;
                      //return(Math.atan(d )-d)/Math.pow(d,3);
                }


public static double SolidAngle(vector R1,vector R2,vector R3,vector obs){
//Compute Solid Angle vectors r1,r2,r3
                                vector SAR1=new vector(vector.subVec(R1,obs));
                                vector SAR2=new vector(vector.subVec(R2,obs));
                                vector SAR3=new vector(vector.subVec(R3,obs));
//Compute Solid Angles R1,R2,R3
                                vector SAr1=new vector(R1);
                                vector SAr2=new vector(R2);
                                vector SAr3=new vector(R3);
                               // f.Eabc(0,9,15);
//compute solid angle nominator for Vertex accuracy
//double SAnominator=vector.dot(SAR1,vector.cross(SAR2,SAR3));
//compute substituting SAR1 with SAr1-SAr2 Line accuracy
//double SAnominator=vector.dot(vector.subVec(SAr1,SAr2),vector.cross(SAR2,SAR3));
//compute substituting SAR2 with SAr2-SAr3 Surface accuracy
                                double SAnominator=vector.dot(vector.subVec(SAr1,SAr2),vector.cross(vector.subVec(SAr2,SAr3),SAR3));
//compute denominator
//break up denominator
                                double SAdenom1=0.0;double SAdenom2=0.0;double SAdenom3=0.0;double SAdenom4=0.0;
                                SAdenom1=vector.magnitude(SAR1)*vector.magnitude(SAR2)*vector.magnitude(SAR3);
                                SAdenom2=vector.dot(SAR1,SAR2)*vector.magnitude(SAR3);
                                SAdenom3=vector.dot(SAR2,SAR3)*vector.magnitude(SAR1);
                                SAdenom4=vector.dot(SAR1,SAR3)*vector.magnitude(SAR2);
                                double SAdenominator=SAdenom1+SAdenom2+SAdenom3+SAdenom4;
                                double solidangle=(double)(SAnominator/SAdenominator);

                                return  solidangle;
                                }


public static double Oosterom(vector R1,vector R2,vector R3,vector obs){
//as solid angle but returns arctan term as atan2 for nominator , denominator
                                                vector SAR1=new vector(vector.subVec(R1,obs));
                                                vector SAR2=new vector(vector.subVec(R2,obs));
                                                vector SAR3=new vector(vector.subVec(R3,obs));
//Compute Solid Angles R1,R2,R3
                                                vector SAr1=new vector(R1);
                                                vector SAr2=new vector(R2);
                                                vector SAr3=new vector(R3);
//compute solid angle nominator for Vertex accuracy
//double SAnominator=vector.dot(SAR1,vector.cross(SAR2,SAR3));
//compute substituting SAR1 with SAr1-SAr2 Line accuracy
//double SAnominator=vector.dot(vector.subVec(SAr1,SAr2),vector.cross(SAR2,SAR3));
//compute substituting SAR2 with SAr2-SAr3 Surface accuracy
                                                double SAnominator=vector.dot(vector.subVec(SAr1,SAr2),vector.cross(vector.subVec(SAr2,SAr3),SAR3));
//compute denominator
//break up denominator
                                                double SAdenom1=0.0;double SAdenom2=0.0;double SAdenom3=0.0;double SAdenom4=0.0;
                                                SAdenom1=vector.magnitude(SAR1)*vector.magnitude(SAR2)*vector.magnitude(SAR3);
                                                SAdenom2=vector.dot(SAR1,SAR2)*vector.magnitude(SAR3);
                                                SAdenom3=vector.dot(SAR2,SAR3)*vector.magnitude(SAR1);
                                                SAdenom4=vector.dot(SAR1,SAR3)*vector.magnitude(SAR2);
                                                double SAdenominator=SAdenom1+SAdenom2+SAdenom3+SAdenom4;
                                                //double solidangle=(double)(SAnominator/SAdenominator);

                                                return  2.0*Math.atan2(SAnominator,SAdenominator);
                                                }


public static double Oosterom1(vector R1,vector R2,vector R3,vector obs,double v,double Ai){

//as Solid angle but by replacing nominator with 2.0*v*Ai
                                                vector SAR1=new vector(vector.subVec(R1,obs));
                                                vector SAR2=new vector(vector.subVec(R2,obs));
                                                vector SAR3=new vector(vector.subVec(R3,obs));
//Compute Solid Angles R1,R2,R3
                                                vector SAr1=new vector(R1);
                                                vector SAr2=new vector(R2);
                                                vector SAr3=new vector(R3); //f.Eabc(0,9,15);
//compute solid angle nominator for Vertex accuracy
//double SAnominator=vector.dot(SAR1,vector.cross(SAR2,SAR3));
//compute solid angle nominator with Line accuracy
//double SAnominator=vector.dot(vector.subVec(SAr1,SAr2),vector.cross(SAR2,SAR3));
//compute solid angle nominator with Surface accuracy
//double SAnominator=vector.dot(vector.subVec(SAr1,SAr2),vector.cross(vector.subVec(SAr2,SAr3),SAR3));
                                                //compute denominatordouble
                                                //break up denominator
                                                double SAnominator=2.0*v*Ai;
                                                double SAdenom1=0.0;double SAdenom2=0.0;double SAdenom3=0.0;double SAdenom4=0.0;
                                                SAdenom1=vector.magnitude(SAR1)*vector.magnitude(SAR2)*vector.magnitude(SAR3);
                                                SAdenom2=vector.dot(SAR1,SAR2)*vector.magnitude(SAR3);
                                                SAdenom3=vector.dot(SAR2,SAR3)*vector.magnitude(SAR1);
                                                SAdenom4=vector.dot(SAR1,SAR3)*vector.magnitude(SAR2);
                                                double SAdenominator=SAdenom1+SAdenom2+SAdenom3+SAdenom4;


                                                return  2.0*Math.atan2(SAnominator,SAdenominator);

                                                }

                public static double signum(double a)
                {
                  if (a>0.0)return 1.0;
                  else if(a<0.0) return -1.0;
                  else return 0.0;
                }

                public static double log2(double x) {

                  return Math.log(x)/Math.log(2.0D);

                }



}


