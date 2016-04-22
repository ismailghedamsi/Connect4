package jeu;
public class JoueurAutomatique extends Joueur{
	
	   public JoueurAutomatique(){
		    
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
