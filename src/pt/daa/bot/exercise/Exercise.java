package pt.daa.bot.exercise;

import pt.daa.bot.Config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is meant to support the user interface of a train ticket machine.
 */
public class Exercise {

    private final int maxRecordsToShow; // if not configured is -1 to show all.
    private boolean allSearchModesApplied; // No more alternative ways to keep searching for matches

    public Exercise(int maxRecordsToShow) {
        this.allSearchModesApplied = false;
        this.maxRecordsToShow = maxRecordsToShow;
    }

    /**
     * The main method for this Exercise.
     * <p>
     * Typing a search string will return:
     * a. All stations that start with the search string;
     * b. All valid next characters for each matched station;
     * <p>
     * A space is a valid character when returning a list of next characters
     * <p>
     * <p>
     * If configured, it will try to match the station names with different "SearchModes"
     *
     * @param input - user input.
     * @return list of matching station names
     */
    public List<String> onTouchScreenChange(String input) {
        List<String> listToPrompt = new ArrayList<>();
        RegExHandler regexHandler = new RegExHandler(Config.SUFFIX_WILDCARDS_ONLY);

        while (!allSearchModesApplied
                && (maxRecordsToShow == -1 || (listToPrompt.size() < maxRecordsToShow))) {
            String searchExpression = regexHandler.transformInput(input);
            listToPrompt.addAll(this.searchMatchingResults(listToPrompt, searchExpression));

            // check if there are modes available to search for matches
            if (regexHandler.enableNextMode() == null)
                this.allSearchModesApplied = true;
        }

        allSearchModesApplied = false; //reset static flag
        return listToPrompt;
    }

    /**
     * Gets additional stations to prompt based on current search mode
     *
     * @param listToPrompt     list being or to-be prompted
     * @param searchExpression user input string
     * @return listToPrompt plus newly found stations
     */

    private List<String> searchMatchingResults(List<String> listToPrompt, String searchExpression) {

        if (!searchExpression.isEmpty()) {
            // searches matching stations with current search mode
            return getSimilarStations(listToPrompt, searchExpression);
        }

        return listToPrompt;
    }

    /**
     * Returns all entries in the list that matches the regex
     * except for already prompting stations
     *
     * @param alreadyPrompting The list of stations to be ignored
     * @param rule             The regular expression to use
     * @return list containing the stations somehow matching the search input
     */
    private List<String> getSimilarStations(List<String> alreadyPrompting, String rule) {
        List<String> matchingList = new ArrayList<>();
        // look up in all Station enum values
        for (Station s : Station.values()) {
            // check match ignoring case and using custom regex
            if (s.getStationName().toUpperCase().matches(rule.toUpperCase())) {
                // add stations that are not already in the list while its size is below maxRecordsToShow
                if (!alreadyPrompting.contains(s.getStationName())
                        && (maxRecordsToShow == -1 || (matchingList.size() < maxRecordsToShow)))
                    matchingList.add(s.getStationName());
            }
        }

        return matchingList.size() > 1 ? getOrderedList(matchingList) : matchingList;
    }


    /**
     * Will order the list from shorter to longest match as it should
     * always be the most similar to user input
     *
     * @param listToPrompt the list to order
     */
    private List<String> getOrderedList(List<String> listToPrompt) {
        return listToPrompt.stream()
                .sorted(Comparator.comparing(String::length))
                .collect(Collectors.toList());
    }

}
