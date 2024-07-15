
package loop;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class SurfaceUndirectedEdge {
    Locale currentLocale = Locale.getDefault();
    FileWriter myWriter;
    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
    String formatString = "#,###,###,##0.0000000000000000";
    DecimalFormat df = new DecimalFormat(formatString, otherSymbols);
    //global variables
    int switch_rc=2;  double e=0.00000000000000011102230246251565;
    vector rc_local = new vector(0.0, 0.0, 0.0);
    vector rc_pos = new vector(0.0, 0.0, 0.0);
    vector ric_f1 = new vector(0.0, 0.0, 0.0);
    vector ric_f2 = new vector(0.0, 0.0, 0.0);
    
    vector centroid_local= new vector(0.0,0.0,0.0);
    vector centroid_pos= new vector(0.0,0.0,0.0);
    vector target_facet_rc_local= new vector(0.0,0.0,0.0);
    vector target_facet_rc_pos= new vector(0.0,0.0,0.0);
    
    double facet_totals[][]={{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0,}};
    double [][] facet_totals_bij={{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0,}};
    double [][] facet_totals_bij8={{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0,}};
    double [][] facet_field_totals_b8={{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0,}};
    vector obs_totals_bij=new vector(0.0,0.0,0.0);
    double [][] facet_totals_bdotr={{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0,}};
    
    double [][] b_vector_sum={{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0,}};
    double facet_totals_volume[][];
    double Xaxis=0.0;
    int obs;
  Data data=new Data();
    double b_dot_r_f1=0,b_dot_r_f2=0,b_dot_r_sum=0;
  
  int target=data.getTargetModel();
  int facetA,facetB,facetC,vertexA,vertexB,vertexC,edgeA,edgeB,edgeC;
  double[][] Edge=data.getEdge_Struct();
  int branch1=0,branch2=0;
  
  public SurfaceUndirectedEdge() {
      centroid_local=data.getCentroid();
      target_facet_rc_local=data.getRCos();
      try{
     myWriter= new FileWriter("C:/users/user/Documents/MATLAB/Examples/R2022a/matlab/GS2DAnd3DPlotsExample/surface.txt");}
    //myWriter= new FileWriter("filename1.txt");}
      catch(IOException e){}
      
  }
  
  public double LamdaStar(double L,vector ric)
  {
  double result=0.0;
  
  result=L/(2*vector.magnitude(ric));
  return result;
  }
public double deltaLamdaStar(double L,vector ric,double DeltaStarBar)
  {
  double result=0.0;
  
  result=LamdaStar(L,ric)*DeltaStarBar;
  return result;
  }
 
   //start method implementation volume
  public double deltaSquareLamda(double Lamda,double LamdaStar,double deltaDeltaBar,double deltaLamda,double DeltaBar, double DeltaStarBar)
  {
  double result=0.0;
  
  result= 1/(2*(Lamda+LamdaStar)*deltaDeltaBar)+1/(2*(deltaLamda*(DeltaBar+DeltaStarBar)));
  
  
  return result;
  }
    
  public double deltaSquarelamda(double lamda,double Lamda,double LamdaStar,double deltaDeltaBar,
          double deltalamda,double deltalamdaCurl,double lamdaStar,double lamdaCurlStar,double lamdaCurl,double DeltaBar, double DeltaBarStar)
  {
  double result=0.0;
  
  result= ((deltalamda+deltalamdaCurl)*(DeltaBar+DeltaBarStar))/2+(((lamda+lamdaStar+lamdaCurl+lamdaCurlStar)*deltaDeltaBar+lamdaCurl*Lamda*LamdaStar)/2);
  
  
  return result;
  }
  
  
  public vector deltaBStar(vector h,vector n,double Lamda,double lamda,double atnh,double atn, double deltaSquareLamda,double deltaSquarelamda)
  
  {
  vector result= new vector(0.0,0.0,0.0);
  double factor1=Math.pow(Lamda,3)*atnh+deltaSquareLamda;
   double factor2=Math.pow(lamda,3)*atn+deltaSquarelamda;
  result=vector.mulScalar(h,2);
  result=vector.mulScalar(result, factor1);
  result=vector.subVec(result,vector.mulScalar(vector.mulScalar(n, 2),factor2));
 
  
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
  
  vector R_diff=new vector(0.0,0.0,0.0);
  vector r_sum=new vector(0.0,0.0,0.0);
  double factor1=0.0;double factor2=0.0;double factor3=0.0; vector factor4=new vector(0.0,0.0,0.0);  double factor41=0.0;
  double ric_mag= vector.magnitude(ric);double rp_mag=vector.magnitude(rp);
  R_diff= vector.subVec(Ric, Rp); r_sum=vector.addVec(ric, rp);
  factor1= 1/Math.pow(ric_mag,3)+1/Math.pow(rp_mag,3);
  factor2= delta_r_icp(ric,rp,Ric,Rp)/(ric_mag*rp_mag);
  factor3=(1/Math.pow(ric_mag,2))+(1/Math.pow(rp_mag,2))+ (1/(ric_mag*rp_mag));

  result = vector.divScalar(R_diff, 2);
  result=vector.mulScalar(result, factor1);
  
  factor4=vector.divScalar(r_sum, 2);
  
  factor41=delta_r_icp(ric, rp, Ric, Rp)/(ric_mag*rp_mag);
  
  factor4=vector.mulScalar(factor4,factor41);
  factor4=vector.mulScalar(factor4, factor3);
  
  result=vector.subVec(result, factor4);
   
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
  vector vector1=vector.subVec(Rc, Rj1);
  vector1=vector.divScalar(vector1,vector.magnitude(rc) );
  result = -Math.pow(vector.magnitude(vector1),2)/2;
  result=result+Math.pow(Delta1(rc,rj1,Rc,Rj1),2)/2;

   return result;
   }
   public double deltaDelta2(vector rc,vector rj2,vector Rc,vector Rj2)
   {
    double result=0.0;
  vector vector1=vector.subVec(Rc, Rj2);
  vector1=vector.divScalar(vector1,vector.magnitude(rc) );
  result = -Math.pow(vector.magnitude(vector1),2)/2;
  result=result+Math.pow(Delta2(rc,rj2,Rc,Rj2),2)/2;

   return result;
   }
   
   public double deltaDelta_bar(vector rc,vector rj1,vector rj2,vector Rc,vector Rj1,vector Rj2)
   {
   double result=0.0;
   result=deltaDelta1(rc,rj1,Rc,Rj1)+deltaDelta2(rc,rj2,Rc,Rj2);
   
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
     
     vector volume_edge_anomaly = new vector(0.0, 0.0, 0.0);
     vector volume_edge_anomaly_sum = new vector(0.0, 0.0, 0.0);
     vector b_vector_sum= new vector(0.0, 0.0, 0.0);
     vector b_vector_sum_f1= new vector(0.0, 0.0, 0.0);
     vector b_vector_sum_f2= new vector(0.0, 0.0, 0.0);
     vector b_vector_star_sum= new vector(0.0, 0.0, 0.0);
     vector b_sum_f1=new vector(0.0,0.0,0.0);
     vector b_sum_f2=new vector(0.0,0.0,0.0);
     vector b_star_f1=new vector(0.0,0.0,0.0);
     vector b_star_f2=new vector(0.0,0.0,0.0);
     vector b_star=new vector(0.0,0.0,0.0);
     //eauation 12
     vector delta_vector_b_f1= new vector(0.0, 0.0, 0.0);
     vector delta_vector_b_f2= new vector(0.0, 0.0, 0.0);
     vector delta_vector_b= new vector(0.0, 0.0, 0.0);
     vector delta_vector_b_sum_f1= new vector(0.0, 0.0, 0.0);
     vector delta_vector_b_sum_f2= new vector(0.0, 0.0, 0.0);
     vector b_star_sum= new vector(0.0, 0.0, 0.0);
     vector b_star_sum_f1= new vector(0.0, 0.0, 0.0);
     vector b_star_sum_f2= new vector(0.0, 0.0, 0.0);
     vector bij_field= new vector(0.0, 0.0, 0.0);
     vector b_vector_star_facet = new vector(0.0, 0.0, 0.0);
     vector facet_field= new vector(0.0, 0.0, 0.0);vector facet_field_sum= new vector(0.0, 0.0, 0.0);
     vector facet_field1= new vector(0.0, 0.0, 0.0);vector facet_field_sum1= new vector(0.0, 0.0, 0.0);
     double anomaly=0.0; 
     double Area=0.0;
     vector vector_Area         =   new vector(0.0, 0.0, 0.0);
     double Cij_volume=0;
     double Cij_bij=0;
   
     double delta_Lamda=0;
     double delta_Lamda_f1=0;
     double delta_Lamda_f2=0;
     double delta_lamda=0;
     double v_facet_total=0;
     double delta_lamda_f1=0;
     double delta_lamda_f2=0;
     int vertex_1=0; int vertex_2=0;
     double h=0.0,h1=0.0;
     double v=0.0,v1=0.0;double L=0.0;
     double Cij=0.0,Ai=0.0,Ai1=0.0,Ai_f1=0.0,Ai_f2=0,Cij_bj=0;
     int facet=0,facet2,facet1;
     double norm_Robs1_len,norm_Robs2_len,r0_len,max_term,l1,l2,eta,surface_ij=0.0,surface1_ij=0.0;
     double sum_hC[] = new double[data.getPolyFacets()],sum_half_Omega_bar[]=new double[data.getPolyFacets()];
     double Solid_Angle,surfaceArctanOffsetAsterisk; 

   
//obs loop
for(obs=0;obs<data.getNumberObs();obs++)
{
  Cij=0.0;
  double G_barycenter=0;
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
  double TaylorLamda,Taylorlamda=0.0,Taylorlamda1,tworc,LamdaAsterisk,Denom1,Denom2,Delta1,Delta2,Delta,Offset=0,Offset1=0,offset=0,offset1=0;
  double lamdaAsterisk,lamdaAsterisk1,sigma,lamdaCurl,lamdaCurl1;
  vector delta_b_star_volume=new vector(0.0,0.0,0.0),vector_b=new vector(0.0,0.0,0.0);
  vector vector_b_f1=new vector(0.0,0.0,0.0);vector vector_b_f2=new vector(0.0,0.0,0.0);
  vector vh = new vector(0.0, 0.0, 0.0);
  vector vh1 = new vector(0.0, 0.0, 0.0);
  vector VrcMr1 = new vector(0.0, 0.0, 0.0);
  vector VrcMr2 = new vector(0.0, 0.0, 0.0);
  vector VrcPr1 = new vector(0.0, 0.0, 0.0);
  vector VrcPr2 = new vector(0.0, 0.0, 0.0);
 
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
//System.out.println(Robs1_bar);
h=vector.dot(vh,Robs1_bar);
counter.addA("E13",3);counter.addB("E13",2);
vh1.x=Edge[adjacent_edge][12];vh1.y=Edge[adjacent_edge][13]; vh1.z=Edge[adjacent_edge][14];
h1=vector.dot(vh1,Robs1_bar);
counter.addA("E13",3);counter.addB("E13",2);
   
       
//compute Lamda
r1_r2=Robs1_len+Robs2_len;
counter.addB("E13",1);
Lamda=L/r1_r2;
lamda_dash=(r1_r2-L*Lamda)/2; 
//counter.addA("E13",1);

counter.addA("E13",1);
   

//compute term b_n

lamda=h*Lamda/(lamda_dash+Math.abs(v));
atan=Math.atan(lamda);
b_n=-Math.abs(v)*atan;

//System.out.println(lamda);
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
   // counter.addB("E13",2);
}
else
{
//compute term b_n,b_n1
//compute main b_n part


lamda             = h*Lamda/(((Robs1_len+Robs2_len)/2*(1-Math.pow(Lamda,2)))+Math.abs(v));
 // System.out.println(h);
//counter.addA("E13",3);counter.addB("E13",3);counter.addC("E13",0);
lamda1            = h1*Lamda/(((Robs1_len+Robs2_len-L*Lamda)/2)+Math.abs(v1));
//counter.addA("E13",3);counter.addB("E13",3);counter.addC("E13",0);
Taylorlamda       = Math.pow(lamda,3)*Math.atan(lamda);
//counter.addA("E13",1);counter.addB("E13",0);counter.addC("E13",2);
Taylorlamda1      = Math.pow(lamda1,3)*Math.atan(lamda1);
//counter.addA("E13",1);counter.addB("E13",0);counter.addC("E13",2);
b_n               = -2.0D * Math.abs(v) * Taylorlamda;
//counter.addA("E13",2);counter.addB("E13",0);counter.addC("E13",0);
b_n1              = -2.0D * Math.abs(v1) * Taylorlamda1;
//counter.addA("E13",2);counter.addB("E13",0);counter.addC("E13",0);
//Compute b_n offsets
//Compute lamda*
lamdaAsterisk     =(h*LamdaAsterisk)/(Math.abs(v)+vector.magnitude(rc_pos));
lamdaAsterisk1    =(h1*LamdaAsterisk)/(Math.abs(v1)+vector.magnitude(rc_pos));
//counter.addA("E13",10);counter.addB("E13",6);counter.addC("E13",2);
//Compute sigma
sigma             =(Robs1_len+Robs2_len-Math.abs(L)*Lamda)/2.0D;
//counter.addA("E13",2);counter.addB("E13",2);counter.addC("E13",0);
//Compute lamda~
lamdaCurl         =(lamdaAsterisk*vector.magnitude(rc_pos))/(Math.abs(v)+sigma);
lamdaCurl1        =(lamdaAsterisk1*vector.magnitude(rc_pos))/(Math.abs(v1)+sigma);
//counter.addA("E13",10);counter.addB("E13",6);counter.addC("E13",0);
//compute b_n offsets
offset            = ((lamda+lamdaCurl)*Delta+lamdaCurl*Lamda*LamdaAsterisk);
offset1           = ((lamda1+lamdaCurl1)*Delta+lamdaCurl1*Lamda*LamdaAsterisk);
//counter.addA("E13",6);counter.addB("E13",4);counter.addC("E13",0);
//Accumulate offsets
b_n               +=-2.0D*Math.abs(v)*offset;
b_n1              +=-2.0D*Math.abs(v1)*offset1;
counter.addA("E13",2);counter.addB("E13",2);counter.addC("E13",0);
//Add b_h,b_n
surface_ij =b_h+b_n;
surface1_ij=b_h1+b_n1;
counter.addB("E13",2);
//accumulate to facets
facet_totals[facet1][0] += surface_ij; 
facet_totals[facet2][0] += surface1_ij;
counter.addB("E13",2);             
    
}

Taylorlamda1      = Math.pow(lamda1,3)*math.Atn_D(lamda1);

//compute vector equations 6,7
//-----------------------------------------------------------------------
//System.out.println(vh1);

 vector normal_curl_f1=vector.mulScalar(data.getNormal(facet1), Math.signum(v));
 vector normal_curl_f2=vector.mulScalar(data.getNormal(facet2), Math.signum(v1));
double r_dash=(Robs1_len+Robs2_len)/2;
double Lamda_1=L/(2*r_dash);
//System.out.println(Lamda_1);


double r_curl_f1=r_dash*(1-Math.pow(Lamda_1, 2))+Math.abs(v);
double r_curl_f2=r_dash*(1-Math.pow(Lamda_1, 2))+Math.abs(v1);


double lamda_1_f1=(h*Lamda_1)/r_curl_f1;
double lamda_1_f2=(h1*Lamda_1)/r_curl_f2;
//System.out.println(lamda_1_f2);


//face 1
vector_b_f1=vector.mulScalar(vh, 2*math.AtanH_D(Lamda_1));
vector_b_f1=vector.subVec(vector_b_f1,vector.mulScalar(data.getNormal(facet1), 2*Math.signum(v)*Math.atan(lamda_1_f1))); 

//face 2
vector_b_f2=vector.mulScalar(vh1, 2*math.AtanH_D(Lamda_1));
vector_b_f2=vector.subVec(vector_b_f2, vector.mulScalar(data.getNormal(facet2), 2*Math.signum(v1)*Math.atan(lamda_1_f2)));   
//System.out.println(vector_b_f1);
//Create facet field

//facet_totals_bij[facet1][0]+=vector_b_f1.x;
//facet_totals_bij[facet1][1]+=vector_b_f1.y;
//facet_totals_bij[facet1][2]+=vector_b_f1.z;
//facet_totals_bij[facet2][0]+=vector_b_f2.x;
//facet_totals_bij[facet2][1]+=vector_b_f2.y;
//facet_totals_bij[facet2][2]+=vector_b_f2.z;
 //System.out.println(facet_totals_bij[facet1][0]);

//  System.out.println(facet_totals_bij[facet1][0]);
//    System.out.println("---------------------------");
//compute Cij from bj
b_dot_r_f1=vector.dot(vector_b_f1, vector.divScalar(vector.addVec(Robs1_bar, Robs2_bar),2));
b_dot_r_f2=vector.dot(vector_b_f2, vector.divScalar(vector.addVec(Robs1_bar, Robs2_bar),2));

//accumulate per facet
//facet_totals_bdotr[facet1][0]+=b_dot_r_f1;
//facet_totals_bdotr[facet2][0]+=b_dot_r_f2;

//--------------------------------------------------------
 //equation 8-12
   
 vector Ric_f1=data.getRCos_Facet(facet1);
 vector Ric_f2=data.getRCos_Facet(facet2);
 
 
 
 Ric_f1=data.getFacetCentroid(facet1);
 Ric_f2=data.getFacetCentroid(facet2);
 //Ric_f1=data.getCentroid();
 //Ric_f2=data.getCentroid();
 
 ric_f1=vector.subVec(Ric_f1,data.getObs(obs));
 ric_f2=vector.subVec(Ric_f2,data.getObs(obs));

 //double ric_magnitude_f1=vector.magnitude(ric_f1);
 //double ric_magnitude_f2=vector.magnitude(ric_f2);
 double LamdaStar_f1=L/(2*vector.magnitude(ric_f1));
 double LamdaStar_f2=L/(2*vector.magnitude(ric_f2));
 //double ric_curl_f1=ric_magnitude_f1+Math.abs(v);
 //double ric_curl_f2=ric_magnitude_f2+Math.abs(v1);
 //System.out.println(Lamda_1);
 delta_Lamda_f1=Lamda_1-LamdaStar_f1;
 delta_Lamda_f2=Lamda_1-LamdaStar_f2;
 
 double rc_curl_f1=vector.magnitude(ric_f1)+Math.abs(v);
 double rc_curl_f2=vector.magnitude(ric_f2)+Math.abs(v1);
 
 double lamdaStar_f1=(h*LamdaStar_f1)/rc_curl_f1;
 double lamdaStar_f2=(h1*LamdaStar_f2)/rc_curl_f2;
 
 delta_lamda_f1=lamda_1_f1-lamdaStar_f1;  
 delta_lamda_f2=lamda_1_f2-lamdaStar_f2;
 //System.out.println(lamda_1_f2);
 //System.out.println(vh);
  //compute delta b for facets 1,2
 delta_vector_b_f1=vector.mulScalar(vh, 2*Math.pow(Lamda_1, 3)*math.AtanH_D(Lamda_1)+delta_Lamda_f1);
 delta_vector_b_f1= vector.subVec(delta_vector_b_f1, vector.mulScalar(data.getNormal(facet1), 2*Math.pow(lamda_1_f1, 3)*Math.atan(lamda_1_f1)+delta_lamda_f1));
 delta_vector_b_f2=vector.mulScalar(vh1, 2*Math.pow(Lamda_1, 3)*math.AtanH_D(Lamda_1)+delta_Lamda_f2);
 delta_vector_b_f2= vector.subVec(delta_vector_b_f2, vector.mulScalar(data.getNormal(facet2), 2*Math.pow(lamda_1_f2, 3)*Math.atan(lamda_1_f2)+delta_lamda_f2));
 
//System.out.println(vector.addVec(delta_vector_b_f1,delta_vector_b_f2));

//Make sum for f1,f2
 delta_vector_b_sum_f1=vector.addVec(delta_vector_b_sum_f1, delta_vector_b_f1);
 delta_vector_b_sum_f2=vector.addVec(delta_vector_b_sum_f2, delta_vector_b_f2);
// System.out.println(vector.addVec(delta_vector_b_sum_f1, delta_vector_b_sum_f2));
 //compute b star for facets 1,2
 b_star_f1=vector.mulScalar(vh, 2*LamdaStar_f1);
 b_star_f1=vector.subVec(b_star_f1, vector.mulScalar(data.getNormal(facet1), 2*lamdaStar_f1));
 b_star_f2=vector.mulScalar(vh1, 2*LamdaStar_f2);
 b_star_f2=vector.subVec(b_star_f2, vector.mulScalar(data.getNormal(facet2), 2*lamdaStar_f2));
 
 vector bMinusbStar1=vector.subVec(vector_b_f1,b_star_f1);
 vector bMinusbStar2=vector.subVec(vector_b_f2,b_star_f2);
 vector bMinusbStar=vector.addVec(bMinusbStar1, bMinusbStar2);
//System.out.println(bMinusbStar);
//b_star_f1=vector.mulScalar(normal_curl_f1, 2*vector.magnitude(data.getArea(facet1))/(vector.magnitude(ric_f1)*rc_curl_f1));
//b_star_f2=vector.mulScalar(normal_curl_f2, 2*vector.magnitude(data.getArea(facet2))/(vector.magnitude(ric_f2)*rc_curl_f2));
  //Sum of b star vectors for facets 1,2
 b_star_sum_f1=vector.addVec( b_star_sum_f1, b_star_f1);
 b_star_sum_f2=vector.addVec( b_star_sum_f2, b_star_f2);
 
  //Sum bij for facets 1,2
 b_vector_sum_f1=vector.addVec(delta_vector_b_sum_f1, b_star_sum_f1);
 b_vector_sum_f2=vector.addVec(delta_vector_b_sum_f2, b_star_sum_f2);
 //System.out.println(b_vector_sum_f1);
  //Create facet field 
 //facet_field_totals_b8[facet1][0]+= b_vector_sum_f1.x;
 //facet_field_totals_b8[facet1][1]+= b_vector_sum_f1.y;
// facet_field_totals_b8[facet1][2]+= b_vector_sum_f1.z;
// facet_field_totals_b8[facet2][0]+= b_vector_sum_f2.x;
// facet_field_totals_b8[facet2][1]+= b_vector_sum_f2.y;
 //facet_field_totals_b8[facet2][2]+= b_vector_sum_f2.z;
 //System.out.println(facet_field_totals_b8[facet1][2]);
 //System.out.println(facet_field_totals_b8[facet2][2]); 
 //System.out.println("----------------------------------");
 //create gravity potential for equation 8
 //b_dot_r_f1=vector.dot(b_vector_sum_f1, vector.divScalar(vector.addVec(Robs1_bar, Robs2_bar),2));
 //b_dot_r_f2=vector.dot(b_vector_sum_f2, vector.divScalar(vector.addVec(Robs1_bar, Robs2_bar),2));
 //accumulate the potential for equation 8
// facet_totals_bij8[facet1][0]+= b_dot_r_f1;
 //facet_totals_bij8[facet2][0]+= b_dot_r_f2;

 //Start volume method
    //compute for facet 1
   facet=facet1;
   vector r_bar=vector.divScalar(vector.addVec(Robs1_bar, Robs2_bar),2);
   rc_local=target_facet_rc_local;
   vector Rp=data.getCentroid();
   vector normal = new vector(0.0, 0.0, 0.0);
   vector rp=vector.subVec(data.getObs(obs),Rp);
   Ai_f1=vector.magnitude(data.getArea(facet1))/2;
   Ai_f2=vector.magnitude(data.getArea(facet2))/2;
   double DeltaBar=(deltaDelta1(ric_f1,Robs1_bar,Ric_f1,data.getVertex(vertex_1))+deltaDelta2(ric_f1,Robs2_bar,Ric_f1,data.getVertex(vertex_2)))/2;
   double deltaLamda=Lamda_1*DeltaBar;
   double deltaDeltaBar=deltaDelta_bar(ric_f1,Robs1_bar,Robs2_bar,Ric_f1,data.getVertex(vertex_1),data.getVertex(vertex_2));  
   //Lambda is same as Lamda 
  
   vector R_bar=vector.divScalar(vector.addVec(data.getVertex(vertex_1), data.getVertex(vertex_2)),2);
   double DeltaStarBar=vector.dot(vector.subVec(rc_local, R_bar),vector.divScalar(rc_local, Math.pow(vector.magnitude(rc_local),2)));
 
   double lamda_vol_f1=(h*Lamda_1)/r_curl_f1;
   double lamda_vol_f2=(h1*Lamda_1)/r_curl_f2;
   
   double lamdaCurl_volume=(vector.magnitude(rc_local)*lamdaStar_f1)/r_curl_f1;
   double deltalamda=(lamda_vol_f1+lamdaCurl_volume)*DeltaBar+lamdaCurl_volume*Lamda_1*LamdaStar_f1;
   
   double lamdaCurlStar=(lamdaStar_f1*vector.magnitude(ric_f1))/rc_curl_f1;
   double deltalamdaStar=(lamdaStar_f1+lamdaCurlStar)*DeltaStarBar;
   double deltalamdaCurl=lamdaCurlStar*(DeltaBar*vector.magnitude(ric_f1)+Math.pow(Lamda_1, 2)*vector.magnitude(r_bar));
 
   normal=data.getNormal(facet1);
   int facetvertex_index=data.getFacetVertex_index(facet, 0);
   delta_b_star_volume=deltaBStar(vh,normal, Lamda_1,lamda_vol_f1,math.AtanH_D(Lamda_1),Math.atan(lamda_vol_f1), deltaSquareLamda(lamda_vol_f1,LamdaStar_f1,deltaDeltaBar,deltaLamda,DeltaBar, DeltaStarBar),deltaSquarelamda(lamda_vol_f1,Lamda_1,LamdaStar_f1,deltaDeltaBar,
   deltalamda,deltalamdaCurl,lamdaStar_f1,lamdaCurlStar,lamdaCurl_volume,DeltaBar,DeltaStarBar));
   volume_edge_anomaly=delta_b_star_volume;
   volume_edge_anomaly=vector.addVec(volume_edge_anomaly, vector.mulScalar(ricrp(ric_f1,rp,Ric_f1, Rp),Ai_f1));
   anomaly=vector.dot(volume_edge_anomaly, r_bar);
   facet_totals_volume[facet1][0]+=anomaly;
  
   //compute for facet 2
   facet=facet2;
   normal=data.getNormal(facet2);
   lamda_vol_f2=(h1*Lamda_1)/r_curl_f2;
   //r_curl_f2=vector.magnitude(ric_f2)+Math.abs(v1);
   lamdaStar_f2=(h1*LamdaStar_f2)/r_curl_f2;
   //lamdaCurl=(vector.magnitude(ric_f2)*lamdaStar_f2)/r_curl_f2;
   deltalamda=(lamda_vol_f2+lamdaCurl_volume)*DeltaBar+lamdaCurl_volume*Lamda_1*LamdaStar_f2;
   lamdaCurlStar=(lamdaStar_f2*vector.magnitude(ric_f2))/rc_curl_f2;
   //DeltaStarBar=vector.dot(vector.subVec(Ric_f2, R_bar),vector.divScalar(ric_f2, Math.pow(vector.magnitude(ric_f2),2)));
   deltalamdaStar=(lamdaStar_f2+lamdaCurlStar)*DeltaStarBar;
   deltalamdaCurl=lamdaCurlStar*(DeltaBar*vector.magnitude(ric_f2)+Math.pow(Lamda_1, 2)*vector.magnitude(r_bar));
   facetvertex_index=data.getFacetVertex_index(facet, 0);
   //Ric_f2=data.getVertex(facetvertex_index);
   
   delta_b_star_volume=deltaBStar(vh1,normal, Lamda_1,lamda_vol_f2,math.AtanH_D(Lamda_1),Math.atan(lamda_vol_f2), deltaSquareLamda(Lamda_1,LamdaStar_f2,deltaDeltaBar,deltaLamda,DeltaBar, DeltaStarBar),deltaSquarelamda(lamda_vol_f2,Lamda_1,LamdaStar_f2,deltaDeltaBar,
   deltalamda,deltalamdaCurl,lamdaStar_f2,lamdaCurlStar,lamdaCurl_volume,DeltaBar,DeltaStarBar));
   volume_edge_anomaly=delta_b_star_volume;
   
   volume_edge_anomaly_sum=vector.addVec(volume_edge_anomaly_sum, volume_edge_anomaly);
   volume_edge_anomaly=vector.addVec(volume_edge_anomaly, vector.mulScalar(ricrp(ric_f2,rp,Ric_f2, Rp),Ai_f2));
   anomaly=vector.dot(volume_edge_anomaly, r_bar);
   facet_totals_volume[facet2][0]+=anomaly;
}// end edge loop

 vector sigma_b_sum=new vector(0.0,0.0,0.0);
 //start facet loop
for (int i=0;i<data.getPolyFacets();i++)
{ 
vector sigma_b=new vector(0.0,0.0,0.0);   
//vector ric=data.getFacetCentroid(i);
vector obs_point=data.getObs(obs);
//ric=vector.subVec(ric, obs_point);
v=facet_totals[i][1];
v_facet_total+=v;
//double ric_magnitude=vector.magnitude(ric);
//double ric_curl=ric_magnitude+Math.abs(v);
vector n_curl=vector.mulScalar(data.getNormal(i), Math.signum(v));
Ai=vector.magnitude(data.getArea(i))/2;
//counter.addA("E14",4);counter.addB("E14",2);counter.addC("E14",1);
//Branching for triangulated models 
if(data.getTargetModel()==2 || data.getTargetModel()==3 )
{ //branch1=triangulated polyhedra, solid angle computation
  Solid_Angle           = math.Oosterom1(data.getPolyVertex(i,1), data.getPolyVertex(i,2),data.getPolyVertex(i,3),data.getObs(obs),v,Ai);
  counter.addA("E14",0);counter.addB("E14",0);counter.addC("E14",1);  
//accumulate solid angle 
  facet_totals       [i][0]    += (-v)*Solid_Angle+(2*Ai/vector.magnitude(rc_pos));
  facet_totals_volume[i][0]    += (-v)*Solid_Angle+(2*Ai/vector.magnitude(rc_pos));
  //facet_totals_bij8[i][0]       += (-v)*Solid_Angle+(2*Ai/vector.magnitude(rc_pos));
  //counter.addA("E14",6);counter.addB("E14",4);counter.addC("E13",1);

}
else
{
//branch2=polygonal polyhedra 
  surfaceArctanOffsetAsterisk = 2.0D*Ai/(vector.magnitude(rc_pos)+Math.abs(v));
  //counter.addA("E14",5);counter.addB("E14",3);counter.addC("E14",1);
//accumulate facet area offsets
facet_totals       [i][0]     += surfaceArctanOffsetAsterisk;
facet_totals_volume[i][0]     += surfaceArctanOffsetAsterisk;
//facet_totals_bdotr [i][0]     += surfaceArctanOffsetAsterisk;
}
//sigma_b=vector.mulScalar(n_curl, -2*Ai/(ric_magnitude*ric_curl));
//sigma_b_sum=vector.addVec(sigma_b_sum, sigma_b);

//facet_field = new vector(facet_totals_bij[i][0],facet_totals_bij[i][1],facet_totals_bij[i][2]);
//facet_field1 = new vector(facet_field_totals_b8[i][0],facet_field_totals_b8[i][1],facet_field_totals_b8[i][2]);

//multiply gravity potential by v/2
Cij                            +=   v*facet_totals[i][0]/2;
//Cij_bj                         +=   v*facet_totals_bdotr[i][0]/2;
//Cij_bij                        +=   v*facet_totals_bij8[i][0]/2;
//G_barycenter                   +=   data.getTargetVolume()/vector.magnitude(centroid_pos);
Cij_volume                     +=   v*facet_totals_volume[i][0]/2;

//facet_field_sum=vector.addVec(facet_field_sum, facet_field);
//facet_field_sum1=vector.addVec(facet_field_sum1, facet_field1);


//obs_totals_bij= vector.addVec(obs_totals_bij,new vector(facet_totals_bij[i][0],facet_totals_bij[i][1],facet_totals_bij[i][2]));


}//close facet loop

//facet_field_sum1=vector.addVec(facet_field_sum1, sigma_b_sum);
//With paramenters for gravity and density
Cij=Cij*data.getGravity_constant()*data.getDensity_constant();
Cij_volume=Cij_volume*data.getGravity_constant()*data.getDensity_constant();
//correct result
//Cij_bj=Cij_bj*data.getGravity_constant()*data.getDensity_constant();

//Cij_bij=Cij_bij*data.getGravity_constant()*data.getDensity_constant();


//vol_s=Math.log10(vol_s);
//vertex_s=-Math.log10(Math.abs(vertex_s));
//line_s=-Math.log10(Math.abs(line_s));
//surface_s=-Math.log10(Math.abs(surface_s));


//System.out.println(sigma_vector_b);
System.out.println("Gravity potentia¨Cij with the Surface method          : "+Cij);
//System.out.println("Equation 6 potential Σbj                              : "+Cij_bj);

//System.out.println("Equation 8 potential Σbj                              : "+Cij_bij);
//System.out.println("Volume method  potential                              : "+Cij_volume );


//System.out.println("Equation 6     Σbj                                    : "+facet_field_sum);
//System.out.println("Equations 8-13 Σbj                                    : "+facet_field_sum1);

}//obs end


}//end class


public static void main(String[] args)
{
    SurfaceUndirectedEdge m = new SurfaceUndirectedEdge();
    m.Facet_Loop();
    m.Extrinsic_Loop();

  }
}