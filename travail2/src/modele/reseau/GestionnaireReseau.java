package modele.reseau;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import modele.communication.Connexion;
import modele.communication.Message;
import modele.gestionnaires.GestionnaireScenario;
import modele.physique.Carte;
import modele.physique.Position;
import org.omg.PortableInterceptor.NON_EXISTENT;
import tda.ListeOrdonnee;

/**
 * Le gestionnaire réseau est responsable de gérer les connexions cellulaires et de relayer
 * les appels, messages et fin d'appel.
 * 
 * Dans le cadre de la simulation, c'est également le gestionnaire réseau qui instancie Antennes et
 * cellulaire et qui gère l'exécution par tour des cellulaires.
 * 
 * Le gestionnaire réseau est un singleton
 * 
 * @author Fred Simard
 * @version Hiver 2021
 */


import observer.MonObservable;

public class GestionnaireReseau extends MonObservable implements Runnable {

	//CONSTANTES
	public final int PERIODE_SIMULATION_MS = 100;
	public final double VITESSE = 10.0;
	public final double DEVIATION_STANDARD = 0.05;
	public static final int NB_CELLULAIRES = 30;
	final int NB_ANTENNES = 10;
	public static final int NB_CRIMINELS = 4;
	public final int CODE_NON_CONNECTE = -1;

	//ATTRIBUTS
	Random rand = new Random();
	ArrayList<Antenne> antennes = new ArrayList<>();
	ArrayList<Cellulaire> cellulaires = new ArrayList<>();
	ListeOrdonnee connexions = new ListeOrdonnee(NB_CELLULAIRES);

	private boolean mondeEnVie = true;
	private static GestionnaireReseau instance = new GestionnaireReseau();

	//METHODES PRIVEES
	void creeAntennes(){
		for(int i=0; i<NB_ANTENNES; i++){
			Antenne antenne = new Antenne(Carte.randPos().getX(), Carte.randPos().getY());
			antennes.add(antenne);
		}
	}
	void creeCellulaires() throws Exception {
		for(int i=0; i<NB_CELLULAIRES; i++){
			Cellulaire cellulaire = new Cellulaire(
					Carte.randPos().getX(),
					Carte.randPos().getY(),
					VITESSE,
					DEVIATION_STANDARD,
					GestionnaireScenario.obtenirNouveauNumeroStandard()
			);
			cellulaire.setAntenneConnecte();
			cellulaires.add(cellulaire);
		}
	}


	//SERVICES
	public Antenne getAntenneProche(ArrayList<Antenne> antennes,Cellulaire cellulaire){
		double min = 0;
		double comparable;
		Antenne antenne = antennes.get(0);

		for(int i=0; i < instance.NB_ANTENNES; i++) {
			comparable = antennes.get(i).distanceCel(cellulaire);
			if (comparable <= min) {
				min = comparable;
				antenne = antennes.get(i);
			}
		}
		return antenne;
	}

	public int relayerAppel(String numeroAppele, String numeroAppelant, Antenne antenneConnecte, int numeroDeConnexion) throws Exception {
		for(int i = 0;i<antennes.size();i++){
			if(cellulaires.contains(antennes.get(i).repondre(numeroAppele,numeroAppelant, numeroDeConnexion))){
				if(antennes.get(i).repondre(numeroAppele,numeroAppelant, numeroDeConnexion).estConnecte()){
					Connexion connexion = new Connexion(numeroDeConnexion, antennes.get(i),antenneConnecte);
					connexions.ajouterFin(connexion);
					ListeOrdonnee.triInsertion(connexions);
					return numeroDeConnexion;
				}
			}
		}
		return CODE_NON_CONNECTE;
	}

	public void relayerMessage(Message message,int numeroDeConnexion,Antenne appelant){
		Connexion connexion = trouverConnexion(numeroDeConnexion);
		if(connexion != null){
			if(connexion.tabAntenne[0] != appelant){
				connexion.tabAntenne[0].recevoir(message);
			}else{
				connexion.tabAntenne[1].recevoir(message);
			}
		}
	}

	public void relayerFinAppel(String numeroAppele, int numDeConnexion) throws Exception {
		Connexion connexion = trouverConnexion(numDeConnexion);
		if(connexion != null){
			for(int i=0;i<connexion.tabAntenne[0].listeCellulaire.size();i++){
				if(connexion.tabAntenne[0].listeCellulaire.get(i).getNumeroLocal().equals(numeroAppele)){
					connexion.tabAntenne[0].listeCellulaire.get(i).finAppelDistant(numeroAppele,numDeConnexion);
				}
			}
			for(int j = 0;j<connexions.getNbElem();j++){
				if(connexions.getElement(j) == connexion){
					connexions.enleverIndex(j);
				}

			}
		}
	}

	Connexion trouverConnexion(int numeroDeConnexion){
		for(int i=0;i<connexions.getNbElem();i++){
			Connexion connexion = connexions.getElement(i);
			if(connexion.getNumDeConnexion() == numeroDeConnexion){
				return connexion;
			}
		}
		return null;
	}

	public void mettreAJourConnexion(int numeroDeConnexion,Antenne ancienneAntenne,Antenne nouvelleAntenne){
		Connexion connexion = trouverConnexion(numeroDeConnexion);
		if(connexion != null){
			connexion.miseAJourConnexion(ancienneAntenne, nouvelleAntenne);
		}

	}




	/**
	 * méthode permettant d'obtenir une référence sur le Gestionnaire Réseau
	 * @return instance
	 */
	public static GestionnaireReseau getInstance() {
		return instance;
	}
	
	private GestionnaireReseau() {}

	/**
	 * permet de mettre fin à la simulation
	 * //@param mondeEnVie
	 */
	public void finDeSimulation() {
		this.mondeEnVie = false;
	}


	/**
	 * s'exécute en continue pour simuler le système
	 */
	@Override
	public void run() {
		creeAntennes();
		try {
			creeCellulaires();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.avertirLesObservers();

		while(this.mondeEnVie) {	
			
			for(Cellulaire cell : cellulaires) {
				try {
					cell.effectuerTour();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			this.avertirLesObservers();
			
			
			try {
				Thread.sleep(PERIODE_SIMULATION_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Antenne> getAntennes() {
		return antennes;
	}

	public ArrayList<Cellulaire> getCellulaires() {
		return cellulaires;
	}
}
