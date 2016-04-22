package gui;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;
import jeu.*;

public class C4Controller {

    //la partie de Connect4 en cours
    private C4Jeu mJeu;

    //l'interface graphique
    private C4Gui mViewer;

    //le thread d'execution
    private Thread mThread;
    private C4Thread mC4Thread;

    //indique si le joueur courant a choisi la colonne dans lequel il jouera
    boolean mNewCoordinate;

    /**
     * Constructeur de la classe 
     * @param pViewer 	L'interface graphique
     */
    public C4Controller( C4Gui pViewer) 
    {
        mViewer  = pViewer;
        mJeu = null;
        //connection des boutons et commandes de l'interface graphique au controleur
        mViewer.addActionListener(new C4Starter());
        mViewer.addMouseListener(new ListenerHit());
        //au depart, le thread n'est pas encore lance 
        mNewCoordinate = false;
        //on lance le thread de jeu
        mThread = new Thread(new C4Thread());
        mThread.run();

    }

    /**
     * Classe responsable de l'interaction avec les cliques de souris du panel affichant le jeu 
     * dans l'interface graphique
     */
    class ListenerHit implements MouseListener{
        /** 
         * Fonction appelee quand un clique de souris est fait dans le panel affichant le jeu 
         */
        public void	mouseClicked(MouseEvent e){
            //on traite l'evenement si une nouvelle coordonnee n'est pas deja en cours de traitement
            // et si un jeu a ete lance
            if(!mNewCoordinate && mJeu!=null){
                //on ajuste les coordonnees choisies pour le joueur courant dans le jeu
              mJeu.setColonneChoisie(e.getX()/C4Gui.getWidthColonne());
              //on indique qu'une nouvelle coordonnee a ete choisie
              mNewCoordinate = true;
           }
        }
        public void	mouseEntered(MouseEvent e){}
        public void	mouseExited(MouseEvent e){}
        public void	mousePressed(MouseEvent e){}
        public void	mouseReleased(MouseEvent e){}
    }
    /**
     * Classe interne listener associe au bouton "Commencer" de l'interface graphique
     */
    class C4Starter implements ActionListener {
        /**
         * Fonction gerant le lancement ou l'arret de la partie Connect4 en cours
         * @param ActionEvent l'evenement de clique sur le bouton commencer
         */
        public void actionPerformed(ActionEvent e) {
            //on verifie les options pour chacun des 2 joueurs
            boolean joueur1Auto, joueur2Auto;
            joueur1Auto = mViewer.isJoueurAuto(1);
            joueur2Auto = mViewer.isJoueurAuto(2);

            //on cree un nouveau jeu
            mJeu = new C4Jeu(joueur1Auto, joueur2Auto);

            //et on l'affiche
            mViewer.setJeu(mJeu);
            mViewer.repaint();
       }

    }
    /**
     * Thread d'execution du jeu 
     */
    class C4Thread implements Runnable{
        /**
         * Fonction definissant le deroulement d'une partie
         */
        public void run(){
            try {
                while (true ) {
                    //tant qu'un jeu n'est pas lance, on attend
                    while(mJeu == null)
                        Thread.sleep(10);
                    //une fois que la partie est lancee, on 
                    //boucle jusqu'a ce que la partie soit terminee
                    while(!mJeu.isFinished()){
                        //a chaque tour, on attend que le joueur courant ait joue son coup
                        //si c'est un joueur automatique, on n'attend pas un clique dans l'interface graphique
                        //si c'est un joueur manuel, on attend que des nouvelles coordonnees aient ete choisies dans l'interface 
                        while(!mJeu.isJoueurCourantAuto()&& !mNewCoordinate){
                            Thread.sleep(10);
                        }
                        //on fait jouer un coup le joueur courant
                        mJeu.joue(); 
                        //on actualise l'affichage du jeu
                        mViewer.repaint();
                        //puisque les coordonnees cliquees sont utilisees, on attend de nouvelles coordonnees
                        mNewCoordinate = false;
                    }
                    mViewer.repaint();
                }
                
            }
            catch (InterruptedException ie) {
            }
        }
    }
}

