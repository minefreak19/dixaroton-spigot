package me.minefreak19.dixarotonSpigot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;

public final class DixarotonSpigot extends JavaPlugin {
    public static final long DISCORD_MC_CHANNEL_ID = 1355846009581863033L;
    public static final long DISCORD_SELF_ID = 1355614585792626932L;

    private JDA discordClient = null;

    @Override
    public void onEnable() {
        String discordApiKey = System.getProperty("dixaroton.discord.key");

        this.getLogger().info("Creating Discord client...");
        try {
            discordClient = JDABuilder.createLight(discordApiKey, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                    .addEventListeners(new SlashCommandListener())
                    .addEventListeners(new DiscordToSpigot(this))
                    .build().awaitReady();
        } catch (InterruptedException e) {
            // TODO: Better error reporting
            throw new RuntimeException(e);
        }

        this.getServer().getPluginManager().registerEvents(new SpigotToDiscord(this), this);

        this.getLogger().info("Dixaroton enabled!");
    }

    @Override
    public void onDisable() {
        this.discordClient.shutdown();
        this.discordClient = null;
    }

    public JDA getDiscordClient() {
        assert this.discordClient != null;
        return this.discordClient;
    }
}
