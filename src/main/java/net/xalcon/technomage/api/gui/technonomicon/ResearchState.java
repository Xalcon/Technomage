package net.xalcon.technomage.api.gui.technonomicon;

public enum ResearchState
{
    INVISIBLE(false),
    NOT_RESEARCHED(false),
    IN_PROGRESS(false),
    RESEARCHED(true);

    private final boolean isResearched;

    public boolean isResearched()
    {
        return this.isResearched;
    }

    ResearchState(boolean isResearched)
    {
        this.isResearched = isResearched;
    }
}
