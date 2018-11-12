import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class TestBinarySearch {

	List<String> myList;
	
	@Before
	public void setup () {
		String[] ss = {
				"apple","apple","apple","apple",
				"cherry","cherry","cherry","cherry","cherry","cherry",
				"orange",
				"zoo","zoo","zoo","zoo","zoo","zoo","zoo",
				"zoo","zoo","zoo","zoo","zoo"};
		
		myList = Arrays.asList(ss);
	}

	@Test
	public void testFirstIndex() {
		String[] keys = {"apple","cherry","lemon","orange","zoo"};
		int[] results = {0,4,-1,10,11};
		for(int k=0; k < keys.length; k++) {
			String target = keys[k];
			int index = BinarySearchLibrary.firstIndex(myList, target, Comparator.naturalOrder());
			assertEquals("wrong first index for "+target,results[k],index);
		}
	}
	
	@Test
	public void testLastIndex() {
		String[] keys = {"apple","cherry","lemon","orange","zoo"};
		int[] results = {3,9,-1,10,22};
		for(int k=0; k < keys.length; k++) {
			String target = keys[k];
			int index = BinarySearchLibrary.lastIndex(myList, target, Comparator.naturalOrder());
			assertEquals("wrong first index for "+target,results[k],index);
		}
	}

}
