import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;








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

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Syntax: java ReadFiltration <filename>");
			//File directory = new File("./");
			//System.out.println(directory.getAbsolutePath());
			System.exit(0);
		}
			
		System.out.println(readFiltration(args[0]));
		Vector<Simplex> F=readFiltration(args[0]);
		int size= F.size();
		Quicksort sortV= new Quicksort();
		sortV.sort(F, 0, size-1);
		System.out.println(F);
		
		BoundaryMatrix matrix =new BoundaryMatrix(F.size(), F);
		//System.out.println(new BoundaryMatrix(F.size(), F));
		System.out.println(matrix);
		Vector <Integer> Lows=matrix.initialLows();
		System.out.println(Lows);
		
		matrix.Reduction();
		System.out.println(matrix);
		System.out.println(matrix.pivotsFound);
		matrix.barCode(F);
		
		//File directory = new File("./");
		//System.out.println(directory.getAbsolutePath());
	}
}