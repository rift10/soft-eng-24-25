
public class RunnableThree implements CustomRunnable {

    public RunnableThree() {}
    
    @Override
    public void run() {
        System.out.println("three");
    }

    @Override
    public int compareTo(CustomRunnable r) {
        if (r.getNumber() == this.getNumber()) return 0;
        return ((r.getNumber() < this.getNumber()) ? 1 : -1);
    }

    @Override
    public int getNumber() {
        return 3;
    }
}