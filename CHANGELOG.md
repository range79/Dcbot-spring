# 📄 Changelog

## Version 0.0.1-SNAPSHOT

- **🚫 Ban Command Added**    a new command allowing server administrators to ban users directly through the bot. This feature helps efficiently manage user behavior and remove problematic users swiftly.

- **🚪 Leave Server Feature**  
  Implemented functionality for the bot to leave a server automatically based on specific conditions or upon request. Useful for managing bot presence in inactive or unnecessary servers.

- **🧹 Prune Command Added**  
  A command to bulk-delete messages in a channel, assisting moderators in cleaning up chats. The command allows specifying the number of messages to delete for flexibility.

- **🔓 Unban Command (Beta)**  
  Introduced the ability to reverse bans. While functional, minor issues remain and will be resolved in future versions.

---

## Version 1.0

- **🔧 Unban Command Fixed**    all known issues with the unban command. It now works as expected, allowing admins to unban users smoothly.

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
## Version 2.1 Changelog

### New Features & Fixes:

- **Info Command Fixed:**
    - Added missing `break` in the switch statement, which was causing the bot to throw an error.

- **Bot Configuration Refactor:**
    - The bot's configuration class has been refactored into two separate classes for better organization and maintainability. Previously, the class was handling too many responsibilities, making it difficult to manage.
    - Now, the configuration has been split into:
        - One class for **initializing JDA**.
        - Another class for **registering slash commands**.

- **New Commands:**
    - **ListBannedUsers** command added, which allows you to see the list of banned users.

- **SlashCommands Refactor:**
    - The **SlashCommands** class has been split into two parts. The original class had grown too large and was becoming difficult to manage.

- **New Package Structure:**
    - **Admin Package**: This package will contain commands that require admin privileges.
    - **User Package**: This package will hold commands that are accessible to all users.
    - **Configuration Package**: This package will store all slash command configurations, including command registration, routing, and event listeners.

- **Application YAML Configuration:**
    - Updated the application configuration to include versioning:
      ```yaml
      app:
        version: "${version}" 
      ```

- **DiscordGuildUtil Refactor:**
    - Added **`@Lazy`** annotation to the **DiscordGuildUtil** bean to resolve circular dependency issues and prevent the application from failing due to bean initialization cycles.

## 📦 Version 2.2 – Enhancements & Refactors

- ➕ **User Module Added**  
  The new `user` package has been introduced.

- 🧹 **PruneCommand Class Refactored**  
  The `PruneCommand` class has been cleaned up and improved for better message deletion behavior.  
  Permission check for `MESSAGE_MANAGE` was added to ensure only authorized users can use `/prune`.

- 🗑️ **Removed `BannerUtil.kt`**  
  The `BannerUtil.kt` class was removed due to being considered an unnecessary and ineffective implementation.

- 🔄 **Updated Endpoints:**
    - `users/bannedUserS/all` ➝ `users/banned`
    - `users/ban/user/{tag}` ➝ `users/ban/{tag}`

- 💾 **Ban Service Save Logic Fixed**  
  The ban service now properly persists data to the repository.

- 🛠️ **Logger Issue Fixed in `DiscordGuildUtil`**  
  Logger setup has been corrected.

- ❌ **Removed JDA from `DiscordGuildUtil` Constructor**  
  The `JDA` dependency was removed from the constructor and will be explained in the README.

## 📦 Version 2.2.1

### 🔧 Refactoring & Improvements
- 🛑 **Unban Command** (`admin/unban`) now uses `BannedUserService` instead of accessing the repository directly.
- 📄 **JavaDoc Cleanup**: All documentation comments from `BannedUserServiceImpl` were moved to the interface for better clarity and consistency.
- ℹ️ **Info Command**: Minor updates and cleanup were made for improved output and readability.

# 📌 Version 3.0.0 – Security, JWT, and Multi-Server Support

---

## 🔐 Security
- `SecurityConfig` added for comprehensive Spring Security setup.
- `JwtUtil` class added to handle token generation and validation.
- Basic JWT-based authentication infrastructure is now in place.
- Full Refresh Token and JWT services improvements are planned for the next version.

---

## 🔁 Refresh Tokens
- `RefreshToken` model and repository created.
- `RefreshUserTokenService` implemented.
  > Please refer to the code documentation for detailed usage.

---

## ⚙️ Configuration & Utilities
- `SecurityConfig` finalized.
- `JwtUtil` developed to simplify JWT operations.
- `docker-compose.yaml` added for easier deployment.
- `README` updated with clearer setup and usage instructions.

---

## 🛠️ Commands & Bug Fixes
- **Say Command** logic bug fixed:
  > Now properly returns the sent message as a reply.

---

## 🛡️ Ban Management and Multi-Server Features
- `serverId` field added to `BannedUser` model:
  > Allows tracking which server the user was banned from.
- `ServerInfo` entity introduced:
  > Bot can now operate across multiple servers, each with its own record.
- `/register-server` command added:
  > After adding the bot to a new server, you must run `/register-server` once to register it.
- `ServerInfo` improvements:
  - Servers with `isBanned = true` will cause the bot to **automatically leave**.
  - Bot will only join servers where `isRegistered = true`.

---

## 🖥️ Server Controller
- A basic `ServerController` has been added.
- More server management functionalities will be developed in upcoming versions.

---

## 🔮 Upcoming Features
- `/set-log-channel` command:
  > Allows each server to set and save its own logging channel.
- JWT and Refresh Token services will be enhanced and made more robust.
- ServerController will be expanded.
- The bot is aimed to reach a **final, stable version before military service**.





Absolutely kanka! Here’s the **English version** of the `v4.0.0` release note — clean, powerful, and professional with just the right swagger:

---

## 🚀 Sprida v4.0.0 – *"A New Era Begins"*

### 🔥 What’s New

* ❌ **Refresh Token Removed**
  We've removed the refresh token system entirely.
  Simplicity wins — no more unnecessary token rotations or cookie complexity.

* 🔐 **Custom Login Page Added**
  Fully responsive login screen with a modern UI.
  Integrated with Spring Security. Goodbye default login, hello elegance.

* 🧑‍💼 **User Roles Introduced**
  The system now recognizes two roles:

  * `ADMIN`: Full permissions — can register servers, ban/unban users, etc.
  * `MODERATOR`:  no server registration but other things are same  but future updates will expand its power.

* 🧭 **Admin Panel is Live**

  * View all users
  * View & register servers
  * Ban & unban users
  * See banned user list
  * Connected directly to live REST API

* 🌗 **Dark / Light Mode Toggle**
  A slick theme switcher in the top corner.
  Switches instantly and remembers your preference across sessions via `localStorage`.

---

### ✅ Feature Summary

| Feature         | Status        |
| --------------- | ------------- |
| Refresh Token   | ❌ Removed     |
| Login System    | ✅ Added       |
| Role Management | ✅ Active      |
| Admin Panel     | ✅ Completed   |
| Theme Toggle    | ✅ Implemented |


> “Less complexity. More control. Refresh tokens are gone — Sprida is now cleaner and sharper.”
> — **Range**


---

## 📣 Note
This release is one of the major milestones for the bot —  
focusing on **security**, **multi-server management**, and **expandability**.  
Development will continue actively. Thank you for your support! 🚀
