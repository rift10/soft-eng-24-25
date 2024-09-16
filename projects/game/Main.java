
public class Main {

    public static Input mInput = new Input();
    public static Character mSprite = new Character();

    public static void main(String[] args) {
        while (true) {
            mSprite.periodic();
            if (mInput.getDirection() != Input.Direction.NONE) {
                System.out.println(mInput.getDirection());
            }
        }
    }
}
