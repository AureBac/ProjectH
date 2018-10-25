import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;


import java.util.Collections;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class ReadFiltration {

	static Vector<Simplex> readFiltration (String filename) throws FileNotFoundException {
		Vector<Simplex> F = new Vector<Simplex>();
		Scanner sc = new Scanner(new File(filename));
		sc.useLocale(Locale.US);
		while (sc.hasNext())
			F.add(new Simplex(sc));
		sc.close();
		return F;
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Syntax: java ReadFiltration <filename>");

			System.exit(0);
		}
			
		Vector<Simplex> F=readFiltration(args[0]);
		int size= F.size();
		Collections.sort(F, new Compare());
		
		
		// Uncomment to use full matrix and comment the sparse matrix ones
		// BoundaryMatrix matrix =new BoundaryMatrix(F.size(), F);
		// matrix.Reduction();
		// matrix.barCode(F);

		// sparse matrix
		String outputName = "./Files/filtration_C_out.txt";
		SparseBoundaryMatrix sparseMatrix=new SparseBoundaryMatrix(F);
		LinkedHashMap<Integer,Integer> map=sparseMatrix.sparseReduction(F);
		new WriteFile(outputName).barCode(map,"./Files/filtration_B_out.txt" , F);
	}
}