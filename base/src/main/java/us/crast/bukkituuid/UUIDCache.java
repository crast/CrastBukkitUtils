package us.crast.bukkituuid;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A cache of username->UUID mappings that automatically cleans itself.
 * 
 * This cache is meant to be used in plugins such that plugins can look up the
 * UUID of a player by using the name of the player.
 * 
 * For the most part, when the plugin asks the cache for the UUID of an online
 * player, it should have it available immediately because the cache registers 
 * itself for the player join/quit events and does background fetches.
 * 
 * @author James Crasta
 *
 */
public class UUIDCache implements Listener {
    private static final UUID ZERO_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private Map<String, UUID> cache = new ConcurrentHashMap<String, UUID>();
    private JavaPlugin plugin;

    public UUIDCache(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    /**
     * Get the UUID from the cache for the player named 'name'.
     * 
     * If the id does not exist in our database, then we will queue
     * a fetch to get it, and return null. A fetch at a later point
     * will then be able to return this id.
     */
    public UUID getIdOptimistic(String name) {
        UUID uuid = cache.get(name);
        if (uuid == null) {
            ensurePlayerUUID(name);
            return null;
        }
        return uuid;
    }
    
    /**
     * Get the UUID from the cache for the player named 'name', with blocking get. 
     * 
     * If the player named is not in the cache, then we will fetch the UUID in
     * a blocking fashion. Note that this will block the thread until the fetch
     * is complete, so only use this in a thread or in special circumstances.
     * 
     * @param name The player name.
     * @return a UUID
     */
    public UUID getId(String name) {
        UUID uuid = cache.get(name);
        if (uuid == null) {
            try {
                Map<String, UUID> m = new UUIDFetcher(nameList(name)).call();
                return m.get(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uuid.equals(ZERO_UUID)) {
            uuid = null;
        }
        return uuid;
    }
    
    /**
     * Clean up any resources used by this class.
     * 
     * Most commonly you will use this in your plugin's onDisable.
     */
    public void shutdown() {
        HandlerList.unregisterAll(this);
        this.plugin = null;
    }
    
    /**
     * 
     * @param name The player's name
     */
    public void ensurePlayerUUID(String name) {
        if (cache.containsKey(name)) return;
        cache.put(name, ZERO_UUID);
        asyncFetch(nameList(name));
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
    
    private ArrayList<String> nameList(String name) {
        ArrayList<String> names = new ArrayList<String>();
        names.add(name);
        return names;
    }
    
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        ensurePlayerUUID(event.getPlayer().getName());
    }
    
    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        cache.remove(event.getPlayer().getName());
    }
}
