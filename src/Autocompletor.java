import java.util.List;

/**
 * An Autocompletor supports returning either the top k best matches, or the
 * single top match, given a String prefix.
 * 
 * @author Austin Lu
 * @author Owen Astrachan changed API from Iterable to List
 */
public interface Autocompletor {

	/**
	 * Returns the top k matching terms in descending order of weight. If there
	 * are fewer than k matches, return all matching terms in descending order
	 * of weight. If there are no matches, return an empty list.
	 */
	public List<Term> topMatches(String prefix, int k);

}
