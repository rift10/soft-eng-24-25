
public class Main {

    public static Input mInput = new Input();

    public static void main(String[] args) {
        while (true) {
            if (mInput.getDirection() != Input.Direction.NONE) {
                System.out.println(mInput.getDirection());
            }
        }
    }
}
