package loop;

/**
 * <p>Title: Gravity anomally calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: C.P.Anastasiades Ph.D</p>
 * @author Costas
 * @version 1.0
 */

public class polyhedron extends AbstractModel {

  int NFacets=8;
  int NVertices=10;
  int NEdges=32;
  vector centroid=new vector(40.0/88.0,250.0/88.0,-1541.0/88.0+12);
    int [][] Facets=
      {{6,0,5,4,3,2,1},
      {4,0,1,7,6,0,0},
      {4,1,2,8,7,0,0},
      {4,2,3,9,8,0,0},
      {3,3,4,9,0,0,0},
      {4,4,5,6,9,0,0},
      {3,0,6,5,0,0,0},
      {4,6,7,8,9,0,0}};

    vector[] Vertex=
      {new vector(10,10,0),
      new vector(10,-10,0),
      new vector(-10,-10,0),
      new vector(-10,10,0),
      new vector(-20,30,0),
      new vector(30,30,0),
      new vector(20,20,-10),
      new vector(20,-30,-10),
      new vector(-20,-30,-10),
      new vector(-20,20,-10)};


 
 
      vector RC=new vector(0.0D,0.0D,0.0D);
      vector RCos=new vector(0.0D,0.0D,0.0D);
double volume=44000.0/3.0;
  public polyhedron() {
    //account for new objects

    //set RC to the centroid of the polyhedron
    RC.set(centroid);
    //abc(3,3,22)

    RCos=Vertex[Facets[0][1]];
  
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
               //x=x/Facets[facet][0];y=y/Facets[facet][0];z=z/Facets[facet][0];
               centroid_vector=new vector(x,y,z);
               centroid=vector.magnitude(centroid_vector);
               
               
  return centroid_vector;
  
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

public int getVertexIndex(vector vec)
{
for (int i=0;i<NVertices;i++)
if (vector.equalVectors(Vertex[i],vec))return  i;
return -1;
}
  public int getEdges()
  {return NEdges;}
  public int getVertices()
  {return NVertices;}
public vector getCentroid()
{return centroid;}
public vector getRC(){return RC;}
public vector getRCos(){return RCos;}
  public double getVolume(){return volume;}
  public vector getRCos_Facet(int i)
{return Vertex[Facets[i][1]];}
}
