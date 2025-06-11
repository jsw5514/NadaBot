package com.bot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface Command {
    public void handle(SlashCommandInteractionEvent event);
}
