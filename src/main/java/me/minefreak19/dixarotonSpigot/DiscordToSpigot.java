package me.minefreak19.dixarotonSpigot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Duration;

public class DiscordToSpigot extends ListenerAdapter {
    private final DixarotonSpigot spigotPlugin;
    public DiscordToSpigot(DixarotonSpigot spigotPlugin) {
        this.spigotPlugin = spigotPlugin;
    }

    // TODO: Handle images by adding a <attachment> or <image> to the message content
    // TODO: Handle replies by adding "replied to..." to message content
    // TODO: Make it so that if an online player's username is used in a message, they get a ping sound effect in-game
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() == DixarotonSpigot.DISCORD_SELF_ID) return;
        if (event.getChannel().getIdLong() != DixarotonSpigot.DISCORD_MC_CHANNEL_ID) return;

        if (event.getMessage().getContentDisplay().contains("\n")) {
            event.getMessage()
                    .reply("The message you sent has too many lines and was not forwarded to Minecraft.")
                    .flatMap(Message::delete)
                    .delay(Duration.ofSeconds(5))
                    .queue();
        }
        System.out.printf("%#s: %s\n",
                event.getAuthor(),
                event.getMessage().getContentDisplay());

        String content = minecraftSanitize(event.getMessage().getContentDisplay());
        this.spigotPlugin.getServer().getOnlinePlayers()
                .forEach(p ->
                        p.sendMessage(
                                String.format("§r[§9§lDiscord§r] <%s> %s", event.getAuthor().getEffectiveName(), content)));
    }

    private static String minecraftSanitize(String s) {
         return s.replace("§", "")
                 .replace("\\","\\\\")
                 .replace("\"", "\\\"");
    }
}
