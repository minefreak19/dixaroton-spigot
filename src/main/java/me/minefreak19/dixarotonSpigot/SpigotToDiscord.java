package me.minefreak19.dixarotonSpigot;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

// TODO: Add a way for players to ping Discord users from minecraft (perhaps by linking Discord accounts and Minecraft usernames in some database?)
public class SpigotToDiscord implements Listener {
    private final TextChannel channel;

    public SpigotToDiscord(DixarotonSpigot plugin) {
        this.channel = plugin.getDiscordClient().getChannelById(TextChannel.class, DixarotonSpigot.DISCORD_MC_CHANNEL_ID);
        assert this.channel != null;
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        String command = event.getCommand();
        String message;
        if (command.startsWith("say")) {
            message = command.substring(3).trim();
        } else if (command.startsWith("/say")) {
            message = command.substring(4).trim();
        } else return;

        String content = String.format("**[%s]** %s", event.getSender().getName(), message);
        this.channel.sendMessage(content).queue();
    }

    @EventHandler
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

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        var content = String.format("**%s**", event.getDeathMessage());
        this.channel.sendMessage(removeColorCodes(content)).queue();
    }

    private static String removeColorCodes(String s) {
        return s.replaceAll("(§.)", "");
    }
}
