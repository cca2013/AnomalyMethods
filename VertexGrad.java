
package loop;
public class VertexGrad {
  Data data=new Data();
  int target=1;
  int facetA,facetB,facetC,vertexA,vertexB,vertexC,edgeA,edgeB,edgeC;
  double[][] Edge=data.getEdge_Struct();
  int branch1=0,branch2=0;
  vector facetVertexBij=new vector(0.0,0.0,0.0);
  double [][] GradVertex;
  
  
  public VertexGrad() {
  
  }

public Data getData(){return data;}

  public  void Facet_Loop()
  {
    int facetIndex = 0;
    vector VectorFacetArea = new vector(0.0, 0.0, 0.0);
    double DoubleFacetArea = 0.0;

    for (facetIndex = 0; facetIndex < data.getPolyFacets(); facetIndex++) {
      vector vectorfacetarea = new vector(0.0, 0.0, 0.0);
      if (target==1)
      vectorfacetarea = ComputeFacet(facetIndex);
     else vectorfacetarea= doTriangleArea(facetIndex);
//data.getUtilitythread().printVector(vectorfacetarea,"facet: "+facetIndex);
         //calculate area and normal for this facet
      DoubleFacetArea += vector.magnitude(vectorfacetarea);
      VectorFacetArea.addVec(vectorfacetarea);
    }

     //merge edge1(16) with edge(32)
     data.mergeEdgeStructures();
     data.ComputeEdge_Intrinsic();

  }

//Facet area calculation(loop 2)
  public vector ComputeFacet(int fact)
  {
     vector NewFacetArea=    new vector(0.0,0.0,0.0);

//initialize vectors of edge vertices with the first triplet of
//primitives.

     vector last    	   =      new vector(0.0,0.0,0.0);
     vector ni             =      new vector(0.0,0.0,0.0);
     vector oldTan   	   =      new vector(0.0,0.0,0.0);
     vector lastTan   	   =      new vector(0.0,0.0,0.0);
     vector TriangleArea   =      new vector(0.0,0.0,0.0);
     vector start    	   =      new vector(data.getPolyVertex(fact,1));
     vector old      	   =      new vector( data.getPolyVertex(fact,2));


//check for storing starting edge V1,V2 where 1=start,2=old
    data.CreateEdgeStructure(start,old,fact);

//compute the first tangent
oldTan=vector.subVec(old,start);
// Fabc(0,3,0);intrinsic

     //start looping from  index of last
     for(int j=3;j<=data.getFacetEdges(fact);j++)
     {
             // calculate last
             last=  data.getPolyVertex(fact,j);
             //create tangents
             oldTan=vector.subVec(old,start);
             lastTan=vector.subVec(last,old);
              // Fabc(0,6,0);intrinsic
             //calculate triangle area
             TriangleArea=oldTan.cross(oldTan,lastTan);//calculate triangular area
               //Fabc(3,3,0);intrinsic
             //accumulate triangular areas
             NewFacetArea=vector.addVec(NewFacetArea,TriangleArea);
              // Fabc(0,3,0);intrinsic

             ////check for storing this edge V1,V2 where 1=old,2=last
            data.CreateEdgeStructure(old,last,fact);

               //preserve last to the old variable
             old.set(data.getPolyVertex(fact,j));
     }
     //check for storing facet closure edge V1,V2 where 1=old,2=start
     data.CreateEdgeStructure(old,start,fact);

     //calculate normal and store it to an array for each facet
     ni=vector.divScalar(NewFacetArea,vector.magnitude(NewFacetArea));
     //data.getUtilitythread().printVector(ni,"n"+fact);

     // Fabc(3,0,0);intrinsic
     data.setNormal(ni,fact);//store Facet normal in position of facet index
     data.setArea(NewFacetArea,fact);//post Facetnormal in position of facet

     //print edges

     //return total facet area
     return NewFacetArea;
     }//end of doFacetsArea loop 3
     public vector doTriangleArea(int fact)
             {

             vector normal   	      =      new vector(0.0,0.0,0.0);
             vector oldTan   	      =      new vector(0.0,0.0,0.0);
             vector lastTan   	      =      new vector(0.0,0.0,0.0);
             vector TriangleArea       =      new vector(0.0,0.0,0.0);
             //set 3 vertices for triangle
             vector start    	       =      new vector(data.getPolyVertex(fact,1));
             vector old      	       =      new vector(data.getPolyVertex(fact,2));
             vector last    	       =      new vector(data.getPolyVertex(fact,3));


             data.CreateEdgeStructure(start,old,fact);
             data.CreateEdgeStructure(old,last,fact);
             data.CreateEdgeStructure(last,start,fact);
             //create tangents
             oldTan=vector.subVec(old,start);
             lastTan=vector.subVec(last,start);
              // Fabc(0,6,0);intrinsic
             //calculate triangle area
             TriangleArea=vector.cross(oldTan,lastTan);
               //Fabc(3,3,0);intrinsic

//calculate normal and store it to an array for each facet
         normal=vector.divScalar(TriangleArea,vector.magnitude(TriangleArea));
              // Fabc(3,0,0);intrinsic
          //data.getUtilitythread().printVector(normal,"normal: "+fact);
         data.setNormal(normal,fact); data.setArea(TriangleArea,fact);


//return total facet area
             return TriangleArea;
             }



public void Extrinsic_Loop()
{int adjacent_edge=0;
     vector r1   	   =   new vector(0.0,0.0,0.0);
     vector r2   	   =   new vector(0.0,0.0,0.0);
     vector Robs1_bar      =   new vector(0.0,0.0,0.0);
     vector Robs2_bar      =   new vector(0.0,0.0,0.0);
     vector t_hat   	   =   new vector(0.0,0.0,0.0);
     vector t_hat_adj      =   new vector(0.0,0.0,0.0);
     vector h_bar  	   =   new vector(0.0,0.0,0.0);
     vector Robs_ij    	   =   new vector(0.0,0.0,0.0);
     vector R_ij  	   =   new vector(0.0,0.0,0.0);
     vector ni  	   =   new vector(0.0,0.0,0.0);
     vector vector_r0  	   =   new vector(0.0,0.0,0.0);
     
     
     int vertex_1=0; int vertex_2=0,facet=0;
     double h=0.0,h1=0.0;
     double v=0.0,v1=0.0;double L=0.0;
     double Cij=0.0;
     int facet2,facet1;
     double norm_Robs1_len,norm_Robs2_len,r0_len,max_term,l1,l2,eta,vertex_ij,vertex1_ij;
     double sum_hC[] = new double[data.getPolyFacets()],sum_half_Omega_bar[]=new double[data.getPolyFacets()];
     vector bijLogVertex   = new vector (0.0D,0.0D,0.0D);
     vector bijArctanVertex= new vector (0.0D,0.0D,0.0D);
     vector vh   = new vector (0.0D,0.0D,0.0D);
     vector vh1= new vector (0.0D,0.0D,0.0D);
     vector vuh   = new vector (0.0D,0.0D,0.0D);
     vector vuh1= new vector (0.0D,0.0D,0.0D);
     vector vun= new vector (0.0D,0.0D,0.0D);
     vector vun1= new vector (0.0D,0.0D,0.0D);
//obs loop
for(int obs=0;obs<data.getNumberObs();obs++)
{
  Cij=0.0;
double vertex_extrinsic_quantities [][];
double facet_totals[][];
facet_totals=new double[data.getPolyFacets()][4];
vertex_extrinsic_quantities=new double[data.getVertices()][4];


//vertex loop
for(int vertex=0;vertex<data.getVertices();vertex++)
  {
   //Vertex:vertex:get local vector data
   R_ij=data.getVertex(vertex);
   //Vertex:vertex:compute position vector
   Robs_ij=vector.subVec(R_ij,data.getObs(obs));
   //vertexB+=3;
   counter.addB("E11",3);
   vertex_extrinsic_quantities[vertex][0]=Robs_ij.x;vertex_extrinsic_quantities[vertex][1]=Robs_ij.y;vertex_extrinsic_quantities[vertex][2]=Robs_ij.z;
   vertex_extrinsic_quantities[vertex][3]=vector.magnitude(Robs_ij);
   //vertexA+=3;vertexB+=2;vertexC+=1;
   counter.addA("E11",3);counter.addB("E11",2);counter.addC("E11",1);
 }


//Vertex:facet pre-edge calculations
for (int i=0;i<data.getPolyFacets();i++)
{int index;
 //Compute v
 //retrieve normal
 ni=data.getNormal(i);
 //retrieve facet's first vertex index (index=1)
 index=data.getFacetVertex_index(i,1);
 Robs1_bar.x=vertex_extrinsic_quantities[index][0];
 Robs1_bar.y=vertex_extrinsic_quantities[index][1];
 Robs1_bar.z=vertex_extrinsic_quantities[index][2];
 v=vector.dot(ni,Robs1_bar);
 counter.addA("E12",3);counter.addB("E12",2);
 facet_totals[i][1]=v;
//System.out.println("v= "+v);
}

//edge loop undirected edge
for (int edge_count=0;edge_count<data.getEdges()/2;edge_count++)
{
  double Lamda=0.0,Robs1_len,Robs2_len,atanh=0.0,atan=0.0,lamda=0.0,lamda_dash=0.0;
  double b=0.0,b1=0.0,b_h=0.0,b_h1=0.0,b_n=0.0,b_n2=0.0,b_n1=0.0,b_n11=0.0,cij=0.0;double r1_r2; 
   
  //retrieve facet1 and adjacent facet2
  facet1=(int)Edge[edge_count][18];
  facet2=(int)Edge[edge_count][19];
  //retrieve L 
  L=Edge[edge_count][20];
  
  
   //find adjacent edge
   for(int not_euler=data.getEdges()/2;not_euler<Edge.length;not_euler++)
   if(((int)Edge[not_euler][18]==facet2)&&(((int)Edge[not_euler][19]==facet1)))
     adjacent_edge=not_euler;
   
   
  //retrieve vector R1,R2(local vertices coordinates) as r1,r2
  r1.x=Edge[edge_count][0];r1.y=Edge[edge_count][1];r1.z=Edge[edge_count][2];
  r2.x=Edge[edge_count][3];r2.y=Edge[edge_count][4];r2.z=Edge[edge_count][5];
  vertex_1=data.getVertexIndex(r1);
  vertex_2=data.getVertexIndex(r2);
  
  //restore v for adjacent facets
   v=facet_totals[facet1][1];
   v1=facet_totals[facet2][1];
  //retrieve vectors Robs as Robs1_bar,Robs2_bar
  Robs1_bar.x=vertex_extrinsic_quantities[vertex_1][0];
  Robs1_bar.y=vertex_extrinsic_quantities[vertex_1][1];
  Robs1_bar.z=vertex_extrinsic_quantities[vertex_1][2];
  Robs2_bar.x=vertex_extrinsic_quantities[vertex_2][0];
  Robs2_bar.y=vertex_extrinsic_quantities[vertex_2][1];
  Robs2_bar.z=vertex_extrinsic_quantities[vertex_2][2];

  //retrieve unit tangent as t_hat
  t_hat.x=Edge[edge_count][9];t_hat.y=Edge[edge_count][10];t_hat.z=Edge[edge_count][11];
  t_hat_adj.x=Edge[adjacent_edge][9];t_hat_adj.y=Edge[adjacent_edge][10];t_hat_adj.z=Edge[adjacent_edge][11];
  //Retrieve lengths Robs1,Robs2 from the extrinsic vertex structure
  Robs1_len=vertex_extrinsic_quantities[vertex_1][3];
  Robs2_len=vertex_extrinsic_quantities[vertex_2][3];
 
  //Vertex: Normalize position vector lengths
  norm_Robs1_len=Math.sqrt(Robs1_len*Robs1_len);
  norm_Robs2_len=Math.sqrt(Robs2_len*Robs2_len);   
  counter.addA("E13",2);counter.addC("E13",2);
  //calculate vh
    vh=vector.cross(t_hat,data.getNormal(facet1));//to be transfered to intrinsic
    if( data.getUtilitythread().getTestMode()==1)data.getUtilitythread().printVector(vh,"vh= vector horizontal               :");
      //getFacet.Eabc(3,3,0);
    vuh=vector.divScalar(vh,vector.magnitude(vh));//to be transfered to intrinsic
    if( data.getUtilitythread().getTestMode()==1)data.getUtilitythread().printVector(vuh,"vuh= unit vector horizontal        :");
      //getFacet.Eabc(3,0,0);
   //Vertex: computing cases for norm Robs
  if (norm_Robs1_len<norm_Robs2_len)
        vector_r0=vector.cross(Robs1_bar, t_hat);
  else
        vector_r0=vector.cross(Robs2_bar, t_hat);
  counter.addA("E13",3);counter.addB("E13",3);

  l2=vector.dot(Robs2_bar, t_hat);l1=l2-L;
  counter.addA("E13",3);counter.addB("E13",3);
  

//Vertex: compute magnitude vector r0
r0_len=vector.magnitude(vector_r0);
counter.addA("E13",3);counter.addB("E13",2);counter.addC("E13",1);
  
 //Vertex:compute vertex max term
max_term=data.getUtilitythread().max(Robs1_len+Math.abs(l1), Robs2_len+Math.abs(l2));
counter.addB("E13",2);counter.addC("E13",2);
  
//Vertex:compute vh
h_bar.x=Edge[edge_count][12];h_bar.y=Edge[edge_count][13]; h_bar.z=Edge[edge_count][14];
vuh=vector.divScalar(h_bar,vector.magnitude(h_bar));
h=vector.dot(h_bar,Robs1_bar);
counter.addA("E13",3);counter.addB("E13",2);
//adjacent edge
h_bar.x=Edge[adjacent_edge][12];h_bar.y=Edge[adjacent_edge][13]; h_bar.z=Edge[adjacent_edge][14];
vuh1=vector.divScalar(h_bar,vector.magnitude(h_bar));
h1=vector.dot(h_bar,Robs1_bar);
counter.addA("E13",3);counter.addB("E13",2);


 //Vertex:compute eta and start switch branch
 eta=max_term/r0_len;
 counter.addA("E13",1);
 
 if (eta==0)vertex_ij=0.0D;
 else
{//sign check

 if ( (l1 >= 0) || (l2 <= 0)) //case l1>=0 and l2>=0(we know l2 >l1)
//or
//l1<0 and l2<=0
{
branch1++;
//b_h=Math.log((Robs2_len+l2)/(Robs1_len+l1));
b = Math.log( (Robs2_len + Math.abs(l2))  / (Robs1_len + Math.abs(l1)));
counter.addA("E13",2);counter.addB("E13",3);counter.addC("E13",4);
}  
 else
//case l1<0  and l2>0
{ branch2++;
b =  Math.log( (Robs1_len + Math.abs(l1))* (Robs2_len + Math.abs(l2))  / vector.dot(vector_r0, vector_r0));
counter.addA("E13",5);counter.addB("E13",4);counter.addC("E13",3);  
} 
//Compute vector edge gradient b_h 
 bijLogVertex      =  vector.mulScalar(vuh,b);
 bijLogVertex      = vector.addVec(vector.mulScalar(vuh1,b),bijLogVertex);

//Compute  b_n term
b_n            = ( (Math.atan2(l2, h)) - Math.atan2(l1, h) -
(Math.atan2( (l2 * Math.abs(v)), (h * Robs2_len)) -Math.atan2( (l1 * Math.abs(v)), (h * Robs1_len))));
counter.addA("E13",4);counter.addB("E13",3);counter.addC("E13",6);
//Compute  b_n1 term for adjacent edge
b_n1            = ( (Math.atan2(l2, h1)) - Math.atan2(l1, h1) -(Math.atan2( (l2 * Math.abs(v1)), (h1 * Robs2_len)) -Math.atan2( (l1 * Math.abs(v1)), (h1 * Robs1_len))));
counter.addA("E13",4);counter.addB("E13",3);counter.addC("E13",6);

//Compute vector edge gradient b_n
 bijArctanVertex   = vector.mulScalar(data.getNormal(facet1),math.signum(-1)*math.signum(v)*b_n);
 bijArctanVertex   = vector.addVec(vector.mulScalar(data.getNormal(facet2),math.signum(-1)*math.signum(v1)*b_n1),bijArctanVertex);
//Accumulate to vector edge gradient bij
 facetVertexBij = vector.addVec(vector.addVec(bijLogVertex,bijArctanVertex),facetVertexBij);
}
 
}// end edge loop
vector cij_field 	   =   new vector(0.0,0.0,0.0);
vector gradx =   new vector(0.0,0.0,0.0);  
vector grady =   new vector(0.0,0.0,0.0);  
vector gradz =   new vector(0.0,0.0,0.0);  
vector grad =   new vector(0.0,0.0,0.0); 

//facet post-edge cij accumulation
for (int i=0;i<data.getPolyFacets();i++)
{
     vector normal =   new vector(0.0,0.0,0.0);            
     normal=data.getNormal(i);
 // Accumulate edge gradient
     if (i==0)
        GradVertex=vector.MulVec(facetVertexBij, normal);
    else
        GradVertex=vector.addMatrix(vector.MulVec(facetVertexBij, normal),GradVertex);  
        counter.addA("E14",2);counter.addB("E14",1);
}//end post-edge facet loop

//print gradient matrix loop
for (int j = 0; j < 3; j++) 
{
       grad.x=GradVertex[j][0];
       grad.y=GradVertex[j][1];
       grad.z=GradVertex[j][2];
        data.getUtilitythread().printVector(grad,
            "Field Gradient Vertex                   :  " + String.valueOf(j));
}

//go to next obs point
}// end obs loop


//counter.PrintVEF();
 // counter.PrintCountAnalytics();
 // counter.PrintCountTotals();
 // counter.PrintEulerCountAnalytics();
}

public static void main(String[] args)
{
    VertexGrad m = new VertexGrad();
    m.Facet_Loop();
    m.Extrinsic_Loop();

  }
}