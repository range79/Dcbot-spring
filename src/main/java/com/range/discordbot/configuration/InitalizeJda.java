    package com.range.discordbot.configuration;

    import com.range.discordbot.commands.configuration.SlashCommands;
    import com.range.discordbot.listener.AutoUserBanner;
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
        @Value(value = "${discord.bot.token}")
        private String token;

        public InitalizeJda(AutoUserBanner autoUserBanner, SlashCommands slashCommands) {
            this.autoUserBanner = autoUserBanner;
            this.slashCommands = slashCommands;
        }

        @Bean
        public JDA jda() {
            try{
                log.info("Initializing JDA");
                JDA jda= JDABuilder
                        .createDefault(token, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_MEMBERS))
                        .build();

                jda.addEventListener(slashCommands, autoUserBanner);

                jda.awaitReady();
                log.info("Successfully initialized JDA");
                return jda;
            }catch (Exception e){
                log.error("Error initializing JDA", e);
                throw new RuntimeException(e);
            }
        }
    }
