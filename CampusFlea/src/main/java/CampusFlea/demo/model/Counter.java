package CampusFlea.demo.model;

public class Counter {
    private int count;
    public Counter() {
        count = 0;
    }

    public int getAndIncrement() {
        return count++;
    }
}
