package pt.daa.bot.ui.menus;

import pt.daa.bot.exercise.Station;
import pt.daa.bot.ui.BotAction;

import java.util.Arrays;

/**
 * Rudimentary UI for a ticket vending machine
 */
public class MainMenu extends Menu {

    private String chosenOption;
    private BotAction botAction;
    private Station currentDestination;

    public MainMenu() {
        chosenOption = "";
    }

    /**
     * Prompt Main Menu screen until a valid option is chosen
     */
    public void run() {
        System.out.println("Welcome to the Train Tickets Vending Bot!\n");
        // get user input until an option is chosen
        while (chosenOption.isEmpty()) {
            promptMainMenuOptions();

            chosenOption = scan.nextLine();
            String promptInput = "You Entered: " + chosenOption;
            this.botAction = getBotAction(chosenOption);
            this.chosenOption = ""; // reset chosenOption to keep looping

            if (this.botAction != null) {
                System.out.println(promptInput);
                doAction();
            } else {
                System.out.println(promptInput + ". Not a valid option.");
            }
        }
    }

    /**
     * Starts user chosen action.
     * When finished, returns to mainMenu.run() loop
     */
    private void doAction() {

        switch (this.botAction) {
            case LISTING_STATION:
                this.doListingAction();
                break;

            case CHOOSING_STATION:
                this.doChoosingStation();
                break;

            case QUIT:
                Quit(false);

            default:
                Quit(true);
        }
    }

    /**
     * Executes the Station Selector Menu until a station is obtained
     */
    private void doChoosingStation() {
        // run stationMenu
        StationMenu stationMenu = new StationMenu();
        var previousDestination = this.currentDestination;
        this.currentDestination = stationMenu.run();
        if (this.currentDestination != null)
            System.out.print("You selected the station " + this.currentDestination.getStationName() + " as your current destination.\n");
        else if (previousDestination != null) {
            this.currentDestination = previousDestination;
            System.out.print("The previous selected station of " + this.currentDestination.getStationName() + " is still your current destination.\n");
        }
    }

    /**
     * Prompts all existent station names
     */
    private void doListingAction() {
        Arrays.stream(Station.values())
                .forEach(p -> System.out.println(p.getStationName() + "\r"));
    }

    /**
     * Finds the proper botAction given the user selection
     *
     * @param option user's selection
     * @return the corresponding botAction. Or Null
     */
    private BotAction getBotAction(String option) {
        return Arrays.stream(BotAction.values())
                .filter(action -> action.getMenuOption().equalsIgnoreCase(option))
                .findFirst()
                .orElse(null);
    }

    /**
     * Show current "status" and list of options
     */
    private void promptMainMenuOptions() {
        String suffix = "\n\nPlease select one option:\n"
                + "\tA:\tView Station List\n"
                + "\tB:\tSelect Destination\n"
                + "\tQ:\tQuit Application.\n\n";

        String mainMenuOptions = this.currentDestination != null ?
                "\n\n Your current destination is: " + this.currentDestination + suffix
                : suffix;

        System.out.print(mainMenuOptions);
    }

    /**
     * Exit the application
     *
     * @param withError when true application returns code 1.
     */
    private void Quit(boolean withError) {
        if (withError)
            System.out.print("Something went wrong. ");
        System.out.println("Application closing.");
        System.exit(withError ? 1 : 0);
    }

}
