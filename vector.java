package loop;
import java.lang.Math;
import java.math.BigDecimal;
/**
 * <p>Title: Gravity anomally calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: C.P.Anastasiades Ph.D</p>
 * @author Costas
 * @version 1.0
 */





/**
  * <p>Title: Polyhedron project</p>
  * <p>Description: Ph.D thesis project</p>
  * <p>Copyright: Copyright (c) 2005</p>
  * <p>Company: </p>
  * @author Costas P.Anastasiades
  * @version 1.1 extensively modified by H Holstein, 20th Feb 2006
  *


/** constructors for the class vector
  */

  public class vector {
    //count metrics ABC

    static int Counta=0,Countb=0,Countc=0;
     double x, y, z;
     double large = 1.0e+38;

    // totaly new vector
    public vector(double x, double y, double z) {
      this.x=x;
     this.y=y;
     this.z=z;

    }

    // totaly new vector
  public vector(double c) {
      this.set(c);
    }

    // new vector as clone of existing one
    public vector(vector v1) {
      this.x=v1.x;
      this.y=v1.y;
      this.z=v1.z;
    }

   // set all components to the same value c
    public void set(double c) {
      this.x=c;
      this.y=c;
      this.z=c;
    }

    public void set(double x, double y, double z) {
      this.x=x;
      this.y=y;
      this.z=z;
    }

    public void set(vector v1) {
      this.x=v1.x;
      this.y=v1.y;
      this.z=v1.z;
    }


/** dot calculates the dot product of two vectors v1 and v2
  * @param v1 is the first vector operand
  * @param v2 is the second vector operand
  * @returns the vector double result for v1 + v2
  */
    public static double dot(vector v1, vector v2)
    {
    return(v1.x*v2.x +v1.y*v2.y + v1.z*v2.z);
    }

/** magnitude calculates the magnitude of a vector
  * in the range -pi/2 to +pi/2
  * @param ()
  * @returns the double result for this magnitude of this
  */
   public static double magnitude(vector v)
   {
   return(Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z));
   }

/** maxComponent calculates the maximum absolute value of the
  * three components of this vector
  * @param ()
  * @returns the double result
  */
    public double maxComponent()
    {
        double max, tmp;
        max = Math.abs(this.x);
        tmp = Math.abs(this.y); if (max<tmp) max=tmp;
        tmp = Math.abs(this.z); if (max<tmp) max=tmp;
        return(max);
    }

/** angle calculates the radian angle between two vectors v1 and v2
  * in the range -pi/2 to +pi/2
  * @param v1 is the first vector operand
  * @param v2 is the second vector operand
  * @returns the double result
  */
  //  public static double angle(vector v1, vector v2)
    //{
      //  double result;
        //  result =
          //( dot(v1,v2) / (v1.magnitude() * v2.magnitude()) );
        // Note: |result| can exceed 1.0 on account of rounding
        //if (Math.abs(result)>1.0) result=Math.signum(result);
        //return(Math.acos(result));
    //}

/** multiply a vector (this) by a scalar s
  * @param s is the scaling factor argument
  * @return this - original vector multiplied by s
 */
  public void mulScalar(double s)
  {
     this.x *= s;
     this.y *= s;
     this.z *= s;
  }

/** multiply a vector v by a scalar s
  * @param v is vector to be scaled
  * @param s is the scaling factor argument
  * @return v*s as a new vector
 */
  public static vector mulScalar(vector v, double s)
  {
  vector newOne = new vector(v);
  newOne.mulScalar(s);
  return newOne;
  }

/** divide a vector(this) by a scalar s
  * Check for possible overflow - print warning if found
  * @param s is the scaling factor argument
  * @return this - original vector divided by s
 */
  public void divScalar(double s)
  {
        double tmp = Math.abs(s);
  // test for possible overflow
  if (tmp<1.0){
        if (s*large<this.maxComponent())
        System.err.println("*** Overflow from divScalar");
        }
     this.x /= s;
     this.y /= s;
     this.z /= s;
  }//abc(0,0,6)

//  public  vector divScalar(double n)
//  {
//  return new vector(this.x/n,this.y/n,this.z/n);
//
//  }


/** divide a vector v by a scalar s
  * @param v is vector argument
  * @param s is the scaling factor argument
  * @return v/s as a new vector
 */
  public static vector divScalar(vector v, double s)
  {
  vector newOne = new vector(v);
  newOne.divScalar(s);
  return newOne;//abc(0,0,8)
  }

/** cross product of two vectors v1 and v2
  * @param v1 is the first vector argument
  * @param v2 is the second vector argument
  * @return v1 cross v2 as a new vector
 */
  public static vector cross(vector v1,vector v2)
  {
  return new
  vector((v1.y*v2.z-v1.z*v2.y),
         (v1.z*v2.x-v1.x*v2.z),
         (v1.x*v2.y-v1.y*v2.x));
  }

/** vector adition of this and v
  * @param v is the vector argument
  * @return this + v, result in this
 */
  public void addVec(vector v)
  {
  this.x += v.x;
  this.y += v.y;
  this.z += v.z;
  }

/** vector addition of two vectors v1 and v2
  * @param v1 is the first vector argument
  * @param v2 is the second vector argument
  * @return v1 + v2 as a new vector
 */
  public static vector addVec(vector v1, vector v2)
  {
  vector newOne = new vector(v1);
  newOne.addVec(v2);
  return newOne;
  }

/** vector subtraction of this and v
  * @param v is the vector argument
  * @return this - v, result in this
 */
  public void subVec(vector v)
  {
  this.x -= v.x;
  this.y -= v.y;
  this.z -= v.z;
  }

/** vector subtraction of two vectors v1 and v2
  * @param v1 is the first vector argument
  * @param v2 is the second vector argument
  * @return v1 + v2 as a new vector
 */
  public static vector subVec(vector v1, vector v2)
  {
  vector newOne = new vector(v1);
  newOne.subVec(v2);
  return newOne;
  }

/** negation of this vector
  * @return -this, result in this
 */
  public void negVec()
  {
  this.x = -this.x;
  this.y = -this.y;
  this.z = -this.z;
  }

/** negation of a vector v
  * @param v
  * @return -v, result in a new vector
 */
  public static vector negVec(vector v)
  {
  vector newOne = new vector(v);Countc++;
  newOne.negVec();Countc++;
  return newOne;
  }

/** add a scaled version of another vector to this
  * @param v2 is the vector to be scaled
  * @param s is the scaling factor
  * @return this + v2*s as this
 */
    public void addScaled(vector v2, double s) {
        this.addVec(vector.mulScalar(v2,s));
    }

/** add a scaled version of a vector to another vector v1
  * @param v1 is the vector to be added to
  * @param v2 is the vector to be scaled
  * @param s is the scaling factor
  * @return v1 + v2*s as a new vector
 */
    public static vector addScaled(vector v1, vector v2, double s) {
        vector newOne = new vector(v1);Countc++;
        newOne.addVec(vector.mulScalar(v2,s));Countc++;
        return newOne;
    }

public static boolean equalVectors(vector a,vector b)
{
if (a.x==b.x&&a.y==b.y&&a.z==b.z)return true;
  return false;
}

  public String toString() {
  return "[" + this.x + ", " + this.y + ", " + this.z + "]";
  }
//implements the vector X vector operation and returns a matrix 3X3.
public static double[][] MulVec(vector a,vector b)
{double[][] ret=new double[3][3];
  ret[0][0]=a.x*b.x;
  ret[0][1]=a.y*b.x;
  ret[0][2]=a.z*b.x;
  ret[1][0]=a.x*b.y;
  ret[1][1]=a.y*b.y;
  ret[1][2]=a.z*b.y;
  ret[2][0]=a.x*b.z;
  ret[2][1]=a.y*b.z;
  ret[2][2]=a.z*b.z;
  return ret;

}//implements addition between 2 matrices 3X3
public static double[][] addMatrix(double a[][],double b[][])
   {double c[][]=new double [3][3];
   for (int i=0;i<3;i++)
     for (int j=0;j<3;j++)
       c[i][j]=a[i][j]+b[i][j];
     return c;
   }

public static vector dotVectorByMatrix3X3(vector a, double[][]b)
   {
   
     a.x=   a.x*b[0][0]+a.y*b[1][0]+a.z*b[2][0];
     a.y=   a.x*b[0][1]+a.y*b[1][1]+a.z*b[2][1]; 
     a.z=   a.x*b[0][2]+a.y*b[1][2]+a.z*b[2][2]; 
        
      
     return a;
   }

   }

   


