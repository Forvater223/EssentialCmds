/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2015 HassanS6000
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.hsyyid.essentialcmds.cmdexecutors;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class VanishExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> target = ctx.<Player> getOne("player");

		if (!target.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (player.get(Keys.INVISIBLE).isPresent() && !player.get(Keys.INVISIBLE).get())
				{
					player.offer(Keys.INVISIBLE, true);
					player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You are now invisible."));
				}
				else if (player.get(Keys.INVISIBLE).isPresent() && player.get(Keys.INVISIBLE).get())
				{
					player.offer(Keys.INVISIBLE, false);
					player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You are now visible."));
				}
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /vanish!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /vanish!"));
			}
		}
		else if (target.isPresent() && src.hasPermission("vanish.others"))
		{
			Player player = target.get();

			if (player.get(Keys.INVISIBLE).isPresent() && !player.get(Keys.INVISIBLE).get())
			{
				player.offer(Keys.INVISIBLE, true);
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set player invisible."));
				player.sendMessage(Texts.of(TextColors.GOLD, src.getName(), " has set you ", TextColors.GRAY, "invisible."));
			}
			else if (player.get(Keys.INVISIBLE).isPresent() && player.get(Keys.INVISIBLE).get())
			{
				player.offer(Keys.INVISIBLE, false);
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set player visible."));
				player.sendMessage(Texts.of(TextColors.GOLD, src.getName(), " has set you ", TextColors.GRAY, "visible."));
			}
		}

		return CommandResult.success();
	}
}
