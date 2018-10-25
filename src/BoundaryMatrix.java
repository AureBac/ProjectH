import java.util.Vector;
import java.util.LinkedList;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class BoundaryMatrix {
       int[][] matrix;
       //Vector<Integer> lowsList=initialLows();
       Vector<Integer> pivotsToFind;
       Vector<Integer> pivotsFound;
       
       
       Vector<LinkedList<Integer>> sparseMatrix;
       Vector<LinkedList<Integer>> sparseMatrixR;
       
       
       public BoundaryMatrix(int N, Vector<Simplex> F) {
    	   matrix=new int [N][N];
    	   pivotsToFind=new Vector<Integer>();
    	   pivotsFound=new Vector<Integer>();
    	   for(int i=0; i<N; i++) {
    		   for(int j=0; j<N;j++) 
    			   matrix[i][j]=0;
    		   
    		   //pivotsToFind=new Vector<Integer>();
    		   pivotsToFind.add(i);
    		   pivotsFound.add(null);
    		   //pivotsFound=new Vector<Integer>();
    	   }
    	   
    	   for(int i=0; i<N; i++) {
    		   int dimRow=F.get(i).dim;
    		   for(int j=i+1; j<N; j++) {
    			   int dimColumn=F.get(j).dim;
    			   if(dimColumn==dimRow+1) {
    			   boolean containsAllVertexes=true;
    			   for(Integer vertex: F.get(i).vert) {
    				   if(!F.get(j).vert.contains(vertex)) {
    					   containsAllVertexes=false;
    					   break;
    				   }
    			   }
    			   if(containsAllVertexes) {
    				   matrix[i][j]=1;
    			   }
    			 }  
    		   }
    		   System.out.println("i= "+i);
    	   }
    	   
       }
       
       public BoundaryMatrix(Vector<Simplex> F) {
    	   sparseMatrix=new Vector<LinkedList<Integer>>();
    	   sparseMatrixR=new Vector<LinkedList<Integer>>();
    	   int N=F.size();
    	   for(int i=0; i<N; i++) {
    		   sparseMatrix.add(new LinkedList<Integer>());
    		   sparseMatrixR.add(new LinkedList<Integer>());
    	   }
    	   for(int i=0; i<N; i++) {
    		   int dimRow=F.get(i).dim;
    		   for(int j=i+1; j<N; j++) {
    			   int dimColumn=F.get(j).dim;
    			   if(dimColumn==dimRow+1) {
    			   boolean containsAllVertexes=true;
    			   for(Integer vertex: F.get(i).vert) {
    				   if(!F.get(j).vert.contains(vertex)) {
    					   containsAllVertexes=false;
    					   break;
    				   }
    			   }
    			   if(containsAllVertexes) {

    				   sparseMatrix.get(j).add(0,i);
    				   sparseMatrixR.get(j).add(0,i);
    				   
    			   }
    			 }  
    		   }
    	      System.out.println("i= "+i);
    		  
    	   }
    	   //for(int i=0; i<N; i++) {
    		   //if(!sparseMatrix.get(i).isEmpty()) {
    			   //System.out.println(sparseMatrix.get(i));
    		   //}
    	   //}
    }
       
       public Vector<Integer> initialLows(){
    	   Vector<Integer> v= new Vector<Integer>();
    	   for(int j=0; j<matrix.length; j++) {
    		   int low=-1;
    		   for(int i=0; i<matrix.length; i++) {
    			   if(matrix[i][j]!=0) {
    				   low=i;
    			   }
    		   }
    		   //v.set(j, low);
    		   v.add(j,low);
    	   }
    	   
    	   return v;
       }
       
       
       public void Reduction() {
    	   int row = matrix.length-1;
    	   while (row>=0) {
    		   Vector<Integer> lows =equalLowsList(row);
    		   if(lows.size()>=1) {
    			   int left=lows.get(0);
    			   pivotsFound.set(left,row);
    			   lows.remove(0);
    			   if(lows.size()>0) {
    			   for(int j: lows) {
    				   double factor=matrix[row][j]/matrix[row][left];
    				   for(int i=row; i>=0;i--) {
    					   matrix[i][j]= (int) (matrix[i][j]-factor*matrix[i][left]); //complete
    					   if(matrix[i][j]==-1) {
    						   matrix[i][j]=1;
    					   }
    				   }
    			   }
    			  } 
    			   boolean remove= pivotsToFind.removeElement( left);
    		   }
    		   
    		   System.out.println("j= "+row);
    		   
    		   row--;
    		   
    		   
    	   }
       }
       
       public void barCode(Vector<Simplex> F) {
    	   String lines="";
    	   for (int j=0; j<matrix.length; j++) {
    		   if(pivotsFound.get(j)!=null) {
    			   int i= pivotsFound.get(j);
    			   float value_i=F.get(i).val;
    			   float value_j=F.get(j).val;
    			   if(value_i!=value_j) {
    				   lines=lines+F.get(i).dim+" ";
    				   lines=lines+value_i+" ";
    				   lines=lines+value_j;
    				   System.out.println(lines);
    				   lines="";
    				   
        			   
    			   }
    			   
    		   }
    		   else {
    			   boolean rightValueFound=false;
    			   for(int k=j+1; k<matrix.length; k++) {
    				   if(pivotsFound.get(k)!=null&&pivotsFound.get(k)==j) {
    					   rightValueFound=true;
    					   //float leftValue=F.get(j).val;
    					   //float rightValue=F.get(k).val;
    					   //if(leftValue!=rightValue) {
    	    				   //lines=lines+F.get(j).dim+" ";
    	    				   //lines=lines+leftValue+" ";
    	    				   //lines=lines+rightValue;
    	    				   //System.out.println(lines);
    	    				   //lines="";
    	    				   
    	        			   
    	    			   //}
    					   break;
    					   
    				   }
    			   }
    			   if(!rightValueFound) {
    				   float leftValue=F.get(j).val;
    				   lines=lines+F.get(j).dim+" ";
    				   lines=lines+leftValue+" ";
    				   lines=lines+"inf";
    				   System.out.println(lines);
    				   lines="";
    			   }
    		   }
    		   
    		   
    		   
    		   System.out.println("k= "+j);
    		   
    	   }
       }
       
       public Vector<Integer> equalLowsList(int row){
    	   Vector<Integer> v= new Vector<Integer>();
    	   
    	   for(Integer column: pivotsToFind) {
    		   if(matrix[row][column]!=0) {
    			   v.add(column);
    		   }
    	   }
    	   return v;
       }
       
       
       public LinkedList<Integer> merge(LinkedList<Integer> l1, LinkedList<Integer> l2){
    	   LinkedList<Integer> list=new LinkedList<Integer>();
    	   int size1= l1.size();
    	   int size2= l2.size();
    	   int i1=0;
    	   int i2=0;
    	   Vector<Integer> toRemove=new Vector<Integer>();
    	   while(i1<size1||i2<size2) {
    		   if(i1>=size1) {
    			   list.add(l2.get(i2));
    			   //toRemove.add(i2);
    			   i2++;
    		   }
    		   else if(i2>=size2) {
    			   list.add(l1.get(i1));
    			   i1++;
    		   }
    		   else if(l1.get(i1)>l2.get(i2)) {
    		       list.add(l1.get(i1));
    		       i1++;   
    		   }
    		   else if(l2.get(i2)>l1.get(i1)) {
    			   list.add(l2.get(i2));
    			   //toRemove.add(i2);
    		       i2++;   
    		   }
    		   // equalitity
    		   else {
    			   i1++;
    			   //toRemove.add(i2);
    			   i2++;
    		   }
    		   

    	   
           }
    	   for(int i=toRemove.size()-1; i>=0; i--) {
    		   l2.remove((int) toRemove.get(i));
    	   }
    	   return list;
       }
       
       
       public LinkedHashMap<Integer, Integer> sparseReduction(Vector<Simplex> F) {
    	   LinkedHashMap<Integer, Integer> mapBirthDeath=new LinkedHashMap<>();
    	   for(int j=0; j<F.size();j++) {
    		   LinkedList<Integer> L= (LinkedList<Integer>) sparseMatrix.get(j).clone();
    		   //System.out.println("sparse");
    		   //for(LinkedList<Integer> ls: sparseMatrix) {
    			   //System.out.println(ls);
    		   //}
    		   sparseMatrixR.set(j,new LinkedList<Integer>());
    		   while((L!=null&&L.size()>0)&&!sparseMatrixR.get(L.get(0)).isEmpty()) {
    			   //if(!sparseMatrixR.get(L.get(0)).isEmpty()) {
    			      L=merge(L,sparseMatrixR.get(L.get(0)));
    			   //}
    			   
    		   }
    		   
    		   
    		   
    		   if(L!=null&&L.size()>0) {
    			   if(F.get(j).val>F.get(L.get(0)).val){
    				   mapBirthDeath.put(L.get(0),j);
    			   }
    			   else {
    				   mapBirthDeath.remove(L.get(0));
    			   }
    			   sparseMatrixR.set(L.get(0),L);
    		   }
<<<<<<< HEAD
    		   //System.out.println("decimacol");
    		   //System.out.println(sparseMatrixR.get(8));
    		   
    	   }
    	   for(int i=0; i<sparseMatrixR.size();i++) {
    		   if(sparseMatrixR.get(i)!=null&&sparseMatrixR.get(i).size()>0) {
    			   System.out.println(i);
    			   System.out.println(sparseMatrixR.get(i));
=======
    		   else {
    			   mapBirthDeath.put(j,-1);
>>>>>>> ab39ffa93fe797b3a6ec0354de81d8730df0df45
    		   }
    		   //System.out.println("decimacol");
    		   //System.out.println(sparseMatrixR.get(8));
    		   
    		   System.out.println("j= "+j);
    		   
    	   }
    	   
    	   //System.out.println(mapBirthDeath);
    	   return mapBirthDeath;
    	   //for(int i=0; i<sparseMatrixR.size();i++) {
    		   //if(sparseMatrixR.get(i)!=null&&sparseMatrixR.get(i).size()>0) {
    			   //System.out.println(i);
    			   //System.out.println(sparseMatrixR.get(i));
    		   //}
    		   
    	   //}
       }
       
       
       
       public String toString() {
    	   int n = matrix.length;
    	   String m ="";
    	   for(int i=0; i<n; i++) {
    		   for(int j=0; j<n; j++) {
    			   m=m+matrix[i][j]+"  ";
    		   }
    		   m=m+"\n";
    	   }
    	   return m;
       }
       
}
