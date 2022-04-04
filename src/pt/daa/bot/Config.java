package pt.daa.bot;

public abstract class Config {
    // SET INPUT'S MINIMUM CHARACTERS COUNT TO ALLOW SEARCH. RECOMMENDED 3
    public final static int MIN_CHARS_TO_SEARCH = 0;

    // SET MATCHING STATIONS LIST MAX RESULTS. -1 WILL SHOW ALL. RECOMMENDED 5
    public final static int MAX_RESULTS_TO_SHOW = -1;

    // SET TO TRUE TO DISABLE ALL SEARCH MODES
    public final static boolean SUFFIX_WILDCARDS_ONLY = true;
}
