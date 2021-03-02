package modele.reseau;
import modele.communication.Message;

public interface UniteCellulaire{

    public int appeler(String numeroAppele, String numeroAppelant,Antenne antenneConnecte) throws Exception;

    public Cellulaire repondre(String numeroAppele, String numeroAppelant,int numDeConnexion);

    public void finAppelLocal(String numeroAppele, int numDeConnexion) throws Exception;

    public void finAppelDistant(String numeroAppele, int numDeConnexion);

    public void envoyer(Message message, int numDeConnexion);

    public void recevoir(Message message);
}
