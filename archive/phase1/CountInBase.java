import java.util.Scanner;

public class CountInBase {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Give the base to count in: ");
        int base = input.nextInt();

        int[] number = {0, 0, 0, 0, 0, 0};

        printNumber(number);
        for(int i = 1; i < Math.pow(base, 5); i++) {
            increment(number, base);
            printNumber(number);
        }

    }

    public static void increment(int[] number, int base) {

        number[(number.length - 1)]++;
        for(int i = (number.length - 1); i >= 0; i--) {

            if(number[i] > (base - 1)) {
                number[i] = 0;
                number[i - 1]++;
            } else {
                return;
            }

        }

    }

    public static void printNumber(int[] number) {
        for(int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }
        System.out.println();
    }

}
