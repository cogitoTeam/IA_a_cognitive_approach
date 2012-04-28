package ac.analysis.structure;

/**
 * Represents a pair of terms for substitution. It is of the type (variable,constant)
 */
public class TermPair 
{
	
	private Term x;
	private Term y;

	/**
	 * Constructor
	 * @param a term 1
	 * @param b term 2
	 */
	public TermPair(Term a, Term b) 
	{
		x = a;
		y = b;
	}
	/**
	 * @return term 1
	 */
	public Term getX()
	{
		return x;
	}
	
	/**
   * @return term 2
   */
	public Term getY()
	{
		return y;
	}
	
	/**
	 * Replaces term 1 by term 2 in a string (passed as passed as parameter)
	 * @param s the source string
	 * @return t the new string after substitution
	 */
	public String replaceXY (String s)
	{
		String t = s.replaceAll(x.toString(), y.toString());
		return t;
	}

	public String toString()
	{
		String s = "(" + x + "," + y + ")";
	return s;
	}

}
