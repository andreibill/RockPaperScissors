package rockpaperscissors;

import java.io.*;
import java.util.*;

public class Main {
    static int score;
    static public ArrayList<String> options = new ArrayList<>();

    static boolean inputVerify(String input) {
        return options.contains(input) || input.equals("!rating") || input.equals("!exit");
    }

    static boolean game(String name) {
        Random random = new Random();
        String input;

        do{
        input = getInput();
        if(input.equals("!exit")) {
            System.out.println("Bye!");
            return false;
        }
        if(input.equals("!rating")) System.out.printf("Your rating: %d\n", score);
        } while (input.equals("!rating"));

        score += computerChoice(input);

        return true;
    }

    static int computerChoice (String input) {
        Random random = new Random();
        int arraysize = options.size();
        int computerChoice = random.nextInt(arraysize);
        int n = options.indexOf(input);
        if(computerChoice == n) {
            System.out.printf("There is a draw (%s)\n", options.get(computerChoice));
            return 50;
        }
        if(computerChoice < n) {
            if((arraysize - n + computerChoice) % arraysize > arraysize / 2) {
                System.out.printf("Well done. The computer chose %s and failed\n", options.get(computerChoice));
                return 100;
            } else {
                System.out.printf("Sorry, but the computer chose %s\n", options.get(computerChoice));
                return 0;
            }
        } else {
            if((computerChoice-n) % arraysize > arraysize / 2) {
                System.out.printf("Well done. The computer chose %s and failed\n", options.get(computerChoice));
                return 100;
            } else {
                System.out.printf("Sorry, but the computer chose %s\n", options.get(computerChoice));
                return 0;
            }
        }
    }

    static int getRating (String name) {
        Scanner scanner = null;
        File file = new File(".\\rating.txt");
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.contains(name)) {
                String rating[] = line.split(" ");
                scanner.close();
                return Integer.parseInt(rating[1]);
            }
        }
        scanner.close();
        return 0;
    }

    static String getInput () {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(!inputVerify(input)){
            System.out.println("Invalid input");
            input = sc.nextLine();
        }
        return input;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.printf("Hello, %s\n", name);
        String[] op = sc.nextLine().split(",");
        for (String elem:op) {
            options.add(elem);
        }
        if(options.size() == 1) {
            options.remove(0);
            options.add("rock");
            options.add("paper");
            options.add("scissors");
        }
        System.out.println(options);
        System.out.println(options.size());
        System.out.println("Okay, let's start");
        score = getRating(name);
        while(game(name));
        sc.close();
    }
}
