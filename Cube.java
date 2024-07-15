package loop;


/**
 * <p>Title: Gravity anomaly calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: K.P.Anastasiadis Ph.D</p>
 * @author Costas
 * @version 1.0
 */

public class Cube extends AbstractModel{

  vector RC=new vector(0.0D,0.0D,0.0D);//abc(0,0,1)
  vector RCos=new vector(0.0D,0.0D,0.0D);//abc(0,0,2)
vector centroid=new vector(0.5,0.5,-0.5);
  int NFacets=12;
  int Vertices=8;
  int NEdges=36;

  int [][] Facets=
  {{3,0,1,3},
  {3,1,2,3},
  {3,4,5,7},
  {3,5,6,7},
  {3,0,3,4},
  {3,4,3,5},
  {3,1,7,6},
  {3,1,6,2},
  {3,0,4,7},
  {3,0,7,1},
  {3,3,2,5},
  {3,2,6,5}};
//at origin top-left back corner
vector[] Vertex=
  {new vector(0,0,0),//abc(0,0,3)
  new vector(0,1,0),//abc(0,0,4)
  new vector(1,1,0),//abc(0,0,5)
  new vector(1,0,0),
  new vector(0,0,-1),
  new vector(1,0,-1),
  new vector(1,1,-1),
  new vector(0,1,-1)};//abc(0,0,6)

  double volume =1;

  public Cube() {

 //set RC to the centroid of the tetrahedron
  RC.set(Vertex[0].x+Vertex[1].x+Vertex[2].x+Vertex[3].x+Vertex[4].x+Vertex[5].x+Vertex[6].x+Vertex[7].x,Vertex[0].y+Vertex[1].y+Vertex[2].y+Vertex[3].y+Vertex[4].y+Vertex[5].y+Vertex[6].y+Vertex[7].y,Vertex[0].z+Vertex[1].z+Vertex[2].z+Vertex[3].z+Vertex[4].z+Vertex[5].z+Vertex[6].z+Vertex[7].z);RC.divScalar(8.0);
 RCos=Vertex[Facets[0][1]];
 centroid.set(vector.divScalar(RC,8));
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
