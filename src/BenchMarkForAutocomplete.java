import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class BenchMarkForAutocomplete {
	public static final String CHARSET = "UTF-8";
	public static final Locale LOCALE = Locale.US;
	
	
	Autocompletor myBinary;
	Autocompletor myBrute;
	
	public void setUp(String filename) throws FileNotFoundException {
		Scanner in = new Scanner(new File(filename), CHARSET);
		in.useLocale(LOCALE);
		int N = Integer.parseInt(in.nextLine());
		String[] terms = new String[N];
		double[] weights = new double[N];
		for (int i = 0; i < N; i++) {
			String line = in.nextLine();
			int tab = line.indexOf('\t');
			weights[i] = Double.parseDouble(line.substring(0, tab).trim());
			terms[i] = line.substring(tab + 1);
		}
		myBinary = new BinarySearchAutocomplete(terms,weights);
		myBrute = new BruteAutocomplete(terms,weights);
	}
	
	public void runAM() {
		int matchSize = 20;
		String[] all = {"","a", "b", "c", "x", "y", "z", "aa", "az", "za", "zz"};
		System.out.printf("search\tsize\t#match\tbinary\tbrute\n");
		for(String s : all) {

			List<Term> binaryResult = myBinary.topMatches(s, Integer.MAX_VALUE);
			
			int allSize = binaryResult.size();
			binaryResult = myBinary.topMatches(s, matchSize);
			
			double start = System.nanoTime();
			binaryResult = myBinary.topMatches(s, matchSize);
			double end = System.nanoTime();
			double binaryTime = (end-start)/1e9;
			
			List<Term> bruteresult = myBrute.topMatches(s, Integer.MAX_VALUE);
			int allSize2 = bruteresult.size();
			bruteresult = myBrute.topMatches(s, matchSize);
			start = System.nanoTime();
			bruteresult = myBrute.topMatches(s, matchSize);
			end = System.nanoTime();
			double bruteTime = (end-start)/1e9;
			if (! binaryResult.equals(bruteresult)) {
				System.out.printf("%d %d\n", binaryResult.size(),bruteresult.size());
//				for(int k=0; k < matchSize; k++) {
//					System.out.printf("%s\t%s\n", binaryResult.get(k),bruteresult.get(k));
//				}
			}
			System.out.printf("%s\t%d\t%d\t%1.4f\t%1.4f\n",
					          s,allSize,matchSize,binaryTime,bruteTime);
		}
	}
	public void doMark() throws FileNotFoundException {
		String fname = "data/fourletterwords.txt"; 
		//fname = "data/words-333333.txt";
		setUp(fname);
		runAM();
	}
	public static void main(String[] args) throws FileNotFoundException {
		
		BenchMarkForAutocomplete bmfb = new BenchMarkForAutocomplete();
		bmfb.doMark();
	}
}
