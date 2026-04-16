public class SumExample {
    public static void main(String[] args) {
        int sum = 0;          // start with 0
        for (int i = 0; i < 10; i++) {   // i goes from 0 to 9
            sum += i;        // add i to the running total
        }
        System.out.println(sum);   // print the result
    }
}