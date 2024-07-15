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

public class Volume {
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
    
  
  
  public Volume()
  {
        try {
            this.printWriter = new PrintWriter ("file.txt");
            util=new utility(printWriter);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Volume.class.getName()).log(Level.SEVERE, null, ex);
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
  Cij=0.0;Sigmab         =   new vector(0.0, 0.0, 0.0);Sigmab1         =   new vector(0.0, 0.0, 0.0);
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
  double b=0.0,b1=0.0,b_h=0.0,b_h1=0.0,b_n=0.0,b_n1=0.0,cij=0.0,c1ij=0.0;double r1_r2; 
  double TaylorLamda,Taylorlamda,Taylorlamda1,tworc,LamdaAsterisk,Denom1,Denom2,Delta1,Delta2,Delta,Offset,Offset1;
  double lamdaAsterisk,lamdaAsterisk1,sigma,lamdaCurl,lamdaCurl1,offset,offset1;
    
    vector vector_b= new vector(0.0, 0.0, 0.0);
    vector vector_b1= new vector(0.0, 0.0, 0.0);
    vector vector_b_dash= new vector(0.0, 0.0, 0.0);
   
    vector[][] vector_b_double_dash=new vector[3][3];
    
    vector vh = new vector(0.0, 0.0, 0.0);
    vector vt = new vector(0.0, 0.0, 0.0);
    vector vh1 = new vector(0.0, 0.0, 0.0);
    vector VrcMr1 = new vector(0.0, 0.0, 0.0);
    vector VrcMr2 = new vector(0.0, 0.0, 0.0);
    vector VrcPr1 = new vector(0.0, 0.0, 0.0);
    vector VrcPr2 = new vector(0.0, 0.0, 0.0);
    vector ni_curl = new vector(0.0, 0.0, 0.0);
    vector ni_curl1 = new vector(0.0, 0.0, 0.0);

//retrieve adjacent facets
  facet1=(int)Edge[edge_count][18];
  facet2=(int)Edge[edge_count][19];
if (edge_count==0){facet_totals[facet1][0]=0;facet_totals[facet2][0]=0;}
  //retrieve L 
  L=Edge[edge_count][20];
     
   //find adjacent edge
   for(int not_euler=data.getEdges()/2;not_euler<Edge.length;not_euler++)
   if(((int)Edge[not_euler][18]==facet2)&&(((int)Edge[not_euler][19]==facet1)))
     adjacent_edge=not_euler;
   
   //retrieve vector horizontal
   vh.x=Edge[edge_count][12];vh.y=Edge[edge_count][13];vh.z=Edge[edge_count][14];
   //retrieve vector tangent
   vt.x=Edge[edge_count][6];vt.y=Edge[edge_count][7];vt.z=Edge[edge_count][8];
   //~n = n sign(v)

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

//restore quantities vh,vh1 compute h,h1
vh.x=Edge[edge_count][12];vh.y=Edge[edge_count][13]; vh.z=Edge[edge_count][14];  
h=vector.dot(vh,Robs1_bar);
counter.addA("E13",3);counter.addB("E13",2);
vh1.x=Edge[adjacent_edge][12];vh1.y=Edge[adjacent_edge][13]; vh1.z=Edge[adjacent_edge][14];
h1=vector.dot(vh1,Robs1_bar);
counter.addA("E13",3);counter.addB("E13",2);

//Compute l1,l2
l1=vector.dot(vt, Robs1_bar);l2=vector.dot(vt, Robs2_bar);
  
//compute Lamda
r1_r2=Robs1_len+Robs2_len;
counter.addB("E13",1);
Lamda=L/r1_r2;
counter.addA("E13",1);
   
//start surface formula
    //compute term b_h
TaylorLamda = Math.pow(Lamda,3)*math.AtnH_D(Lamda)          ;
counter.addA("E13",1);counter.addC("E13",2);
b_h           = 2.0D * (h * TaylorLamda)                    ; 
b_h1          = 2.0D * (h1 * TaylorLamda)                   ;   
counter.addA("E13",2);
tworc           =  vector.magnitude(rc_pos)*2.0D            ;  
LamdaAsterisk     =  Math.abs(L)/tworc                      ;
counter.addA("E13",3);counter.addB("E13",2);counter.addC("E13",1);

//Compute Delta
VrcMr1         =vector.subVec(rc_local,r1)                  ;             
VrcMr2         =vector.subVec(rc_local,r2)                  ;                     
VrcPr1         =vector.addVec(rc_pos,Robs1_bar)             ;                 
VrcPr2         =vector.addVec(rc_pos,Robs2_bar)             ;   
counter.addB("E13",12);
Denom1         =tworc*(vector.magnitude(rc_pos)+Robs1_len)  ;
Denom2         =tworc*(vector.magnitude(rc_pos)+Robs2_len)  ;
counter.addA("E13",6);counter.addB("E13",4);counter.addC("E13",2);
Delta1         =vector.dot(VrcMr1,VrcPr1)/Denom1            ;               
Delta2         =vector.dot(VrcMr2,VrcPr2)/Denom2            ;
Delta          =Delta1+Delta2                               ;
counter.addA("E13",8);counter.addB("E13",5);

//Compute b_h offsets
Offset       =2*h*(Delta*Lamda)                             ;
Offset1       =2*h1*(Delta*Lamda)                           ;
counter.addA("E13",6);
 b_h  += Offset;
 b_h1 += Offset1;
 counter.addB("E13",2);
 
if(data.getTargetModel()==2 || data.getTargetModel()==3 )
{ 
    cij = b_h;
    c1ij = b_h1;
    surface_ij = cij;
    surface1_ij =c1ij;
    facet_totals[facet1][0] += surface_ij; 
    facet_totals[facet2][0] += surface1_ij;
    counter.addB("E13",2);
}
else
{
//compute term b_n,b_n1
//compute main b_n part
lamda             = h*Lamda/(((Robs1_len+Robs2_len-L*Lamda)/2)+Math.abs(v));
counter.addA("E13",3);counter.addB("E13",3);counter.addC("E13",0);
lamda1            = h1*Lamda/(((Robs1_len+Robs2_len-L*Lamda)/2)+Math.abs(v1));
counter.addA("E13",3);counter.addB("E13",3);counter.addC("E13",0);
Taylorlamda       = Math.pow(lamda,3)*math.Atn_D(lamda);
counter.addA("E13",1);counter.addB("E13",0);counter.addC("E13",2);
Taylorlamda1      = Math.pow(lamda1,3)*math.Atn_D(lamda1);
counter.addA("E13",1);counter.addB("E13",0);counter.addC("E13",2);
b_n               = -2.0D * Math.abs(v) * Taylorlamda;
counter.addA("E13",2);counter.addB("E13",0);counter.addC("E13",0);
b_n1              = -2.0D * Math.abs(v1) * Taylorlamda1;
counter.addA("E13",2);counter.addB("E13",0);counter.addC("E13",0);
//Compute b_n offsets
//Compute lamda*
lamdaAsterisk     =(h*LamdaAsterisk)/(Math.abs(v)+vector.magnitude(rc_pos));
lamdaAsterisk1    =(h1*LamdaAsterisk)/(Math.abs(v1)+vector.magnitude(rc_pos));
counter.addA("E13",10);counter.addB("E13",6);counter.addC("E13",2);
//Compute sigma
sigma             =(Robs1_len+Robs2_len-Math.abs(L)*Lamda)/2.0D;
counter.addA("E13",2);counter.addB("E13",2);counter.addC("E13",0);
//Compute lamda~
lamdaCurl         =(lamdaAsterisk*vector.magnitude(rc_pos))/(Math.abs(v)+sigma);
lamdaCurl1        =(lamdaAsterisk1*vector.magnitude(rc_pos))/(Math.abs(v1)+sigma);
counter.addA("E13",10);counter.addB("E13",6);counter.addC("E13",0);
//compute b_n offsets
offset            = ((lamda+lamdaCurl)*Delta+lamdaCurl*Lamda*LamdaAsterisk);
offset1           = ((lamda1+lamdaCurl1)*Delta+lamdaCurl1*Lamda*LamdaAsterisk);
counter.addA("E13",6);counter.addB("E13",4);counter.addC("E13",0);
//Accumulate offsets
b_n               +=-2.0D*Math.abs(v)*offset;
b_n1              +=-2.0D*Math.abs(v1)*offset1;
counter.addA("E13",2);counter.addB("E13",2);counter.addC("E13",0);

//Add b_h,b_n
surface_ij =b_h+b_n;
surface1_ij=b_h1+b_n1;
counter.addB("E13",2);
//accumulate to facets
//facet_totals[facet1][0] += surface_ij; 
//facet_totals[facet2][0] += surface1_ij;
counter.addB("E13",2);
}
//start towards volume analysis of quantities
   ni_curl=vector.mulScalar(ni, math.signum(v));
   ni_curl1=vector.mulScalar(ni, math.signum(v1));
   //compute vectors b facet, b1 adjucent facet
   vector vh2=vector.mulScalar(vh,2.0);
   vector vh2_1=vector.mulScalar(vh1,2.0);
   //h term
   vector vh2ArctanL=vector.mulScalar(vh2,math.AtanH_D(Lamda));
   vector vh2ArctanL_1=vector.mulScalar(vh2_1,math.AtanH_D(Lamda));
   //n term
   vector vn=vector.mulScalar(ni_curl, 2.0*math.AtnH_D(lamda));
   vector vn1=vector.mulScalar(ni_curl1, 2.0*math.AtnH_D(lamda));
   
   vector vector_b_new =vector.subVec(vector.mulScalar(vh, b_h),vector.mulScalar(ni,b_n));
   double bij_new=vector.dot(vector_b_new, Robs1_bar);
   Cij_new+= bij_new;
   vector vector_b_new1 =vector.subVec(vector.mulScalar(vh1, b_h1),vector.mulScalar(ni,b_n1));
   double bij_new1=vector.dot(vector_b_new1, Robs1_bar);
   Cij_new+=bij_new1;
   
   Sigmab1=vector.addVec( vector_b_new,Sigmab1);
   Sigmab1=vector.addVec( vector_b_new1,Sigmab1);
   
   //vector bij=h term +n term
   vector_b =vector.subVec(vh2ArctanL, vn);
   double bij=vector.dot(vector_b, Robs1_bar);
   //accumulate for facet 1
   facet_totals[facet1][0] += bij;
   
   Cij_new1+=bij;
   
   Sigmab=vector.addVec( vector_b,Sigmab);
   vector_b1=vector.subVec(vh2ArctanL_1, vn1);
   bij=vector.dot(vector_b1, Robs1_bar);
   //accumulate for adjucent facet
   facet_totals[facet2][0] += bij;
   
   Cij_new1+=bij;
   Sigmab=vector.addVec(Sigmab, vector_b1);

   




}// end edge loop

Cij=0.0;

for (int i=0;i<data.getPolyFacets();i++)
{
//v=facet_totals[i][1];
Ai                    =vector.magnitude(data.getArea(i))/2;
counter.addA("E14",4);counter.addB("E14",2);counter.addC("E14",1);
//Branching models standard - triangulated 
if(data.getTargetModel()==2 || data.getTargetModel()==3 )
{ //branch1=triangulated polyhedra, solid angle computation
  Solid_Angle           = math.Oosterom1(data.getPolyVertex(i,1), data.getPolyVertex(i,2),data.getPolyVertex(i,3),data.getObs(obs),v,Ai);
counter.addA("E14",0);counter.addB("E14",0);counter.addC("E14",1);  
//accumulate solid angle 
  //facet_totals[i][0]    += (-v)*Solid_Angle+(2*Ai/vector.magnitude(rc_pos));
  counter.addA("E14",6);counter.addB("E14",4);counter.addC("E13",1);
}
else
{
//branch2=polygonal polyhedra 
surfaceArctanOffsetAsterisk = 2.0D*Ai/(vector.magnitude(rc_pos)+Math.abs(v));
counter.addA("E14",5);counter.addB("E14",3);counter.addC("E14",1);
//accumulate facet area offsets
//facet_totals[i][0]    += surfaceArctanOffsetAsterisk;
counter.addA("E14",0);counter.addB("E14",2);counter.addC("E14",0);
}
//Cij += v*facet_totals[i][0]/2;
counter.addA("E14",2);counter.addB("E14",1);counter.addC("E14",0);
//counter.addA("E14",1);counter.addB("E14",1);
}


//Line facet post-edge cij accumulation
for (int i=0;i<data.getPolyFacets();i++)
{double x,y,z;
v=facet_totals[i][1];
Cij += v*facet_totals[i][0];
//counter.addA("E14",1);counter.addB("E14",1);
}
//System.out.println(" facets  :"+data.getPolyFacets());
System.out.println("obs :"+(obs+1));

System.out.println("Total cij for obs :"+(obs+1)+"  = "+Cij);
//ystem.out.println("branch1:"+branch1);
//System.out.println("branch2:"+branch2);

}// end obs loop
//counter.PrintVEF();
 // counter.PrintCountAnalytics();
 // counter.PrintCountTotals();
 // counter.PrintEulerCountAnalytics();
}
  
 public static void main(String[] args)
{
    Volume m = new Volume();
    m.Facet_Loop();
    m.Extrinsic_Loop();
  } 
  
  
}
