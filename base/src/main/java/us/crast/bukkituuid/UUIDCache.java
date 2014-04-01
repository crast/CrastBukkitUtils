package us.crast.bukkituuid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class UUIDCache implements Listener {
    private Map<String, UUID> cache = new HashMap<String, UUID>();
    private JavaPlugin plugin;

    public UUIDCache(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    /**
     * Get the UUID from the cache for the player named 'name'
     */
    public UUID getIdForName(String name) {
        UUID uuid = cache.get(name);
        if (uuid == null) {
            ensurePlayer(name);
            return null;
        }
        return uuid;
    }
    
    /**
     * Clean up any resources used by this class.
     * 
     * Most commonly you will use this in your plugin's onDisable.
     */
    public void shutdown() {
        this.plugin = null;
    }
    
    private void ensurePlayer(String name) {
        ArrayList<String> names = new ArrayList<String>();
        names.add(name);
        asyncFetch(names);
    }

    private void asyncFetch(ArrayList<String> names) {
        final UUIDFetcher fetcher = new UUIDFetcher(names);
        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            public void run() {
                try {
                    cache.putAll(fetcher.call());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        ensurePlayer(event.getPlayer().getName());
    }
    
    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        cache.remove(event.getPlayer().getName());
    }
    
    
    
    
}
