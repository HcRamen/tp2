package modele.physique;

public class Position {
    //attributs
    private double x;
    private double y;
    Position position;

    //constructeur
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //getter
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    //setter
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }


    /**
     * Donne la distance entre 2 points donne en parametre
     * @param pos1 postion du point 1
     * @param pos2 postion du point 2
     * @return la distance entre les 2 points
     */
    public static double distance(Position pos1, Position pos2) {
        double x1 = pos1.x;
        double x2 = pos2.x;

        double y1 = pos1.y;
        double y2 = pos2.y;

        return (y2-y1)/(x2-x1);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * instancie un point et affiche la position dans la console
     */
    public void test(){
        Position position = new Position(0,0);
        System.out.println(position.toString());
    }

}
