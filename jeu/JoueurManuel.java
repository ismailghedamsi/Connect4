package jeu;
public class JoueurManuel extends Joueur {
	
	    public JoueurManuel(){
		}
		public void setColonne(int pColonne){
			if(pColonne<7 && pColonne>=0){
				mColonne = pColonne;
			}
		}
	    public int getColonne(){
			return mColonne;
		}
	
}
