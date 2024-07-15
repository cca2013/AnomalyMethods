package loop;




public class counter {

  static String E11= "extrinsic vertex loop          :";
  static String E12= "Pre-edge facet loop            :";
  static String E13= "Edge loop                      :";
  static String E131="Euler edge loop                :";
  static String E14= "Post-edge facet loop           :";
  static String E15= "Body counts                    :";


  static String A=   "Multiplications-Divisions      :";
  static String B=   "Additions-Subtractions         :";
  static String C=   "Function calls                 :";

  static int A_E11;//vertex loop,multiplication-division
    static int A_E12;//pre-edge loop,multiplication,division
      static int A_E13;//edge loop,multiplication,division
          static int A_E131;//euler edge loop,multiplication,division
              static int A_E14;//post-edge loop,multiplication,division
              static int A_E15;//body count,multiplication,division


  static int B_E11;//vertex loop,addition-subtraction
   static int B_E12;//pre-edge loop,addition-subtraction
     static int B_E13;//edge loop,addition-subtraction
         static int B_E131;//euler edge loop,addition-subtraction
             static int B_E14;//post-edge loop,addition-subtraction
             static int B_E15;//body count,addition-subtraction

  static int C_E11;//vertex loop,function calls
   static int C_E12;//pre-edge loop,function calls
     static int C_E13;//edge loop,function calls
         static int C_E131;//euler edge loop,function calls
             static int C_E14;//post-edge loop,function calls
             static int C_E15;//body count,function calls




  static void addA(String s,int n)
  {
   if (s=="E11"){A_E11+=n;} if (s=="E12"){A_E12+=n;}
   if (s=="E13"){A_E13+=n;} if (s=="E131"){A_E131+=n;}
   if (s=="E14"){A_E14+=n;}if (s=="E15"){A_E15+=n;}

 }


  static void addB(String s,int n)
  {if (s=="E11"){B_E11+=n;} if (s=="E12"){B_E12+=n;}
   if (s=="E13"){B_E13+=n;} if (s=="E131"){B_E131+=n;}
   if (s=="E14"){B_E14+=n;}if (s=="E15"){B_E15+=n;}}

  static void addC(String s,int n)
  {if (s=="E11"){C_E11+=n;} if (s=="E12"){C_E12+=n;}
   if (s=="E13"){C_E13+=n;} if (s=="E131"){C_E131+=n;}
   if (s=="E14"){C_E14+=n;}if (s=="E15"){C_E15+=n;} }


 static void addA(String s)
 {if (s=="E11"){A_E11++;} if (s=="E12"){B_E12++;}
  if (s=="E13"){A_E13++;} if (s=="E131"){A_E131++;}
  if (s=="E14"){A_E14++;}  if (s=="E15"){A_E15++;}}

static void addB(String s)
{if (s=="E11"){B_E11++;} if (s=="E12"){B_E11++;}
 if (s=="E13"){B_E13++;} if (s=="E131"){B_E131++;}
 if (s=="E14"){B_E14++;}if (s=="E15"){B_E15++;} }

  static void addC(String s)
  {if (s=="E11"){C_E11++;} if (s=="E12"){C_E12++;}
   if (s=="E13"){C_E13++;} if (s=="E131"){C_E131++;}
   if (s=="E14"){C_E14++;}if (s=="E15"){C_E15++;}
}
 static void PrintEulerCountAnalytics()
{System.out.println();System.out.println("Operations count per V,E,F:  ");
  System.out.println("=================================");
PrintVEF();
}


static void PrintCountAnalytics()
{System.out.println();System.out.println("Operations Analytical count:  ");
  System.out.println("=================================");
PrintA();PrintB();PrintC();
}
static void PrintVEF()
{int total=A_E11+B_E11+C_E11+A_E12+B_E12+C_E12+A_E14+A_E15+B_E14+B_E15+C_E14+C_E15+A_E13+B_E13+C_E13+A_E131+B_E131+C_E131;
  System.out.println();System.out.println("Vertex-Facet-Edge operation count");
  System.out.println("=================================");
  System.out.println("Vertex        :"+(A_E11+B_E11+C_E11));
  System.out.println("Facet         :"+(A_E12+B_E12+C_E12+A_E14+B_E14+C_E14));
  System.out.println("Edge          :"+(A_E13+B_E13+C_E13+A_E131+B_E131+C_E131));
  System.out.println("Body counts   :"+(A_E15+B_E15+C_E15));
  System.out.println("=================================");
  System.out.println("Total   :"+total);System.out.println("=================================");

}

static void PrintA()
{System.out.println();System.out.println(A);System.out.println("=================================");
  System.out.println(E11+A_E11);
  System.out.println(E12+A_E12);
  System.out.println(E13+A_E13);
  System.out.println(E131+A_E131);
  System.out.println(E14+A_E14);
  System.out.println(E15+A_E15);


}
static void PrintB()
{System.out.println();System.out.println(B);System.out.println("=================================");
  System.out.println(E11+B_E11);
   System.out.println(E12+B_E12);
   System.out.println(E13+B_E13);
   System.out.println(E131+B_E131);
   System.out.println(E14+B_E14);
System.out.println(E15+B_E15);

}
static void PrintC()
{System.out.println();
System.out.println(C);System.out.println("=================================");
   System.out.println(E11+C_E11);
   System.out.println(E12+C_E12);
   System.out.println(E13+C_E13);
   System.out.println(E131+C_E131);
   System.out.println(E14+C_E14);System.out.println(E15+C_E15);


}
static void PrintCountTotals()
{int sumA=0,sumB=0,sumC=0,sum_totals=0;
  sumA=A_E11+A_E12+A_E13+A_E131+A_E14+A_E15;
  sumB=B_E11+B_E12+B_E13+B_E131+B_E14+B_E15;
  sumC=C_E11+C_E12+C_E13+C_E131+C_E14+C_E15;sum_totals=sumA+sumB+sumC;
  System.out.println();
  System.out.println("ABC operation count   :");System.out.println("=================================");
 System.out.println(A+sumA);System.out.println(B+sumB);System.out.println(C+sumC);
 System.out.println("=================================");
 System.out.println("Total    :"+sum_totals);


}
}
