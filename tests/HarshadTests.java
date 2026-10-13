
import harshad.LinkedListOperations;
import java.util.Arrays;

public class HarshadTests {
    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        LinkedListOperations list = new LinkedListOperations();

        check("01 Empty list", list.isEmpty());
        check("02 Initial size", list.size() == 0);
        check("03 Empty search", list.search(10) == -1);
        check("04 Empty delete", !list.delete(10));
        check("05 Empty diagram", list.diagram().equals("HEAD -> null (empty list)"));

        list.insert(10);
        list.insert(20);
        list.insert(30);

        check("06 Insert size", list.size() == 3);
        check("07 Not empty after insert", !list.isEmpty());
        check("08 Insert order", list.toList().equals(Arrays.asList(10, 20, 30)));
        check("09 Search head", list.search(10) == 0);
        check("10 Search middle", list.search(20) == 1);
        check("11 Search tail", list.search(30) == 2);
        check("12 Search missing", list.search(99) == -1);

        check("13 Delete middle", list.delete(20));
        check("14 List after deletion", list.toList().equals(Arrays.asList(10, 30)));
        check("15 Delete head", list.delete(10));
        check("16 Delete tail", list.delete(30));
        check("17 Empty after all deletes", list.isEmpty());

        list.insert(50);
        list.insert(50);

        check("18 Duplicate values", list.toList().equals(Arrays.asList(50, 50)));
        check("19 Delete first duplicate", list.delete(50)
                && list.toList().equals(Arrays.asList(50)));

        System.out.println("\n================================");
        System.out.println("HARSHAD LINKED LIST TEST RESULTS");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("================================");

        if (failed > 0) {
            System.exit(1);
        }
    }
}
