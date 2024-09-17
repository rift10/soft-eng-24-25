
public class Main {

    public static Character character = new Character();

    public static void main(String[] args) {
        while (true) {
            character.periodic();
            if (character.getDirection() != Character.Direction.NONE) {
                System.out.println(character.getDirection());
            }
        }
    }
}
