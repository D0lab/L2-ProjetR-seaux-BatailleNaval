package projet;

public class Bateaux {
	int longueurBateau;
	int hauteurBateau;
	int tailleBateau;
	int identifiantBateau;
	
	public Bateaux(int l, int h, int id) {
		this.longueurBateau = l;
		this.hauteurBateau = h;
		this.tailleBateau = l*h;
		this.identifiantBateau = id;
		
	}
	
	@Override
	public String toString() {
		return "bateau de longueur "+this.longueurBateau+" et de hauteur "+this.hauteurBateau;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
