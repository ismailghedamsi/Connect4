package jeu;
/**
 * Classe representant un plateau de jeu Connect4 
 */
 import java.util.*;
public class C4Plateau{
    //les differentes constantes utilisees par le jeu

    //les valeurs dans le tableau associes aux numeros des joueurs
    //trois valeurs possibles dans le tableau: VIDE, ROUGE ou NOIR
    public static final int ROUGE = 0;
    public static final int NOIR = 1;
    public static final int VIDE = -1;

    //les dimensions du plateau de jeu
    public static final int LARGEUR = 7; // ligne
    public static final int HAUTEUR = 6; // colonne
    
    public int[][] mPlateau;
    public  static int mNbCasesRemplies;

    /** Constructeur par defaut
     * Plateau vide
     * */
    public C4Plateau(){
		
		mPlateau = new int[LARGEUR][HAUTEUR];
		for(int i=0;i<LARGEUR;i++){ // ligne
			for(int j=0;j<HAUTEUR;j++){ // colonne
				mPlateau[i][j]=VIDE;
				System.out.println("i "+i+"j "+j);
			}
			
		}
		mNbCasesRemplies=0;
	}
	
	/** Constructeur surcharger 
	 * Param : pPlateau tableau partiellement rempli
	 * Fonction du constructeur : sert a debuger
	 * */
	public C4Plateau(int[][] pPlateau){
		mPlateau = pPlateau;
	}


    /**
     * Fonction : retourne le gagnant de la partie
     * -1 : Partie nul
     *  1 : joueur 1 gagne
     *  2 : joueur 2 gagne
     */
    public int getGagnant(){
		int nbJetonAdjoint = 0; 
		
		// Gagner horizentalement toString
	
		for(int i=0;i<=HAUTEUR;i++){ // ligne
			for(int j=0;j<LARGEUR-2;j++){ // colonne
				if(mPlateau [i][j]!= VIDE && mPlateau[i][j] == mPlateau[i][j+1]){
					nbJetonAdjoint++;
			
					if(nbJetonAdjoint == 3){
						return  mPlateau[i][j];
					}
				}else{
					nbJetonAdjoint=0;
				}
			}
		}
		    
		    
		    nbJetonAdjoint=0;
		    
			for(int i=0;i<HAUTEUR;i++){ 
				for(int j=0;j<LARGEUR-1;j++){ 
					if(mPlateau [j][i]!= VIDE && mPlateau[j][i] == mPlateau[j+1][i]){
						nbJetonAdjoint++;
						if(nbJetonAdjoint == 3){
							return  mPlateau[j][i];
						}
					
					}else{
						nbJetonAdjoint=0;
					}
				}
		}
		
		
		 /*    int[][] tableau = {
			{-1,-1,-1,-1,-1,-1}, 
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1}
            };*/
            
		nbJetonAdjoint=0;
		//diagonale principale
		/*for(int i=0;i<HAUTEUR-2;i++){ 
			for(int j=0;j<HAUTEUR-2;j++){
				if(i == j){ 
					if(mPlateau [i][j]!= VIDE && mPlateau [i][j] == mPlateau [i+1][j+1] ){
						nbJetonAdjoint++;
						if(nbJetonAdjoint == 3){
							return mPlateau[i][j];
						}
					}
				}
			}
		}*/
		
		return VIDE;
	}
	
	 /**
     * Function : indique si le tableau est remplie
     */
	public  boolean  isRemplie(){
		return  mNbCasesRemplies == LARGEUR*HAUTEUR; 
	}
	
	
	
	 /**
     * Function : Ajoute un jeton a la case i si elle n'est pas empli
     * 
     * Param :
     * -pColonne: La colonne ou inserer le nouveau jeton
     */
	public boolean addJeton(int pColonne, int pJoueur){
		int ligne = HAUTEUR-1;  // LARGEUR-1 =  6
	    
		if(isRemplie()){ // si la grille est completement rempli imposible d'ajouter le jeton
			return false;
		}

		while(mPlateau[pColonne][ligne] != VIDE && ligne>0 ){ // cherche la case non vide pour inserer
			ligne--;	
		}
		
		mPlateau[pColonne][ligne] = pJoueur; // mettre la case a la bonne place
		mNbCasesRemplies++;
		return true;
	}
	
	public int getCase(int pColonne, int pRangee){
		return mPlateau[pColonne][pRangee]; // le plateau est une transpose donc la rangee est la colonne et la colonne est la rangee
	}
	
	public String toString(){
		 String stringify = ""; // la representation du tableau en String
		 for(int i=0;i<HAUTEUR;i++){
			 for(int j=0;j<LARGEUR;j++){
				 stringify+="["+mPlateau[j][i]+"] "; // transpose
			 }
			 stringify+="\n";
		 }
        return  stringify;
	}
	
	
	 
   /**
     * Main d'exemple pour tester votre plateau 
     */
    public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int colonne;
		int joueurCourant=ROUGE;
      //il faut voir le plateau comme transpose. Les colonnes sont les rangees, et les rangees sont les colonnes dans la facon de l'ecrire 
      //de cette facon:
        int[][] tableau = {
			{-1,-1,-1,-1,-1,-1}, 
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1}
            };
            
   
        C4Plateau plateau = new C4Plateau(tableau);
        //test inserer un jeton
         System.out.println(plateau);
         System.out.println(plateau.getGagnant());
	      while(plateau.getGagnant() == VIDE){
   
			  colonne = sc.nextInt();
			  plateau.addJeton(colonne,joueurCourant);
			  joueurCourant = (mNbCasesRemplies%2 == 0) ? ROUGE : NOIR;

		  }
		 
		 
        //verifie si la fonction getGagnant detecte 4 jetons pareil en vertical
          
        
        System.out.println("Gagnant "+plateau.getGagnant());
    }
} 
