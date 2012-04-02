package moteurDeRecherche;

import java.io.*;

import structure.*;


/**
 * Classe qui execute le moteur de recherche (en mode "console"). Elle permet l'utilisateur de :
 *1)Saisir le nom du fichier qui charge la base de connaissances
 *2)Vider la base de faits
 *3)Ajouter des nouveaux faits à la base de faits
 *4)Saisir une requête conjonctive
 *
 *Elle affiche à son tour :
 *1)La base de connaissances
 *2)La base de faits si elle est modifiée par l'utilisateur
 *3)La base de faits saturée
 *4)La(Les) reponse(s) à la requête saisie
 *5)Les éléments illustrant l'exploitation du graphe de règles durant la saturation
 *
 *@author patel
 */

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	
	public static void main(String[] args) throws IOException {
				
		BufferedReader lectureInput = new BufferedReader (new InputStreamReader(System.in));
		
		System.out.print("Saisie du nom du fichier pour charger la Base de Connaissances ou 'q' pour quitter : ");
		String input = lectureInput.readLine();
		if(input.equals("q"))
			return; 
		
		BaseConnaissances bc = new BaseConnaissances(input);
		System.out.println("AFFICHAGE DE LA BASE DE CONNAISSANCES:" + bc);
	//	System.out.println("AFFICHAGE DE LA BASE DE FAITS SATURÉE PAR LA SATURATION PROPOSITIONNELLE :\n" + bc.saturationOrdre0().getBF());
	//	System.out.println("AFFICHAGE DE LA BASE DE FAITS SATURÉE PAR LA SATURATION << PREMIER ORDRE >> :\n" + bc.saturationOrdre1().getBF());
		
		do {
	/*		System.out.println("\nVIDER LA BASE DE FAITS ? (Saisir 'oui' ou 'non') ou 'q' pour quitter : ");
			input = lectureInput.readLine();
			if(input.equals("q"))
				return;
			if(input.equals("oui"))
				bc.viderBaseFaits();
			System.out.println("AJOUTER NOUVEAU FAIT À LA BASE DE FAITS ? (Saisir 'oui' ou 'non') ou 'q' pour quitter : ");
			input = lectureInput.readLine();
			if(input.equals("q"))
				return;
			if(input.equals("oui"))
				do {
					System.out.println("AJOUTER NOUVEAU FAIT : Saisir fait (bien formatté) ou 't' pour terminer : ");
					input = lectureInput.readLine();
					if (!input.equals("t")) 
						bc.ajouterNouveauFait(new Atome(input));
				} while (!input.equals("t"));
			System.out.println("AFFICHAGE DE LA NOUVELLE BASE DE FAITS : " + bc.getBF());
		*/	
			System.out.print("\n\nSAISIE D'UNE REQUÊTE (bien formattée) ou 'q' pour quitter : ");
			input = lectureInput.readLine();
			if(input.equals("q"))
				return;
			Requete requete = new Requete(input);
			
		//	System.out.println("\nAVEC LA REQUÊTE : " + new GDR().calculeGDRAvecRequete(requete,new BaseConnaissances(bc)));
			
	//		System.out.print("AFFICHAGE DURANT LA SATURATION << PREMIER ORDRE >> \nDES ÉLÉMENTS ILLUSTRANT L'EXPLOITATION DU ");
			bc = bc.saturationOrdre1Exploite();
			BaseFaits bf = bc.getBF();
			Homomorphismes reponses = new Homomorphismes(requete, bf);
			System.out.println("\n\nEst-ce que la requête est satisfaite?");
			if (reponses.existeHomomorphisme()) {
				System.out.println("RÉPONSE : Oui");
				System.out.println("\nAFFICHAGE DE LA LISTE DE RÉPONSES :");
				System.out.println(reponses.getHomomorphismes());
			} else
				System.out.println("RÉPONSE : Non");
		
		} while (!input.equals("q"));
		lectureInput.close();
		
	}

}
