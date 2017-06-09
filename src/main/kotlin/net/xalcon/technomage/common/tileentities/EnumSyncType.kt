package net.xalcon.technomage.common.tileentities

enum class EnumSyncType(val isFullUpdate:Boolean)
{
    /**
     * TE is loaded from or stored to disk
     */
    TILE_STORE(true),
    /**
     * TE is send from server to client on chunk load
     */
    TILE_UPDATE_FULL(true),
    /**
     * TE was updated, send partial data
     */
    TILE_UPDATE_PARTIAL(false);
}