
public class Main {

    public static Character character = new Character();

    public static void main(String[] args) {
        while (true) {
            character.periodic();
            // if ((character.getDirection() != Character.Direction.NONE) &&
            //         (character.getCurrentKeyCode() != character.getReleasedKeyCode())) {
            //     System.out.println("direction: " + character.getDirection());
            //     System.out.println("coordinate: " + character.getX() + ", " + character.getY());
            // }
        }
    }
}
