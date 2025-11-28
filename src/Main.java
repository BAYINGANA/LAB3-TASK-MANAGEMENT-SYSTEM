import java.util.Scanner;
import services.*;
import utils.ConsoleMenu;
import models.*;

public class Main {
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        Scanner scanner = new Scanner(System.in);

        menu.MainMenu();
        int choice = scanner.nextInt();
        scanner.nextLine();
    }
}
