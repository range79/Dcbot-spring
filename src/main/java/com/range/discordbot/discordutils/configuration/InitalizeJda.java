    package com.range.discordbot.discordutils.configuration;

    import com.range.discordbot.discordutils.commands.configuration.SlashCommands;
    import com.range.discordbot.discordutils.listener.AutoCheckServerInfo;
    import com.range.discordbot.discordutils.listener.AutoLeaveFromOtherServers;
    import com.range.discordbot.discordutils.listener.AutoUserBanner;
    import net.dv8tion.jda.api.JDA;
    import net.dv8tion.jda.api.JDABuilder;
    import net.dv8tion.jda.api.requests.GatewayIntent;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    import java.util.EnumSet;

    @Configuration
    public class InitalizeJda {
        private final Logger log = LoggerFactory.getLogger(this.getClass());
        private final AutoUserBanner autoUserBanner;
        private final SlashCommands slashCommands;
        private final AutoLeaveFromOtherServers autoLeaveFromOtherServers;
        private final AutoCheckServerInfo autoCheckServerInfo;
        @Value(value = "${discord.bot.token}")
        private String token;

        public InitalizeJda(AutoUserBanner autoUserBanner, SlashCommands slashCommands,
                            AutoLeaveFromOtherServers autoLeaveFromOtherServers, AutoCheckServerInfo autoCheckServerInfo) {
            this.autoUserBanner = autoUserBanner;
            this.slashCommands = slashCommands;
            this.autoLeaveFromOtherServers = autoLeaveFromOtherServers;
            this.autoCheckServerInfo = autoCheckServerInfo;
        }

        @Bean
        public JDA jda() {
            try{
                log.info("Initializing JDA");
                //
                JDA jda= JDABuilder
                        //set token and permissions
                        .createDefault(token, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_MEMBERS))
                        .build();
                //add commandd listener
                jda.addEventListener(slashCommands, autoUserBanner,autoLeaveFromOtherServers,autoCheckServerInfo);
                jda.awaitReady();
                log.info("Successfully initialized JDA");
                return jda;
            }catch (Exception e){
                log.error("Error initializing JDA", e);
                //Jda yuklenemezse veya baska birsey olursa exception firlat
                throw new RuntimeException(e);
            }
        }
    }
