package gui;
/**
 * Classe permettant l'affichage du jeu Connect4 
 * @author Melissa Jourdain
 * @date h2014
 */
import javax.swing.*;

import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import jeu.*;

@SuppressWarnings("serial")

public class C4Gui extends JFrame{
    //le jeu affiche
    private C4Jeu mJeu; 
    //le panel permettant l'affichage du jeu
    private C4Panel mPanelJeu;
    //les options possibles pour chacun des joueurs (automatique ou manuel)
    private JComboBox<String> mJoueur1Box, mJoueur2Box;
    //le bouton de demarrage de la partie
    private JButton buttonStart;
    //panel qui contient les boutons et combo box.
    private JPanel panDown;
    //la largeur en pixel de chacune des colonnes du jeu affiche de Connect4
    private final static int WIDTH_COLONNE= 100;
    /**
     * Constructeur de l'interface graphique
     * Cree les differentes composantes, les connecte a leur fonction rattachee, et 
     * les place dans la fenetre d'affichage
     */
    public C4Gui(){
        super("Connect4");
        //les options possibles pour chacun des joueurs
        String[] choixJoueur1 = {"Joueur1:manuel","Joueur1:automatique"};
        String[] choixJoueur2 = {"Joueur2:manuel","Joueur2:automatique"};

        //au depart aucun jeu n'est lance donc aucun jeu n'est affiche
        mJeu = null; 

        this.setLayout(new BorderLayout());

        //CREATION DES COMPOSANTES GRAPHIQUES       
        mPanelJeu = new C4Panel();
        mJoueur1Box = new JComboBox<String>(choixJoueur1);
        mJoueur2Box = new JComboBox<String>(choixJoueur2);
        buttonStart = new JButton("Commencer la partie"); 

        //AJOUT DES COMPOSANTES GRAPHIQUES A LA FENETRE GRAPHIQUE
        //hack pour centrer le panel d'affichage de jeu
        this.add(new JLabel("           "),BorderLayout.WEST);
        this.add(mPanelJeu, BorderLayout.CENTER);

        //on ajoute un panel qui contiendra les boutons et autres composantes de commandes
        panDown = new JPanel();
        panDown.setLayout(new FlowLayout());
        panDown.add(mJoueur1Box);
        panDown.add(mJoueur2Box);
        panDown.add(buttonStart);
        this.add(panDown,BorderLayout.SOUTH);

        //on fixe la taille et les differentes caracteristiques de la fenetre graphique
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,700);
        this.setVisible(true);
    }
    /**
     * Fonction d'acces a la valeur des comboBox 
     * @param le numero du joueur pour lequel on veut obtenir l'information (manuel ou automatique?)
     * @return boolean true si le joueur numero est automatique, false sinon
     */
    public boolean isJoueurAuto(int numero){
        if(numero == 1)
            return mJoueur1Box.getSelectedIndex()==1?true:false;
        else
            return mJoueur2Box.getSelectedIndex()==1?true:false;
    }

    /**
     * Fonction permettant de changer le jeu Connect4 a afficher 
     * @param pJeu le jeu a afficher
     */
    public void setJeu(C4Jeu pJeu){
        mJeu = pJeu;
    }

    /**
     * Fonction permettant d'ajouter un MouseListener au panel affichant le jeu 
     * @param listener le listener 
     */
    public void addMouseListener(MouseListener listener){
        mPanelJeu.addMouseListener(listener);
    }

    /**
     * Fonction ajoutant un ActionListener au bouton de demarrage du jeu 
     * @param listener le listener qui interagira avec le bouton
     */
    public void addActionListener(ActionListener listener){
        buttonStart.addActionListener(listener);
    }

    /**
     * Fonction d'acces a la largeur d'une colonne du jeu 
     */
    public static int getWidthColonne(){
        return WIDTH_COLONNE;
    }

    /**
     * Classe responsable de l'affichage du jeu 
     */
    class C4Panel extends JPanel{
        //les couleurs des pions (vide = BLUE, RED = joueur1, BLACK= joueur2)
        Color[] colorTab = {Color.BLUE, Color.RED, Color.BLACK}; 
        /*
         * Permet l'affichage graphique du jeu  
         */
        public void paint(Graphics g){
            //le message qui sera affiche quand la partie sera terminee
            String gagnant = "Le gagnant est le joueur ";
            String partieNulle = "Partie nulle";
            String toDisplay;
            //on initialise la taille du panel
            setSize(C4Plateau.LARGEUR*WIDTH_COLONNE,C4Plateau.HAUTEUR*WIDTH_COLONNE);
            g.setColor(Color.YELLOW);
            Rectangle area = g.getClipBounds();            
            g.fillRect((int)area.getX(),(int)area.getY(),(int)area.getWidth()-1,(int)area.getHeight()-1);
            int xStart = WIDTH_COLONNE/4, yStart = WIDTH_COLONNE/4;
            int step = WIDTH_COLONNE;
            Color currentColor;
            int boardState;
            //dessin de chacune des cellules du jeu COnnect4
            for(int i = 0; i<C4Plateau.LARGEUR; i++)
                for(int j = 0; j<C4Plateau.HAUTEUR; j++){
                    if(mJeu != null)
                        boardState = mJeu.getCase(i,j);
                    else
                        boardState = -1;
                    if(boardState <-1 || boardState>1){
                        System.out.println("Erreur dans la fonction getCase de Connect4Jeu: "+boardState);
                        System.exit(0);
                    }
                    currentColor = colorTab[boardState+1];
                    g.setColor(currentColor);
                    g.fillOval(xStart+(i*step), yStart+(j*step),WIDTH_COLONNE/2,WIDTH_COLONNE/2);
                    g.setColor(Color.BLACK);
                    g.drawOval(xStart+(i*step), yStart+(j*step),WIDTH_COLONNE/2,WIDTH_COLONNE/2);

                }
            for(int i = 0; i<C4Plateau.LARGEUR; i++){
                g.drawRect(i*WIDTH_COLONNE-1,0,WIDTH_COLONNE,(int)area.getHeight()-1);
            }
            //dessin des contours du jeu
            g.drawLine((int)area.getWidth()-1,0,(int)area.getWidth()-1,(int)area.getHeight()-1);
            g.drawLine(0,0,0,(int)area.getHeight()-1);

            //si le jeu est fini, on affiche le message indiquant le gagnant/partie nulle
            if(mJeu != null)
                if(mJeu.isFinished()){
                    if(mJeu.getGagnant() == -1)
                        toDisplay = partieNulle;
                    else
                        toDisplay = gagnant+mJeu.getGagnant();
                    g.drawString(toDisplay,(int)area.getWidth()/2,(int)area.getHeight()/2);
                }
        }
    }
}
