// PACKAGE //
package org.skydevelopment.logger;

// IMPORTS //
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

// MAIN CLASS //
public final class Logger extends JavaPlugin {
    public FileConfiguration config = getConfig();

    HttpStuff cl = new HttpStuff();
    @Override
    public void onEnable() {
        if((config.isSet("webhook.main") && config.getString("webhook.main").startsWith("https://discord.com/api/webhooks/") && config.getString("webhook.main") != null)) {
            cl.sendToWebhook(config.getString("webhook.main"), "Enabled", "The plugin is enabled!");
            getServer().getPluginManager().registerEvents(new Events(this, getLogger()), this);
        } else {
            getLogger().info("No main Webhook found in config, while there is no webhook, the plugin will not work, so please put the right webhook!");
            // todo: try to get a life
            config.addDefault("webhook.join", "none");
            config.addDefault("webhook.leave", "none");
            config.addDefault("webhook.kill", "none");
            config.addDefault("webhook.break", "none");
            config.addDefault("webhook.place", "none");
            config.addDefault("webhook.achievement", "none");
            config.addDefault("webhook.main", "none");
            config.addDefault("whitelist.on", false);
            config.addDefault("whitelist.break", "[ ]");
            config.addDefault("whitelist.place", "[ ]");
            config.addDefault("blacklist.on", false);
            config.addDefault("blacklist.break", "[ ]");
            config.addDefault("blacklist.place", "[ ]");
            config.options().copyDefaults(true);
            saveConfig();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if((config.isSet("webhook.main") && config.getString("webhook.main").contains("https://discord.com/api/webhooks/") && config.getString("webhook.main") != null))
        cl.sendToWebhook(config.getString("webhook"), "Disabled", "The plugin got disabled");
    }
}
