package pt.daa.bot.exercise;

public class RegExHandler {

    private SearchMode currentSearchMode;
    private final boolean suffixWildcardsOnly;

    RegExHandler(boolean onlySuffixWildcards) {
        currentSearchMode = SearchMode.SUFFIX_WILDCARDS;
        suffixWildcardsOnly = onlySuffixWildcards;
    }

    /**
     * Add wildcards between each character of the searchString
     *
     * @param searchString the string containing the characters
     * @return a valid regexpression using the searchString with wildcards
     * Empty when current mode is not valid
     */
    public String transformInput(String searchString) {
        switch (this.currentSearchMode) {
            case SUFFIX_WILDCARDS:
                return searchString + ".*";
            case PREFIX_SUFFIX_WILDCARDS:
                return ".*" + searchString + ".*";
            case WILDCARDS_EVERYWHERE:
                return transformWildCardsEverywhere(searchString);
            default:
                return "";
        }
    }

    /**
     * Inserts wildcard before each char of the searchString
     *
     * @param searchString the string to transform
     * @return *s*e*a*r*c*h*s*t*r*i*n*g*
     */
    private String transformWildCardsEverywhere(String searchString) {
        StringBuilder regExpression = new StringBuilder();
        for (int i = 0; i < searchString.length(); i++) {
            regExpression.append(".*").append(searchString.charAt(i));
            // when last character, append another wildcard
            if (i == searchString.length() - 1) {
                regExpression.append(".*");
            }
        }

        return regExpression.toString();
    }

    /**
     * Manages "search-mode" order.
     * Sets and returns the next search mode for this handler instance.
     *
     * @return next SearchMode for this handler instance.
     * Null when no more modes defined.
     */
    public SearchMode enableNextMode() {
        switch (this.currentSearchMode) {
            case SUFFIX_WILDCARDS:
                this.currentSearchMode = this.suffixWildcardsOnly ? this.currentSearchMode = null : SearchMode.PREFIX_SUFFIX_WILDCARDS;
                break;
            case PREFIX_SUFFIX_WILDCARDS:
                this.currentSearchMode = SearchMode.WILDCARDS_EVERYWHERE;
                break;
            default:
                this.currentSearchMode = null;
                break;
        }

        return this.currentSearchMode;
    }
}
