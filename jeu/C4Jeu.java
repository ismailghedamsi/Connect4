package jeu;
/**
 * Classe representant un jeu de connect4. Attention!! En codant cette classe, n'oubliez pas que beaucoup de fonctions existent dans la classe
 * C4Plateau et vous devez les utiliser!
 */
 
 import java.util.*;
 import jeu.*;
 import gui.*;
public class C4Jeu{

    //le plateau de jeu
    private C4Plateau mPlateau;

    //les joueurs du jeu
    //vous pouvez aussi vous declarer 2 joueurs, je trouvais simplement ca plus facile a gerer si 
    //je placais les deux joueurs dans un tableau de 2 cases de deux joueurs. Si vous ne l'utilisez pas, 
    //vous pouvez l'enlever.
    //Puisque les classes Joueur, JoueurManuel et JoueurAutomatique ne sont pas encore implantees, la declaration de cette variable est 
    //en commentaire:
    private Joueur[] mJoueurs;

    //le nombre de coups joues
    //cette variable servira principalement a determiner a qui le tour de jouer. Si vous ne l'utilisez pas, 
    //vous devez l'enlever de la classe
    private int mCoupsJoues;

    //l'index du joueur courant dans le tableau mJoueurs. L'interface graphique enverra la colonne cliquee par la fonction setColonneChoisie
    //La colonne qui sera choisie sera pour le joueur a qui c'est le tour de joueur, donc le joueur courant
    private int mJoueurCourant;

    //le numero du gagnant, -1 s'il n'y a pas de gagnant
    private int mGagnant;

    /**
     * Constructeurs de la classe initialisant chacune des variables membres de la classe 
     * Si jamais vous n'avez pas code le joueur automatique, ne creez que des joueurs manuels, peu 
     * importe la valeur de autoJoueur1 et autoJoueur2
     * @param autoJoueur1 indique si le joueur 1 est automatique ou non
     * @param autoJoueur2 indique si le joueur 2 est automatique ou non
     */
    public C4Jeu(boolean autoJoueur1, boolean autoJoueur2){
		mCoupsJoues=0; // variable pour detecter le joueur courant et si la partie est fini
		mJoueurs = new Joueur[2];
		mPlateau = new C4Plateau();
       if(autoJoueur1){ //Creation joueur 1 automatique
		   mJoueurs[0] = new JoueurAutomatique();
	   }else{ //Creation joueur 1 manuel
		    mJoueurs[0] = new JoueurManuel();
	   }
	   
	   if(autoJoueur2){  //Creation joueur 2 automatique
		   mJoueurs[1] = new JoueurAutomatique();
	   }else{  //Creation joueur 2 manuel
		    mJoueurs[1] = new JoueurManuel();
	   }
    }

    /**
     * Fonction d'acces aux cases du plateau
     * @param pColonne, la colonne du tableau 
     * @param pRangee, la rangee du plateau
     */
    public int getCase(int pColonne, int pRangee){
        return mPlateau.getTableau()[pColonne][pRangee];
        //ce return n'est present que pour permettra la compilation
    }
    
    /**
     * Fonction indiquant si le jeu est termine ou non 
     * Le jeu est termine quand le plateau est rempli ou quand il y a eu un gagnant
     * @return true si la partie est terminee
     */
    public boolean isFinished(){
        return getGagnant() != mPlateau.VIDE || mPlateau.isRemplie(); // il y'a un gagnant ou la partie est nul
    }   

    /**
     * Fonction indiquant si le joueur courant est automatique ou non 
     * @return boolean true si le joueur courant est automatique
     */
    public boolean isJoueurCourantAuto(){
        return mJoueurs[mJoueurCourant] instanceof JoueurAutomatique;
    }
    
    /**
     * Fonction indiquant s'il y a un gagnant ou non 
     * @return int le numero du gagnant (0 ou 1), -1 s'il n'y a pas de gagnant
     */
    public int getGagnant(){
        return mPlateau.getGagnant();
    }
    /**
     *Fonction permettant de jouer un coup. Cette fonction effectue les operations suivantes:
     * 1. demande au joueur courant quelle est la colonne sur laquelle il veut joueur 
     * 2. ajoute le jeton dans le plateau. Le jeton ajoute sera celui associe au joueur courant. 
     * 3. change la valeur du joueur courant puisque c'est le tour d'un nouveau joueur
     */
    public void joue(){
		Scanner sc = new Scanner(System.in);
		mJoueurCourant = (mCoupsJoues%2 == 0) ? 0 : 1;
		System.out.println("mJoueurCourant"+mJoueurCourant);
		mPlateau.addJeton( mJoueurs[mJoueurCourant].getColonne(),mJoueurCourant);
		mCoupsJoues++;
		
    }
    /**
     * Cette fonction permet de reinitialiser la colonne choisie par le joueur courant. Cette fonction est appelee
     * par l'interface graphique lorsqu'un joueur clique sur une des colonnes
     * @param pColonne la colonne choisie par le joueur courant 
     */
   public void setColonneChoisie(int pColonne){
	    mJoueurCourant = (mCoupsJoues%2 == 0) ? 0 : 1;
	    mJoueurs[mJoueurCourant].setColonne(pColonne);
    }
    /**
     * Pour debugger. Vous pouvez imprimer les informations que vous voulez, mais je vous suggere d'imprimer le plateau de jeu. 
     * @return String la chaine de caractere representant le jeu
     */
    public String toString(){
      return "";
    }
}
