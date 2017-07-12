package net.xalcon.technomage.common.items;

import net.minecraft.block.BlockPortal;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.xalcon.technomage.common.world.TeleportDungeonDim;
import net.xalcon.technomage.lib.item.IItemModelRegisterHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTranslocationOrb extends Item implements IItemModelRegisterHandler
{
    public final static String INTERNAL_NAME = "translocation_orb";

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if(stack.hasTagCompound())
        {
            playerIn.sendStatusMessage(new TextComponentTranslation(this.getUnlocalizedName() + ".already_bound"), true);
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

        if(!playerIn.isSneaking())
        {
            playerIn.sendStatusMessage(new TextComponentTranslation(this.getUnlocalizedName() + ".usage"), true);
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

        BlockPos playerPos = playerIn.getPosition();
        stack.setTagInfo("dim", new NBTTagInt(worldIn.provider.getDimension()));
        stack.setTagInfo("x", new NBTTagInt(playerPos.getX()));
        stack.setTagInfo("y", new NBTTagInt(playerPos.getY()));
        stack.setTagInfo("z", new NBTTagInt(playerPos.getZ()));

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void doTeleport(World worldIn, EntityPlayer playerIn, ItemStack stack)
    {
        if(worldIn.isRemote) return;
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt == null) return;
        int dim = nbt.getInteger("dim");
        int x = nbt.getInteger("x");
        int y = nbt.getInteger("y");
        int z = nbt.getInteger("z");
        EntityPlayerMP playerMP = (EntityPlayerMP)playerIn;
        if(dim != worldIn.provider.getDimension())
            FMLClientHandler.instance().getServer().getPlayerList().transferPlayerToDimension(playerMP, dim, new TeleportDungeonDim(playerMP.getServerWorld()));
        playerIn.setPositionAndUpdate(x + 0.5f, y + 0.5f, z + 0.5f);
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return stack.hasTagCompound();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt != null)
        {
            int dim = nbt.getInteger("dim");
            int x = nbt.getInteger("x");
            int y = nbt.getInteger("y");
            int z = nbt.getInteger("z");
            tooltip.add(I18n.format(this.getUnlocalizedName() + ".bound_pos", dim, x, y, z));
        }
        else
        {
            tooltip.add(this.getUnlocalizedName() + ".not_bound");
        }
    }
}
