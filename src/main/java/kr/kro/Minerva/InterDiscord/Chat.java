package kr.kro.Minerva.InterDiscord;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Chat extends ListenerAdapter implements Listener {
    // 버킷 이벤트
    @EventHandler
    public static void MinecraftChat(AsyncPlayerChatEvent event){
        String Massage_Discord = event.getMessage();
        String Massage_Minecraft = event.getMessage();
        for(Member member: Bot.getJda().getGuildById("635433006366195712").getMembers() ) {
            if (Massage_Discord.contains("@"+member.getEffectiveName())) {
                Massage_Discord = Massage_Discord.replace("@"+member.getEffectiveName(), member.getAsMention());
                Massage_Minecraft = Massage_Minecraft.replace("@"+member.getEffectiveName(),"§d§l@" +member.getEffectiveName()+"§f");
            }
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Massage_Minecraft.contains("@"+player.getName())) {
                Massage_Minecraft = Massage_Minecraft.replace("@"+player.getName(), "§a§l@" +player.getName()+"§f");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BELL, 5, 4);
            }
        }
        Bot.getJda().getTextChannelById("635433006366195714").sendMessage(event.getPlayer().getName() +" : "+ Massage_Discord).queue();
        event.setMessage(Massage_Minecraft);
    }

    // 디스코드 이벤트
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(!(event.getMember().getUser() == event.getJDA().getSelfUser())&& !(event.getMessage().getContentDisplay().startsWith("$"))) {
            String Massage = event.getMessage().getContentDisplay();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Massage.contains("@"+player.getName())) {
                    Massage = Massage.replace("@"+player.getName(), "§a§l@" + player.getName()+"§f");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BELL, 5, 4);
                }
            }
            Bukkit.broadcastMessage(event.getAuthor().getName() +" : "+Massage);
        }
    }
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        event.getJDA().getTextChannelById("635433006366195714").sendMessage("서버 온!").queue();
    }
}
