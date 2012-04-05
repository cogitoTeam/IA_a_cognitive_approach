package ac.analysis.inferenceEngine;

import java.io.*;

import ac.analysis.structure.*;



/**
 * Classe qui execute le moteur de recherche (en mode "console"). Elle permet l'utilisateur de :
 *1)Saisir le nom du fichier qui charge la base de connaissances
 *2)Vider la base de faits
 *3)Ajouter des nouveaux faits � la base de faits
 *4)Saisir une requ�te conjonctive
 *
 *Elle affiche � son tour :
 *1)La base de connaissances
 *2)La base de faits si elle est modifi�e par l'utilisateur
 *3)La base de faits satur�e
 *4)La(Les) reponse(s) � la requ�te saisie
 *5)Les �l�ments illustrant l'exploitation du graphe de r�gles durant la saturation
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
		
		KnowledgeBase bc = new KnowledgeBase(input);
		System.out.println("AFFICHAGE DE LA BASE DE CONNAISSANCES:" + bc);
	//	System.out.println("AFFICHAGE DE LA BASE DE FAITS SATUR�E PAR LA SATURATION PROPOSITIONNELLE :\n" + bc.saturationOrdre0().getBF());
	//	System.out.println("AFFICHAGE DE LA BASE DE FAITS SATUR�E PAR LA SATURATION << PREMIER ORDRE >> :\n" + bc.saturationOrdre1().getBF());
		
		do {
	/*		System.out.println("\nVIDER LA BASE DE FAITS ? (Saisir 'oui' ou 'non') ou 'q' pour quitter : ");
			input = lectureInput.readLine();
			if(input.equals("q"))
				return;
			if(input.equals("oui"))
				bc.viderBaseFaits();
			System.out.println("AJOUTER NOUVEAU FAIT � LA BASE DE FAITS ? (Saisir 'oui' ou 'non') ou 'q' pour quitter : ");
			input = lectureInput.readLine();
			if(input.equals("q"))
				return;
			if(input.equals("oui"))
				do {
					System.out.println("AJOUTER NOUVEAU FAIT : Saisir fait (bien formatt�) ou 't' pour terminer : ");
					input = lectureInput.readLine();
					if (!input.equals("t")) 
						bc.ajouterNouveauFait(new Atome(input));
				} while (!input.equals("t"));
			System.out.println("AFFICHAGE DE LA NOUVELLE BASE DE FAITS : " + bc.getBF());
		*/	
			System.out.print("\n\nSAISIE D'UNE REQU�TE (bien formatt�e) ou 'q' pour quitter : ");
			input = lectureInput.readLine();
			if(input.equals("q"))
				return;
			Query query = new Query(input);
			
		//	System.out.println("\nAVEC LA REQU�TE : " + new GDR().calculeGDRAvecRequete(requete,new BaseConnaissances(bc)));
			
	//		System.out.print("AFFICHAGE DURANT LA SATURATION << PREMIER ORDRE >> \nDES �L�MENTS ILLUSTRANT L'EXPLOITATION DU ");
			bc = bc.saturationOrdre1Exploite();
			FactBase bf = bc.getBF();
			Homomorphisms reponses = new Homomorphisms(query, bf);
			System.out.println("\n\nEst-ce que la requ�te est satisfaite?");
			if (reponses.existeHomomorphisme()) {
				System.out.println("R�PONSE : Oui");
				System.out.println("\nAFFICHAGE DE LA LISTE DE R�PONSES :");
				System.out.println(reponses.getHomomorphismes());
			} else
				System.out.println("R�PONSE : Non");
		
		} while (!input.equals("q"));
		lectureInput.close();
		
	}

}
