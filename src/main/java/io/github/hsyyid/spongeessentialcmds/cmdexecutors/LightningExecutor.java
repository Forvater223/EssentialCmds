package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import com.google.common.base.Optional;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.extent.Extent;

public class LightningExecutor implements CommandExecutor
{
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
    {
        if(src instanceof Player)
        {
            Player player = (Player) src;
            Location playerLocation = player.getLocation();
            Location lightningLocation = new Location(playerLocation.getExtent(), playerLocation.getX() + 5, playerLocation.getY(), playerLocation.getZ());
            spawnEntity(lightningLocation);
            player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created Lightning Strike!"));
        }
        else if(src instanceof ConsoleSource) {
            src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /lightning!"));
        }
        else if(src instanceof CommandBlockSource) {
            src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /lightning!"));
        }
        return CommandResult.success();
    }
    
    public void spawnEntity(Location location)
    {
        Extent extent = location.getExtent();
        Optional<Entity> optional = extent.createEntity(EntityTypes.LIGHTNING, location.getPosition());

        if (optional.isPresent())
        {
            Entity lightning = optional.get();
            System.out.println("Spawned Lightning " + extent.spawnEntity(lightning));
        }
        else
        {
            System.out.println("Error! Optional Entity is null");
        }
    }
}