package me.minefreak19.dixarotonSpigot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // TODO: Add a central Listener in the main class for server events and maintain server status and player list in a cache for this response
        // TODO: Implement a persistent message published in a set channel which is updated upon receiving a new status, rather than through a command
        if (event.getName().equals("status")) {
            event.reply("Hello from the Exaroton server! If you're seeing this, the server is online.").queue();
        }
    }
}
