package pt.daa.bot.ui.menus;

import pt.daa.bot.Config;
import pt.daa.bot.exercise.Exercise;
import pt.daa.bot.exercise.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class will handle the whole station selection UI
 */
public class StationMenu extends Menu {

    private List<String> stations;
    private String chosenStationName;
    private Station chosenStation;
    private String searchString;
    Exercise exercise;

    public StationMenu() {
        searchString = "";
        chosenStationName = "";
        stations = new ArrayList<>();
        exercise = new Exercise(Config.MAX_RESULTS_TO_SHOW);
    }

    /**
     * @return the Station enum value corresponding to users' selection
     */
    public Station run() {
        // Only leave the menu when a valid Station enum value is found or user goes back
        while (getStation(chosenStationName) == null) {
            // Keeps asking the user to type a valid string until he does
            System.out.println("\n\nGo Back (0)\nType the name of the station...\n");
            searchString = scan.nextLine();
            if (searchString.equals("0"))
                return null;
            if (searchStringIsValid()) {
                // calls the Exercise method to fetch a list of stations based on a search string
                stations = exercise.onTouchScreenChange(searchString);
                // proceeds with UI methods
                if (stations.size() == 0) {
                    System.out.println("No stations were found matching the given text...\n");
                } else {
                    SelectStationMenu();
                }
            }
        }

        return chosenStation;
    }

    /**
     * this method will keep prompting station options until a valid Station is selected
     * or user goes to previous menu where he will have to type a new search string.
     */
    private void SelectStationMenu() {
        boolean optionIsValid = false;

        while (!optionIsValid) {
            promptStationOptions();
            String userSelection = scan.nextLine();

            try {
                int selectedNumber = Integer.parseInt(userSelection);
                // when user selected last option
                if (selectedNumber == stations.size() + 1) {
                    System.out.println("You chose to go to previous menu.\n");
                    return; // option "New Search" chosen
                }
                // when user selected one of the stations
                if (selectedNumber > 0 && selectedNumber <= stations.size()) {
                    optionIsValid = true;
                    chosenStationName = stations.get(selectedNumber - 1);
                } else {
                    System.out.println("You chose an invalid option.\n");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Chose a valid number, corresponding to the displayed options.\n");
            }
        }
    }

    /**
     * @return True when string is valid to begin a search
     */
    private boolean searchStringIsValid() {
        System.out.println("You Entered: " + searchString.toUpperCase() + "\n");

        if (searchString.length() >= Config.MIN_CHARS_TO_SEARCH) {
            return true;
        } else {
            System.out.println("Make sure your search string has at least 3 characters. \n");
            return false;
        }
    }

    /**
     * Prompt available options to screen
     */
    private void promptStationOptions() {
        System.out.println("Showing possible matching list...\n");
        for (int i = 1; i <= stations.size(); i++) {
            System.out.println("\t" + String.valueOf(i) + " - " + stations.get(i - 1) + "\r");
        }
        System.out.println("\t" + String.valueOf(stations.size() + 1) + " - Nova Pesquisa \r");
    }

    /**
     * Gets a Station enum based on its name
     *
     * @param station Station name
     * @return null if not found
     */
    private Station getStation(String station) {
        if (!station.isEmpty()) {
            chosenStation = Arrays.stream(Station.values())
                    .filter(p -> p.getStationName().equals(chosenStationName))
                    .findFirst()
                    .orElse(null);

            return chosenStation;
        }

        return null;
    }

}

