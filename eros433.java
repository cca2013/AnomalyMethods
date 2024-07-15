package loop;

import java.io.InputStream;
import java.util.Properties;


/**
 * <p>Title: Gravity anomally calculations</p>
 * <p>Description: Classes and operations for calculating gravity</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: C.P.Anastasiades Ph.D</p>
 * @author Costas
 * @version 1.0
 */


public class eros433 extends AbstractModel{

  vector RC=new vector(0.0D,0.0D,0.0D);//abc(0,0,1)
  vector RCos=new vector(0.0D,0.0D,0.0D);//abc(0,0,2)
  
  double G=6.67430e-11; //m^3 /kg.s^2
vector centroid=new vector(3446.0397196261683,4102.511682242991,-6052.663551401869);
  double volume =7*10^15;

 //int side=10;
 int NFacets=1708;
 int Vertices=856;
 int NEdges=(3*NFacets);

 vector[]Vertex;
 Eros433Faces faces;
 Eros433Vertices vertices;
  
  
  public eros433() {
try{
faces=new Eros433Faces();
vertices=new Eros433Vertices();}
catch(Exception e){}
RCos=vertices.Vertex[faces.Faces[0][1]];

 //set RC to the centroid of Eros
 RC.set(3446.0397196261683,4102.511682242991,-6052.663551401869);
 // RC.divScalar(4.0);
 // RC=Vertex[Facets[0][1]];
 //RCos=Vertex[Facets[0][1]];
 //centroid.set(vector.divScalar(new vector(Vertex[0].x+Vertex[1].x+Vertex[2].x+Vertex[3].x,Vertex[0].y+Vertex[1].y+Vertex[2].y+Vertex[3].y,Vertex[0].z+Vertex[1].z+Vertex[2].z+Vertex[3].z),4));
//abc(3,9,14)

 }

  public int[]getFacet(int facet)
  {return faces.Faces[facet];}
  public int getVertexIndex(int facet,int index)
  { return faces.Faces[facet][index];}
  public vector getVertex(int vec)
  {
      //System.out.println(vec);
      return vertices.Vertex[vec];}
  public int getNumberFacets()
  {return NFacets;}
  public int getNumberEdges(int facet)
  {return NEdges;}
  public double getVolume()
  {return volume;}
  public int getEdges()
  {return NEdges;}
  public int getVertices()
  {return vertices.getVertices();}
  public vector getCentroid()
{return centroid;}
  public vector getRCos()
{return RCos;}
  public int getVertexIndex(vector vec)
  {
  int index=0;
  for (int i=0;i<vertices.getVertices();i++)
  {if (vector.equalVectors(vertices.Vertex[i],vec))index=i;}
  //System.out.println(index);
  return index;
  }
    public vector getRCos_Facet(int i)
{return vertices.Vertex[faces.Faces[i][1]];}
  
    public vector getFacetCentroid(int facet)
  {
      double centroid=0.0;
      double x=0.0,y=0.0,z=0.0;
      vector centroid_vector=new vector(0.0,0.0,0.0);
      
      for (int i=1;i<=faces.Faces[facet][0];i++)
              {
              x+=Vertex[faces.Faces[facet][i]].x;
              y+=Vertex[faces.Faces[facet][i]].y;
              z+=Vertex[faces.Faces[facet][i]].z;
              
             
              }
               x=x/faces.Faces[facet][0];y=y/faces.Faces[facet][0];z=z/faces.Faces[facet][0];
               centroid_vector=new vector(x,y,z);
               centroid=vector.magnitude(centroid_vector);
               
               
  return centroid_vector;
  
  }

}
