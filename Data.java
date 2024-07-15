package loop;

/**
 * <p>Title: Data controller class</p>
 * <p>Description: Protocol class acting like a data control class</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: C.P.Anastasiades, Ph.D thesis proposal</p>
 * @author Costas
 * @version 1.0
* To add a target:
* 1.create vertex,facet list
* 2.add class as polyhedron
* 3.adjust data primitives
* 4.adjust number of facets
* 5.create a new object
* 6.add else if target==new new. in accessor methods of Data class.
*
 */
import java.io.*;
public class Data {
 survey surveydata=new survey();
 
 utility util;
 File file;
 PrintWriter out;
//TestSurvey surveydata;
//target model switch 1 for polyhedro, 2 for tetrahedro
int target=2;

    public void setSurveydata(survey surveydata) {
        this.surveydata = surveydata;
    }

    public void setUtil(utility util) {
        this.util = util;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void setPoly(polyhedron poly) {
        this.poly = poly;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }

    public void setTetra(Tetrahedron tetra) {
        this.tetra = tetra;
    }

    public void setEros(eros433 eros) {
        this.eros = eros;
    }

    public void setDidimos(Didimos didimos) {
        this.didimos = didimos;
    }

    public void setBennu(Bennu bennu) {
        this.bennu = bennu;
    }

    public void setGravity_constant(double gravity_constant) {
        this.gravity_constant = gravity_constant;
    }

    public void setDensity_constant(double density_constant) {
        this.density_constant = density_constant;
    }

    public void setTriPoly(TriangulatedStandardPolyhedron TriPoly) {
        this.TriPoly = TriPoly;
    }

    public void setEdgeIndex(int EdgeIndex) {
        this.EdgeIndex = EdgeIndex;
    }

    public void setEdgeIndex1(int EdgeIndex1) {
        this.EdgeIndex1 = EdgeIndex1;
    }

    public void setEdgeOperations(int EdgeOperations) {
        this.EdgeOperations = EdgeOperations;
    }

    public void setEdge_col(int edge_col) {
        this.edge_col = edge_col;
    }

    public void setEdge_data(int edge_data) {
        this.edge_data = edge_data;
    }

    public void setEdge_found_at_position(int edge_found_at_position) {
        this.edge_found_at_position = edge_found_at_position;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public void setVertex(double[] Vertex) {
        this.Vertex = Vertex;
    }

    public void setLine(double[] Line) {
        this.Line = Line;
    }

    public void setSurface(double[] Surface) {
        this.Surface = Surface;
    }

    public void setEdge(double[][] edge) {
        this.edge = edge;
    }

    public void setEdge1(double[][] edge1) {
        this.edge1 = edge1;
    }

    public void setVertexField(vector[] VertexField) {
        this.VertexField = VertexField;
    }

    public void setLineField(vector[] LineField) {
        this.LineField = LineField;
    }

    public void setSurfaceField(vector[] SurfaceField) {
        this.SurfaceField = SurfaceField;
    }

    public void setNormals(vector[] normals) {
        this.normals = normals;
    }

    public void setAreas(vector[] areas) {
        this.areas = areas;
    }

    public void setStoredValues(double[][] StoredValues) {
        this.StoredValues = StoredValues;
    }

    public void setRetrievedValues(double[] RetrievedValues) {
        this.RetrievedValues = RetrievedValues;
    }

    public eros433 getEros() {
        return eros;
    }

    public Didimos getDidimos() {
        return didimos;
    }

    public Bennu getBennu() {
        return bennu;
    }
polyhedron poly;
Cube cube;
Tetrahedron tetra;
eros433 eros;
Didimos didimos;
Bennu bennu;


double gravity_constant = 6.6743*Math.pow(10, -11);
        //units of G=m^3 kg^(-1) s^(-2)
        
     
  
double density_constant=2670;

    public survey getSurveydata() {
        return surveydata;
    }

    public utility getUtil() {
        return util;
    }

    public File getFile() {
        return file;
    }

    public PrintWriter getOut() {
        return out;
    }

    public int getTarget() {
        return target;
    }

    public polyhedron getPoly() {
        return poly;
    }

    public Cube getCube() {
        return cube;
    }

    public Tetrahedron getTetra() {
        return tetra;
    }

    public double getGravity_constant() {
        return gravity_constant;
    }

    public double getDensity_constant() {
        return density_constant;
    }

    public TriangulatedStandardPolyhedron getTriPoly() {
        return TriPoly;
    }

    public int getEdgeOperations() {
        return EdgeOperations;
    }

    public int getEdge_col() {
        return edge_col;
    }

    public int getEdge_data() {
        return edge_data;
    }

    public int getEdge_found_at_position() {
        return edge_found_at_position;
    }

    public boolean isFound() {
        return found;
    }

    

    public double[] getLine() {
        return Line;
    }

    public double[] getSurface() {
        return Surface;
    }

    public double[][] getEdge() {
        return edge;
    }

    public double[][] getEdge1() {
        return edge1;
    }

    public vector[] getVertexField() {
        return VertexField;
    }

    public vector[] getLineField() {
        return LineField;
    }

    public vector[] getSurfaceField() {
        return SurfaceField;
    }

    public double[][] getStoredValues() {
        return StoredValues;
    }

    public double[] getRetrievedValues() {
        return RetrievedValues;
    }
//units density =  Kg m^(-3)
//Dodekaedron dodeka;
TriangulatedStandardPolyhedron TriPoly;
 int EdgeIndex=0; int EdgeIndex1=0;
 int EdgeOperations=17;
 int edge_col=0;
 int edge_data=25;
 int edge_found_at_position;boolean found=false;

double [] Vertex;
double [] Line;
double [] Surface;
double [][] edge; double[][]edge1;
vector [] VertexField;vector [] LineField; vector [] SurfaceField;
//arrays for storing normals for max number of facets =16
vector[] normals= new vector[5000];
//arrays for storing areas max=16
vector[] areas=new vector[5000];


double StoredValues[][];
double RetrievedValues [];

boolean isPrime(int n) {
    for(int i=2;i<n;i++) {
        if(n%i==0)
            return false;
    }
    return true;
}
private int PrimeEdges(int edges)
{
if (isPrime(edges))return edges-1;
        return edges;
}

public Data(){
    for (int n=0;n<areas.length;n++)
    {
    areas[n]=new vector(0,0,0);
    }
    for (int n=0;n<normals.length;n++)
    {
    normals[n]=new vector(0,0,0);
    }
    

            if (target==1)
                    { poly=new polyhedron();
                    edge_col=poly.getEdges();
                    //System.out.println("number of edges  :"+edge_col);
                    edge=new double[edge_col][edge_data];
                    edge1=new double[PrimeEdges(edge_col)/2][edge_data];
                    for(int i=0;i<edge_col;i++){for(int j=0;j<edge_data;j++){edge[i][j]= 0;}}
                    for(int i=0;i<PrimeEdges(edge_col)/2;i++){for(int j=0;j<edge_data;j++){edge1[i][j]= 0;}}}
              else if( target==2)
                    { tetra=new Tetrahedron();
                    edge_col=tetra.getEdges();
                    edge=new double[edge_col][edge_data];
                    edge1=new double[PrimeEdges(edge_col)/2][edge_data];
                    for(int i=0;i<edge_col;i++){for(int j=0;j<edge_data;j++){edge[i][j]= 0;}}
                    for(int i=0;i<PrimeEdges(edge_col)/2;i++){for(int j=0;j<edge_data;j++){edge1[i][j]= 0;}}}
              else if(target==3)
                    { TriPoly=new TriangulatedStandardPolyhedron();
                    edge_col=TriPoly.getEdges();                 
                    edge=new double[edge_col][edge_data];
                    edge1=new double[PrimeEdges(edge_col)/2][edge_data];
                    for(int i=0;i<edge_col;i++){for(int j=0;j<edge_data;j++){edge[i][j]= 0;}}
                    for(int i=0;i<PrimeEdges(edge_col)/2;i++){for(int j=0;j<edge_data;j++){edge1[i][j]= 0;}}}
              else if(target==4)
                    { cube=new Cube();
                    edge_col=cube.getEdges();
                    //System.out.println("number of edges  :"+edge_col);
                    edge=new double[edge_col][edge_data];
                    edge1=new double[PrimeEdges(edge_col)/2][edge_data];
                    for(int i=0;i<edge_col;i++){for(int j=0;j<edge_data;j++){edge[i][j]= 0;}}
                    for(int i=0;i<PrimeEdges(edge_col)/2;i++){for(int j=0;j<edge_data;j++){edge1[i][j]= 0;}}}
             else if(target==5)
                    { eros=new eros433();
                    edge_col=eros.getEdges();
                    //System.out.println("number of edges  :"+edge_col);
                    edge=new double[edge_col][edge_data];
                    edge1=new double[PrimeEdges(edge_col)/2][edge_data];
                    for(int i=0;i<edge_col;i++){for(int j=0;j<edge_data;j++){edge[i][j]= 0;}}
                    for(int i=0;i<PrimeEdges(edge_col)/2;i++){for(int j=0;j<edge_data;j++){edge1[i][j]= 0;}}}
            else if(target==6)
                    { didimos=new Didimos();
                    edge_col=didimos.getEdges();
                    //System.out.println("number of edges  :"+edge_col);
                    edge=new double[edge_col][edge_data];
                    edge1=new double[PrimeEdges(edge_col)/2][edge_data];
                    for(int i=0;i<edge_col;i++){for(int j=0;j<edge_data;j++){edge[i][j]= 0;}}
                    for(int i=0;i<PrimeEdges(edge_col)/2;i++){for(int j=0;j<edge_data;j++){edge1[i][j]= 0;}}}
             
            else if(target==7)
                    { bennu=new Bennu();
                 //   bennu.vertices.printVertices();
                    edge_col=bennu.getEdges();
                    //System.out.println("number of edges  :"+edge_col);
                    edge=new double[edge_col][edge_data];
                    edge1=new double[PrimeEdges(edge_col)/2][edge_data];
                    for(int i=0;i<edge_col;i++){for(int j=0;j<edge_data;j++){edge[i][j]= 0;}}
                    for(int i=0;i<PrimeEdges(edge_col)/2;i++){for(int j=0;j<edge_data;j++){edge1[i][j]= 0;}}}

  try{
  file=new File("MyGravity.txt");
  out=new PrintWriter(new FileWriter(file));
  }
  catch(IOException e){}
  
  
surveydata      =   new survey();
//arrays for storing potentials
Vertex          =   new double[surveydata.getNumberObs()];
Line            =   new double[surveydata.getNumberObs()];
Surface         =   new double[surveydata.getNumberObs()];
//arrays for storing gravity fields
VertexField     =   new vector[surveydata.getNumberObs()];
LineField       =   new vector[surveydata.getNumberObs()];
SurfaceField    =   new vector[surveydata.getNumberObs()];
//start a print object
util=new utility(out);
}//end public data

//target primitives getters
public vector getPolyVertex(int facet,int vec)
{
if(target==1)return poly.getVertex(poly.getVertexIndex(facet,vec));
else if (target==2) return tetra.getVertex(tetra.getVertexIndex(facet,vec));
else if (target==4) return cube.getVertex(cube.getVertexIndex(facet,vec));
else if (target==5) return eros.getVertex(eros.getVertexIndex(facet,vec));
else if (target==6) return didimos.getVertex(didimos.getVertexIndex(facet,vec));
else if (target==7) return bennu.getVertex(bennu.getVertexIndex(facet,vec));
else  return TriPoly.getVertex(TriPoly.getVertexIndex(facet,vec));}

public int getFacetVertex_index(int facet,int vertex)
{
  if(target==1)
  return poly.getVertexIndex(facet,vertex);
else if (target==2)return tetra.getVertexIndex(facet,vertex);
else if (target==4)return cube.getVertexIndex(facet,vertex);
else if (target==5) return eros.getVertexIndex(facet,vertex);
else if (target==6) return didimos.getVertexIndex(facet,vertex);
else if (target==7) return bennu.getVertexIndex(facet,vertex);
else return TriPoly.getVertexIndex(facet,vertex);}

public vector getVertex(int vec)
{
  if (target==1)
  return poly.getVertex(vec);
else if (target==2) return tetra.getVertex(vec);
  else if (target==4) return cube.getVertex(vec);
  else if (target==5) return eros.getVertex(vec);
  else if (target==6) return didimos.getVertex(vec);
  else if (target==7) return bennu.getVertex(vec);
  else return TriPoly.getVertex(vec);}

public int[] getPolyFacet(int facet)
{
  if(target==1)
  return poly.getFacet(facet);
  else if (target==2) return tetra.getFacet(facet);
  else if (target==4) return cube.getFacet(facet);
  else if (target==5) return eros.getFacet(facet);
  else if (target==6) return didimos.getFacet(facet);
  else if (target==7) return bennu.getFacet(facet);
  else 
      return TriPoly.getFacet(facet);}

public int getPolyFacets()
{
  if(target==1)
  return poly.getNumberFacets();
else if (target==2) return tetra.getNumberFacets();
else if (target==4) return cube.getNumberFacets();
else if (target==5) return eros.getNumberFacets();
else if (target==6) return didimos.getNumberFacets();
else if (target==7) return bennu.getNumberFacets();
else return TriPoly.getNumberFacets();
}

public int getFacetEdges(int facet)
{
  if(target==1)
  return poly.getNumberEdges(facet);
  else if (target==2) return tetra.getNumberEdges(facet);
  else if (target==4) return cube.getNumberEdges(facet);
  else if (target==5) return eros.getNumberEdges(facet);
  else if (target==6) return didimos.getNumberEdges(facet);
  else if (target==7) return bennu.getNumberEdges(facet);
  else return TriPoly.getNumberEdges(facet);}

public double getTargetVolume()
{
  if(target==1)
    return poly.getVolume();
    else if (target==2) return tetra.getVolume();
    else if (target==4) return cube.getVolume();
    else if (target==5) return eros.getVolume();
  else if (target==6) return didimos.getVolume();
  else if (target==7) return bennu.getVolume();
  
  else return TriPoly.getVolume();}

public int getVertexIndex(vector v)
{if(target==1)
  return poly.getVertexIndex(v);
else if (target==2) return tetra.getVertexIndex(v);
else if (target==4) return cube.getVertexIndex(v);
else if (target==5) return eros.getVertexIndex(v);
else if (target==6) return didimos.getVertexIndex(v);
else if (target==7) return bennu.getVertexIndex(v);
else return TriPoly.getVertexIndex(v);}

public int getVertices()
{
  if (target==1)
  return poly.getVertices();
else if (target==2) return tetra.getVertices();
else if (target==4) return cube.getVertices();
else if (target==5) return eros.getVertices();
else if (target==6) return didimos.getVertices();
else if (target==7) return bennu.getVertices();
else return TriPoly.getVertices();}

public int getEdges()
{
  if(target==1)
  return poly.getEdges();
else if (target==2)return tetra.getEdges();
else if (target==4)return cube.getEdges();
else if (target==5) return eros.getEdges();
else if (target==6) return didimos.getEdges();
else if (target==7) return bennu.getEdges();
else return TriPoly.getEdges();}

public vector getCentroid()
{
  if(target==1)
  return poly.getCentroid();
else if (target==2) return tetra.getCentroid();
else if (target==4) return cube.getCentroid();
else if (target==5) return eros.getRCos();
else if (target==6) return didimos.getRCos();
else if (target==7) return bennu.getCentroid();
else return TriPoly.getCentroid();}

public vector getRCos()
{

  if(target==1)return poly.getRCos();
else if (target==2) return tetra.getRCos();
else if (target==4) return cube.getRCos();
else if (target==5) return eros.getRCos();
else if (target==6) return didimos.getRCos();
else if (target==7) return bennu.getRCos();
else return TriPoly.getRCos();}

public vector getRCos_Facet(int i)
{ if(target==1)return poly.getRCos_Facet(i);
else if (target==2) return tetra.getRCos_Facet(i);
else if (target==4) return cube.getRCos_Facet(i);
else if (target==5) return eros.getRCos_Facet(i);
else if (target==6) return didimos.getRCos_Facet(i);
else if (target==7) return bennu.getRCos_Facet(i);
else return TriPoly.getRCos_Facet(i);}


public vector getFacetCentroid(int facet)
{

  if(target==1)return poly.getFacetCentroid(facet);
else if (target==2) return tetra.getFacetCentroid(facet);
else if (target==4) return cube.getFacetCentroid(facet);
else if (target==5) return eros.getFacetCentroid(facet);
else if (target==6) return didimos.getFacetCentroid(facet);
else if (target==7) return bennu.getFacetCentroid(facet);
else return TriPoly.getRCos();}



//normals and areas
//getters
public vector[] getNormals()
{return normals;}
public vector[] getAreas()
{return areas;}
public vector getNormal(int facet)
{return normals[facet];}
public vector getArea(int facet)
{return areas[facet];}
//setters
public void setNormal(vector v,int facet)
{normals[facet]=v;}
public void setArea(vector v,int facet)
{areas[facet]=v;}

//survey getters
public vector getObs(int index)
{return surveydata.Obs[index];}
public int getNumberObs(){return surveydata.MOpoints;}
public vector[] getAllObs(){return surveydata.Obs;}
public Data getData(){return this;}
public utility getUtilitythread(){return util;}
public int getTargetModel(){return target;}
//result arrays getters for potential
public double getVertexResults(int position)  {return Vertex[position];}
public double getLineResults(int position)    {return Line[position];}
public double getSurfaceResults(int position) {return Surface[position];}
//result arrays setters for potential
public void setVertexResults(int position,double value){Vertex[position]=value;}
public void setLineResults(int position,double value){Line[position]=value;}
public void setSurfaceResults(int position,double value){Surface[position]=value;}
//result arrays setters for gravity field
public void setVertexFieldResults(int position,vector value){VertexField[position]=value;}
public void setLineFieldResults(int position,vector value){LineField[position]=value;}
public void setSurfaceFieldResults(int position,vector value){SurfaceField[position]=value;}
//result arrays getters for field
public vector getVertexFieldResults(int position)  {return VertexField[position];}
public vector getLineFieldResults(int position)    {return LineField[position];}
public vector getSurfaceFieldResults(int position) {return SurfaceField[position];}

public double[] getEdgeRow(int index)
{return edge[index];}
public double getEdgeValue(int i, int j)
{return edge[i][j];}

//initialize arrays
public void InitStoredValues()
{
  StoredValues=new double[getEdges()][EdgeOperations+2];
  for(int i=0;i<getEdges();i++)
  for (int j=0;j<EdgeOperations+2;j++)StoredValues[i][j]=0.0D;
  RetrievedValues=new double[EdgeOperations];
  for(int i=0;i<EdgeOperations;i++)RetrievedValues[i]=0.0;
}

public boolean CheckValues(double r1,double r2)
{
  for(int i=0;i<getEdges();i++)
   if (r1==StoredValues[i][0] || r1==StoredValues[i][1])
    if (r2==StoredValues[i][0] || r2==StoredValues[i][1]){for(int j=0;j<EdgeOperations;j++)RetrievedValues[j]=StoredValues[i][2+j];return true;}
  return false;
}

//NEW IMPLEMENTATION- EULER EDGE STRUCTURE for undirected edge loop
public boolean Edge_not_Stored(vector r1,vector r2)
{edge_found_at_position=0;found=false;
  for(int i=0;i<edge.length;i++)
  {
   if (
   //if r1== 1st stored or r1==2nd stored
        (//1st AND component start
        ((r1.x==edge[i][0]) && (r1.y==edge[i][1]) && (r1.z==edge[i][2] ))
        &&
        ((r2.x==edge[i][3]) && (r2.y==edge[i][4]) && (r2.z==edge[i][5] ))
        )//1st AND component end
        ||
   //or r2==2nd stored or r2== 1st stored
        (//2nd AND start
        ((r1.x==edge[i][3]) && (r1.y==edge[i][4]) && (r1.z==edge[i][5] ))
        &&
        ((r2.x==edge[i][0]) && (r2.y==edge[i][1]) && (r2.z==edge[i][2] ))
        )//2nd AND end

       )//end if
      {
       found=true;
       edge_found_at_position=i;}
 }
  
 if (found==false)
   return true;
 else return false;
}

public void Store_Edge_Vertex(vector r1,vector r2)
{
edge[getEdgeIndex()][0]=r1.x;edge[getEdgeIndex()][1]=r1.y;edge[getEdgeIndex()][2]=r1.z;
edge[getEdgeIndex()][3]=r2.x;edge[getEdgeIndex()][4]=r2.y;edge[getEdgeIndex()][5]=r2.z;
}
public void Store_Edge_Vertex1(vector r1,vector r2)
 {
edge1[getEdgeIndex1()][0]=r1.x;edge1[getEdgeIndex1()][1]=r1.y;edge1[getEdgeIndex1()][2]=r1.z;
edge1[getEdgeIndex1()][3]=r2.x;edge1[getEdgeIndex1()][4]=r2.y;edge1[getEdgeIndex1()][5]=r2.z;
 }

 public void Store_Edge_Facet1(int facet)
 {edge[getEdgeIndex()][18]=facet;}

 public void Store_Edge_Facet11(int facet)
 {edge1[getEdgeIndex1()][18]=facet;
 edge1[getEdgeIndex1()][19]=edge[edge_found_at_position][18];
 }

 public void Store_Edge_Facet2(int facet)
 {edge[edge_found_at_position][19]=facet;}

public double getEdgeItem(int i, int j){return edge[i][j];}

 public void increaseEdgeIndex()
{if (EdgeIndex<(getEdges()-1))EdgeIndex++;}
 public void increaseEdgeIndex1()
{if (EdgeIndex1<(getEdges()/2-1))EdgeIndex1++;}
  public int getEdgeIndex()
{return EdgeIndex;}
  public int getEdgeIndex1()
{return EdgeIndex1;}
public double[][] getEdge_Struct(){return edge;}
public double[][] getEdge1_Struct(){return edge1;}

public void CreateEdgeStructure(vector r1,vector r2,int fact)
{
  if(Edge_not_Stored(r1,r2))
   {
     Store_Edge_Facet1(fact); 
     Store_Edge_Vertex(r1,r2);
     increaseEdgeIndex();
   }
   else
   {
    Store_Edge_Facet11(fact);
    Store_Edge_Vertex1(r1,r2);
    Store_Edge_Facet2(fact);
    increaseEdgeIndex1();
   }
}

public void mergeEdgeStructures()
{
for(int i=PrimeEdges(getEdges())/2;i<edge.length;i++)
    edge[i]=edge1[i-getEdges()/2];
}

public void ComputeEdge_Intrinsic()
{
  vector r1=new vector(0.0,0.0,0.0);
  vector r2=new vector(0.0,0.0,0.0);
  vector t_bar=new vector(0.0,0.0,0.0);
  vector t_hat=new vector(0.0,0.0,0.0);
  vector h_bar=new vector(0.0,0.0,0.0);
  vector h_hat=new vector(0.0,0.0,0.0);
  vector ni=new vector(0.0,0.0,0.0);
  int facet1=0;int facet2=0;
  double L=0;

//begin intrinsic calculations BODY COUNTS
for(int i=0;i<edge.length;i++)
{
r1.x=edge[i][0];r1.y=edge[i][1];r1.z=edge[i][2];
r2.x=edge[i][3];r2.y=edge[i][4];r2.z=edge[i][5];

//compute t bar as the vector edge tangent
t_bar=vector.subVec(r2,r1);
  //count 0,3,0
edge[i][6]=t_bar.x;edge[i][7]=t_bar.y;edge[i][8]=t_bar.z;
//compute L as the edge length
L=vector.magnitude(t_bar);//count
  //3,2,1
edge[i][20]=L;
t_hat=vector.divScalar(t_bar,L);
  //3,0,0
//System.out.println("t.x  :"+t_hat.x+"t.y   :"+t_hat.y+"t.z  :"+t_hat.z);
edge[i][9]=t_hat.x;edge[i][10]=t_hat.y;edge[i][11]=t_hat.z;
facet1=(int)edge[i][18];facet2=(int)edge[i][19];
ni=getNormal(facet1);
//compute h
h_bar=vector.cross(t_hat,ni);
  //0,3,0
h_hat=vector.divScalar(h_bar,vector.magnitude(h_bar));
  //3,0,0
  //3,2,1
edge[i][12]=h_bar.x;edge[i][13]=h_bar.y;edge[i][14]=h_bar.z;
edge[i][15]=h_hat.x;edge[i][16]=h_hat.y;edge[i][17]=h_hat.z;
}
}

public double getStoredValue(int val)
{return RetrievedValues[val-2];}

  public void resetEdgeIndex()
{EdgeIndex=0;}

public void setValues(int position,double r1,double r2,double val2,double val3,double val4,double val5,
                        double val6,double val7,double val8,double val9,double val10,double val11,
                        double val12,double val13,double val14,double val15,double val16,
                        double val17,double val18)
{
    StoredValues[position][0]=r1;StoredValues[position][1]=r2;
    StoredValues[position][2]=val2;StoredValues[position][3]=val3;
    StoredValues[position][4]=val4;StoredValues[position][5]=val5;
    StoredValues[position][6]=val6;StoredValues[position][7]=val7;
    StoredValues[position][8]=val8;StoredValues[position][9]=val9;
    StoredValues[position][10]=val10;StoredValues[position][11]=val11;
    StoredValues[position][12]=val12;StoredValues[position][13]=val13;
    StoredValues[position][14]=val14;StoredValues[position][15]=val15;
    StoredValues[position][16]=val16;StoredValues[position][17]=val17;
    StoredValues[position][18]=val18;
increaseEdgeIndex();
}

public void setValue(int position,int index,double value)
{
StoredValues[position][index]=value;
}
public double printStoredValue(int i,int index){return StoredValues[i][index];}
public double printStoredIndexR1(int i){return StoredValues[i][0];}
public double printStoredIndexR2(int i){return StoredValues[i][1];}
 public  void CloseFiles()
{
out.close();System.runFinalization();
}

}
