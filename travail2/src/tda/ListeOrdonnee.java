package tda;


import modele.communication.Connexion;

public class ListeOrdonnee {

    // constantes
    public final static int VALEUR_ABSENTE = -1;

    // membres priv�s contenant l'information n�cessaire
    // � l'op�ration de la liste
    private Connexion[] tab;
    private int nbElem = 0;

    /**
     * Constructeur, initialise une liste de la dimension fournis
     * @param dimListe(int), dimension de la liste
     */
    public ListeOrdonnee(int dimListe){
        // alloue l'espace pour le tableau
        this.tab = new Connexion[dimListe];
    }

    /**
     * Accesseur permettant de conna�tre le nombre d'�l�ment dans la liste
     * @return nombre d'�l�ment dans la liste.
     */
    public int getNbElem(){
        return this.nbElem;
    }

    /**
     * Accesseur permettant de conna�tre le nombre de place libre dans la liste
     * @return nombre de place libre dans la liste
     */
    public int getPlaceDispo(){
        return this.tab.length-this.nbElem;
    }

    /**
     * M�thode permettant d'ajouter un �l�ment � la fin de la liste
     * @param element(int), element � ajouter
     * @throws Exception si la liste est pleine
     */
    public void ajouterFin(Connexion element) throws Exception{
        // v�rifie qu'il y a assez d'espace dans la liste
        if(this.nbElem >= this.tab.length){
            throw new Exception("La liste est pleine");
        }

        // ajoute l'�l�ment � la fin et ajuste le compte
        this.tab[this.nbElem] = element;
        this.nbElem++;

    }

    /**
     * M�thode permettant d'ajouter un �l�ment au d�but de la liste
     * @param element(int), element � ajouter
     * @throws Exception si la liste est pleine
     */
    public void ajouterDebut(Connexion element) throws Exception{

        // v�rifie qu'il y a assez d'espace dans la liste
        if(this.nbElem >= this.tab.length){
            throw new Exception("La liste est pleine");
        }

        // d�cale les �l�ments
        this.decalerTableauHaut(0);
        // ajoute l'�l�ment au d�but et ajuste le compte
        this.tab[0] = element;
        this.nbElem++;
    }

    /**
     * M�thode permettant d'inserer un �l�ment � un index dans la liste
     * Si l'index est plus grand que le nombre d'�l�ment, l'�l�ment est ajout�
     * � la fin.
     * @param element(int), element � ajouter
     * @param index(int), index o� l'ajouter
     * @throws Exception si la liste est pleine
     */
    public void ajouterIndex(Connexion element, int index) throws Exception{


        // v�rifie qu'il y a assez d'espace dans la liste
        if(this.nbElem >= this.tab.length){
            throw new Exception("La liste est pleine");
        }

        if(index<0){
            return;
        }

        // v�rifie si l'index donne une insertion entre 2 �l�ments
        // existant
        if(index<this.nbElem){
            // oui, cr�e un espace et ajoute.
            this.decalerTableauHaut(index);
            this.tab[index] = element;
            this.nbElem++;
        }else{
            // ajoute l'�l�ment � la fin
            this.ajouterFin(element);
        }
    }

    /**
     * M�thode permettant d'enlever un �l�ment � la fin de la liste
     * @return l'�l�ment enlev�
     * @throws Exception si la liste est vide
     */
    public Connexion enleverFin() throws Exception{

        // v�rifie qu'il y a assez d'espace dans la liste
        if(this.nbElem <= 0){
            throw new Exception("La liste est vide");
        }

        // enl�ve de la fin et retourne le dernier �l�ment
        this.nbElem--;
        return this.tab[this.nbElem+1];

    }

    /**
     * M�thode permettant d'enlever un �l�ment au d�but de la liste
     * @return l'�l�ment enlev�
     * @throws Exception si la liste est vide
     */
    public Connexion enleverDebut() throws Exception{

        // v�rifie qu'il y a assez d'espace dans la liste
        if(this.nbElem <= 0){
            throw new Exception("La liste est vide");
        }

        // copie l'�l�ment
        Connexion elemCopy = this.tab[0];

        // enl�ve du d�but et d�cale les �l�ments
        this.nbElem--;
        decalerTableauBas(0);

        // retourne l'�l�ment enlev�
        return elemCopy;
    }

    /**
     * M�thode permettant d'enlever un �l�ment dans la liste � l'index
     * Si l'index est plus grand que le nombre d'�l�ment, l'�l�ment de la fin
     * est enlev�. Si l'index est n�gatif, l'�l�ment du d�but est enlev�
     * @return l'�l�ment enlev�
     * @throws Exception si la liste est vide
     */
    public Connexion enleverIndex(int index) throws Exception{

        // v�rifie qu'il y a assez d'espace dans la liste
        if(this.nbElem <= 0){
            throw new Exception("La liste est vide");
        }

        // si l'index est n�gatif retourne l'�l�ment du d�but
        if(index < 0){
            return this.enleverDebut();
            // si l'index est trop grand, retourne la fin
        }else if(index >= this.nbElem){
            return this.enleverFin();
            // sinon retourne de l'index et r�arrange le tableau
        }else{
            Connexion elemCopy = this.tab[index];
            this.nbElem--;
            decalerTableauBas(index);
            return elemCopy;
        }
    }

    /**
     * M�thode utilis� pour cr�er un espace dans la liste
     * tous les �l�ments � partir de l'index fournis sont d�cal�
     * vers le haut.
     * @param index, index d'o� d�caler les �l�ments
     */
    private void decalerTableauHaut(int index){
        for(int i=this.nbElem; i>0 ; i--){
            this.tab[i+1] = this.tab[i];
        }
    }

    /**
     * M�thode utilis� pour combler un espace dans la liste
     * tous les �l�ments � partir de l'index fournis sont d�cal�
     * vers le bas.
     * @param index, index d'o� d�caler les �l�ments
     */
    private void decalerTableauBas(int index){
        for(int i=index; i<this.nbElem ; i++){
            this.tab[i] = this.tab[i+1];
        }
    }

    public Connexion getElement(int index){
        return tab[index];
    }

    private void setElement(int index,Connexion nouvelVal){
        tab[index] = nouvelVal;
    }


    /**
     * Tri Insertion, fait le tri en reculant une valeur � sa place en allant
     * vers la d�but du tableau
     * @param tab, le tableau � trier

     */
    public static void triInsertion(ListeOrdonnee tab){
        // Tri du vecteur par insertion.
        for(int indice = 1;indice<tab.nbElem; indice++){
            Connexion valeur_a_reculer = tab.getElement(indice);

            // Pour chaque valeur tab(i) superieure a la valeur_a_reculer,
            // on copie cette valeur dans tab(i + 1).
            int position = indice - 1;
            while ((position >= 0) && tab.getElement(position).getNumDeConnexion() > valeur_a_reculer.getNumDeConnexion()){
                tab.setElement(position + 1, (tab.getElement(position)));
                position = position - 1;
            }
            // Ensuite, on copie la valeur_a_reculer dans la derniere valeur
            // tab(i) superieure trouvee.
            int test = tab.getElement(position + 1).getNumDeConnexion();
            tab.setElement(position + 1, valeur_a_reculer);
        }

    }

    /**
     * Fouille dichotomique. Commence au milieu, parcour le tableau en le coupant en deux
     * � chaque fois.
     * @param tab, le tableau � fouiller
     * @param cible, la valeur recherche
     * @return index o� la cible se trouve
     */
    public static int fouilleDichotomique(ListeOrdonnee tab, int cible){

        // Fouille dichotomique dans le tableau.
        // Au debut, l'espace de recherche va de 1 a tab.length.
        int debut = 0;
        int fin = tab.nbElem -1;
        int milieu = 0;

        boolean trouvee = false;

        //tanque les indices ne se sont pas crois�s
        while (!trouvee && debut <= fin){

            // On trouve le milieu de l'espace de recherche.
            milieu = (debut + fin) / 2;

            //On conserve la moitie de droite ou de gauche de l'espace de
            // recherche en d�pla�ant les indice de d�but ou de fin
            if (cible < tab.getElement(milieu).getNumDeConnexion())
                fin = milieu - 1;

            else if (cible > tab.getElement(milieu).getNumDeConnexion())
                debut = milieu + 1;

            else
                trouvee = true;
        }

        //On v�rifie si la valeur n'a �t� trouv�e
        //On doit mettre la valeur n�gative.
        if(!trouvee){
            milieu = VALEUR_ABSENTE;
        }
        //On retourne la position
        return milieu;
    }


}
