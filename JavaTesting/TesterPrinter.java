

public class TesterPrinter {
    public static void main(String[] args){
        int numTesters = 20;
        Tester[] testers = new Tester[numTesters];
        for (int i = 0; i <testers.length; i++){
            testers[i] = new Tester();
        }
        for (int i = 0; i< testers.length; i++){
            System.out.println(testers[i].getId());
        }

    }
}
