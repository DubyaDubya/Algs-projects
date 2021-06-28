public class Tester{
    private static int counter = 0;
    private final int testerId;

    public Tester(){
        this.testerId = ++counter/2;
    }

    public int getId(){
        return testerId;
    }
}