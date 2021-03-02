package tda;


import modele.communication.Connexion;

public class ListeOrdonnee {

    // constantes
    public final static int VALEUR_ABSENTE = -1;

    // membres privés contenant l'information nécessaire
    // à l'opération de la liste
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
     * Accesseur permettant de connaître le nombre d'élément dans la liste
     * @return nombre d'élément dans la liste.
     */
    public int getNbElem(){
        return this.nbElem;
    }

    /**
     * Accesseur permettant de connaître le nombre de place libre dans la liste
     * @return nombre de place libre dans la liste
     */
    public int getPlaceDispo(){
        return this.tab.length-this.nbElem;
    }

    /**
     * Méthode permettant d'ajouter un élément à la fin de la liste
     * @param element(int), element à ajouter
     * @throws Exception si la liste est pleine
     */
    public void ajouterFin(Connexion element) throws Exception{
        // vérifie qu'il y a assez d'espace dans la liste
        if(this.nbElem >= this.tab.length){
            throw new Exception("La liste est pleine");
        }

        // ajoute l'élément à la fin et ajuste le compte
        this.tab[this.nbElem] = element;
        this.nbElem++;

    }

    /**
     * Méthode permettant d'ajouter un élément au début de la liste
     * @param element(int), element à ajouter
     * @throws Exception si la liste est pleine
     */
    public void ajouterDebut(Connexion element) throws Exception{

        // vérifie qu'il y a assez d'espace dans la liste
        if(this.nbElem >= this.tab.length){
            throw new Exception("La liste est pleine");
        }

        // décale les éléments
        this.decalerTableauHaut(0);
        // ajoute l'élément au début et ajuste le compte
        this.tab[0] = element;
        this.nbElem++;
    }

    /**
     * Méthode permettant d'inserer un élément à un index dans la liste
     * Si l'index est plus grand que le nombre d'élément, l'élément est ajouté
     * à la fin.
     * @param element(int), element à ajouter
     * @param index(int), index où l'ajouter
     * @throws Exception si la liste est pleine
     */
    public void ajouterIndex(Connexion element, int index) throws Exception{


        // vérifie qu'il y a assez d'espace dans la liste
        if(this.nbElem >= this.tab.length){
            throw new Exception("La liste est pleine");
        }

        if(index<0){
            return;
        }

        // vérifie si l'index donne une insertion entre 2 éléments
        // existant
        if(index<this.nbElem){
            // oui, crée un espace et ajoute.
            this.decalerTableauHaut(index);
            this.tab[index] = element;
            this.nbElem++;
        }else{
            // ajoute l'élément à la fin
            this.ajouterFin(element);
        }
    }

    /**
     * Méthode permettant d'enlever un élément à la fin de la liste
     * @return l'élément enlevé
     * @throws Exception si la liste est vide
     */
    public Connexion enleverFin() throws Exception{

        // vérifie qu'il y a assez d'espace dans la liste
        if(this.nbElem <= 0){
            throw new Exception("La liste est vide");
        }

        // enlève de la fin et retourne le dernier élément
        this.nbElem--;
        return this.tab[this.nbElem+1];

    }

    /**
     * Méthode permettant d'enlever un élément au début de la liste
     * @return l'élément enlevé
     * @throws Exception si la liste est vide
     */
    public Connexion enleverDebut() throws Exception{

        // vérifie qu'il y a assez d'espace dans la liste
        if(this.nbElem <= 0){
            throw new Exception("La liste est vide");
        }

        // copie l'élément
        Connexion elemCopy = this.tab[0];

        // enlève du début et décale les éléments
        this.nbElem--;
        decalerTableauBas(0);

        // retourne l'élément enlevé
        return elemCopy;
    }

    /**
     * Méthode permettant d'enlever un élément dans la liste à l'index
     * Si l'index est plus grand que le nombre d'élément, l'élément de la fin
     * est enlevé. Si l'index est négatif, l'élément du début est enlevé
     * @return l'élément enlevé
     * @throws Exception si la liste est vide
     */
    public Connexion enleverIndex(int index) throws Exception{

        // vérifie qu'il y a assez d'espace dans la liste
        if(this.nbElem <= 0){
            throw new Exception("La liste est vide");
        }

        // si l'index est négatif retourne l'élément du début
        if(index < 0){
            return this.enleverDebut();
            // si l'index est trop grand, retourne la fin
        }else if(index >= this.nbElem){
            return this.enleverFin();
            // sinon retourne de l'index et réarrange le tableau
        }else{
            Connexion elemCopy = this.tab[index];
            this.nbElem--;
            decalerTableauBas(index);
            return elemCopy;
        }
    }

    /**
     * Méthode utilisé pour créer un espace dans la liste
     * tous les éléments à partir de l'index fournis sont décalé
     * vers le haut.
     * @param index, index d'où décaler les éléments
     */
    private void decalerTableauHaut(int index){
        for(int i=this.nbElem; i>0 ; i--){
            this.tab[i+1] = this.tab[i];
        }
    }

    /**
     * Méthode utilisé pour combler un espace dans la liste
     * tous les éléments à partir de l'index fournis sont décalé
     * vers le bas.
     * @param index, index d'où décaler les éléments
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
     * Tri Insertion, fait le tri en reculant une valeur à sa place en allant
     * vers la début du tableau
     * @param tab, le tableau à trier

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
     * à chaque fois.
     * @param tab, le tableau à fouiller
     * @param cible, la valeur recherche
     * @return index où la cible se trouve
     */
    public static int fouilleDichotomique(ListeOrdonnee tab, int cible){

        // Fouille dichotomique dans le tableau.
        // Au debut, l'espace de recherche va de 1 a tab.length.
        int debut = 0;
        int fin = tab.nbElem -1;
        int milieu = 0;

        boolean trouvee = false;

        //tanque les indices ne se sont pas croisés
        while (!trouvee && debut <= fin){

            // On trouve le milieu de l'espace de recherche.
            milieu = (debut + fin) / 2;

            //On conserve la moitie de droite ou de gauche de l'espace de
            // recherche en déplaçant les indice de début ou de fin
            if (cible < tab.getElement(milieu).getNumDeConnexion())
                fin = milieu - 1;

            else if (cible > tab.getElement(milieu).getNumDeConnexion())
                debut = milieu + 1;

            else
                trouvee = true;
        }

        //On vérifie si la valeur n'a été trouvée
        //On doit mettre la valeur négative.
        if(!trouvee){
            milieu = VALEUR_ABSENTE;
        }
        //On retourne la position
        return milieu;
    }


}
