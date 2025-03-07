
package loop;
public class VertexDirectedEdgeLoopModified {
  Data data=new Data();
  utility util;
  int target=2;
  int facetA,facetB,facetC,vertexA,vertexB,vertexC,edgeA,edgeB,edgeC;


  double[][] Edge=data.getEdge_Struct();

  public VertexDirectedEdgeLoopModified() {}


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
     vector Robs1_bar    	   =   new vector(0.0,0.0,0.0);
     vector Robs2_bar    	   =   new vector(0.0,0.0,0.0);
     vector t_hat   	   =   new vector(0.0,0.0,0.0);
     vector h_bar  	   =   new vector(0.0,0.0,0.0);
     vector Robs_ij    	   =   new vector(0.0,0.0,0.0);
     vector R_ij  	   =   new vector(0.0,0.0,0.0);
     vector ni  	   =   new vector(0.0,0.0,0.0);
     vector vector_r0  	   =   new vector(0.0,0.0,0.0);
    
     int vertex_1=0; int vertex_2=0,facet=0;
     double h=0.0;
     double v=0.0;double L=0.0;
     double Cij=0.0;
     int facet2,facet1;

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
   R_ij=data.getVertex(vertex);
   Robs_ij=vector.subVec(R_ij,data.getObs(obs));
   //vertexB+=3;
   counter.addB("E11",3);
   vertex_extrinsic_quantities[vertex][0]=Robs_ij.x;vertex_extrinsic_quantities[vertex][1]=Robs_ij.y;vertex_extrinsic_quantities[vertex][2]=Robs_ij.z;
   vertex_extrinsic_quantities[vertex][3]=vector.magnitude(Robs_ij);
   //vertexA+=3;vertexB+=2;vertexC+=1;
   counter.addA("E11",3);counter.addB("E11",2);counter.addC("E11",1);
 }

//facet pre-edge calculations
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
//edge loop directed edge
for (int edge_count=0;edge_count<data.getEdges();edge_count++)
{
  double Lamda=0.0,Robs1_len,Robs2_len,atanh=0.0,atan=0.0,lamda=0.0,lamda_dash=0.0;
  double b_h=0.0,b_n=0.0,cij=0.0;double r1_r2;
  double norm_Robs1_len,norm_Robs2_len,r0_len,max_term,l1,l2,eta,vertex_ij,r1_l1,r2_l2,r1l1 = 0,r2l2 = 0;
  //retrieve facets
  facet1=(int)Edge[edge_count][18];
  facet2=(int)Edge[edge_count][19];
  
  //retrieve v component
  v=facet_totals[facet1][1];
//System.out.println("facet1 = "+facet1+"   "+"facet 2= "+facet2);
  //retrieve vector R1,R2(local vertices coordinates) as r1,r2
  r1.x=Edge[edge_count][0];r1.y=Edge[edge_count][1];r1.z=Edge[edge_count][2];
  r2.x=Edge[edge_count][3];r2.y=Edge[edge_count][4];r2.z=Edge[edge_count][5];
  vertex_1=data.getVertexIndex(r1);
  vertex_2=data.getVertexIndex(r2);

  //retrieve vectors Robs as Robs1_bar,Robs2_bar
  Robs1_bar.x=vertex_extrinsic_quantities[vertex_1][0];
  Robs1_bar.y=vertex_extrinsic_quantities[vertex_1][1];
  Robs1_bar.z=vertex_extrinsic_quantities[vertex_1][2];
  Robs2_bar.x=vertex_extrinsic_quantities[vertex_2][0];
  Robs2_bar.y=vertex_extrinsic_quantities[vertex_2][1];
  Robs2_bar.z=vertex_extrinsic_quantities[vertex_2][2];

  //retrieve vector unit tangent as t_hat
  t_hat.x=Edge[edge_count][9];t_hat.y=Edge[edge_count][10];t_hat.z=Edge[edge_count][11];
  //Retrieve lengths Robs1,Robs2 from the extrinsic vertex structure
  Robs1_len=vertex_extrinsic_quantities[vertex_1][3];
  Robs2_len=vertex_extrinsic_quantities[vertex_2][3];
  
  //Vertex: Normalize position vector lengths
  norm_Robs1_len=Math.sqrt(Robs1_len*Robs1_len);
  norm_Robs2_len=Math.sqrt(Robs2_len*Robs2_len);   
  counter.addA("E13",2);counter.addC("E13",2);
  
  
  //Vertex: cases norm Robs
  if (norm_Robs1_len<norm_Robs2_len)
        vector_r0=vector.cross(Robs1_bar, t_hat);
  else
        vector_r0=vector.cross(Robs2_bar, t_hat);
  counter.addA("E13",3);counter.addB("E13",3);
  //Vertex:compute l1,l2
  l1=vector.dot(Robs1_bar, t_hat); l2=vector.dot(Robs2_bar, t_hat);
  counter.addA("E13",3);counter.addB("E13",2);
  //Vertex: compute magnitude vector r0
  r0_len=vector.magnitude(vector_r0);
  counter.addA("E13",3);counter.addB("E13",2);counter.addC("E13",1);
  
  //Vertex:compute vertex max term
  max_term=data.getUtilitythread().max(Robs1_len+Math.abs(l1), Robs2_len+Math.abs(l2));
  counter.addB("E13",2);counter.addC("E13",3);
  
  //Vertex:compute h
  h_bar.x=Edge[edge_count][12];h_bar.y=Edge[edge_count][13]; h_bar.z=Edge[edge_count][14];
  h=vector.dot(h_bar,Robs1_bar);
  counter.addA("E13",3);counter.addB("E13",2);

  //Vertex:compute eta and start switch branch
  eta=r0_len/max_term;
  counter.addA("E13",1);
  r1_l1=Robs1_len-l1;r2_l2=Robs2_len-l2;
  if (eta==0)vertex_ij=0.0D;
  else
{//sign check

 if ( (l1 >= 0) || (l2 <= 0)) //case l1>=0 and l2>=0(we know l2 >l1)
//or
//l1<0 and l2<=0
{
    //b_h=Math.log((Robs2_len+l2)/(Robs1_len+l1));
    //b_h = math.signum(l1 + l2) * h *Math.log( (Robs2_len + Math.abs(l2)) / (Robs1_len + Math.abs(l1)));
counter.addA("E13",3);counter.addB("E13",3);counter.addC("E13",4);
b_h=-h*Math.log((r2_l2)/(r1_l1));  

//System.out.println("branch 1 :");

}  
 else
//case l1<0  and l2>0
{r1l1=Robs1_len+l1;
     r2l2=Robs2_len+l2;
    b_h=h*Math.log((r2l2)/(r1l1));
   // b_h = h * Math.log( (Robs1_len + Math.abs(l1)) * (Robs2_len + Math.abs(l2)) / vector.dot(vector_r0, vector_r0));
 counter.addA("E13",6);counter.addB("E13",4);counter.addC("E13",3);  
 //System.out.println("branch 2 :");
} 

}//end else eta 
  
//Compute  b_n term
    b_n            = ( (Math.atan2(l2, h)) - Math.atan2(l1, h) -
(Math.atan2( (l2 * Math.abs(v)), (h * Robs2_len)) -Math.atan2( (l1 * Math.abs(v)), (h * Robs1_len))));
  counter.addA("E13",4);counter.addB("E13",3);counter.addC("E13",6);
    //b_n=Math.atan2(r2l2+v, h)-Math.atan2(r1l1+v, h);
 //b_n=Math.atan2(r2_l2+v, h)+Math.atan2(r1_l1+v, h);
  //b_n=-Math.atan2(r2_l2+v, h)+Math.atan2(r1_l1+v, h);
  //b_n=Math.atan2(r2l2+v, h)-Math.atan2(r1l1+v, h);
   b_n        = -Math.abs(v) *b_n;
 counter.addA("E13",1);counter.addC("E13",1);
 
 vertex_ij=b_h+b_n;
  //counter.addB("E13",1);
 

 facet_totals[facet1][0] += vertex_ij;
 counter.addB("E13",1);
   //System.out.println("Edge count :"+edge_count);
}// end edge loop

//facet post-edge cij accumulation
 //double Fb_n=0.0;
for (int i=0;i<data.getPolyFacets();i++)
{double x,y,z;
v=facet_totals[i][1];
 // Fb_n+=facet_totals[i][2];

Cij += v*facet_totals[i][0]/2;

 counter.addA("E14",2);counter.addB("E14",1);
}

 // System.out.println(" Obs total b_n*2  :"+Fb_n);

System.out.println("Total cij for obs :"+(obs+1)+"  = "+Cij);
}// end obs loop
counter.PrintVEF();
  counter.PrintCountAnalytics();
  counter.PrintCountTotals();
  counter.PrintEulerCountAnalytics();
}

public static void main(String[] args)
{
    VertexDirectedEdgeLoopModified m = new VertexDirectedEdgeLoopModified();
    m.Facet_Loop();
    m.Extrinsic_Loop();

  }


}