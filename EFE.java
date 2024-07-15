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

public class EFE {
    int switch_rc=1;
    vector rc_local = new vector(0.0, 0.0, 0.0);
    vector rc_pos = new vector(0.0, 0.0, 0.0);
    vector centroid_local= new vector(0.0,0.0,0.0);
    vector centroid_pos= new vector(0.0,0.0,0.0);
    vector target_facet_rc_local= new vector(0.0,0.0,0.0);
    vector target_facet_rc_pos= new vector(0.0,0.0,0.0); 
    double epsilon=2.2204460492503130808472633361816E-16;
    
    Data data=new Data();  
    int target=data.getTargetModel();
    int facetA,facetB,facetC,vertexA,vertexB,vertexC,edgeA,edgeB,edgeC;
    int branch1=0,branch2=0;int obs;
    double facet_totals[][];
     double facet_totals_volume[][];
    double[][] Edge=data.getEdge_Struct();
    
    PrintWriter printWriter;
    utility util;
    
  
  
  public EFE()
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
  ///start method implementation volume
  public double deltaSquareLamda(double Lamda,double LamdaStar,double deltaDeltaBar,double deltaLamda,double DeltaBar, double DeltaStarBar)
  {
  double result=0.0;
  
  result= 1/(2*(Lamda+LamdaStar)*deltaDeltaBar)+1/(2*(deltaLamda*(DeltaBar+DeltaStarBar)));
  
  
  return result;
  }
    
  public double deltaSquarelamda(double lamda,double Lamda,double LamdaStar,double deltaDeltaBar,
          double deltalamda,double deltalamdaCurl,double lamdaStar,double lamdaCurlStar,double lamdaCurl,double DeltaBar, double DeltaStarBar)
  {
  double result=0.0;
  
  result= ((deltalamda+deltalamdaCurl)*(DeltaBar+DeltaStarBar))/2+(((lamda+lamdaStar+lamdaCurl+lamdaCurlStar)*deltalamdaCurl*Lamda*LamdaStar)/2);
  
  
  return result;
  }
  
  
  public vector deltaBStar(vector h,vector n_curl,double Lamda,double lamda,double atnh,double atn, double deltaSquareLamda,double deltaSquarelamda)
  
  {
  vector result= new vector(0.0,0.0,0.0);
  double factor1=Math.pow(Lamda,3)*atnh+deltaSquareLamda;
   double factor2=Math.pow(lamda,3)*atn+deltaSquarelamda;
  result=vector.mulScalar(h,2);
  result=vector.mulScalar(result, factor1);
  result=vector.subVec(result,vector.mulScalar(vector.mulScalar(n_curl, 2),factor2));
 
  
  return result;
  }
  
  public double delta_r_icp(vector ric,vector rp,vector Ric,vector Rp){
  double result=0.0;
  vector result1=new vector(0.0,0.0,0.0);
  vector ratio=new vector(0.0,0.0,0.0);
  
  ratio=vector.addVec(ric, rp);
  ratio=vector.divScalar(ratio, (vector.magnitude(ric)+vector.magnitude(rp)));
  result1 =vector.subVec(Ric, Rp);

  result=vector.dot(result1,ratio);
  return result;
  }
  
  public vector ricrp(vector ric,vector rp,vector Ric,vector Rp)
  {
  vector result=new vector(0.0,0.0,0.0);
  
  vector R_diff=new vector(0.0,0.0,0.0);vector r_diff=new vector(0.0,0.0,0.0);
  vector vector1=new vector(0.0,0.0,0.0);  vector vector2=new vector(0.0,0.0,0.0);
  double factor1=0.0;double factor2=0.0;double factor3=0.0; vector factor4=new vector(0.0,0.0,0.0);  double factor41=0.0;
  double ric_mag= vector.magnitude(ric);double rp_mag=vector.magnitude(rp);
  R_diff= vector.subVec(Ric, Rp);r_diff=vector.subVec(ric, rp);
  factor1=1/Math.pow(ric_mag, 3)+1/Math.pow(rp_mag,3);
  factor2=1/Math.pow(ric_mag,2)+1/ric_mag*rp_mag+1/Math.pow(rp_mag,2);
  factor2=factor1*delta_r_icp(ric,rp,Ric,Rp)/(ric_mag*rp_mag);
  factor1=factor1/2;factor2=factor2/2;
  vector1=vector.mulScalar(R_diff,factor1); vector2=vector.mulScalar(r_diff,factor2);
  result=vector.subVec(vector1,vector2);
  
   
  return result;
  }
  
  
  public double Delta1(vector rc,vector rj1,vector Rc,vector Rj1)
  {
  double result=0.0;
  
  vector vector1=new vector(0.0,0.0,0.0);
  vector vector2=new vector(0.0,0.0,0.0);
 
  vector1=vector.divScalar(vector.subVec(Rc, Rj1), vector.magnitude(rc));
  vector2=vector.divScalar(vector.addVec(rc, rj1), vector.magnitude(vector.addVec(rc, rj1)));
  
  result=vector.dot(vector1, vector2);
  
  return result;
  }
  
   public double Delta2(vector rc,vector rj2,vector Rc,vector Rj2)
  {
  double result=0.0;
  
  vector vector1=new vector(0.0,0.0,0.0);
  vector vector2=new vector(0.0,0.0,0.0);
 
  vector1=vector.divScalar(vector.subVec(Rc, Rj2), vector.magnitude(rc));
  vector2=vector.divScalar(vector.addVec(rc, rj2), vector.magnitude(vector.addVec(rc, rj2)));
  
  result=vector.dot(vector1, vector2);
  
  return result;
  }
  
   public double deltaDelta1(vector rc,vector rj1,vector Rc,vector Rj1)
   {
   double result=0.0;
  vector vector1=vector.subVec(Rc, Rj1);vector vector2=vector.divScalar(rc, Math.pow(vector.magnitude(rc),2));
  vector1=vector.divScalar(vector1, 2);vector2=vector.divScalar(vector2, 2);
          
  result=vector.dot(vector1, vector2);
   return result;
   }
   public double deltaDelta2(vector rc,vector rj2,vector Rc,vector Rj2)
   {
   double result=0.0;
   vector vector1=vector.subVec(Rc, Rj2);vector vector2=vector.divScalar(rc, Math.pow(vector.magnitude(rc),2));
  vector1=vector.divScalar(vector1, 2);vector2=vector.divScalar(vector2, 2);
          
  result=vector.dot(vector1, vector2);
  
   return result;
   }
   
   public double deltaDelta_bar(vector rc,vector rj1,vector rj2,vector Rc,vector Rj1,vector Rj2)
   {
   double result=0.0;
   result=(deltaDelta1(rc,rj1,Rc,Rj1)+deltaDelta2(rc,rj2,Rc,Rj2))/2;
   
   return result;
   }
  //end implementation volume
   //EFE
   
    public vector  b_EFE(vector A,vector vh,vector n_curl,double Lamda, double lamda,double deltaSquareLamda, double deltaSquarelamda)
   {
   vector result=new vector(0.0,0.0,0.0);
   vector term1=new vector(0.0,0.0,0.0);
   vector term2=new vector(0.0,0.0,0.0);
   vector term3=new vector(0.0,0.0,0.0);
   
   
   
   return result;
   }
   
  public Data getData(){return data;}
  
   public  void Facet_Loop()
  {
    int facetIndex = 0;
    vector VectorFacetArea = new vector(0.0, 0.0, 0.0);
    double DoubleFacetArea = 0.0;

    for (facetIndex = 0; facetIndex < data.getPolyFacets(); facetIndex++) {
      vector vectorfacetarea = new vector(0.0, 0.0, 0.0);
      if (target==2)
            vectorfacetarea = doTriangleArea(facetIndex);
     else 
            vectorfacetarea= ComputeFacet(facetIndex);;
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
     vector vector_Sigma_b_volume         =   new vector(0.0, 0.0, 0.0);
     double suma=0.0;
     double Sigma_b_volume=0.0;
     double Area=0.0;
     vector vector_Area         =   new vector(0.0, 0.0, 0.0);
     vector volume_edge_anomaly = new vector(0.0, 0.0, 0.0);
     double anomaly=0.0; 
             
     
     
     int vertex_1=0; int vertex_2=0,facet=0;
     double h=0.0,h1=0.0;
     double rm_ij=0.0;
     double v=0.0,v1=0.0;double L=0.0;
     double Cij=0.0,Ai=0.0,Ai1=0.0,Cij_new=0,Cij_new1=0,Cij_volume=0;
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
facet_totals_volume=new double[data.getPolyFacets()][4];
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
  // counter.addB("E11",3);
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
 facet_totals_volume[i][1]=v;
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
    vector delta_b_star_volume=new vector(0.0,0.0,0.0);
      
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
    b1=vector.dot(vector_b, Robs2_bar);
   facet_totals[facet2][0] += b1;
   
   
  //Start volume method

   vector r_bar=vector.divScalar(vector.addVec(Robs1_bar, Robs2_bar),2);
  //  System.out.println("r1,r2  for obs :"+(obs+1)+"  = "+r_bar);
   // System.out.println("vectir r_bar :"+(obs+1)+"  = "+r_bar);
   vector ric = new vector(0.0, 0.0, 0.0);
   vector rp = new vector(0.0, 0.0, 0.0);
   vector Ric = new vector(0.0, 0.0, 0.0);
   Ric=data.getRCos();
   vector Rp = new vector(0.0, 0.0, 0.0);
   Rp=data.getCentroid();
   vector normal = new vector(0.0, 0.0, 0.0);
   ric=target_facet_rc_pos;
   double ric_magnitude=vector.magnitude(ric);
   ric=vector.subVec(Ric,data.getObs(obs));
   rp=vector.subVec(Rp,data.getObs(obs));
   double LamdaStar=L/(2*ric_magnitude);
   double DeltaBar=(deltaDelta1(ric,Robs1_bar,Ric,data.getVertex(vertex_1))+deltaDelta2(ric,Robs2_bar,Ric,data.getVertex(vertex_2)))/2;
   double deltaLamda=Lamda*DeltaBar;
   double deltaDeltaBar=deltaDelta_bar(ric,Robs1_bar,Robs2_bar,Ric,data.getVertex(vertex_1),data.getVertex(vertex_2));  
   //Lambda is same as Lamda
   double Lambda=L/(vector.magnitude(Robs1_bar)+vector.magnitude(Robs2_bar));   
   vector R_bar=vector.mulScalar(vector.addVec(data.getVertex(vertex_1), data.getVertex(vertex_2)),2);
   double DeltaStarBar=vector.dot(vector.subVec(Ric, R_bar),vector.divScalar(ric, Math.pow(vector.magnitude(ric),2)));

   //compute for facet 1
   facet=facet1;
   normal=data.getNormal(facet);
   vector normal_curl = vector.mulScalar(normal, Math.signum(v));
   int facetvertex_index=data.getFacetVertex_index(facet, 0);
   Ric=data.getVertex(facetvertex_index);
   double r_curl=r_dash*(1-Math.pow(Lambda,2))+Math.abs(v);
   double lamda_vol=(h*Lambda)/r_curl;
   double rc_curl=vector.magnitude(ric)+Math.abs(v);
   double lamdaStar=(h*Lambda)/rc_curl;
   double lamdaCurl=(vector.magnitude(ric)*lamdaStar)/r_curl;
   double deltalamda=(lamda_vol+lamdaCurl)*DeltaBar+lamdaCurl*Lambda*LamdaStar;
   double lamdaCurlStar=(lamdaStar*vector.magnitude(ric))/rc_curl;
   double deltalamdaStar=(lamdaStar+lamdaCurlStar)*DeltaStarBar;
   double deltalamdaCurl=lamdaCurlStar*(DeltaBar*vector.magnitude(ric)+Math.pow(Lambda, 2)*vector.magnitude(r_bar))/r_curl;
   delta_b_star_volume=deltaBStar(vh,normal_curl, Lamda,lamda_vol,math.AtanH_D(Lamda),Math.atan(lamda_vol), deltaSquareLamda(Lamda,LamdaStar,deltaDeltaBar,deltaLamda,DeltaBar, DeltaStarBar),deltaSquarelamda(lamda_vol,Lamda,LamdaStar,deltaDeltaBar,
   deltalamda,deltalamdaCurl,lamdaStar,lamdaCurlStar,lamdaCurl,DeltaBar,DeltaStarBar));
   volume_edge_anomaly=delta_b_star_volume;
   vector_Area=data.getArea(facet);Area=vector.magnitude(vector_Area);
   volume_edge_anomaly=vector.addVec(volume_edge_anomaly, vector.mulScalar(ricrp(ric,rp,Ric, Rp),Area));
   anomaly=vector.dot(volume_edge_anomaly, r_bar);
   facet_totals_volume[facet][0]+=anomaly;
  
   //compute for facet 2
   facet=facet2;
   normal=data.getNormal(facet);
   normal_curl = vector.mulScalar(normal, Math.signum(v1));
   r_curl=r_dash*(1-Math.pow(Lambda,2))+Math.abs(v1);
   lamda_vol=(h1*Lambda)/r_curl;
   rc_curl=vector.magnitude(ric)+Math.abs(v1);
   lamdaStar=(h1*Lambda)/rc_curl;
   lamdaCurl=(vector.magnitude(ric)*lamdaStar)/r_curl;
   deltalamda=(lamda_vol+lamdaCurl)*DeltaBar+lamdaCurl*Lambda*LamdaStar;
   lamdaCurlStar=(lamdaStar*vector.magnitude(ric))/rc_curl;
   deltalamdaStar=(lamdaStar+lamdaCurlStar)*DeltaStarBar;
   deltalamdaCurl=lamdaCurlStar*(DeltaBar*vector.magnitude(ric)+Math.pow(Lambda, 2)*vector.magnitude(r_bar));
   lamdaStar=(h1*LamdaStar)/rc_curl;
   lamdaCurl=(vector.magnitude(ric)*lamdaStar)/r_curl;
   deltalamda=(lamda_vol+lamdaCurl)*DeltaBar+lamdaCurl*Lambda*LamdaStar;
   lamdaCurlStar=(lamdaStar*vector.magnitude(ric))/rc_curl;
   deltalamdaStar=(lamdaStar+lamdaCurlStar)*DeltaStarBar;
   deltalamdaCurl=lamdaCurlStar*(DeltaBar*vector.magnitude(ric)+Math.pow(Lambda, 2)*vector.magnitude(r_bar))/r_curl;
   facetvertex_index=data.getFacetVertex_index(facet, 0);
   Ric=data.getVertex(facetvertex_index);
   
  delta_b_star_volume=deltaBStar(vh1,normal_curl, Lamda,lamda_vol,math.AtanH_D(Lamda),Math.atan(lamda_vol), deltaSquareLamda(Lamda,LamdaStar,deltaDeltaBar,deltaLamda,DeltaBar, DeltaStarBar),deltaSquarelamda(lamda_vol,Lamda,LamdaStar,deltaDeltaBar,
  deltalamda,deltalamdaCurl,lamdaStar,lamdaCurlStar,lamdaCurl,DeltaBar,DeltaStarBar));
  volume_edge_anomaly=delta_b_star_volume;
  vector_Area=data.getArea(facet);Area=vector.magnitude(vector_Area);
  volume_edge_anomaly=vector.addVec(volume_edge_anomaly, vector.mulScalar(ricrp(ric,rp,Ric, Rp),Area));
  anomaly=vector.dot(volume_edge_anomaly, r_bar);
  // System.out.println("Total volume facet2 for obs :"+(obs+1)+"  = "+anomaly);
   facet_totals_volume[facet][0]+=anomaly;
   
   
   
}// end edge loop





Cij=0.0;
Cij_volume=0;

//Line facet post-edge cij accumulation
for (int i=0;i<data.getPolyFacets();i++)
{double x,y,z;
v=facet_totals[i][1];
double v_volume;
v_volume=facet_totals_volume[i][1];
//Cij += -2*facet_totals[i][0];
Cij += 
        v*facet_totals[i][0];
Cij_volume+=v_volume*facet_totals_volume[i][0];
}

//System.out.println("obs :"+(obs+1));
//System.out.println("Total cij for obs :"+(obs+1)+"  = "+Cij);
System.out.println("Total anomaly for volume for obs :"+(obs+1)+"  = "+Cij_volume);
vector ric=data.getObs(obs);
double newtonian_response=vector.magnitude(vector.divScalar(ric, Math.pow(vector.magnitude(ric),3)));
double delta=vector.magnitude(ric);
double apoint=1/Math.pow(delta,2);
//apoint=newtonian_response;
double r_difference=Math.abs(Cij_volume-apoint)/Math.abs(apoint);
r_difference=Math.max(r_difference, epsilon);
//System.out.println("apoint for obs :"+(obs+1)+"  = "+apoint);

double log_r_difference=Math.log10(r_difference);
//System.out.println("Relative difference for volume for obs :"+(obs+1)+"  = "+log_r_difference);
double log_delta=Math.log10(delta/L);
//System.out.println("log10(L/E) for obs :"+(obs+1)+"  = "+log_delta);

}// end obs loop

}
  
 public static void main(String[] args)
{
    EFE m = new EFE();
    m.Facet_Loop();
    m.Extrinsic_Loop();
  } 
  
  
}
