package kr.kro.Minerva.InterDiscord;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    Thread thread = new Bot();


    public static YamlConfiguration yaml = null;
    public static Object getConfig(String path) { return yaml.get(path); }
    @Override
    public void onEnable() {
        File file = new File(this.getDataFolder().getPath(),"config.yml");
        yaml = YamlConfiguration.loadConfiguration(file);
        Bukkit.getPluginManager().registerEvents(new Chat() , this);
        yaml.options().header("https://Minerva.kro.kr").copyDefaults(true);
        yaml.addDefault("Bot.Token", " ");
        yaml.addDefault("Bot.Start", true);
        yaml.addDefault("Bot.Stat","Online");
        yaml.addDefault("Account.Type", "BOT");
        yaml.addDefault("Activity.Type", "Playing");
        yaml.addDefault("Activity.Content", " ");
        yaml.addDefault("Activity.Start", false);
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Bot.getJda() == null) {
            thread.start();
        }
    }
    @Override
    public void onDisable() {
        Bot.getJda().getTextChannelById("635433006366195714").sendMessage("서버 오프!").queue();
        thread.interrupt();
    }
}
