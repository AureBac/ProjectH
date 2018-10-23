
import java.util.Vector;
import java.util.Collections;

public class Quicksort {
	int partition(Vector<Simplex> arr, int low, int high) 
    { 
        float pivot = arr.get(high).val;  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than or 
            // equal to pivot 
            if (arr.get(j).val <= pivot) 
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                 //if(arr.get(j).val== pivot) {
                	 //if(arr.get(j).dim <arr.get(high).dim) {
                		 //Collections.swap(arr, i, j);
                	 //}
                 //}
                 //else Collections.swap(arr, i, j);
                if(arr.get(i).val == pivot && arr.get(i).dim > arr.get(high).dim) {
               	 Collections.swap(arr, i, high);	 
                }
                
                else Collections.swap(arr, i, j);
                 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        Collections.swap(arr, i+1, high);
  
        return i+1; 
    } 
	
	
	
	
	
	void sort(Vector<Simplex> arr, int low, int high) 
    { 
        if (low < high) 
        { 
            /* pi is partitioning index, arr[pi] is  
              now at right place */
            int pi = partition(arr, low, high); 
  
            // Recursively sort elements before 
            // partition and after partition 
            sort(arr, low, pi-1); 
            sort(arr, pi+1, high); 
        } 
    } 
}
