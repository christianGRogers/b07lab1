import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        testDefaultConstructor();
        testParameterizedConstructor();
        testAdd();
        testEvaluate();
        testHasRoot();
        testMultiply();
        testFileConstructor();
        testFormatEqn();
        testSaveToFile();
		System.out.println("Done test execution");
    }

    public static void testDefaultConstructor() {
        Polynomial poly = new Polynomial();
        assertArrayEquals(new double[]{0}, poly.coefficients, "testDefaultConstructor - coefficients");
        assertArrayEquals(new int[]{0}, poly.exponents, "testDefaultConstructor - exponents");
    }

    public static void testParameterizedConstructor() {
        double[] coeffs = {5, 0, 3};
        int[] exps = {0, 1, 2};
        Polynomial poly = new Polynomial(coeffs, exps);
        assertArrayEquals(new double[]{5, 3}, poly.coefficients, "testParameterizedConstructor - coefficients");
        assertArrayEquals(new int[]{0, 2}, poly.exponents, "testParameterizedConstructor - exponents");
    }

    public static void testAdd() {
        Polynomial poly1 = new Polynomial(new double[]{3, 5}, new int[]{2, 0});
        Polynomial poly2 = new Polynomial(new double[]{1, 4}, new int[]{2, 1});
        Polynomial result = poly1.add(poly2);
        assertArrayEquals(new double[]{5, 4, 4}, result.coefficients, "testAdd - coefficients");
        assertArrayEquals(new int[]{0, 1, 2}, result.exponents, "testAdd - exponents");
    }

    public static void testEvaluate() {
        Polynomial poly = new Polynomial(new double[]{3, 5}, new int[]{2, 0});
        double result = poly.evaluate(2);
        assertEquals(17, result, 0.001, "testEvaluate");
    }

    public static void testHasRoot() {
        Polynomial poly = new Polynomial(new double[]{1, -3}, new int[]{1, 0});
        assertTrue(poly.hasRoot(3), "testHasRoot - has root");
        assertFalse(poly.hasRoot(2), "testHasRoot - does not have root");
    }

	public static void testMultiply() {
		// Test 1: Simple multiplication
		Polynomial poly1 = new Polynomial(new double[]{2}, new int[]{1});
		Polynomial poly2 = new Polynomial(new double[]{3, 1}, new int[]{1, 0});
		Polynomial result = poly1.multiply(poly2);
		assertArrayEquals(new double[]{2, 6}, result.coefficients, "testMultiply - coefficients (Test 1)");
		assertArrayEquals(new int[]{1, 2}, result.exponents, "testMultiply - exponents (Test 1)");
	
		// Test 2: Multiplying with zero polynomial
		poly1 = new Polynomial(new double[]{0}, new int[]{0});
		poly2 = new Polynomial(new double[]{3, 1}, new int[]{1, 0});
		result = poly1.multiply(poly2);
		assertArrayEquals(new double[]{0}, result.coefficients, "testMultiply - coefficients (Test 2)");
		assertArrayEquals(new int[]{0}, result.exponents, "testMultiply - exponents (Test 2)");
	
		// Test 3: Multiplying with polynomial having higher degree terms
		poly1 = new Polynomial(new double[]{1, 2}, new int[]{1, 2});
		poly2 = new Polynomial(new double[]{1, 1}, new int[]{1, 0});
		result = poly1.multiply(poly2);
		assertArrayEquals(new double[]{1, 3, 2}, result.coefficients, "testMultiply - coefficients (Test 3)");
		assertArrayEquals(new int[]{1, 2, 3}, result.exponents, "testMultiply - exponents (Test 3)");
		
		// Test: Multiplying two polynomials with multiple terms
       	poly1 = new Polynomial(new double[]{3, 2, 1}, new int[]{2, 1, 0});
        poly2 = new Polynomial(new double[]{4, -1, 2}, new int[]{1, 3, 0});
        result = poly1.multiply(poly2);
        // Expected coefficients and exponents after multiplication (ordered by coefficients)
        double[] expectedCoefficients = {2, 8, 14, 11, -2, -3};
        int[] expectedExponents = {0, 1, 2, 3, 4, 5};
		assertArrayEquals(expectedCoefficients, result.coefficients, "testMultiply - coefficients");
        assertArrayEquals(expectedExponents, result.exponents, "testMultiply - exponents");
	
		// Test 5: Multiplying by one
		poly1 = new Polynomial(new double[]{1}, new int[]{0});
		poly2 = new Polynomial(new double[]{4, 3, 2, 1}, new int[]{3, 2, 1, 0});
		result = poly1.multiply(poly2);
		assertArrayEquals(new double[]{1,2,3,4}, result.coefficients, "testMultiply - coefficients (Test 5)");
		assertArrayEquals(new int[]{0,1,2,3}, result.exponents, "testMultiply - exponents (Test 5)");
	}

    public static void testFileConstructor() {
        try {
            File file = new File("poly.txt");
            FileWriter writer = new FileWriter(file);
            writer.write("3x2+5x0");
            writer.close();
            Polynomial poly = new Polynomial(file);
            assertArrayEquals(new double[]{5, 3}, poly.coefficients, "testFileConstructor - coefficients");
            assertArrayEquals(new int[]{0, 2}, poly.exponents, "testFileConstructor - exponents");
            file.delete(); // clean up
        } catch (IOException e) {
            System.out.println("testFileConstructor - failed due to IOException");
        }
    }

    public static void testFormatEqn() {
        Polynomial poly = new Polynomial(new double[]{3, 5}, new int[]{2, 0});
        String result = poly.formatEqn();
        assertEquals("5.0x0+3.0x2", result, "testFormatEqn");
    }

    public static void testSaveToFile() {
        try {
            Polynomial poly = new Polynomial(new double[]{3, 5}, new int[]{2, 0});
            String fileName = "output.txt";
            poly.saveToFile(fileName);

            // Read the file and check its content
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            String fileContent = scanner.nextLine();
            scanner.close();
            file.delete(); // clean up

            assertEquals("5.0x0+3.0x2", fileContent, "testSaveToFile");
        } catch (IOException e) {
            System.out.println("testSaveToFile - failed due to IOException");
        }
    }

    // Helper methods to simulate assertions
    public static void assertArrayEquals(double[] expected, double[] actual, String testName) {
        if (expected.length != actual.length) {
            System.out.println(testName + " - failed (length mismatch)");
            return;
        }
        for (int i = 0; i < expected.length; i++) {
            if (Math.abs(expected[i] - actual[i]) > 0.001) {
                System.out.println(testName + " - failed (element mismatch at index " + i + ")");
                return;
            }
        }
        System.out.println(testName + " - passed");
    }

    public static void assertArrayEquals(int[] expected, int[] actual, String testName) {
        if (expected.length != actual.length) {
            System.out.println(testName + " - failed (length mismatch)");
            return;
        }
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                System.out.println(testName + " - failed (element mismatch at index " + i + ")");
                return;
            }
        }
        System.out.println(testName + " - passed");
    }

    public static void assertEquals(double expected, double actual, double delta, String testName) {
        if (Math.abs(expected - actual) > delta) {
            System.out.println(testName + " - failed (expected " + expected + ", got " + actual + ")");
        } else {
            System.out.println(testName + " - passed");
        }
    }

    public static void assertEquals(String expected, String actual, String testName) {
        if (!expected.equals(actual)) {
            System.out.println(testName + " - failed (expected \"" + expected + "\", got \"" + actual + "\")");
        } else {
            System.out.println(testName + " - passed");
        }
    }

    public static void assertTrue(boolean condition, String testName) {
        if (!condition) {
            System.out.println(testName + " - failed (condition is false)");
        } else {
            System.out.println(testName + " - passed");
        }
    }

    public static void assertFalse(boolean condition, String testName) {
        if (condition) {
            System.out.println(testName + " - failed (condition is true)");
        } else {
            System.out.println(testName + " - passed");
        }
    }
}
