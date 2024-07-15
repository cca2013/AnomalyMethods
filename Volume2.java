/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */

public class Volume2 {
    int switch_rc=1;
    vector rc_local = new vector(0.0, 0.0, 0.0);
    vector rc_pos = new vector(0.0, 0.0, 0.0);
    vector centroid_local= new vector(0.0,0.0,0.0);
    vector centroid_pos= new vector(0.0,0.0,0.0);
    vector target_facet_rc_local= new vector(0.0,0.0,0.0);
    vector target_facet_rc_pos= new vector(0.0,0.0,0.0); 
    
    
    Data data=new Data();  
    int target=data.getTargetModel();
    int facetA,facetB,facetC,vertexA,vertexB,vertexC,edgeA,edgeB,edgeC;
    int branch1=0,branch2=0;int obs;
    double facet_totals[][];
    double[][] Edge=data.getEdge_Struct();
    
    PrintWriter printWriter;
    utility util;
    
  
  
  public Volume2()
  {
        try {
            this.printWriter = new PrintWriter ("file.txt");
            util=new utility(printWriter);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Volume1.class.getName()).log(Level.SEVERE, null, ex);
        }
   centroid_local=data.getCentroid();
   target_facet_rc_local=data.getRCos();
   
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
     else 
            vectorfacetarea= doTriangleArea(facetIndex);
     //calculate area and normal for this facet
     DoubleFacetArea += vector.magnitude(vectorfacetarea);
     VectorFacetArea.addVec(vectorfacetarea);
    }
     //merge edge1(16) with edge(32)
     data.mergeEdgeStructures();
     data.ComputeEdge_Intrinsic();
  }
   
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
     }
   
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
{
    int adjacent_edge=0;
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
     vector Sigmab         =   new vector(0.0, 0.0, 0.0);
     vector Sigmab1         =   new vector(0.0, 0.0, 0.0);
     double suma=0.0;
     
     int vertex_1=0; int vertex_2=0,facet=0;
     double h=0.0,h1=0.0;
     double rm_ij=0.0;
     double v=0.0,v1=0.0;double L=0.0;
     double Cij=0.0,Ai=0.0,Ai1=0.0,Cij_new=0,Cij_new1=0;
     int facet2,facet1;
     double norm_Robs1_len,norm_Robs2_len,r0_len,max_term,l1,l2,eta,surface_ij=0.0,surface1_ij=0.0;
     double sum_hC[] = new double[data.getPolyFacets()],sum_half_Omega_bar[]=new double[data.getPolyFacets()];
     double Solid_Angle,surfaceArctanOffsetAsterisk; 
     
//obs loop
for(obs=0;obs<data.getNumberObs();obs++)
{
  Cij=0.0;
  Sigmab         =   new vector(0.0, 0.0, 0.0);
  Sigmab1         =   new vector(0.0, 0.0, 0.0);
  Cij_new=0.0;
  double vertex_extrinsic_quantities [][];

facet_totals=new double[data.getPolyFacets()][4];
vertex_extrinsic_quantities=new double[data.getVertices()][4];

//compute position vectors for target centroids for obs
//target centroid position vector
centroid_pos= new vector(vector.subVec(data.getCentroid(),data.getObs(obs)));
//1st facet's 1st vertex for target
target_facet_rc_pos= new vector(vector.subVec(data.getRCos(),data.getObs(obs)));

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

//switch for vector rc 2 cases:1.centroid,2.1stfacet-1st vertex
switch (switch_rc) {
    case 1:{rc_local=centroid_local;rc_pos=centroid_pos;};
    case 2:rc_local=target_facet_rc_local;rc_pos=target_facet_rc_pos;
                   }
//edge loop undirected edge
for (int edge_count=0;edge_count<data.getEdges()/2;edge_count++)
{
  double Lamda=0.0,Robs1_len,Robs2_len,atanh=0.0,atanh1=0.0,atan=0.0,atan1=0.0,lamda=0.0,lamda1=0.0,lamda_dash=0.0,lamda_dash1=0.0;
  double b=0.0,b1=0.0,b_h=0.0,b_h1=0.0,b_n=0.0,b_n1=0.0,cij=0.0;double r1_r2,r_dash; 
    vector vector_b= new vector(0.0, 0.0, 0.0);
    vector vector_b1= new vector(0.0, 0.0, 0.0);
    vector vector_b_dash= new vector(0.0, 0.0, 0.0);
    vector[][] vector_b_double_dash=new vector[3][3];
    vector vh = new vector(0.0, 0.0, 0.0);
    vector vt = new vector(0.0, 0.0, 0.0);
    vector vh1 = new vector(0.0, 0.0, 0.0);
    vector ni_curl = new vector(0.0, 0.0, 0.0);
    vector ni_curl1 = new vector(0.0, 0.0, 0.0);
      
  //retrieve adjacent facets
  facet1=(int)Edge[edge_count][18];
  facet2=(int)Edge[edge_count][19];
  //retrieve L 
  L=Edge[edge_count][20];
  
   //find adjacent edge
   for(int not_euler=data.getEdges()/2;not_euler<Edge.length;not_euler++)
   if(((int)Edge[not_euler][18]==facet2)&&(((int)Edge[not_euler][19]==facet1)))
     adjacent_edge=not_euler;
      
   //retrieve vector horizontal
   vh.x=Edge[edge_count][12];vh.y=Edge[edge_count][13];vh.z=Edge[edge_count][14];
   vh1.x=Edge[adjacent_edge][12];vh1.y=Edge[adjacent_edge][13];vh1.z=Edge[adjacent_edge][14];

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
  h=vector.dot(vh,Robs1_bar);
  h1=vector.dot(vh1,Robs1_bar);
  // counter.addA("E13",3);counter.addB("E13",2);
  r1_r2=Robs1_len+Robs2_len;
  r_dash=(Robs1_len+Robs2_len)/2;
  Lamda=Edge[edge_count][20]/r1_r2;
  lamda_dash=(r1_r2-L*Lamda)/2; 
  
  
  lamda=h*Lamda/(lamda_dash+Math.abs(v));
  lamda=h*Lamda/(r_dash*(1-Lamda*Lamda)+Math.abs(v));
  lamda1=h1*Lamda/(lamda_dash+Math.abs(v1));
  lamda1=h1*Lamda/(r_dash*(1-Lamda*Lamda)+Math.abs(v1));

 //--------------------------------------------------
 //start towards volume research quantities
 //implementation Barcelona (4) for bij
  //get normals
  ni=data.getNormal(facet1);
  vector ni1=data.getNormal(facet2);
   
  ni_curl=vector.mulScalar(ni, math.signum(v));
   ni_curl1=vector.mulScalar(ni1, math.signum(v1));
   
    //h term
   vector vh2ArctanL=vector.mulScalar(vh,math.AtanH_D(Lamda));
   vector vh2ArctanL_1=vector.mulScalar(vh1,math.AtanH_D(Lamda));
   //n term
   vector bn=vector.mulScalar(ni_curl, Math.atan(lamda));
   //util.printDouble(vector.dot(bn, Robs1_bar), "suma");
   vector bn1=vector.mulScalar(ni_curl1, Math.atan(lamda1));
   vector_b =vector.subVec(vh2ArctanL, bn);
   
   //accumulate for facet 1
   b=vector.dot(vector_b, Robs1_bar);
   facet_totals[facet1][0] += b;
   Sigmab=vector.addVec( Sigmab,vector_b);
   //facet 2 vector b
   vector_b=vector.subVec(vh2ArctanL_1, bn1);
   //accumulate for adjucent facet
    b1=vector.dot(vector_b, Robs1_bar);
   facet_totals[facet2][0] += b1;
   Sigmab=vector.addVec(Sigmab, vector_b);
   
   
}// end edge loop

Cij=0.0;

//Line facet post-edge cij accumulation
for (int i=0;i<data.getPolyFacets();i++)
{double x,y,z;
v=facet_totals[i][1];
//Cij += -2*facet_totals[i][0];
Cij += v*facet_totals[i][0];
}

//System.out.println("obs :"+(obs+1));
System.out.println("Total cij for obs :"+(obs+1)+"  = "+Cij);

}// end obs loop

}
  
 public static void main(String[] args)
{
    Volume2 m = new Volume2();
    m.Facet_Loop();
    m.Extrinsic_Loop();
  } 
  
  
}
