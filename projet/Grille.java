package projet;

import java.util.Arrays;
import java.util.Scanner;

public class Grille {
	int[][] grille_tab;	
	Bateaux[] bateaux = {new Bateaux(1,1,1),new Bateaux(2,1,2)};
	int[][] coordonnées = new int[bateaux.length][2];
	int nbBateaux;
	int compteur;
	int compteurAdversaire;
	int tour;
	
	
	public Grille() {
		this.grille_tab = new int[5][5];	
		this.nbBateaux = bateaux.length;
		this.compteur = 0;
		this.compteurAdversaire = 0;
		this.tour = 0;
	}
	
	public boolean estLibre(int ligne,int colonne) {
		return this.grille_tab[colonne][ligne] == 0;		
		
	}
	
	
	public boolean estLibre(String message) {
		

	 	int ligne = (message.charAt(1) - '0')-1;
		int colonne = this.tradColonne(message.charAt(0));
		return this.grille_tab[colonne][ligne] == 0;		
		
	}
	
	public boolean placementPossible(String message,Bateaux bateau) {
		

	 	int ligne = (message.charAt(1) - '0')-1;
		int colonne = this.tradColonne(message.charAt(0));
		
		return (ligne+bateau.longueurBateau) <= 5 && (colonne+bateau.hauteurBateau)<=5;
		
	}
	
	public int tradColonne(char lettre) {
	 	if(lettre == 'A') {
	 		return 0;
	 	}else if(lettre == 'B') {
	 		return 1;
	 	}else if(lettre == 'C') {
	 		return 2;
	 	}else if(lettre == 'D') {
	 		return 3;
	 	}else if(lettre == 'E') {
	 		return 4;
	 	}else {
	 		return -1;
	 	}
		
	}
	
	public boolean verifCase(String message) {
		message = message.replaceAll("\\s", "");
	 	
		return (message.length() == 2) && ((message.charAt(1) - '0') > 0 && (message.charAt(1) - '0') <= 5) && (this.tradColonne(message.charAt(0)) != -1);
	}
	
	public void debutJeu() {			
			for (int i=0; i<bateaux.length; i++) {
			 	Scanner scanner_pos = new Scanner(System.in);
			 	
			 	

				this.afficherGrille();
				System.out.println("Bateau N°"+(i+1));	
	
			    System.out.println("Position (EXEMPLE : A1) :");
			    String choix_utilisateur = scanner_pos.nextLine();
			    
			    if (this.verifCase(choix_utilisateur)) {
			 	
				 	int choix_ligne = choix_utilisateur.charAt(1) - '0';
	
				 	int choix_colonne = this.tradColonne(choix_utilisateur.charAt(0));
				    
				    
				    
				    if (this.estLibre(choix_ligne-1, choix_colonne)) {
				    	
				    	if (this.placementPossible(choix_utilisateur, bateaux[i])) {
				    		coordonnées[i][0] = choix_ligne;
				    		coordonnées[i][1] = choix_colonne;
					    	for(int j=0; j<bateaux[i].longueurBateau; j++) {
					    		for(int u=0; u<bateaux[i].hauteurBateau; u++) {
					    			this.grille_tab[choix_colonne+u][choix_ligne-1+j] = bateaux[i].identifiantBateau;
					    		}
					    	}
				    	}else {
				    		System.out.println("LE BATEAU EST TROP GROS CHEF !");
				    		i-=1;
				    	}
				    	
				    }else {
				    	System.out.println("VOUS AVEZ DÉJA PLACÉ UN BATEAU ICI CHEF !");
				    	i-=1;
				    }
			    
			    } else {
			    	System.out.println("CES COORDONNÉES NE SONT PAS VALABLES CHEF !");
			    	i -= 1;
			    }
				
			}
					
		}
	
	
	public int verif_touche(String message) {
		

	 	int ligne = (message.charAt(1) - '0')-1;
		int colonne = this.tradColonne(message.charAt(0));
				
		this.tour += 1;
		
		return this.grille_tab[colonne][ligne];
	}


	
	public void tag(String message) {
	 	int ligne = (message.charAt(1) - '0')-1;
		int colonne = this.tradColonne(message.charAt(0));
		int id = this.grille_tab[colonne][ligne]-1;
		
		this.bateaux[id].longueurBateau -= 1;
		if (this.bateaux[id].longueurBateau == 0) {
			this.compteurAdversaire += 1;
		}
		
				
	
		this.grille_tab[colonne][ligne]=-2;
	}
	
	public boolean estCoule(String message) {
	 	int ligne = (message.charAt(1) - '0')-1;
		int colonne = this.tradColonne(message.charAt(0));
		int id = this.grille_tab[colonne][ligne]-1;
		
		return (this.bateaux[id].longueurBateau)-1 == 0;
		
	}
	
	public void loupe(String message) {
    	System.out.println("Dommage il n'y avait rien chef !");

	 	int ligne = (message.charAt(1) - '0')-1;
		int colonne = this.tradColonne(message.charAt(0));
		int id = this.grille_tab[colonne][ligne]-1;

		this.grille_tab[colonne][ligne]= -3;		
	}
	
	public void touche(String message, int id) {
		
		System.out.println("Boum touché chef !");

	 	int ligne = (message.charAt(1) - '0')-1;
		int colonne = this.tradColonne(message.charAt(0));	
		

		this.grille_tab[colonne][ligne]= -2;
		
		this.bateaux[id].tailleBateau -= 1;	
		
		if (this.bateaux[id].tailleBateau == 0) {
			coule(ligne,colonne,id);
		}		
	}
	
	public void coule(int ligne, int colonne, int id) {
		
		System.out.println("un bateau en moins chef !");
    	for(int i=0; i<bateaux[id].longueurBateau; i++) {
    		for(int j=0; j<bateaux[id].hauteurBateau; j++) {
    			this.grille_tab[(coordonnées[id][1])+j][(coordonnées[id][0])+i] = -1;
    		}
    		
    	}
    	
		this.compteur += 1;
	}


	
	public void afficherGrille() {
		String[] lettres = {"A","B","C","D","E"};
		int cpt = 0;
		
		
		
		System.out.println("   1 2 3 4 5");
		System.out.println("  ___________");
		for (int[] i : this.grille_tab) {
			System.out.print(lettres[cpt]+"| ");
			for (int j : i) {
				if (j == 0) {
					System.out.print(". ");						
				}else if(j == -1) {
					System.out.print("- ");
				}else if(j == -2) {
					System.out.print("x ");
				}else if(j == -3) {
					System.out.print("o ");
				}else {
					System.out.print(Integer.valueOf(j)+" ");
				}
			}
			System.out.print("|\n");
			cpt+=1;
		}

		System.out.println("  ‾‾‾‾‾‾‾‾‾‾‾");
	}

	public static void main(String[] args) {
		

	}

}
