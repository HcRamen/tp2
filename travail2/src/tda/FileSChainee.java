package tda;

public class FileSChainee<String> {

    private class Noeud{

        String element;
        Noeud suivant;

        public Noeud(String element, Noeud suivant){
            this.element = element;
            this.suivant = suivant;
        }
    }

    private Noeud tete = null;

    public void enfiler(String element){

        if(tete==null) {
            tete = new Noeud(element, null);
        }else{

            Noeud courant = tete;

            while(courant.suivant!=null) {
                courant = courant.suivant;
            }

            courant.suivant = new Noeud(element, null);

        }

    }

    public String defiler() throws Exception{

        if(tete==null){
            throw new Exception("[FileSChainee] la file est vide");
        }

        String aRetourner = tete.element;
        tete = tete.suivant;

        return aRetourner;

    }


}

