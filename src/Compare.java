import java.util.Comparator;

public class Compare implements Comparator<Simplex> {
       public int compare( Simplex a, Simplex b) {
    	   float val= a.val-b.val;
    	   if(val!=0.) {
    		   return Float.compare(a.val, b.val);
    	   }
    	   int dim= a.dim-b.dim;
    	   return dim;
       }
}
