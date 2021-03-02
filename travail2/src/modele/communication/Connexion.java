package modele.communication;

import modele.reseau.Antenne;

import java.util.Objects;

public class Connexion {
    //CONSTANTES
    final int NB_ANTENNES = 2;

    //ATTRIBUTS
    int numDeConnexion;
    public Antenne[] tabAntenne = new Antenne[NB_ANTENNES];

    //CONSTRUCTEUR
    public Connexion(int numDeConnexion, Antenne appelant, Antenne appele) {
        this.numDeConnexion = numDeConnexion;
        tabAntenne[0] = appelant;
        tabAntenne[1] = appele;
    }

    //GETTER
    public int getNumDeConnexion() {
        return numDeConnexion;
    }

    /**
     * compare 2 numero de connexion
     * @param numDeConnexion le numeor a comparé
     * @return boolean
     */
    public boolean equals(int numDeConnexion) {
      return this.numDeConnexion == numDeConnexion;
    }

    //SERVICES
    /**
     * Met a jour la connexion pour le changement d'atenne
     * @param ancienne anciennne atenne connecté
     * @param nouvelle nouvelle antenne assigné
     */
    public void miseAJourConnexion(Antenne ancienne, Antenne nouvelle){
        if(ancienne == tabAntenne[0]){
            tabAntenne[0] = nouvelle;
        }else{
            tabAntenne[1] = nouvelle;
        }
    }

}
