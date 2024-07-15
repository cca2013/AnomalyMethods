package loop;



/**
 * <p>Title: Gravity anomally calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: C.P.Anastasiades Ph.D</p>
 * @author Costas
 * @version 1.0
 */

public class TriangulatedStandardPolyhedron extends AbstractModel{

 vector centroid=new vector(40.0/88.0,250.0/88.0,-1541.0/88.0+12);
  int NFacets=16;
  int NVertices=10;
  int NEdges=48;
    int [][] Facets=
      {{3,0,5,4},
      {3,0,4,3},
      {3,0,3,2},
      {3,0,2,1},
      {3,0,1,7},
      {3,0,7,6},
      {3,1,2,8},
      {3,1,8,7},
      {3,2,3,9},
      {3,2,9,8},
      {3,3,4,9},
      {3,0,6,5},
      {3,4,5,6},
      {3,4,6,9},
      {3,6,7,8},
      {3,6,8,9}
  };

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

      public TriangulatedStandardPolyhedron() {
    //account for new objects

    //set RC to the centroid of the triangulated polyhedron
    RC.set(40.0,250.0,-1541.0);RC.divScalar(88);RC.addVec(new vector(0,0,12));
    //abc(3,3,22)

    RCos=Vertex[Facets[0][1]];
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
  public vector getCentroid()
{return centroid;}
public int getVertexIndex(vector vec)
{
for (int i=0;i<NVertices;i++)
if (vector.equalVectors(Vertex[i],vec))
return  i;
return -1;
}
  public int getEdges()
  {return NEdges;}
  public int getVertices()
  {return NVertices;}

public vector getRC(){return RC;}
public vector getRCos(){return RCos;}
  public double getVolume(){return volume;}
  public vector getRCos_Facet(int i)
{return Vertex[Facets[i][1]];}
}
