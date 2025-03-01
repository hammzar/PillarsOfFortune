package me.hamza.pillarsoffortune;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.hamza.pillarsoffortune.arena.ArenaHandler;
import me.hamza.pillarsoffortune.commands.ArenaCommand;
import me.hamza.pillarsoffortune.commands.BuildModeCommand;
import me.hamza.pillarsoffortune.commands.MainCommand;
import me.hamza.pillarsoffortune.commands.SetLobbyCommand;
import me.hamza.pillarsoffortune.commands.random.TNTCartCommand;
import me.hamza.pillarsoffortune.game.GameListener;
import me.hamza.pillarsoffortune.game.GameManager;
import me.hamza.pillarsoffortune.hotbar.HotbarHandler;
import me.hamza.pillarsoffortune.hotbar.HotbarListener;
import me.hamza.pillarsoffortune.item.ItemOrganizer;
import me.hamza.pillarsoffortune.item.ItemRandomizer;
import me.hamza.pillarsoffortune.lobby.LobbyHandler;
import me.hamza.pillarsoffortune.player.PlayerHandler;
import me.hamza.pillarsoffortune.player.PlayerListener;
import me.hamza.pillarsoffortune.item.ItemRunnable;
import me.hamza.pillarsoffortune.player.PlayerRunnable;
import me.hamza.pillarsoffortune.sidebar.Sidebar;
import me.hamza.pillarsoffortune.utils.CC;
import me.hamza.pillarsoffortune.utils.Config;
import me.hamza.pillarsoffortune.utils.assemble.Assemble;
import me.hamza.pillarsoffortune.utils.assemble.AssembleStyle;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Hammzar
 * @since 20/02/2025
 */

@Getter
public class Mortal extends JavaPlugin {

    @Getter
    public static Mortal instance;
    private ArenaHandler arenaHandler;
    private ItemOrganizer itemOrganizer;
    private ItemRandomizer itemRandomizer;
    private ItemRunnable itemRunnable;
    private GameManager gameManager;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private PlayerHandler playerHandler;
    private PlayerRunnable playerRunnable;
    private HotbarHandler hotbarHandler;
    private LobbyHandler lobbyHandler;

    private Config arenaConfiguration, messagesConfiguration, settingsConfiguration;

    @Override
    public void onEnable() {
        instance = this;

        arenaConfiguration = new Config("arenas.yml", this);
        messagesConfiguration = new Config("messages.yml", this);
        settingsConfiguration = new Config("settings.yml", this);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
        Bukkit.getPluginManager().registerEvents(new HotbarListener(), this);

        arenaHandler = new ArenaHandler();
        arenaHandler.init();
        itemOrganizer = new ItemOrganizer();
        itemOrganizer.init();
        itemRandomizer = new ItemRandomizer();
        itemRunnable = new ItemRunnable();
        itemRunnable.runTaskTimer(this, 0L, 1L);
        gameManager = new GameManager();
        initMongo();
        playerHandler = new PlayerHandler();
        playerRunnable = new PlayerRunnable();
        playerRunnable.runTaskTimer(this, 0L, 20L);
        hotbarHandler = new HotbarHandler();
        hotbarHandler.init();
        lobbyHandler = new LobbyHandler();
        lobbyHandler.init();

        Objects.requireNonNull(this.getCommand("arena")).setExecutor(new ArenaCommand());
        Objects.requireNonNull(this.getCommand("mortal")).setExecutor(new MainCommand());
        Objects.requireNonNull(this.getCommand("tntcart")).setExecutor(new TNTCartCommand());
        Objects.requireNonNull(this.getCommand("buildmode")).setExecutor(new BuildModeCommand());
        Objects.requireNonNull(this.getCommand("setlobby")).setExecutor(new SetLobbyCommand());

        Assemble assemble = new Assemble(this, new Sidebar());
        assemble.setAssembleStyle(AssembleStyle.MODERN);

        Bukkit.getWorlds().forEach(world -> {
            world.setTime(6000);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setDifficulty(Difficulty.HARD);
        });
    }

    @Override
    public void onDisable() {
        itemRunnable.cancel();
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

            CC.log("&8[&dMortal&8] &aEstablished MongoDB connection!");
        } catch (Exception exception) {
            CC.log("&8[&dMortal&8] &cMongoDB has failed to establish a connection!");
        }
    }

}
