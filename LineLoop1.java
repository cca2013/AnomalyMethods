
package loop;
public class LineLoop1 {
  Data data=new Data();
  int target=1;
  int facetA,facetB,facetC,vertexA,vertexB,vertexC,edgeA,edgeB,edgeC;


  double[][] Edge=data.getEdge_Struct();

  public LineLoop1() {}


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
   vertexB+=3;
   counter.addB("E11",3);
   vertex_extrinsic_quantities[vertex][0]=Robs_ij.x;vertex_extrinsic_quantities[vertex][1]=Robs_ij.y;vertex_extrinsic_quantities[vertex][2]=Robs_ij.z;
   vertex_extrinsic_quantities[vertex][3]=vector.magnitude(Robs_ij);
   vertexA+=3;vertexB+=2;vertexC+=1;
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
}
//edge loop directed edge
for (int edge_count=0;edge_count<data.getEdges();edge_count++)
{
  double Lamda=0.0,Robs1_len,Robs2_len,atanh=0.0,atan=0.0,lamda=0.0,lamda_dash=0.0;
  double b_h=0.0,b_n=0.0,cij=0.0;double r1_r2;

  //retrieve facets
  facet1=(int)Edge[edge_count][18];
  facet2=(int)Edge[edge_count][19];

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

  //retrieve unit tangent as t_hat
  t_hat.x=Edge[edge_count][9];t_hat.y=Edge[edge_count][10];t_hat.z=Edge[edge_count][11];
  //Retrieve lengths Robs1,Robs2 from the extrinsic vertex structure
  Robs1_len=vertex_extrinsic_quantities[vertex_1][3];
  Robs2_len=vertex_extrinsic_quantities[vertex_2][3];
  //retrieve the edge length
  L=Edge[edge_count][20];
  
//start an Euler count undirected edge
 if(edge_count<data.getEdges()/2)
{
 r1_r2=Robs1_len+Robs2_len;
 counter.addB("E131",1);
 
    //PICK adjacent edge
    for(int not_euler=data.getEdges()/2;not_euler<Edge.length;not_euler++)
        if(((int)Edge[not_euler][18]==facet2)&&(((int)Edge[not_euler][19]==facet1)))
            adjacent_edge=not_euler; 
  //compute Lamda
  Lamda=Edge[edge_count][20]/r1_r2;

  counter.addA("E131",1);
  //retrieve v
  v=facet_totals[facet1][1];
  //compute lamda_dash
  lamda_dash=(r1_r2-L*Lamda)/2;
  counter.addA("E131",2);counter.addB("E131",1);
  try{
  atanh=math.AtanH_D(Lamda);
  counter.addC("E131",1);}
  catch(ArctanException arcException){}
  //store Lamda
  //Euler
  Edge[edge_count][21]=Lamda;
  Edge[edge_count][22]=atanh;
  Edge[edge_count][23]=r1_r2;
  Edge[edge_count][24]=lamda_dash;
  //store to adjacent edge as -Lamda
  Edge[adjacent_edge][21]=-Lamda;
  Edge[adjacent_edge][22]=-atanh;
  Edge[adjacent_edge][23]=r1_r2;
  Edge[adjacent_edge][24]=-lamda_dash;

 }//end if of Euler count
else
 {
   for(int euler=0;euler<Edge.length/2;euler++)
      if(((int)Edge[euler][18]==facet2)&&(((int)Edge[euler][19]==facet1)))
        adjacent_edge=euler;
        Lamda=Edge[adjacent_edge][21];
        atanh=Edge[adjacent_edge][22];
        r1_r2=Edge[adjacent_edge][23];
        lamda_dash=Edge[adjacent_edge][24];
      }
//compute v,h,lamda, atan(lamda), b_h,b_n,
 h_bar.x=Edge[edge_count][12];h_bar.y=Edge[edge_count][13];h_bar.z=Edge[edge_count][14];
 h=vector.dot(h_bar,Robs1_bar);
 counter.addA("E13",3);counter.addB("E13",2);
 //ni=data.getNormal(facet1);
 //retrieve v
 v=facet_totals[facet1][1];

//compute lamda
 lamda=h*Lamda/(lamda_dash+Math.abs(v));
 counter.addA("E13",2);counter.addB("E13",1);
 atan=Math.atan(lamda);
counter.addC("E13",1);
//compute b_h
 b_h=h*atanh;
counter.addA("E13",1);
 //compute b_n
 b_n=-Math.abs(v)*atan;
counter.addA("E13",1);
 //accumulate cij to facet level
 facet_totals[facet1][0] += (b_h+b_n);
 counter.addB("E13",2);
   //System.out.println("Edge count :"+edge_count);
}// end edge loop

//facet post-edge cij accumulation
 double Fb_n=0.0;
for (int i=0;i<data.getPolyFacets();i++)
{double x,y,z;
v=facet_totals[i][1];
 // Fb_n+=facet_totals[i][2];

Cij += v*facet_totals[i][0];

 counter.addA("E14",1);counter.addB("E14",1);
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
    LineLoop1 m = new LineLoop1();
    m.Facet_Loop();
    m.Extrinsic_Loop();

  }


}