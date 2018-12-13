/**
 * Simple unit test framework.
 *
 * Author: Tomek
 */
public class SimpleUnitTest {

    protected static void it(String expectation, Runnable runnable) {

        try {

            runnable.run();
            System.out.println("✔ It " + expectation);

        } catch(Exception e) {

            System.out.println("✘ It " + expectation);
            System.out.println();
            System.out.println("Exception thrown: ");
            e.printStackTrace();
            System.exit(-1);

        } catch(AssertionError e) {

            System.out.println("✘ It " + expectation);
            System.out.println();

            if(e.getMessage() != null) {
                System.out.println("Assertion failed: " + e.getMessage());
            } else {
                System.out.println("Assertion failed!");
            }

            System.out.println();
            System.exit(-1);

        }

    }

}