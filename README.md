
# Discord Bot with Spring Boot and JDA

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
   git clone https://github.com/your-username/discord-bot.git
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

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

## Changelog


`# Changelog

## Version 0.0.1-SNAPSHOT

- **🚫 Ban Command Added**: A new command allowing server administrators to ban users directly through the bot. This feature helps efficiently manage user behavior and swiftly remove problematic users from the server.

- **🚪 Leave Bot From Server**: Added functionality for the bot to leave the server automatically, based on specific conditions or upon request. This is particularly useful for managing bot activity in inactive or unwanted servers.

- **🧹 Prune Command Added**: A command to delete multiple messages at once in a channel, helping moderators clean up chats quickly. The prune command can be customized to specify the number of messages to delete, improving moderation flexibility.

- **🔓 Unban Command Added**: Introduced the ability for server administrators to reverse bans. While the feature is functional, some minor issues remain, which will be addressed in future updates.

---

## Version 1.0

- **🔧 Unban Command Fixed**: The unban command has been fixed and is now working as expected. Server administrators can now reinstate banned users without any issues.

- **🔄 BannedUserJoinListener Class Renamed to AutoUserBan**: The `BannedUserJoinListener` class has been renamed to `AutoUserBan` for improved code clarity and readability. This class is responsible for banning users who attempt to rejoin the server after being banned.

- **🚫 AutoUserBan Class**:
   - **🔒 Automatic Ban for Invited Banned Users**: The `AutoUserBan` class automatically bans users who have been previously banned and attempt to rejoin the server via invites. It ensures that banned users cannot bypass their ban and re-enter the server.
   - **🚫 Prevents Ban Bypassing**: This feature prevents banned users from using invites to re-enter the server, adding an extra layer of protection against potential loopholes.
   - **🛡️ Maintains Server Integrity**: The `AutoUserBan` class plays a crucial role in maintaining server integrity by preventing banned users from returning through invites, ensuring that the community is protected from disruptive individuals.

- **📜 Renamed `discord.bot.banneduser.channelid` to `discord.bot.log.channelid`**: The configuration setting `discord.bot.banneduser.channelid` has been renamed to `discord.bot.log.channelid` for better clarity. This change more accurately reflects the channel's role in logging events related to banned users.

- **ℹ️ Info Command Added**: A new "info" command has been introduced, providing detailed information about the bot, its version, and the developer team behind it. This command is helpful for users who wish to learn more about the bot's functionality and development.
  