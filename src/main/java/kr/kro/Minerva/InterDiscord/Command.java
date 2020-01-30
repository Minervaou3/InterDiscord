package kr.kro.Minerva.InterDiscord;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import javax.annotation.Nonnull;

public class Command extends ListenerAdapter {
    public String arg(String[] args , int start , int end) {
        String string = args[start];
        for (int i = start+1; i < end; i++) {
            string = string +" "+ args[i];
        }
        return string;
    }
    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        //String[] args = event.getMessage().getContentDisplay().split(" ");
        ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
        if(event.getAuthor().getId().equals("280186600153939970") ) {
            Bukkit.dispatchCommand(consoleSender, event.getMessage().getContentDisplay());
        }else {
            event.getMessage().getTextChannel().sendMessage("권한이 없습니다..!").queue();
        }
    }
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentDisplay().split(" ");
        ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
        if(event.getMessage().getContentDisplay().startsWith("$명령어")) {
                if(event.getAuthor().getId().equals("280186600153939970") ) {
                Bukkit.dispatchCommand(consoleSender,arg(args,1,args.length) );
            }else {
                    event.getMessage().getTextChannel().sendMessage("권한이 없습니다..!").queue();
                }
        }
    }
}
