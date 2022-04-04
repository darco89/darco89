package pt.daa.bot.ui.menus;

import java.util.Scanner;

/**
 * I really didnt want to declare multiple scanners
 */
public class Menu {
    protected Scanner scan;

    Menu() {
        this.scan = new Scanner(System.in);
    }
}
