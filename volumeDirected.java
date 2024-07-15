/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loop;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */

public class volumeDirected {
    int switch_rc=1;
    vector rc_local = new vector(0.0, 0.0, 0.0);
    vector rc_pos = new vector(0.0, 0.0, 0.0);
    vector centroid_local= new vector(0.0,0.0,0.0);
    vector centroid_pos= new vector(0.0,0.0,0.0);
    vector target_facet_rc_local= new vector(0.0,0.0,0.0);
   
     vector m= new vector(0.0,0.0,1.0); 
    double epsilon=2.2204460492503130808472633361816E-16;
    
    Data data=new Data();  
    int target=data.getTargetModel();
    int facetA,facetB,facetC,vertexA,vertexB,vertexC,edgeA,edgeB,edgeC;
    int branch1=0,branch2=0;int obs;
    double facet_totals[][];
     double facet_totals_volume[][];
    double[][] Edge=data.getEdge_Struct();
      double potential_anomaly=0.0; double anomaly1=0.0; 
    
    PrintWriter printWriter;
    utility util;
    
  
  public volumeDirected()
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
  
    public double Lamda(double L, double r_dash)
  {
  double Lamda=L/2*r_dash;
  return Lamda;
  }
   public double lamda(double Lamda, double h,double r_curl)
  {
  double lamda=Lamda*h/r_curl;
  return lamda;
  }
  public double r_dash(double r1, double r2)
  
  {double r_dash=(r1+r2)/2;
  return r_dash;
  }
  
  public double LamdaStar(double L,vector rc)
  {
  double result=0.0;
  
  result=L/(2*vector.magnitude(rc));
  return result;
  }
  
  public double lamda_curl(double rc, double lamdaStar,double r_curl)
  {
  double lamda_curl=(rc*lamdaStar)/r_curl;
  
  return lamda_curl;
  }
  
   public double lamdaStar(double LamdaStar,double h,double ric_curl)
  {
  double result=0.0;
  
  result=(LamdaStar*h)/ric_curl;
  return result;
  }
   
   public double ric_curl(double ric,double v)
  {
  double ric_curl=ric+Math.abs(v);
  return ric_curl;
  } 
  
  public double deltaLamda(double Lamda, double Delta_dash)
  {
  double deltaLamda=Lamda*Delta_dash;
  return deltaLamda;
  }
  
  public double DeltaDash(double Delta1,double Delta2)
  {
  return (1/2)*(Delta1+Delta2);
  }
  
  
  public double deltalamda(double Lamda,double LamdaStar, double Delta_dash,double lamdaCurl,double lamda)
  {
  double deltalamda=(lamda+lamdaCurl)*Delta_dash+lamdaCurl*Lamda*LamdaStar;
  return deltalamda;
  }
  
   
public double deltaLamdaStar(double L,vector ric,double DeltaStarBar)
  {
  double result=0.0;
  
  
  result=LamdaStar(L,ric)*DeltaStarBar;
  return result;
  }

public vector delta_b_star(vector vh, double Lamda,double lamda,double Atnh,double Atn,vector n_curl,double deltaSquareLamda,double deltaSquarelamda )
{
vector deltabstar=new vector (0,0,0);

deltabstar=vector.mulScalar(vector.mulScalar(vh, 2),Math.pow(Lamda,3)*Atnh+deltaSquareLamda);
deltabstar=vector.subVec(deltabstar,vector.mulScalar(vector.mulScalar(n_curl, 2),Math.pow(lamda,3)*Atn+deltaSquarelamda));

return deltabstar;
}

  //methods for implementation volume
  public double deltaSquareLamda(double Lamda,double LamdaStar,double deltaDeltaBar,double deltaLamda,double DeltaBar, double DeltaStarBar)
  {
  double result=0.0;
  //try {

  result= (0.5*(Lamda+LamdaStar)*deltaDeltaBar)+(0.5*(deltaLamda*(DeltaBar+DeltaStarBar)));
   //}
        //catch (ArithmeticException e) {
            // Exception handler
           // System.out.println(
            //    "Divided by zero operation cannot possible");
       // }
  
  return result;
  }
    
  public double deltaSquarelamda(double lamda,double Lamda,double LamdaStar,double deltaDeltaBar,
          double deltalamda,double deltalamdaCurl,double lamdaStar,double lamdaCurlStar,double lamdaCurl,double DeltaBar, double DeltaStarBar)
  {
  double result=0.0;
  
  result= (0.5*(deltalamda+deltalamdaCurl)*(DeltaBar+DeltaStarBar))+(0.5*((lamda+lamdaStar+lamdaCurl+lamdaCurlStar)*deltaDeltaBar+(lamdaCurl*Lamda*LamdaStar)));
  
  
  return result;
  }
  
  
  public vector deltaBStar(vector vh,double Lamda,double lamda,double atnh,double atn,vector n_curl, double deltaSquareLamda,double deltaSquarelamda)
  
  {
  vector result= new vector(0.0,0.0,0.0);
  double factor1=Math.pow(Lamda,3)*atnh+deltaSquareLamda;
   double factor2=Math.pow(lamda,3)*atn+deltaSquarelamda;
  result=vector.mulScalar(vh,2);
  result=vector.mulScalar(result, factor1);
  result=vector.addVec(result,vector.mulScalar(vector.mulScalar(n_curl, -2),factor2));
 
  
  return result;
  }
  
  public double delta_r_icp(vector ric,vector rp,vector Ric,vector Rp){
  double result=0.0;vector result1=new vector(0.0,0.0,0.0);
  
  vector ratio=vector.addVec(ric, rp);
  try{ 
  ratio=vector.divScalar(ratio, (vector.magnitude(ric)+vector.magnitude(rp)));
  }
  catch (ArithmeticException e) {
            // Exception handler
            System.out.println(
                "Divided by zero operation cannot possible");
  }
  result1 =vector.subVec(Ric, Rp);

  result=vector.dot(result1,ratio);
  return result;
  }
  
  public vector ricrp(vector ric,vector rp,vector Ric,vector Rp)
  {
  vector result=new vector(0.0,0.0,0.0);
  
  vector R_diff=new vector(0.0,0.0,0.0);vector r_sum=new vector(0.0,0.0,0.0);
  vector vector1=new vector(0.0,0.0,0.0);  vector vector2=new vector(0.0,0.0,0.0);
  double factor1=0.0;double factor2=0.0;double factor3=0.0; vector factor4=new vector(0.0,0.0,0.0);  double factor41=0.0;
  
  double ric_mag= vector.magnitude(ric);double rp_mag=vector.magnitude(rp);
 
  R_diff= vector.subVec(Ric, Rp);
  r_sum=vector.addVec(ric, rp);
  factor1=(1/Math.pow(ric_mag, 3))+(1/Math.pow(rp_mag,3));
  factor2=1/Math.pow(ric_mag,2)+1/(ric_mag*rp_mag)+1/Math.pow(rp_mag,2);
  factor2=factor2*(delta_r_icp(ric,rp,Ric,Rp))/(ric_mag*rp_mag);

  vector1=vector.mulScalar(R_diff,factor1); 
  vector2=vector.mulScalar(r_sum,factor2);
  result=vector.divScalar(vector.subVec(vector1,vector2),2);

  return result;
  }

  public vector ricrp1(vector ric,vector rp,vector Ric,vector Rp)
  {
  vector result=new vector(0.0,0.0,0.0);
  
 vector vector1=vector.divScalar(ric, vector.magnitude(ric));
 vector vector2=vector.divScalar(rp, vector.magnitude(rp));
 result=vector.subVec(vector1, vector2);
  
   
  return result;
  }
  
  public double Delta1(vector rc,vector rj1,vector Rc,vector Rj1)
  {
      double result=0.0;
 
  vector vector1=vector.divScalar(vector.subVec(Rc, Rj1), vector.magnitude(rc));
  double div=vector.magnitude(rc)+vector.magnitude(rj1);
  vector vector2=vector.divScalar(vector.addVec(rc, rj1),div );
  
  result=vector.dot(vector1, vector2);
  
  return result;
  }
  
   public double Delta2(vector rc,vector rj2,vector Rc,vector Rj2)
  {
 double result=0.0;
 
  vector vector1=vector.divScalar(vector.subVec(Rc, Rj2), vector.magnitude(rc));
  double div=vector.magnitude(rc)+vector.magnitude(rj2);
 vector vector2=vector.divScalar(vector.addVec(rc, rj2),div );
  
  result=vector.dot(vector1, vector2);
  
  return result;
  }
  
   public double deltaDelta1(vector rc,vector rj1,vector Rc,vector Rj1)
   {
   double result=0.0;
  vector vector1=vector.subVec(Rc, Rj1);
  vector1=vector.divScalar(vector1,vector.magnitude(rc) );
  //result = -Math.pow(vector.magnitude(vector1),2)/2;
  result=-vector.dot(vector1, vector1)/2;
  
  result=result+(Math.pow(Delta1(rc,rj1,Rc,Rj1),2))/2;

   return result;
   }
   public double deltaDelta2(vector rc,vector rj2,vector Rc,vector Rj2)
   {
      double result=0.0;
  vector vector1=vector.subVec(Rc, Rj2);
  vector1=vector.divScalar(vector1,vector.magnitude(rc) );
  //result = -Math.pow(vector.magnitude(vector1),2)/2;
  result=-vector.dot(vector1, vector1)/2;
  
  result=result+(Math.pow(Delta2(rc,rj2,Rc,Rj2),2))/2;

   return result;
   }
   
   public double deltaDelta_bar(vector rc,vector rj1,vector rj2,vector Rc,vector Rj1,vector Rj2)
   {
   double result=0.0;
   result=(deltaDelta1(rc,rj1,Rc,Rj1)+deltaDelta2(rc,rj2,Rc,Rj2))/2;
   
   return result;
   }
    public double DeltaStarBar(vector Rc,vector R_bar,vector rc)
   {
   double result=0.0;
   result=vector.dot(vector.subVec(Rc, R_bar),vector.divScalar(rc, vector.magnitude(rc)));
   
   return result;
   }
   public vector R_bar(vector R1, vector R2)
   {
   vector result=vector.divScalar(vector.addVec(R1, R2),2);
   return result;
   }
   
  
  //end implementation volume
   
  public Data getData(){return data;}
  
   public  void Facet_Loop()
  {
    int facetIndex = 0;
    vector VectorFacetArea = new vector(0.0, 0.0, 0.0);
    double DoubleFacetArea = 0.0;

    for (facetIndex = 0; facetIndex < data.getPolyFacets(); facetIndex++) {
      vector vectorfacetarea = new vector(0.0, 0.0, 0.0);
      if (target==2||target==5||target==6||target==7)
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
     
       vector r_bar  	   =   new vector(0.0,0.0,0.0);
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
     vector volume_magnetic_field_anomaly = new vector(0.0, 0.0, 0.0); vector volume_total_anomaly = new vector(0.0, 0.0, 0.0);
        vector b_star_total= new vector(0.0, 0.0, 0.0);
       vector b_target_total=new vector(0.0,0.0,0.0);vector b_target_total_volume=new vector(0.0,0.0,0.0);
               vector volume_deltab_anomaly = new vector(0.0, 0.0, 0.0);
                 vector volume_bStar_edges_anomaly = new vector(0.0, 0.0, 0.0);
     
      
      double[][] sigma_ni_bij=new double[3][3];
      vector b=new vector(0.0,0.0,0.0);vector b1=new vector(0.0,0.0,0.0);
    vector m_dot_sigma_ni_bij=new vector(0.0,0.0,0.0);
   
    vector b_volume_facet=new vector(0.0,0.0,0.0);
     
     int vertex_1=0; int vertex_2=0,facet=0;
     double h=0.0,h1=0.0;
     double rm_ij=0.0;
     double v=0.0,v1=0.0;double L=0.0,v_volume=0;
     double Cij=0.0,Ai=0.0,Ai1=0.0,Cij_new=0,Cij_new1=0,Cij_volume=0;
     int facet2,facet1;
     double norm_Robs1_len,norm_Robs2_len,r0_len,max_term,l1,l2,eta,surface_ij=0.0,surface1_ij=0.0;
     double sum_hC[] = new double[data.getPolyFacets()],sum_half_Omega_bar[]=new double[data.getPolyFacets()];
     double Solid_Angle,surfaceArctanOffsetAsterisk; 
      vector total_areas = new vector(0.0, 0.0, 0.0);
      double b_line=0; 
      
      double volume_total_potential=0.0;
      
      
   vector Ric=data.getFacetCentroid(facet);
   vector ric= new vector(vector.subVec(Ric,data.getObs(obs)));
   vector Rc=data.getCentroid();
   vector rc= new vector(vector.subVec(data.getCentroid(),data.getObs(obs)));
   double rc_magnitude= vector.magnitude(rc);
   double ric_magnitude=vector.magnitude(ric);
    vector Rp=data.getCentroid();
   vector rp=vector.subVec(Rp,data.getObs(obs));
//obs loop
//System.out.println( "Polyhedral anomaly with Line "+  
//"           Equation 8-Denver "+"                          Volume -eq 21-Denver           ");
double[][] gradbij_total=new double[3][3];
      double[][] gradbij_total_volume=new double[3][3];    
        
for(obs=0;obs<data.getNumberObs();obs++)
{
  Cij=0.0;
  Sigmab         =   new vector(0.0, 0.0, 0.0);
  Sigmab1         =   new vector(0.0, 0.0, 0.0);
  Cij_new=0.0;
  double vertex_extrinsic_quantities [][];

facet_totals=new double[data.getPolyFacets()][5];
facet_totals_volume=new double[data.getPolyFacets()][5];
vector facet_totals_field_volume        =   new vector(0.0, 0.0, 0.0);
vertex_extrinsic_quantities=new double[data.getVertices()][4];
double sigma_b_total=0;
    double sigma_b_star_total=0;
//compute position vectors for target centroids for obs
//target centroid position vector
centroid_pos= new vector(vector.subVec(data.getCentroid(),data.getObs(obs)));
//1st facet's 1st vertex for target
vector target_facet_rc_pos= new vector(vector.subVec(data.getFacetCentroid(facet),data.getObs(obs)));

//vertex loop, compute vertex position vectors
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

//Vertex:facet pre-edge calculations, get normals, v
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
 
  index=data.getFacetVertex_index(i,2);
 Robs2_bar.x=vertex_extrinsic_quantities[index][0];
 Robs2_bar.y=vertex_extrinsic_quantities[index][1];
 Robs2_bar.z=vertex_extrinsic_quantities[index][2];

    //System.out.println("Robs1   "+Robs1_bar+"Robs2   "+Robs2_bar);
 
 v_volume=vector.dot(ni,vector.divScalar(vector.addVec(Robs1_bar,Robs2_bar),2));
 v=vector.dot(ni, Robs1_bar);
 counter.addA("E12",3);counter.addB("E12",2);
 facet_totals[i][1]=v;
 facet_totals_volume[i][1]=v_volume;
//System.out.println("v= "+v+"v volume =  "+v_volume);


}

//switch for vector rc 2 cases:1.centroid,2.1stfacet-1st vertex
switch (switch_rc) {
    case 1:{rc_local=centroid_local;rc_pos=centroid_pos;};
    case 2:rc_local=target_facet_rc_local;rc_pos=target_facet_rc_pos;
                   }
//edge loop directed edge
for (int edge_count=0;edge_count<data.getEdges();edge_count++)
{
  double Lamda=0.0,Robs1_len,Robs2_len,atanh=0.0,atanh1=0.0,atan=0.0,atan1=0.0,lamda=0.0,lamda1=0.0,lamda_dash=0.0,lamda_dash1=0.0;
  double bij=0,b1ij=0,b_h=0.0,b_h1=0.0,b_n=0.0,b_n1=0.0,cij=0.0;double r1_r2,r_dash; 
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
    
   
  //retrieve facet
  facet=(int)Edge[edge_count][18];
  Ric=data.getFacetCentroid(facet);
  ric= new vector(vector.subVec(Ric,data.getObs(obs)));
  //retrieve L 
  L=Edge[edge_count][20];
  
   //retrieve vector horizontal
   vh.x=Edge[edge_count][12];vh.y=Edge[edge_count][13];vh.z=Edge[edge_count][14];
  // vh1.x=Edge[adjacent_edge][12];vh1.y=Edge[adjacent_edge][13];vh1.z=Edge[adjacent_edge][14];

  r1.x=Edge[edge_count][0];r1.y=Edge[edge_count][1];r1.z=Edge[edge_count][2];
  r2.x=Edge[edge_count][3];r2.y=Edge[edge_count][4];r2.z=Edge[edge_count][5];
  vertex_1=data.getVertexIndex(r1);
  vertex_2=data.getVertexIndex(r2);
  vector r1_obs=vector.subVec(r1, data.getObs(obs));
  vector r2_obs=vector.subVec(r2, data.getObs(obs));
  //restore v for adjacent facets
   v=facet_totals[facet][1];
   v_volume=facet_totals_volume[facet][1];
   //v1=facet_totals[facet2][1];
   
  //retrieve vectors Robs as Robs1_bar,Robs2_bar
  Robs1_bar.x=vertex_extrinsic_quantities[vertex_1][0];
  Robs1_bar.y=vertex_extrinsic_quantities[vertex_1][1];
  Robs1_bar.z=vertex_extrinsic_quantities[vertex_1][2];
  Robs2_bar.x=vertex_extrinsic_quantities[vertex_2][0];
  Robs2_bar.y=vertex_extrinsic_quantities[vertex_2][1];
  Robs2_bar.z=vertex_extrinsic_quantities[vertex_2][2];
  //v_volume=vector.dot(ni,vector.divScalar(vector.addVec(Robs1_bar,Robs2_bar),2));
  //retrieve unit tangent as t_hat
  t_hat.x=Edge[edge_count][9];t_hat.y=Edge[edge_count][10];t_hat.z=Edge[edge_count][11];
  t_hat_adj.x=Edge[adjacent_edge][9];t_hat_adj.y=Edge[adjacent_edge][10];t_hat_adj.z=Edge[adjacent_edge][11];
  //Retrieve lengths Robs1,Robs2 from the extrinsic vertex structure
  Robs1_len=vertex_extrinsic_quantities[vertex_1][3];
  Robs2_len=vertex_extrinsic_quantities[vertex_2][3];
  h=vector.dot(vh,Robs1_bar);

  r1_r2=Robs1_len+Robs2_len;
  r_dash=r1_r2/2;

  Lamda=L/r1_r2;
  lamda_dash=(r1_r2-L*Lamda)/2; 

  //get normals
  ni=data.getNormal(facet);
  v_volume=vector.dot(ni,vector.divScalar(vector.addVec(Robs1_bar,Robs2_bar),2));
  v=vector.dot(ni, Robs1_bar);
  //vector ni1=data.getNormal(facet2);
  lamda=h*Lamda/(lamda_dash+Math.abs(v));
  ni_curl=vector.mulScalar(ni, math.signum(v));
   //ni_curl1=vector.mulScalar(ni1, math.signum(v1));
   
    //h term
   vector vh2ArctanL=vector.mulScalar(vh,math.AtanH_D(Lamda));
   //vh2ArctanL=vector.mulScalar(vh2ArctanL,2);
  // vector vh2ArctanL_1=vector.mulScalar(vh1,math.AtanH_D(Lamda));
   //n term
   vector bn=vector.mulScalar(ni_curl, Math.atan(lamda));
   //bn=vector.mulScalar(bn, 2);
   //util.printDouble(vector.dot(bn, Robs1_bar), "suma");
   //vector bn1=vector.mulScalar(ni_curl1, Math.atan(lamda1));
   vector_b =vector.subVec(vh2ArctanL, bn);
   
   //store this for this facet
   facet_totals[facet][2]+=vector_b.x;
      facet_totals[facet][3]+=vector_b.y;
         facet_totals[facet][4]+=vector_b.z;
   

   //accumulate for facet 1
   bij=vector.dot(vector_b, Robs1_bar);
   
   
   facet_totals[facet][0] += bij;
  // System.out.println(facet_totals[facet][0]);
   
   //implement b as in equation 8
   vector delta_b=new vector(0,0,0);      
  // Sigmab=vector.addVec( Sigmab,vector_b);
   
  //end implement equation 8
 
   //volume method for each edge
   r_bar=vector.divScalar(vector.addVec(Robs1_bar, Robs2_bar),2);
  
   double Lambda=L/(2*r_dash);  
   vector R_bar=vector.divScalar(vector.addVec(data.getVertex(vertex_1), data.getVertex(vertex_2)),2);
   vector normal=data.getNormal(facet);
   vector normal_curl = vector.mulScalar(ni, math.signum(v_volume));
  
   double LamdaStar=LamdaStar(L,rc);
   double DeltaBar=(Delta1(rc,Robs1_bar,Rc,data.getVertex(vertex_1))+Delta2(rc,Robs2_bar,Rc,data.getVertex(vertex_2)))/2;
   double deltaLamda=Lambda*DeltaBar;
   double deltaDeltaBar=deltaDelta_bar(rc,Robs1_bar,Robs2_bar,Rc,data.getVertex(vertex_1),data.getVertex(vertex_2));  

 
   double DeltaStarBar=vector.dot(vector.subVec(Rc, R_bar),vector.divScalar(rc, Math.pow(rc_magnitude,2)));
   double r_curl=r_dash*(1-Math.pow(Lambda,2))+Math.abs(v_volume);
   double lamda_vol=(h*Lambda)/r_curl;
   double rc_curl=rc_magnitude+Math.abs(v_volume);
   double lamdaStar=(h*LamdaStar)/rc_curl;
   double deltaLamdaStar=LamdaStar*DeltaStarBar;
   double lamdaCurl=(rc_magnitude*lamdaStar)/r_curl;
   double deltalamda=((lamda_vol+lamdaCurl)*DeltaBar)+(lamdaCurl*Lambda*LamdaStar);
   double lamdaCurlStar=(lamdaStar*rc_magnitude)/rc_curl;
   double deltalamdaStar=(lamdaStar+lamdaCurlStar)*DeltaStarBar;
   double deltalamdaCurl=(lamdaCurlStar*(DeltaBar*rc_magnitude+Math.pow(Lambda, 2)*r_dash))/r_curl;
   
   
   //implement 2nd option with gamma squared terms as in equation 15
   double deltaSquareLamda1=deltaLamda-deltaLamdaStar;
   double deltaSquarelamda1=deltalamda-deltalamdaStar;
 
   //implement 2nd option with gamma squared terms as in equation 15),Math.atan(lamda_vol),deltaSquareLamda(Lamda,LamdaStar,deltaDeltaBar,deltaLamda,DeltaBar, DeltaStarBar),deltaSquarelamda(lamda_vol,Lamda,LamdaStar,deltaDeltaBar,deltalamda,deltalam
  //volume_edge_anomaly=deltaBStar(vh,normal_curl, Lambda,lamda_vol,math.AtanH_D(Lambda),Math.atan(lamda_vol),deltaSquareLamda1,deltaSquarelamda1);
volume_magnetic_field_anomaly=deltaBStar(vh, Lambda,lamda_vol,math.AtanH_D(Lambda),Math.atan(lamda),normal_curl,deltaSquareLamda(Lambda,LamdaStar,deltaDeltaBar,deltaLamda,DeltaBar, DeltaStarBar),deltaSquarelamda(lamda_vol,Lamda,LamdaStar,deltaDeltaBar,deltalamda,deltalamdaCurl,lamdaStar,lamdaCurlStar,lamdaCurl,DeltaBar, DeltaStarBar));
vector_Area=data.getArea(facet);
Area=vector.magnitude(vector_Area);
//equation 21


volume_magnetic_field_anomaly=vector.addVec(volume_magnetic_field_anomaly, vector.mulScalar(ricrp(ric,rp,Ric, Rp),Area));


 sigma_ni_bij=vector.MulVec(ni,volume_magnetic_field_anomaly);
// volume_edge_anomaly=vector.dotVectorByMatrix3X3(m, sigma_ni_bij);

//accumulate edge-facet anomaly for volume
//volume_bStar_edges_anomaly=vector.addVec(volume_bStar_edges_anomaly,volume_edge_anomaly);
   
  

    potential_anomaly=vector.dot(volume_magnetic_field_anomaly, 
            Robs1_bar);
    //volume_edge_potential+=anomaly;
    
    
    facet_totals_volume[facet][0]+=potential_anomaly;
    
    
   // System.out.println(facet_totals_volume[facet][0]);
    facet_totals_volume[facet][2]+=volume_magnetic_field_anomaly.x;
      facet_totals_volume[facet][3]+=volume_magnetic_field_anomaly.y;
         facet_totals_volume[facet][4]+=volume_magnetic_field_anomaly.z;
   
         
   //implement b star, as in equation 8
   vector b_star=new vector(0.0,0.0,0.0);
   b_star=vector.mulScalar(vh, 2*LamdaStar);
   b_star=vector.subVec(b_star, vector.mulScalar(normal_curl, 2*lamdaStar));
  // b_star_total=vector.addVec(b_star, b_star_total);
   sigma_b_star_total+=vector.dot(b_star,r_bar);
   
   double deltaLamda1=Lamda-(L/(2*ric_magnitude));
   double deltalamda1=lamda-((Lambda*h)/rc_curl);
   //compute delta b
   delta_b=delta_b_star(vh,Lambda,lamda,math.AtanH_D(Lamda),math.Atn_D(lamda),normal_curl,deltaLamda1,deltalamda1);
   sigma_b_total+=vector.dot(delta_b,r_bar);   

}// end edge loop


volume_total_anomaly=new vector(0,0,0);


//facet totals
for (int i=0;i<data.getPolyFacets();i++)
    {double x,y,z,m_dot_n;
    //b volume , b1 line
    m=new vector(0.0,0.0,1);
    v=facet_totals[i][1];
    vector normal=data.getNormal(i);
    vector_Area=data.getArea(i);
    m_dot_n=vector.dot(m, normal);
    
    //restore edge-facet total field vector  for line
    b1.x=facet_totals[i][2];  b1.y=facet_totals[i][3];  b1.z=facet_totals[i][4];
    //accumulate line facet magnetic field
    b_target_total=vector.addVec(b_target_total, vector.mulScalar(b1, m_dot_n));
    
    //restore edge-facet field vector  for volume
    b.x=facet_totals_volume[i][2];  b.y=facet_totals_volume[i][3];  b.z=facet_totals_volume[i][4];
   
    //create facet magnetic field for volume
    b_volume_facet=vector.mulScalar(b, m_dot_n);
    //accumulate total vector field anomaly for volume    
    volume_total_anomaly=vector.addVec(volume_total_anomaly, b_volume_facet);

   //volume potential accumulate over facets
    volume_total_potential+=facet_totals_volume[i][0];  
    
    //compute gradient for volume 
  //    sigma_ni_bij=vector.MulVec(normal,volume_edge_anomaly);
   //   m_dot_sigma_ni_bij=vector.dotVectorByMatrix3X3(m, sigma_ni_bij);
 
   
   // b_line+= facet_totals[i][0];
    Cij += v*facet_totals[i][0];
   
   
// Create gradients Gg
       
    //line
    double[][] gradbij=new double[3][3];
    gradbij=vector.MulVec(normal,b1);
    gradbij_total=vector.addMatrix(gradbij_total,gradbij);
    //volume
    double[][] gradbij_volume=new double[3][3];
    gradbij_volume=vector.MulVec(normal,b);
    gradbij_total_volume=vector.addMatrix(gradbij_total_volume,gradbij_volume);

   // b_target_total_volume=vector.addVec(b_target_total_volume,  m_dot_sigma_ni_bij);
}//end facet loop
Cij=Cij*data.getGravity_constant()*data.getDensity_constant();
volume_total_potential=volume_total_potential*data.getGravity_constant()*data.getDensity_constant();
ric=data.getRCos();   
//double newtonian_response=vector.magnitude(vector.divScalar(ric, Math.pow(vector.magnitude(ric),3)));
double delta=vector.magnitude(ric);
delta=Math.log10(delta);
double y=-2*delta;
//double apoint=1/Math.pow(delta,2);


double newtonian=1/Math.pow(delta, 2);
double mdotb=0;

//print totals for obs
//System.out.print(  Cij+"                                   ");
//System.out.println( volume_total_potential);

//Sigmab=vector.addVec(Sigmab, b_star_total);
//double eq8=vector.dot(Sigmab, r_bar);
   //implement Sigma b , as in equation 8
double eq8=sigma_b_total+sigma_b_star_total;


    //create total magnetic field for volume
      
System.out.println( "field magnetic line");
System.out.println(b_target_total);
System.out.println( "field magnetic volume");
System.out.println(volume_total_anomaly);
System.out.println("total potential line");
System.out.println( Cij);
System.out.println("total potential volume");
System.out.println(volume_total_potential);




System.out.println("gradbij total for line");
for (int i = 0; i < gradbij_total.length; i++) {
    for (int j = 0; j < gradbij_total[i].length; j++) {
        System.out.print(gradbij_total[i][j] + " ");
    }
    System.out.println();
}


System.out.println("gradbij total for volume");
for (int i = 0; i < gradbij_total_volume.length; i++) {
    for (int j = 0; j < gradbij_total_volume[i].length; j++) {
        System.out.print(gradbij_total_volume[i][j] + " ");
    }
    System.out.println();
}





vector test=new vector(2,3,4);vector test1=new vector(3,4,5);
double v_sum=vector.magnitude(vector.addVec(test, test1));
double v_sep=vector.magnitude(test)+vector.magnitude(test1);



Cij=Math.log10(Math.abs(Cij));
 String stry=Double.toString(Cij);
        stry = stry.replaceAll("\\.", ",");

double log_delta=Math.log10(delta/L);
//System.out.println("log10(L/E) for obs :"+(obs+1)+"  = "+log_delta);
Cij_volume=0;




}// end observation  loop

}
  
 public static void main(String[] args)
{
    volumeDirected m = new volumeDirected();
    m.Facet_Loop();
    m.Extrinsic_Loop();
  } 
  
  
}
