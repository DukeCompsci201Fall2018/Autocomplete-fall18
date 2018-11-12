import java.util.Comparator;

public class CountedComparator<E extends Comparable<E>> implements Comparator<E> {
	private Comparator<E> myComparator;
	private int myCount;
	
	public CountedComparator(){
		myComparator = Comparator.naturalOrder();
	}
	
	public CountedComparator(Comparator<E> comp) {
		myComparator = comp;
	}
	
	@Override
	public int compare(E o1, E o2) {
		myCount++;
		return myComparator.compare(o1,o2);
	}
	
	public int getCount() {
		return myCount;
	}
	
}
