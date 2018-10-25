import java.util.Vector;
import java.util.LinkedList;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SparseBoundaryMatrix {
          
    Vector<LinkedList<Integer>> sparseMatrix;
    Vector<LinkedList<Integer>> sparseMatrixR;
    

    // For understanding how the sparse representation and algorithm work
    // please refer to the pdf book excerpt we provide
    // Herbert Edelsbrunner, John Harer-Computational Topology - An Introduction 
    public SparseBoundaryMatrix(Vector<Simplex> F) {
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
        } 
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
                i2++;   
            }
            else {
                i1++;
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
    
            sparseMatrixR.set(j,new LinkedList<Integer>());
            while((L!=null&&L.size()>0)&&!sparseMatrixR.get(L.get(0)).isEmpty()) {
                    L=merge(L,sparseMatrixR.get(L.get(0)));
                
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
        }
        return mapBirthDeath;
    }
};
