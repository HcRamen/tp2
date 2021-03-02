package modele.physique;
import java.util.Random;
import static java.lang.Math.*;

public abstract class ObjetMobile extends ObjetPhysique{
    double vitesse;
    double direction = 0;
    double deviation;



    public ObjetMobile(double x, double y,double vitesse, double deviation) {
        super(x, y);
        this.vitesse = vitesse;
        this.deviation = deviation;
    }

    public void seDeplacer(){
        Random rand = new Random();
        direction = direction + rand.nextGaussian() + deviation;
        this.position.setX(this.getPosition().getX() + vitesse * cos(direction));
        this.position.setY(this.getPosition().getY() + vitesse * sin(direction));
        Carte.ajuster(position);

    }

    public void test(){

    }
}
