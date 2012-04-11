/**
 * 
 */
package ac;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 6 avr. 2012
 * @version 0.1
 */
public class TimeRemaining
{

  /**
   * @param args
   * @throws ParseException
   */
  public static void main(String[] args) throws ParseException
  {
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    Date soutenance = format.parse("07/05/12 13:00:00");
    Date current = new Date();

    long restant = soutenance.getTime() - current.getTime(); // Ms restantes
    restant /= 1000; // Secondes restantes
    restant /= 60; // Minutes restantes
    int minutes = (int) restant;
    restant /= 60; // Heures restantes
    int heures = (int) restant;
    restant /= 24; // Jours restants

    System.out.println("////////////////////////////////////////////////");
    System.out.println("//// TEMPS RESTANT AVANT LE RENDU DU PROJET ////");
    System.out.println("////             (SOUTENANCE)               ////");
    System.out.println("////////////////////////////////////////////////");
    System.out.println("\n              " + restant + " jours");
    System.out.println("Soit          " + heures + " heures");
    System.out.println("Soit          " + minutes + " minutes");
    System.out
        .println("\n\n Et maintenant pensez à chaque minute qui passe :)\n\n");
    
    System.out.println("                          oooo$$$$$$$$$$$$oooo");
    System.out.println("                      oo$$$$$$$$$$$$$$$$$$$$$$$$o");
    System.out.println("                   oo$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$o         o$   $$ o$");
    System.out.println("   o $ oo        o$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$o       $$ $$ $$o$");
    System.out.println("oo $ $ \"$      o$$$$$$$$$    $$$$$$$$$$$$$    $$$$$$$$$o       $$$o$$o$");
    System.out.println("\"$$$$$$o$     o$$$$$$$$$      $$$$$$$$$$$      $$$$$$$$$$o    $$$$$$$$");
    System.out.println("  $$$$$$$    $$$$$$$$$$$      $$$$$$$$$$$      $$$$$$$$$$$$$$$$$$$$$$$");
    System.out.println("  $$$$$$$$$$$$$$$$$$$$$$$    $$$$$$$$$$$$$    $$$$$$$$$$$$$$  \"\"\"$$$");
    System.out.println("   \"$$$\"\"\"\"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$     \"$$$");
    System.out.println("    $$$   o$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$     \"$$$o");
    System.out.println("   o$$\"   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$       $$$o");
    System.out.println("   $$$    $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\" \"$$$$$$ooooo$$$$o");
    System.out.println("  o$$$oooo$$$$$  $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$   o$$$$$$$$$$$$$$$$$");
    System.out.println("  $$$$$$$$\"$$$$   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$     $$$$\"\"\"\"\"\"\"\"");
    System.out.println(" \"\"\"\"       $$$$    \"$$$$$$$$$$$$$$$$$$$$$$$$$$$$\"      o$$$");
    System.out.println("            \"$$$o     \"\"\"$$$$$$$$$$$$$$$$$$\"$$\"         $$$");
    System.out.println("              $$$o          \"$$\"\"$$$$$$\"\"\"\"           o$$$");
    System.out.println("               $$$$o                                o$$$\"");
    System.out.println("                \"$$$$o      o$$$$$$o\"$$$$o        o$$$$");
    System.out.println("                  \"$$$$$oo     \"\"$$$$o$$$$$o   o$$$$\"\"");
    System.out.println("                     \"\"$$$$$oooo  \"$$$o$$$$$$$$$\"\"\"");
    System.out.println("                        \"\"$$$$$$$oo $$$$$$$$$$");
    System.out.println("                                \"\"\"\"$$$$$$$$$$$");
    System.out.println("                                    $$$$$$$$$$$$");
    System.out.println("                                     $$$$$$$$$$\"");
    System.out.println("                                      \"$$$\"\"  ");
    
  }

}
