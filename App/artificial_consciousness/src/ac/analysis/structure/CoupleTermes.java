package structure;

/**
 * Le couple de termes qui définit la substitution d'une variable par une constante.
 * C'est l'élément de base pour la classe Substitution.
 */
public class CoupleTermes 
{
	
	private Terme x; //1e terme du couple (variable)
	private Terme y; //2e terme du couple (constante)

	/**
	 * Constructeur de la classe CoupleTermes
	 * @param a premier terme du couple
	 * @param b deuxième terme du couple
	 */
	public CoupleTermes(Terme a, Terme b) 
	{
		x = a;
		y = b;
	}
	
//Les getters de la classe
	public Terme getX()
	{
		return x;
	}
	
	public Terme getY()
	{
		return y;
	}
	
//Les méthodes qui caractérisent les fonctionnalitées de la classe
	/**
	 * méthode qui remplace x (le 1e terme du couple) par y (le 2e terme du couple) dans une string
	 * @param s string dans laquelle on désire faire le remplacement
	 * @return t nouvelle string avec x remplacé par y
	 */
	public String replaceXY (String s)
	{
		String t = s.replaceAll(x.toString(), y.toString());
		return t;
	}
//Méthode toString de la classe	
	public String toString()
	{
		String s = "(" + x + "," + y + ")";
	return s;
	}

}
