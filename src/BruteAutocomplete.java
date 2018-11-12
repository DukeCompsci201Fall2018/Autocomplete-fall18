import java.util.*;

/**
 * Implements Autocompletor by scanning through the entire array of terms for
 * every topKMatches or topMatch query.
 */
public class BruteAutocomplete implements Autocompletor {

	Term[] myTerms;

	/**
	 * Create immutable instance with terms constructed from parameter
	 * @param terms words such that terms[k] is part of a word pair 0 <= k < terms.length
	 * @param weights weights such that weights[k] corresponds to terms[k]
	 * @throws NullPointerException if either parameter is null
	 * @throws IllegalArgumentException if terms.length != weights.length
	 * @throws IllegalArgumentException if any elements of weights is negative
	 * @throws IllegalArgumentException if any elements of terms is duplicate
	 */
	public BruteAutocomplete(String[] terms, double[] weights) {

		if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}

		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}

		myTerms = new Term[terms.length];

		HashSet<String> words = new HashSet<>();

		for (int i = 0; i < terms.length; i++) {
			words.add(terms[i]);
			myTerms[i] = new Term(terms[i], weights[i]);
			if (weights[i] < 0) {
				throw new IllegalArgumentException("Negative weight "+ weights[i]);
			}
		}
		if (words.size() != terms.length) {
			throw new IllegalArgumentException("Duplicate input terms");
		}
	}

	@Override
	public List<Term> topMatches(String prefix, int k) {
		if (k < 0) {
			throw new IllegalArgumentException("Illegal value of k:"+k);
		}
		
		// maintain pq of size k
		PriorityQueue<Term> pq = new PriorityQueue<Term>(10, new Term.WeightOrder());
		for (Term t : myTerms) {
			if (!t.getWord().startsWith(prefix))
				continue;
			if (pq.size() < k) {
				pq.add(t);
			} else if (pq.peek().getWeight() < t.getWeight()) {
				pq.remove();
				pq.add(t);
			}
		}
		int numResults = Math.min(k, pq.size());
		LinkedList<Term> ret = new LinkedList<>();
		for (int i = 0; i < numResults; i++) {
			ret.addFirst(pq.remove());
		}
		return ret;
	}
}
