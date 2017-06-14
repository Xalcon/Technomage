package net.xalcon.technomage.api.gui.technonomicon;

public enum ResearchIconType
{
    DEFAULT,
    ROUND,
    HEX,
    SPECIAL;

    public int getIndex()
    {
        return this.ordinal();
    }
}
