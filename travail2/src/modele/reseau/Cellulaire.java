package modele.reseau;
import modele.communication.Connexion;
import modele.communication.Message;
import modele.gestionnaires.GestionnaireScenario;
import modele.physique.ObjetMobile;
import java.util.Random;


public class Cellulaire extends ObjetMobile implements UniteCellulaire{

    //CONSTANTES
    public final int NON_CONNECTE = -1;
    public final double PROB_APPELER = 0.05;//0.05
    public final double PROB_ENVOI_MSG = 0.2;
    public final double PROB_DECONNEXION = 0.1;

    //ATTRIBUTS
    String numeroLocal;
    int numeroConnexion = NON_CONNECTE;
    String numeroConnecte = null;

    Antenne antenneConnecte;
    Random rand = new Random();
    GestionnaireReseau reseau = GestionnaireReseau.getInstance();

    //CONSTRUCTEUR
    public Cellulaire(double x, double y, double vitesse, double deviation,String numeroLocal) {
        super(x, y, vitesse, deviation);
        this.numeroLocal = numeroLocal;
    }

    //GETTER
    public String getNumeroLocal() {
        return numeroLocal;
    }

    public int getNumeroConnexion() {
        String numSansTiret ="";
        char lettre;
        for(int i=3;i<numeroLocal.length();i++){
            lettre = numeroLocal.charAt(i);
            if(lettre != '-'){
                numSansTiret += lettre;
            }
        }
        return Integer.parseInt(numSansTiret);
    }

    //SETTER
    public void setAntenneConnecte() throws Exception {
        this.antenneConnecte = reseau.getAntenneProche(reseau.antennes,this);
        antenneConnecte.ajouterCel(this);
    }


    //METHODES
    @Override
    public int appeler(String numeroAppele, String numeroAppelant, Antenne antenneConnecte) throws Exception {
        numeroConnexion = getNumeroConnexion();
        estConnecte();
        antenneConnecte.appeler(numeroAppele,numeroAppelant,antenneConnecte);
        return numeroConnexion;
    }

    @Override
    public Cellulaire repondre(String numeroAppele, String numeroAppelant, int numDeConnexion) {
        if(this.numeroConnexion == NON_CONNECTE){
            numeroConnecte = numeroAppelant;
            numeroConnexion = numDeConnexion;
            estConnecte();
            return this;
        }
        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele, int numDeConnexion) throws Exception {
        numeroConnexion =NON_CONNECTE;
        estConnecte();
        antenneConnecte.finAppelLocal(numeroAppele,numDeConnexion);
    }

    @Override
    public void finAppelDistant(String numeroAppele, int numDeConnexion) {
        numeroConnexion =NON_CONNECTE;
        estConnecte();
    }

    @Override
    public void envoyer(Message message, int numDeConnexion) {

    }

    @Override
    public void recevoir(Message message) {
        System.out.println(message.getMessage());
    }

    public boolean estConnecte(){
        if(numeroConnexion != NON_CONNECTE){
            return true;
        }
        return false;
    }

    public boolean comparerNumero(String numero){
        return numeroLocal.equals(numero);
    }

    public void effectuerTour() throws Exception {
        Antenne ref = reseau.getAntenneProche(reseau.getAntennes(), this);
        seDeplacer();
        Antenne comparable = reseau.getAntenneProche(reseau.getAntennes(), this);
        if(ref != comparable){
            ref.enleverCel(this);
            comparable.ajouterCel(this);
            comparable.mettreAJourConnexion(numeroConnexion, ref);
        }
        if(!estConnecte()){
            if(rand.nextDouble() <= PROB_APPELER){
                appeler(GestionnaireScenario.obtenirNumeroStandardAlea(numeroLocal),numeroLocal,antenneConnecte);
            }
        }else{
            if(rand.nextDouble() <= PROB_ENVOI_MSG){
                if(numeroConnecte != null){
                    Message message = new Message(numeroConnecte, GestionnaireScenario.obtenirMessage(numeroLocal));
                    comparable.envoyer(message,numeroConnexion);
                }
            }else if(rand.nextDouble() <= PROB_DECONNEXION){
                finAppelLocal(numeroConnecte, numeroConnexion);
            }
        }


    }


    @Override
    public String toString() {
        return "Cellulaire{" +
                "numeroLocal='" + numeroLocal + '\'' +
                "position='" + getPosition() + '\'' +
                '}';
    }


}
