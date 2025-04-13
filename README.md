# Sprida
## Discord Bot with Spring Boot and JDA

A Discord bot built using [Spring Boot](https://spring.io/projects/spring-boot) and [JDA](https://github.com/DV8FromTheWorld/JDA), with PostgreSQL for data storage and Lombok for reduced boilerplate code.

## Features

- Custom commands for interacting with users.
- PostgreSQL database integration for storing data.
- Lombok to reduce boilerplate code in model and service layers.
- Admin panel (coming soon) for managing bot configurations and interactions.

## Setup

### Prerequisites

- Java 11 or later
- [PostgreSQL](https://www.postgresql.org/)
- [Gradle](https://gradle.org/)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/range79/sprida.giy
   cd discord-bot
   ```

2. Configure `application.yml` with your PostgreSQL and Discord bot details:

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/message
       username: your_username
       password: your_password
   discord:
     bot:
       serverid:
       token: your_discord_bot_token
       log:
         channelid: your_log_channel_id
   ```

3. Install dependencies:

   ```bash
   ./gradlew build
   ```

4. Run the bot:

   ```bash
   ./gradlew bootRun
   ```

5. The bot will start and connect to Discord.

### Admin Panel (Coming Soon)

An admin panel feature will be added soon to allow management of bot settings and interactions through a web-based interface.

## License

**This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).**

# 📄 Changelog

## Version 0.0.1-SNAPSHOT

- **🚫 Ban Command Added**  
  Added a new command allowing server administrators to ban users directly through the bot. This feature helps efficiently manage user behavior and remove problematic users swiftly.

- **🚪 Leave Server Feature**  
  Implemented functionality for the bot to leave a server automatically based on specific conditions or upon request. Useful for managing bot presence in inactive or unnecessary servers.

- **🧹 Prune Command Added**  
  A command to bulk-delete messages in a channel, assisting moderators in cleaning up chats. The command allows specifying the number of messages to delete for flexibility.

- **🔓 Unban Command (Beta)**  
  Introduced the ability to reverse bans. While functional, minor issues remain and will be resolved in future versions.

---

## Version 1.0

- **🔧 Unban Command Fixed**  
  Fixed all known issues with the unban command. It now works as expected, allowing admins to unban users smoothly.

- **🧠 `BannedUserJoinListener` Renamed to `AutoUserBan`**  
  Renamed for better clarity and code readability. The class automatically handles bans for returning users.

- **🚫 AutoUserBan Enhancements**
    - **🔒 Auto Ban on Invite**: Automatically bans previously banned users trying to rejoin via invite links.
    - **🚫 Prevents Ban Evasion**: Adds an extra layer of security against ban bypass attempts.
    - **🛡️ Server Protection**: Ensures a safe and consistent community by blocking re-entry of disruptive users.

- **📜 Config Key Renamed**  
  `discord.bot.banneduser.channelid` → `discord.bot.log.channelid` to reflect its purpose more accurately.

- **ℹ️ Info Command Added**  
  A command that displays information about the bot, including version, features, and developer details.

---

## Version 2.0

- **📦 New Package Structure Introduced**
    - `commands`: All command classes moved here for better organization.
    - `controller`: Contains all controller classes.
    - `dto`: Added DTO classes for data transfer between layers.
    - `exception`: Centralized exception handling classes.
    - `utils`: Added `DiscordGuildUtil` to help retrieve server users.

- **🌐 UserController Added**  
  New controller to expose user-related endpoints.

- **🖼️ Thymeleaf Integrated**  
  Added for rendering dynamic web views.(But l don't added frontend)
