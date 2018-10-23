import java.util.Vector;

public class BoundaryMatrix {
       int[][] matrix;
       //Vector<Integer> lowsList=initialLows();
       Vector<Integer> pivotsToFind;
       Vector<Integer> pivotsFound;
       
       
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
    	   }
    	   
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
