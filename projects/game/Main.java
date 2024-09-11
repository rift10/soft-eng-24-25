import javax.swing.JTextArea;
import javax.swing.text.html.HTMLDocument;

public class Main {

    public static Input mInput = new Input();
    public static JTextArea mTextArea = new JTextArea(new HTMLDocument(), "hello world", 10, 10);

    public static void main(String[] args) {
        mTextArea.addKeyListener(mInput);
        // while (true) {
        //     System.out.println(mInput.getDirection());
        // }
    }
}
