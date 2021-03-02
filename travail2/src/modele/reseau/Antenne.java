package modele.reseau;
import modele.communication.Message;
import tda.ListeOrdonnee;
import modele.physique.ObjetPhysique;
import modele.physique.Position;

import java.util.ArrayList;


public class Antenne extends ObjetPhysique implements UniteCellulaire{
    //ATTRIBUTS
    GestionnaireReseau reseau = GestionnaireReseau.getInstance();
    ArrayList<Cellulaire> listeCellulaire = new ArrayList<>(GestionnaireReseau.NB_CELLULAIRES);
    Position position;

    //CONSTRUCTEUR
    public Antenne(double x, double y) {
        super(x, y);
        position = new Position(x, y);
    }

    //SERVICES
    public Position getPosition() {
        return position;
    }

    @Override
    public int appeler(String numeroAppele, String numeroAppelant, Antenne antenneConnecte) throws Exception {
        int numeroDeConnexion;
        for(int i=0;i<listeCellulaire.size();i++){
            if(listeCellulaire.get(i).getNumeroLocal().equals(numeroAppelant)){
                numeroDeConnexion = listeCellulaire.get(i).numeroConnexion;
                for(int j=0;j<reseau.antennes.size();j++){
                    return reseau.relayerAppel(numeroAppele,numeroAppelant,antenneConnecte,numeroDeConnexion);
                }
            }
        }

        return -1;
    }

    @Override
    public Cellulaire repondre(String numeroAppele, String numeroAppelant, int numDeConnexion) {
        for(int i=0;i<listeCellulaire.size();i++){
            if(listeCellulaire.get(i).getNumeroLocal().equals(numeroAppele)){
                listeCellulaire.get(i).repondre(numeroAppele,numeroAppelant,numDeConnexion);
                return listeCellulaire.get(i);
            }
        }
        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele, int numDeConnexion) throws Exception {
        reseau.relayerFinAppel(numeroAppele, numDeConnexion);
    }

    @Override
    public void finAppelDistant(String numeroAppele, int numDeConnexion) {
        for(int i=0;i<listeCellulaire.size();i++){
            if(listeCellulaire.get(i).getNumeroLocal().equals(numeroAppele)){
                listeCellulaire.get(i).finAppelDistant(numeroAppele,numDeConnexion);

            }
        }
    }

    @Override
    public void envoyer(Message message, int numDeConnexion) {
        reseau.relayerMessage(message,numDeConnexion, this);
    }

    @Override
    public void recevoir(Message message) {
        for(int i=0;i<listeCellulaire.size();i++){
            if(message.getNumDeDestination().equals(listeCellulaire.get(i).getNumeroLocal())){
                listeCellulaire.get(i).recevoir(message);
            }
        }
    }


    @Override
    public String toString() {
        return "Antenne{" +
                "position=" + getPosition() +
                '}';
    }

    public double distanceCel(Cellulaire cellulaire){
        return Position.distance(this.getPosition(),cellulaire.getPosition());
    }

    public void ajouterCel(Cellulaire cellulaire) throws Exception {
        listeCellulaire.add(cellulaire);
    }

    public void enleverCel(Cellulaire cellulaire) throws Exception {
        listeCellulaire.remove(cellulaire);
    }

    public void mettreAJourConnexion(int numeroDeConnexion,Antenne ancienneAntenne){
        reseau.mettreAJourConnexion(numeroDeConnexion, ancienneAntenne, this);
    }
}
