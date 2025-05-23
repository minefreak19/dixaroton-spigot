package me.minefreak19.dixarotonSpigot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("status")) {
            event.reply("Hello from the Exaroton server! If you're seeing this, the server is online.").queue();
        }
    }
}
