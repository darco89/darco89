package pt.daa.bot.ui;

public enum BotAction {
    LISTING_STATION("A"),
    CHOOSING_STATION("B"),
    QUIT("Q");
    /*
        add more like
        SELECTING_SERVICE
        BUYING TICKET
     */

    private final String menuOption;

    BotAction(String menuOption) {
        this.menuOption = menuOption;
    }

    public String getMenuOption() {
        return this.menuOption;
    }
}