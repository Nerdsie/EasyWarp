package com.collinsrichard.easywarp.managers;

import com.collinsrichard.easywarp.Helper;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


public class CooldownManager {
    private static HashMap<UUID, Date> cooldowns = new HashMap<UUID, Date>();

    public static void setCooldown(Player player, int cooldown) {
        if (cooldown > 0) {
            Date date = new Date();
            date.setTime(date.getTime() + (cooldown * 1000L));
            cooldowns.put(player.getUniqueId(), date);
        } else {
            cooldowns.remove(player.getUniqueId());
        }
    }

    private static Date getCooldown(Player player) {
        return cooldowns.get(player.getUniqueId());
    }

    public static String getCooldownString(Player player){
        Date date = getCooldown(player);
        if(date == null){
            return null;
        }

        long diff = date.getTime() - new Date().getTime();
        if(diff <= 0){
            return null;
        }

        StringBuilder builder = new StringBuilder();
        long seconds = diff / 1000 % 60;
        long minutes = diff / (60 * 1000) % 60;
        long hours = diff / (60 * 60 * 1000) % 24;

        if(hours > 0){
            builder.append(hours);
            builder.append(" hour");
            if (hours > 1) {
                builder.append("s");
            }
            builder.append(" ");
        }

        if(minutes > 0){
            builder.append(minutes);
            builder.append(" minute");
            if (minutes > 1) {
                builder.append("s");
            }
            builder.append(" ");
        }

        if(seconds > 0){
            builder.append(seconds);
            builder.append(" second");
            if (seconds > 1) {
                builder.append("s");
            }
        }

        return builder.toString();
    }
}
