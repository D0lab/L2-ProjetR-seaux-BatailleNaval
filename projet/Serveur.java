package projet;

import java.io.*;
import java.net.*;

public class Serveur {
    public static void main(String[] args) {
        int port = 1234;
        
        Grille grilleServeur1 = new Grille(); 
        Grille grilleServeur2 = new Grille(); 
        int flagMauvaiseEntrée = 0;
        

        try (ServerSocket serveurSocket = new ServerSocket(port)) {
            System.out.println("Serveur en attente de connexion...");

            Socket socket = serveurSocket.accept();
            System.out.println("Client connecté.");
            
            grilleServeur1.debutJeu();
            grilleServeur1.afficherGrille();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader in_infos = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out_infos = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

            String messageClient, messageServeur;
            String infoClient, infoServeur;
            
            out_infos.println("go");
            System.out.println("En attente du CLIENT");
            while (true) {
            	infoClient = in_infos.readLine();
                
                if (infoClient.equals("go")) {
                	System.out.println("CLIENT OK");
                	break;
                }            	
            }

            
            System.out.println("VOTRE GRILLE : \n");
            grilleServeur1.afficherGrille();
            System.out.println("------------");
            
            
            // Discussion continue
        	int flagToucheCoule = 0;
        	messageClient = "AUCUN";
            while (true) {
            	flagToucheCoule = 0;
            	
            	if (flagMauvaiseEntrée == 0) {
            		

                                	
                    
                
	                // Lire le message du client
            		System.out.println("EN ATTENTE DU COUP DE L'ADVERSAIRE");
	                messageClient = in.readLine();
	                


	                
	                //CAS D'ARRET
	                if (messageClient.equalsIgnoreCase("ff")) {
	                    System.out.println("Client a quitté la discussion.");
	                    break;
	                }
	                
	                
	
	
	                
	                out_infos.println(grilleServeur1.verif_touche(messageClient));
	                
	                
            	
            	
	                
	                
	                if (grilleServeur1.verif_touche(messageClient) > 0) {
	                 	if (grilleServeur1.estCoule(messageClient)) {
	                 		flagToucheCoule = 2;
	                 	} else {
		                	flagToucheCoule = 1;	                 		
	                 	}

	                	grilleServeur1.tag(messageClient);
	                }
	                
	                if (grilleServeur1.compteurAdversaire == grilleServeur1.nbBateaux) {
	                	System.out.println("VOUS AVEZ PERDU !");
	                	break;
	                }
	                

            	}
            	
            	
            	
            	
                

                // Envoyer un message au client
                

                System.out.println("TOUR "+grilleServeur1.tour+" ---------");
                System.out.print("Votre grille\n");
                grilleServeur1.afficherGrille();
                System.out.print("\n Grille de l'adversaire\n");
                grilleServeur2.afficherGrille();
                if (flagMauvaiseEntrée == 0) {
                    System.out.println("Client : " + messageClient);                 	
                }       
                
                if (flagToucheCoule == 1) {
        			System.out.println("CHEF, ON A TOUCHÉ UN DE VOS BATEAU !");                	
                } else if (flagToucheCoule == 2) {
        			System.out.println("CHEF, UN DE VOS BATEAU A ÉTÉ COULÉ !");
                }
                
            	System.out.println("A VOUS DE JOUER : ");

                flagMauvaiseEntrée = 0;
                
                messageServeur = clavier.readLine();

                if (grilleServeur1.verifCase(messageServeur)) {     
	                if (grilleServeur2.estLibre(messageServeur)) {                
		                out.println(messageServeur);
		                
		
		                infoClient = in_infos.readLine();
		                
		                if (Integer.valueOf(infoClient)>0) {
		                	grilleServeur2.touche(messageServeur,Integer.valueOf(infoClient)-1);
		                	if (grilleServeur2.compteur==grilleServeur1.nbBateaux) {
		                    	System.out.println("VOUS AVEZ GAGNÉ !");
		                		break;
		                	}
		                } else if (Integer.valueOf(infoClient)==0) {
		                	grilleServeur2.loupe(messageServeur);	                	
		                }
		
		                if (messageServeur.equalsIgnoreCase("ff")) {
		                    System.out.println("Vous avez quitté la discussion.");
		                    break;
		                }
	                } else {
	                	System.out.println("EUUH CHEF, ON A DÉJA TIRÉ ICI, REJOUEZ !");
	                	flagMauvaiseEntrée=1;
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
