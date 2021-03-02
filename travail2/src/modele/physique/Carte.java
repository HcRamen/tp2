package modele.physique;
import java.util.Random;

public class Carte {
    //attributs
    public static final Position TAILLE = new Position(1920,1080);
    public static Random rand = new Random();

    /**
     * genere une position random
     * @return la postion random
     */
    public static Position randPos(){
        double x = rand.nextInt((int)TAILLE.getX());
        double y = rand.nextInt((int)TAILLE.getY());

        return new Position(x,y);
    }

    /**
     * verifie si le point en parametre depasse les limites et le rammene dans la carte selon la logique toroide
     * si c'est le cas
     * @param position point qu"on verifie
     */
    public static void ajuster(Position position){
        if(position.getX() < 0){
            position.setX(TAILLE.getX());
        }
        if(position.getY() < 0){
            position.setY(TAILLE.getY());
        }
        if(position.getX() > TAILLE.getX()){
            position.setX(0);
        }
        if(position.getY() > TAILLE.getY()){
            position.setY(0);
        }
    }

    public static void test(){

    }
}
