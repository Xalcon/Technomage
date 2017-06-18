package net.xalcon.technomage.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerBrickFurnace extends ContainerTM
{
    private EntityPlayer player;

    public ContainerBrickFurnace(EntityPlayer player)
    {
        this.player = player;
        this.bindPlayerInventory(player);
    }
}
