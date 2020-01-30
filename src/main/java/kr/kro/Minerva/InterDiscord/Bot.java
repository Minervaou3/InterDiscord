package kr.kro.Minerva.InterDiscord;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot extends Thread {
    private static JDA Jda ;
    @Override
    public synchronized void start() {
        if((boolean)Main.getConfig("Bot.Start")) {
            AccountType accountType = null;
            Activity.ActivityType activityType = null;
            OnlineStatus onlineStatus = null;
            if (((String) Main.getConfig("Account.Type")).equalsIgnoreCase("Bot")) {
                accountType = AccountType.BOT;
            } else if (((String) Main.getConfig("Account.Type")).equalsIgnoreCase("Client")) {
                accountType = AccountType.CLIENT;
            }
            if(((String) Main.getConfig("Activity.Type")).equalsIgnoreCase("Playing")){
                activityType = Activity.ActivityType.DEFAULT;
            } else if (((String) Main.getConfig("Activity.Type")).equalsIgnoreCase("Listening")){
                activityType = Activity.ActivityType.LISTENING;
            } else if (((String) Main.getConfig("Account.Type")).equalsIgnoreCase("Streaming")) {
                activityType = Activity.ActivityType.STREAMING;
            } else if (((String) Main.getConfig("Activity.Type")).equalsIgnoreCase("Watching")){
                activityType = Activity.ActivityType.WATCHING;
            }
            if (((String) Main.getConfig("Bot.Stat")).equalsIgnoreCase("Online")){
                onlineStatus = OnlineStatus.ONLINE;
            }else if (((String) Main.getConfig("Bot.Stat")).equalsIgnoreCase("Invisible")){
                onlineStatus = OnlineStatus.INVISIBLE;
            }else if(((String) Main.getConfig("Bot.Stat")).equalsIgnoreCase("Dnd")){
                onlineStatus = OnlineStatus.DO_NOT_DISTURB;
            }else if(((String) Main.getConfig("Bot.Stat")).equalsIgnoreCase("Idle")){
                onlineStatus = OnlineStatus.IDLE;
            }else if(((String) Main.getConfig("Bot.Stat")).equalsIgnoreCase("Unknown")){
                onlineStatus = OnlineStatus.UNKNOWN;
            }

            JDABuilder builder = new JDABuilder(accountType);
            builder.setToken((String) Main.getConfig("Bot.Token"));
            builder.addEventListeners(new Chat());
            builder.addEventListeners(new Command());
            builder.setStatus(onlineStatus);
            if((boolean)Main.getConfig("Activity.Start")){
                builder.setActivity(Activity.of  (activityType , (String)Main.getConfig("Activity.Content")));
            }
            try {
                Jda = builder.build();
            } catch (LoginException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt() {
        Jda.shutdownNow();
        Jda = null;
    }

    public static JDA getJda(){
        return Jda;
    }
}
