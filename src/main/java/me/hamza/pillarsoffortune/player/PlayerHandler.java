package me.hamza.pillarsoffortune.player;

import lombok.Getter;
import lombok.Setter;

import com.mongodb.client.MongoCollection;
import me.hamza.pillarsoffortune.POF;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@Getter @Setter
public class PlayerHandler {

    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerData getPlayer(UUID uuid) {
        PlayerData playerData = playerDataMap.get(uuid);
        if (playerData == null) {
            playerData = new PlayerData(uuid);
            loadData(playerData);
            playerDataMap.put(uuid, playerData);
        }
        return playerData;
    }

    public void storeData(PlayerData playerData) {
        MongoCollection<Document> collection = POF.getInstance().getMongoDatabase().getCollection("player_data");
        Document playerDocument = new Document("uuid", playerData.getUuid().toString())
                .append("username", playerData.getUsername())
                .append("wins", playerData.getWins())
                .append("losses", playerData.getLosses())
                .append("kills", playerData.getKills());

        collection.updateOne(new Document("uuid", playerData.getUuid().toString()),
                new Document("$set", playerDocument),
                new com.mongodb.client.model.UpdateOptions().upsert(true));
    }

    public void loadData(PlayerData playerData) {
        MongoCollection<Document> collection = POF.getInstance().getMongoDatabase().getCollection("player_data");
        Document playerDocument = collection.find(new Document("uuid", playerData.getUuid().toString())).first();

        if (playerDocument != null) {
            playerData.setWins(playerDocument.getInteger("wins", 0));
            playerData.setLosses(playerDocument.getInteger("losses", 0));
            playerData.setKills(playerDocument.getInteger("kills", 0));
        }
    }

}
