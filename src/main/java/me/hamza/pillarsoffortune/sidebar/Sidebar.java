package me.hamza.pillarsoffortune.sidebar;

import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.player.PlayerData;
import me.hamza.pillarsoffortune.player.PlayerState;
import me.hamza.pillarsoffortune.utils.CC;
import me.hamza.pillarsoffortune.utils.assemble.AssembleAdapter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hammzar
 * @since 23/02/2025
 */

public class Sidebar implements AssembleAdapter {
    @Override
    public String getTitle(Player player) {
        return CC.color("&d&lPillars of Fortune");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        int playersOnTheServer = Mortal.getInstance().getServer().getOnlinePlayers().size();
        int gameSize = Mortal.getInstance().getSettingsConfiguration().getConfig().getInt("game.size");
        String playerOrPlayers = playersOnTheServer == 1 ? "player" : "players";

        lines.add("&7&m------------------------");

        if (playerData.getState().equals(PlayerState.LOBBY)) {
            lines.add(CC.color("&fWaiting for &d" + (gameSize - playersOnTheServer) + "&f " + playerOrPlayers));
            lines.add(CC.color("&fto start the game!"));
            lines.add("");
            lines.add(CC.color(" &7▪ &dPlayers online: &f" + playersOnTheServer));
            lines.add(CC.color(" &7▪ &dGame size: &f" + gameSize));
        }
        else if (playerData.getState().equals(PlayerState.PLAYING)) {
            lines.add(CC.color("&fGame Status: &aIn Progress"));
            lines.add(CC.color(" &7▪ &dDuration: &f" + getTime(player)));
            lines.add("");
            lines.add(CC.color(" &7▪ &dPlayers Left: &f" + Mortal.getInstance().getGameManager().getActiveGame().getGamePlayers().size()));
        }

        lines.add("");
        lines.add(CC.color("&dwww.hammzar.net"));
        lines.add("&7&m------------------------");

        return lines;
    }

    private String getTime(Player player) {
        return "";
    }

}
