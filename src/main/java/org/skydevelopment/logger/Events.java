package org.skydevelopment.logger;

import org.bukkit.Achievement;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
    private Logger log;
    private java.util.logging.Logger logger;
    HttpStuff c = new HttpStuff();

    public Events(final Logger log, java.util.logging.Logger logger) {
        this.log = log;
        this.logger = logger;
    }

    String Join = this.log.config.getString("webhook.join");
    String Leave = this.log.config.getString("webhook.leave");
    String Break = this.log.config.getString("webhook.break");
    String Place = this.log.config.getString("webhook.place");
    String Kill = this.log.config.getString("webhook.kill");
    String Achievement = this.log.config.getString("webhook.achievement");

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(Join != "none" && Join.startsWith("https://discord.com/api/webhooks/"))
            c.sendToWebhook(Join, "User Joined", "the user `"+player.getDisplayName()+"` Just Joined");
        else return;
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(Leave != "none" && Leave.startsWith("https://discord.com/api/webhooks/"))
            c.sendToWebhook(Leave, "User Left", "the user `"+player.getDisplayName()+"` Has Left");
        else return;
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(Break != "none" && Break.startsWith("https://discord.com/api/webhooks"))
            c.sendToWebhook(Break, "Block Broken", "The User `"+player.getDisplayName()+"` Had just broke the block `"+block.getType().name()+"`");
        else return;
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(Place != "none" && Place.startsWith("https://discord.com/api/webhooks"))
            c.sendToWebhook(Place, "Block Placed", "The user `"+player.getDisplayName()+"` Had placed the block `"+block.getType().name()+"`");
        else return;
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();
        this.logger.info("real "+player.getLastDamageCause());
        if(Kill != "none" && Kill.startsWith("https://discord.com/api/webhooks")) {
            if (killer.equals(null)) {
                c.sendToWebhook(Kill, "Player Died", "The Player `" + player.getDisplayName() + "` Got Killed in an unknown/yet unhandled way");
            } else
                c.sendToWebhook(Kill, "Player Died", "The User " + player.getDisplayName() + " Got killed By " + killer.getName());
        } else return;
    }
    @EventHandler
    public void onAchievementGet(PlayerAchievementAwardedEvent event){
        Player player = event.getPlayer();
        Achievement achievement = event.getAchievement();
        if(Achievement != "none" && Achievement.startsWith("https://discord.com/"))
            c.sendToWebhook(Achievement, "Achievement Earned", "The Player `"+player.getDisplayName()+"` had unlocked the `"+achievement.name()+"` Achievement");
        else return;
    }
}
