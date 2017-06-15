package net.xalcon.technomage.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.xalcon.technomage.Technomage;

public class ItemTechnonomicon extends ItemTM
{
    public ItemTechnonomicon()
    {
        super("technonomicon");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.openGui(Technomage.getInstance(), 0, worldIn, 0, 0, 0);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
