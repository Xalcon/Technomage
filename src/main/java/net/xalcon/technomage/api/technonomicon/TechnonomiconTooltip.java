package net.xalcon.technomage.api.technonomicon;

public class TechnonomiconTooltip
{
    private final String title;
    private final String description;

    public final String getTitle()
    {
        return this.title;
    }

    public final String getDescription()
    {
        return this.description;
    }

    public TechnonomiconTooltip(String title, String description)
    {
        this.title = title;
        this.description = description;
    }
}
