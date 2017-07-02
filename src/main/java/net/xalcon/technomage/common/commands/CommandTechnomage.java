package net.xalcon.technomage.common.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.xalcon.technomage.common.world.TeleportDungeonDim;

import java.util.Collections;
import java.util.List;

public class CommandTechnomage extends CommandBase
{
    /**
     * Gets the name of the command
     */
    @Override
    public String getName()
    {
        return "technomage";
    }

    /**
     * Get a list of aliases for this command. <b>Never return null!</b>
     */
    @Override
    public List<String> getAliases()
    {
        return Collections.singletonList("tm");
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender
     */
    @Override
    public String getUsage(ICommandSender sender)
    {
        return "TODO: Add this, lul";
    }

    /**
     * Callback for when the command is executed
     *
     * @param server
     * @param sender
     * @param args
     */
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if(!(sender instanceof EntityPlayerMP))
            return;

        EntityPlayerMP player = (EntityPlayerMP) sender;

        if(player.getServerWorld().provider.getDimension() == -5400)
        {
            server.getPlayerList().transferPlayerToDimension(player, 0, new TeleportDungeonDim(player.getServerWorld()));
        }
        else
        {
            server.getPlayerList().transferPlayerToDimension(player, -5400, new TeleportDungeonDim(player.getServerWorld()));
        }
    }
}
