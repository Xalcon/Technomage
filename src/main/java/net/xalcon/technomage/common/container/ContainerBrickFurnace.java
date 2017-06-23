package net.xalcon.technomage.common.container;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerBrickFurnace extends ContainerTM
{

    public ContainerBrickFurnace(EntityPlayer player)
    {
        this.bindPlayerInventory(player);
    }
}
