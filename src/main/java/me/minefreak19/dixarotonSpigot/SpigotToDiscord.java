package me.minefreak19.dixarotonSpigot;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpigotToDiscord implements Listener {
    private DixarotonSpigot plugin;
    private TextChannel channel;

    public SpigotToDiscord(DixarotonSpigot plugin) {
        this.plugin = plugin;

        this.channel = this.plugin.getDiscordClient().getChannelById(TextChannel.class, DixarotonSpigot.DISCORD_MC_CHANNEL_ID);
        assert this.channel != null;
    }

    @EventHandler
    // TODO: Add a way for the console to talk to discord
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String content = String.format("**<%s>** %s", event.getPlayer().getDisplayName(), event.getMessage());
        this.channel.sendMessage(content).queue();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var content = String.format("**%s**", event.getJoinMessage());
        this.channel.sendMessage(removeColorCodes(content)).queue();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var content = String.format("**%s**", event.getQuitMessage());
        this.channel.sendMessage(removeColorCodes(content)).queue();
    }

    private static String removeColorCodes(String s) {
        return s.replaceAll("(§.)", "");
    }
}
