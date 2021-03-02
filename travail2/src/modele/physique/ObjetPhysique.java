package modele.physique;

public abstract class ObjetPhysique {
    double x;
    double y;
    Position position;

    public ObjetPhysique(double x, double y) {
        this.x = x;
        this.y = y;
        this.position = new Position(x,y);
    }


    public Position getPosition() {
        return position;
    }
}
