
public class RunnableTwo implements CustomRunnable {

    public RunnableTwo() {}

    @Override
    public void run() {
        System.out.println("two");
    }

    @Override
    public int compareTo(CustomRunnable r) {
        if (r.getNumber() == this.getNumber()) return 0;
        return ((r.getNumber() < this.getNumber()) ? 1 : -1);
    }

    @Override
    public int getNumber() {
        return 2;
    }
}