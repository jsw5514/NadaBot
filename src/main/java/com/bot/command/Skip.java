package com.bot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Skip implements Command {
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
