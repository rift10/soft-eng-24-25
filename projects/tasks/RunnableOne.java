public class RunnableOne implements CustomRunnable {

    public RunnableOne() {}

    @Override
    public void run() {
        System.out.println("one");
    }

    @Override
    public int compareTo(CustomRunnable r) {
        if (r.getNumber() == this.getNumber()) return 0;
        return ((r.getNumber() < this.getNumber()) ? 1 : -1);
    }

    @Override
    public int getNumber() {
        return 1;
    }
}