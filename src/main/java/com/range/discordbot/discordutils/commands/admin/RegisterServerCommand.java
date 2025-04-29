package com.range.discordbot.discordutils.commands.admin;

import com.range.discordbot.service.ServerInfoService;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RegisterServerCommand {
    private final ServerInfoService serverInfoService;
    private final Map<String, String> pendingRegistrations = new ConcurrentHashMap<>();
    private final Logger log = LoggerFactory.getLogger(RegisterServerCommand.class);

    public RegisterServerCommand(ServerInfoService serverInfoService) {
        this.serverInfoService = serverInfoService;
    }

    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("register-server")) {
            return;
        }
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("❌ You don't have permission.").setEphemeral(true).queue();
            return;
        }
        if (event.getOption("serverid") == null) {
            event.reply("❌ Server ID is required.").setEphemeral(true).queue();
            return;
        }

        String serverId = event.getOption("serverid").getAsString();
        pendingRegistrations.put(event.getUser().getId(), serverId);

        event.reply("Are you sure you want to register this server?")
                .addActionRow(
                        Button.success("confirm_register", "✅ Confirm"),
                        Button.danger("cancel_register", "❌ Cancel")
                )
                .setEphemeral(true)
                .queue();
    }

    public void handleButtonInteraction(ButtonInteractionEvent event) {
        String buttonId = event.getButton().getId();
        if (buttonId == null) return;

        String userId = event.getUser().getId();
        String serverId = pendingRegistrations.get(userId);
        if (serverId == null) return;

        if (buttonId.equals("confirm_register")) {
            try {
                serverInfoService.registerServer(serverId);
                event.editMessage("✅ Server registered successfully!").setComponents().queue();
                log.info("Server registered: " + serverId);
            } catch (Exception e) {
                log.error("Failed to register server", e);
                event.editMessage("❌ Failed to register server.").setComponents().queue();
            }
            pendingRegistrations.remove(userId);
        } else if (buttonId.equals("cancel_register")) {
            event.editMessage("❌ Server registration cancelled.").setComponents().queue();
            pendingRegistrations.remove(userId);
        }
    }
}
