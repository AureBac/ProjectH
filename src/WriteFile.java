import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
public class WriteFile {
	BufferedWriter writer;
	
	public WriteFile(String filename) throws IOException {
		String str="";
		writer=new BufferedWriter(new FileWriter(filename));
		writer.write(str);
		writer.close();	
	}
	
	
	public void barCode(LinkedHashMap<Integer,Integer> map, String filename,Vector<Simplex> F) throws IOException {
		String line="";
		writer = new BufferedWriter(new FileWriter(filename, true));
		for(Integer i: map.keySet()) {
			line=line+F.get(i).dim+" ";
			line=line+F.get(i).val+" ";
			if(map.get(i)==-1)
				line=line+"inf";
			else
			    line=line+F.get(map.get(i)).val;
			writer.append(line);
			writer.append(System.getProperty("line.separator"));
			line="";
			System.out.println("k= "+i);
		}
		
		
		writer.close();	
	}


}
