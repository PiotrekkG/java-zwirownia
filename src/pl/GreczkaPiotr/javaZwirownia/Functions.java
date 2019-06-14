package pl.GreczkaPiotr.javaZwirownia;

import java.util.Arrays;
import java.util.Scanner;

public class Functions {
    /**
     * Prosi o podanie wartości (typu int) i sprawdza czy na podstawie podanych argumentów wartość zgadza się z wymaganym przedziałem
     * @param text Tekst informujący o wprowadzanych danych
     * @param allowNegative dozwolone są liczby ujemne
     * */
    public static int GetInputInt(String text, boolean allowNegative) {
        Scanner inputScanner = new Scanner(System.in);
        int s = 0;

        while (true) {
            System.out.print(text + ">> ");
            try {
                s = inputScanner.nextInt();

                if (!allowNegative && s < 0) {
                    System.out.println("Ta liczba nie może być ujemna!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Błędna wartość - wymagana jest liczba całkowita!");
            }
        }

        return s;
    }

    /**
     * Prosi o podanie wartości (typu int) i sprawdza czy na podstawie podanych argumentów wartość zgadza się z wymaganym przedziałem
     * @param text Tekst informujący o wprowadzanych danych
     * @param limits tablica dwuelementowa intów - dozwolony przedział liczbowy
     * */
    public static int GetInputInt(String text, int[] limits) {
        if(limits.length != 2)
            return GetInputInt(text, true);

        Scanner inputScanner = new Scanner(System.in);
        int s = 0;

        while (true) {
            System.out.print(text + ">> ");
            try {
                s = inputScanner.nextInt();

                if (s < limits[0]) {
                    System.out.println("Wprowadzona wartość jest zbyt mała! (min " + limits[0] + ")");
                } else if (s > limits[1]) {
                    System.out.println("Wprowadzona wartość jest zbyt duża! (max " + limits[1] + ")");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Błędna wartość - wymagana jest liczba całkowita!");
            }
        }

        return s;
    }

    /**
     * Prosi o podanie wartości (typu int) i sprawdza na podstawie podanych argumentów czy wartość zgadza się z wymaganym wzorcem (podana wartość zawiera się w tablicy intów)
     * @param allowOnlyInput tablica dozwolonych wartości
     * @param text Tekst informujący o wprowadzanych danych
     * */
    public static int GetInputInt(int[] allowOnlyInput, String text) {
        if(allowOnlyInput.length == 0)
            return GetInputInt(text, true);

        Scanner inputScanner = new Scanner(System.in);
        int s = 0;

        while (true) {
            System.out.print(text + ">> ");
            try {
                s = inputScanner.nextInt();
            } catch (Exception e) {
                System.out.println("Błędna wartość - wymagana jest liczba całkowita!");
            }

            if (contains(allowOnlyInput, s)) {
                break;
            } else {
                System.out.print("Nieprawidłowa wartość, prawidłowe to: ");
                for (var a : allowOnlyInput) {
                    if (a != allowOnlyInput[0])
                        System.out.print(", ");

                    System.out.print(a);
                }
                System.out.println();
            }
        }

        return s;
    }

    /**
     * Prosi o podanie wartości (typu String) i sprawdza na podstawie podanych argumentów czy wartość zgadza się z wymaganym wzorcem (podana wartość zawiera się w tablicy Stringów)
     * @param text Tekst informujący o wprowadzanych danych
     * @param allowOnlyInput tablica dozwolonych wartości
     * */
    public static String GetInputString(String text, String[] allowOnlyInput) {
        if(allowOnlyInput.length == 0)
            return GetInputString(text);

        Scanner inputScanner = new Scanner(System.in);
        String s = "";

        while (true) {
            System.out.print(text + ">> ");
            try {
                s = inputScanner.next();
            } catch (Exception e) {
                System.out.println("Błędne dane!");
            }

            if (contains(allowOnlyInput, s)) {
                break;
            } else {
                System.out.print("Nieprawidłowa wartość, prawidłowe to: ");
                for (var a : allowOnlyInput) {
                    if (a != allowOnlyInput[0])
                        System.out.print(", ");

                    System.out.print(a);
                }
                System.out.println();
            }
        }

        return s.toLowerCase();
    }
    /**
     * Prosi o podanie wartości (typu String) i sprawdza na podstawie podanych argumentów czy wartość zgadza się z wymaganym limitem długości podanej wartości
     * @param text Tekst informujący o wprowadzanych danych
     * @param limits tablica dwuelementowa intów dozwolonej długości podanych wartości
     * */
    public static String GetInputString(String text, int[] limits) {
        if(limits.length != 2)
            return GetInputString(text);

        Scanner inputScanner = new Scanner(System.in);
        String s = "";

        while (true) {
            System.out.print(text + ">> ");
            try {
                s = inputScanner.next();
            } catch (Exception e) {
                System.out.println("Błędne dane!");
            }
            if (s.length() < limits[0]) {
                System.out.println("Wprowadzona wartość ma za mało znaków. (min " + limits[0] + ")");
            } else if (limits[1] > 0 && s.length() > limits[1]) {
                System.out.println("Wprowadzona wartość ma za dużo znaków! (max " + limits[1] + ")");
            } else {
                break;
            }
        }

        return s.toLowerCase();
    }
    /**
     * Prosi o podanie wartości (typu String) i zwraca podaną wartość przez użytkownika
     * @param text Tekst informujący o wprowadzanych danych
     * */
    public static String GetInputString(String text) {
        Scanner inputScanner = new Scanner(System.in);
        String s = "";

        while (true) {
            System.out.print(text + ">> ");
            try {
                s = inputScanner.next();
                break;
            } catch (Exception e) {
                System.out.println("Błędne dane!");
            }
        }

        return s.toLowerCase();
    }


    /**
     * Funkcja powoduje wyświetlenie komunikatu i oczekiwanie na naciśnięcie klawisza, by kontynuować wykonywanie programu
     * */
    public static void PressKeyToContinue() {
        WaitFor(350);
        try {
            System.out.println("\n\nNaciśnij ENTER, aby kontynuować.");
            System.in.read();
        } catch (Exception i) {
        }
    }


    /**
     * Funkcja powoduje uśpienie programu na podaną ilość milisekund.
     * @param millis czas na jaki funkcja ma uśpić program
     * */
    public static boolean WaitFor(int millis) {
        if (millis < 0)
            return false;

        if (millis == 0)
            return true;

        try {
            Thread.sleep(millis);
        } catch (Exception i) {
            //System.out.println("Wystąpił jakiś błąd!");
            return false;
        }

        return true;
    }


    /*
    public static int GetMaxValueIndex(int[] numbers) {
        int maxValueIndex = 0;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > numbers[maxValueIndex]) {
                maxValueIndex = i;
            }
        }
        return maxValueIndex;
    }

    public static int GetMinValueIndex(int[] numbers){
        int minValueIndex = numbers[0];
        for(int i=1;i<numbers.length;i++){
            if(numbers[i] < numbers[minValueIndex]){
                minValueIndex = i;
            }
        }
        return minValueIndex;
    }
    */

    /**
     * Sprawdza czy element znajduje się w podanej tablicy.
     * @param arr tablica
     * @param targetValue szukany element
     * */
    public static boolean contains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * Sprawdza czy element znajduje się w podanej tablicy.
     * @param arr tablica
     * @param targetValue szukany element
     * */
    public static boolean contains(int[] arr, int targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }
}