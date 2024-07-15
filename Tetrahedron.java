package loop;


/**
 * <p>Title: Gravity anomally calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: C.P.Anastasiades Ph.D</p>
 * @author Costas
 * @version 1.0
 */

public class Tetrahedron extends AbstractModel{

  vector RC=new vector(0.0D,0.0D,0.0D);//abc(0,0,1)
  vector RCos=new vector(0.0D,0.0D,0.0D);//abc(0,0,2)
  
  double G=6.6743*Math.pow(10, -11);//m^3 /kg.s^2
vector centroid=new vector(10/4,10/4,-30/4);
 int side=10;
  int NFacets=4;
  int Vertices=4;
  int NEdges=12;
  int [][] Facets=
  {{3,0,2,1},
  {3,0,1,3},
  {3,1,2,3},
  {3,0,3,2}};

vector[] Vertex=
  {new vector(0,0,-side),//abc(0,0,3)
  new vector(side,0,-side),//abc(0,0,4)
  new vector(0,side,-side),//abc(0,0,5)
  new vector(0,0,side)};//abc(0,0,6)

  double volume =side*side*side/(6.0*Math.sqrt(2));
  

  public Tetrahedron() {
 //set RC to the centroid of the tetrahedron
  RC.set(Vertex[0].x+Vertex[1].x+Vertex[2].x+Vertex[3].x,Vertex[0].y+Vertex[1].y+Vertex[2].y+Vertex[3].y,Vertex[0].z+Vertex[1].z+Vertex[2].z+Vertex[3].z);
  RC.divScalar(4.0);
 RCos=Vertex[Facets[0][1]];
 centroid.set(vector.divScalar(new vector(Vertex[0].x+Vertex[1].x+Vertex[2].x+Vertex[3].x,Vertex[0].y+Vertex[1].y+Vertex[2].y+Vertex[3].y,Vertex[0].z+Vertex[1].z+Vertex[2].z+Vertex[3].z),4));
//abc(3,9,14)
 }

  public int[]getFacet(int facet)
  {return Facets[facet];}
  public int getVertexIndex(int facet,int index)
  { return Facets[facet][index];}
  public vector getVertex(int vec)
  {return Vertex[vec];}
  public int getNumberFacets()
  {return NFacets;}
  public int getNumberEdges(int facet)
  {return Facets[facet][0];}
  public double getVolume()
  {return volume;}
  public int getEdges()
  {return NEdges;}
  public int getVertices()
  {return Vertices;}
  public vector getCentroid()
{return centroid;}
  public vector getRCos()
{return RCos;}
  public vector getRCos_Facet(int i)
{return Vertex[Facets[i][1]];}
  
  
  public int getVertexIndex(vector vec)
  {
  int index=0;
  for (int i=0;i<Vertices;i++)
  {if (vector.equalVectors(Vertex[i],vec))index=i;}
  return index;
  }
  
  
    public vector getFacetCentroid(int facet)
  {
      double centroid=0.0;
      double x=0.0,y=0.0,z=0.0;
      vector centroid_vector=new vector(0.0,0.0,0.0);
      
      for (int i=1;i<=Facets[facet][0];i++)
              {
              x+=Vertex[Facets[facet][i]].x;
              y+=Vertex[Facets[facet][i]].y;
              z+=Vertex[Facets[facet][i]].z;
              
             
              }
               x=x/Facets[facet][0];y=y/Facets[facet][0];z=z/Facets[facet][0];
               centroid_vector=new vector(x,y,z);
               centroid=vector.magnitude(centroid_vector);
               
               
  return centroid_vector;
  
  }

}
