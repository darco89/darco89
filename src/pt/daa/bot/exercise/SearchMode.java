package pt.daa.bot.exercise;

/**
 * Enumerator of ways to search the stations list
 */
public enum SearchMode {

    PREFIX_SUFFIX_WILDCARDS(0),
    WILDCARDS_EVERYWHERE(1),
    SUFFIX_WILDCARDS(2);

    private final int modifier;

    SearchMode(int mode) {

        this.modifier = mode;
    }

    public int getModifier() {
        return modifier;
    }
}
