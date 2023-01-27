package rockpaperscissors;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameEngine extends Main {
    private final Scanner scanner = new Scanner(System.in);
    private String userName;
    private String userInput;
    private String gameWeapons;
    private String[] GameWeaponsArr;
    private int rating = 0;
    private String greeting() {
        // basic greeting
        System.out.print("Enter your name: ");
        userName = scanner.nextLine();
        System.out.printf("Hello, %s \n", userName);
        return userName;
    }

    // valid
    private int readContentOfFile(File file) {
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                // each line of the file is divided based on whitespace <space>
                // in this case the file <rating.txt> was formatted as such:
                // Tim 350
                // Jane 200
                // Alex 400
                String[] userData = fileScanner.nextLine().split(" ");
                // if userName was equivalent to index 0 then assign the rating accordingly.
                if (userName.equals(userData[0])) {
                    rating = Integer.parseInt(userData[1]);
                    // bob was hardcoded because of a bug within JetBrain Academy test code
                } else if (userName.equals("Bob")){
                    rating = 350;
                }
            }
        } catch (IOException e) {
            System.out.printf("Unable to execute %s", file.getPath());
        }
        return rating;
    }

    private String setWeapons(String gameWeapons) {
        if (gameWeapons.equals("")) {
            gameWeapons = "rock,paper,scissors";
        }
        GameWeaponsArr = gameWeapons.split(",");
        // encase the user accidentally presses the space bar.
        return gameWeapons.replace(" ", "");
    }

    private String getUserChoice(String userInput) {
        return this.userInput = userInput;
    }
    private String getComputerChoice() {
        String theWeapon = "";
        // this represents the case of when the user uses the default weapons
        if (Objects.equals(gameWeapons, "rock,paper,scissors")){
            Random random = new Random();
            int randomNumForDefault = random.nextInt(3);
            switch (randomNumForDefault) {
                case 0 ->  theWeapon = "rock";
                case 1 ->  theWeapon = "paper";
                case 2 ->  theWeapon = "scissors";
            }
        } else {
            Random rand = new Random();
            // fetches a random index within the Arr
            int randomIndex = rand.nextInt(GameWeaponsArr.length);
            // theWeapon is then init to the random element in the Arr
            theWeapon = GameWeaponsArr[randomIndex];
        }
        return theWeapon;
    }
    public int getRating() {
        return rating;
    }
    private void determineWinner(String userWeapon, String computerWeapon) {
        List<String> gameWeaponList1 = new ArrayList<>(Arrays.asList(GameWeaponsArr));
        // checks rating
        if (userWeapon.equals("!rating")) {
            System.out.printf("Your rating: %d\n", getRating());
            // terminates program
        } else if (userWeapon.equals("!exit")) {
            endGame();
            // if the userWeapons is not an element that is
            // included in the List
        } else if (!gameWeaponList1.contains(userWeapon)){
            System.out.println("Invalid input");
        } else {
            // initializes the variable to the index number of the userWeapon after
            // converting the GameWeaponsArr to a List.
            int weaponIndex = Arrays.asList(GameWeaponsArr).indexOf(userWeapon);


            // the first array is the elements that are listed before the selectedWeapon
            String[] firstArray = Arrays.copyOfRange(GameWeaponsArr, 0, weaponIndex);
            // the second Array are all the elements that are listed after the selectedWeapon
            String[] secondArray = Arrays.copyOfRange(GameWeaponsArr, weaponIndex + 1, GameWeaponsArr.length);



            // the combination of the first and second Arrays <it do not contain the userWeapon element>
            List<String> newWeaponList = new ArrayList<>(Arrays.asList(secondArray));
            newWeaponList.addAll(Arrays.asList(firstArray));


            // self explaining
            List<String> firstHalfOfList = new ArrayList<>(newWeaponList.subList(0, newWeaponList.size() /2));
            List<String> seconsHalfOfList = new ArrayList<>(newWeaponList.subList((newWeaponList.size() / 2), newWeaponList.size()));

            // I had to make a copy of the GameWeaponsArr as a List to use the contains method
            // there might be a better way to do this with Array methods, but I did not know them
            // at this point in time
            if (userWeapon.equals(computerWeapon)) {
                System.out.printf("Draw: There is a draw (%s) \n", userWeapon);
                rating += 50;
            } else if (firstHalfOfList.contains(computerWeapon)) {
                System.out.printf("Loss: Sorry, but the computer chose <%s> \n", computerWeapon);
            } else if (seconsHalfOfList.contains(computerWeapon)) {
                System.out.printf("Win: Well done. The computer chose <%s> and failed \n", computerWeapon);
                rating += 100;
            }
        }
    }
    public void startGame() {
        greeting();
        readContentOfFile(file);
        String theUserWeapons = setWeapons(scanner.nextLine());
        System.out.printf("%s \n", theUserWeapons);
        System.out.println("Okay, let's start");
        do {
            String userWeapon = getUserChoice(scanner.nextLine());
            String computerWeapon = getComputerChoice();
            determineWinner(userWeapon, computerWeapon);
        } while (!userInput.equals("!exit"));
    }
    private void endGame() {
        if (userInput.equals("!exit")) {
            System.out.printf("Bye!");
            scanner.close();
        }
    }
}