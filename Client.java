package projet;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String adresseServeur = "localhost";
        int port = 1234;
        
        Grille grilleClient1 = new Grille(); 
        Grille grilleClient2 = new Grille(); 

        try (Socket socket = new Socket(adresseServeur, port)) {
            System.out.println("Connecté au serveur.");
            
            grilleClient1.debutJeu();
            grilleClient1.afficherGrille();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader in_infos = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out_infos = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
            
            String messageServeur, messageClient;
            String infoClient, infoServeur;
            
            out_infos.println("go");
            System.out.println("En attente du SERVEUR");
            while (true) {
                infoServeur = in_infos.readLine();
                
                if (infoServeur.equals("go")) {
                	System.out.println("SERVEUR OK");
                	break;
                }            	
            }

            
            System.out.println("VOTRE GRILLE : \n");
            grilleClient1.afficherGrille();
            System.out.println("------------");
            
            
            
            // Discussion continue

        	int flagToucheCoule = 0;
            messageServeur = "AUCUN";
            while (true) {       

                System.out.println("TOUR "+grilleClient1.tour+" ---------");
                System.out.print("Votre grille\n");
                grilleClient1.afficherGrille();
                System.out.print("\n Grille de l'adversaire\n");
                grilleClient2.afficherGrille();
                
                if (grilleClient1.tour > 0) {
	                System.out.println("Serveur : " + messageServeur);
                }                

                
                if (flagToucheCoule == 1) {
        			System.out.println("CHEF, ON A TOUCHÉ UN DE VOS BATEAU !");                	
                } else if (flagToucheCoule == 2) {
        			System.out.println("CHEF, UN DE VOS BATEAU A ÉTÉ COULÉ !");
                }

            	flagToucheCoule = 0;
            	
            	System.out.println("A VOUS DE JOUER : ");
                messageClient = clavier.readLine();
                

                if (grilleClient1.verifCase(messageClient)) {                
	                if (grilleClient2.estLibre(messageClient)) {
		                out.println(messageClient);
		                
		
		                infoServeur = in_infos.readLine();
		                
		                if (Integer.valueOf(infoServeur)>0) {
		                	grilleClient2.touche(messageClient,Integer.valueOf(infoServeur)-1);
		                	if (grilleClient2.compteur==grilleClient1.nbBateaux) {
		                    	System.out.println("VOUS AVEZ GAGNÉ !");
		                		break;
		                	}
		                } else if (Integer.valueOf(infoServeur)==0) {
		                	grilleClient2.loupe(messageClient);	                	
		                }
		
		                if (messageClient.equalsIgnoreCase("ff")) {
		                    System.out.println("Vous avez quitté la discussion.");
		                    break;
		                }
		
		                // Lire le message du serveur

	            		System.out.println("EN ATTENTE DU COUP DE L'ADVERSAIRE");
		                messageServeur = in.readLine();
		                
		                //CAS D'ARRET
		                if (messageServeur.equalsIgnoreCase("ff")) {
		                    System.out.println("Le serveur a quitté la discussion.");
		                    break;
		                }
		                
		                
		
		                
		                out_infos.println(grilleClient1.verif_touche(messageServeur));
		                
		                
	
		                
		                if (grilleClient1.verif_touche(messageServeur) > 0) {
			                if (grilleClient1.estCoule(messageServeur)) {
			                	flagToucheCoule = 2;
			                } else {
			                	flagToucheCoule = 1;			                	
			                }

		                	grilleClient1.tag(messageServeur);
		                }
		                
		                if (grilleClient1.compteurAdversaire == grilleClient1.nbBateaux) {
		                	System.out.println("VOUS AVEZ PERDU !");
		                	break;
		                }
		                
	                } else {
	                	System.out.println("EUUH CHEF, ON A DÉJA TIRÉ ICI, REJOUEZ !");
	                }
                } else {
			    	System.out.println("CES COORDONNÉES NE SONT PAS VALABLES CHEF !");                	
                }
            }

            socket.close();
            System.out.println("Connexion fermée.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
