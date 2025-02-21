package me.hamza.pillarsoffortune;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.hamza.pillarsoffortune.arena.ArenaHandler;
import me.hamza.pillarsoffortune.commands.ArenaCommand;
import me.hamza.pillarsoffortune.game.GameHandler;
import me.hamza.pillarsoffortune.item.ItemOrganizer;
import me.hamza.pillarsoffortune.item.ItemRandomizer;
import me.hamza.pillarsoffortune.player.PlayerHandler;
import me.hamza.pillarsoffortune.player.PlayerListener;
import me.hamza.pillarsoffortune.item.ItemRunnable;
import me.hamza.pillarsoffortune.utils.CC;
import me.hamza.pillarsoffortune.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Hammzar
 * @since 20/02/2025
 */

@Getter
public class POF extends JavaPlugin {

    @Getter
    public static POF instance;
    private ArenaHandler arenaHandler;
    private ItemOrganizer itemOrganizer;
    private ItemRandomizer itemRandomizer;
    private ItemRunnable itemRunnable;
    private GameHandler gameHandler;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private PlayerHandler playerHandler;

    private Config arenaConfiguration, messagesConfiguration, settingsConfiguration;

    @Override
    public void onEnable() {
        instance = this;

        arenaConfiguration = new Config("arenas.yml", this);
        messagesConfiguration = new Config("messages.yml", this);
        settingsConfiguration = new Config("settings.yml", this);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        arenaHandler = new ArenaHandler();
        arenaHandler.init();
        itemOrganizer = new ItemOrganizer();
        itemOrganizer.init();
        itemRandomizer = new ItemRandomizer();
        itemRunnable = new ItemRunnable();
        itemRunnable.runTaskTimer(this, 0L, 1L);
        gameHandler = new GameHandler();
        initMongo();
        playerHandler = new PlayerHandler();

        Objects.requireNonNull(this.getCommand("arena")).setExecutor(new ArenaCommand());
    }

    @Override
    public void onDisable() {
        itemRunnable.cancel();
        gameHandler.getActiveGame().end();
        arenaHandler.getArenas().forEach(arena -> arenaHandler.saveArena(arena));
        playerHandler.getPlayerDataMap().forEach((uuid, playerData) -> playerData.store());
    }

    private void initMongo() {
        try {
            ConnectionString connectionString = new ConnectionString(Objects.requireNonNull(settingsConfiguration.getConfig().getString("MONGO.STRING")));
            MongoClientSettings.Builder mongoclientbuilder = MongoClientSettings.builder();
            mongoclientbuilder.applyConnectionString(connectionString);
            mongoclientbuilder.applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(30, TimeUnit.SECONDS));

            this.mongoClient = MongoClients.create(mongoclientbuilder.build());
            this.mongoDatabase = mongoClient.getDatabase(Objects.requireNonNull(settingsConfiguration.getConfig().getString("MONGO.DATABASE")));

            CC.log("&8[&dPOF&8] &aEstablished MongoDB connection!");
        } catch (Exception exception) {
            CC.log("&8[&dPOF&8] &cMongoDB has failed to establish a connection!");
        }
    }

}
