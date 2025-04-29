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
       url: jdbc:postgresql://localhost:5432/dbname_in_compose_yaml
       username: your_username
       password: your_password 
   discord:
     bot:
       server_id:
       token: your_discord_bot_token
       log:
         channel_id: your_log_channel_id
       allow-connect-other-servers: #true or false
   app:
    version: "${version}"   
   ```
3. Generate a yaml file name compose.yaml

```yaml
version: '3.9'

services:

  db:
    image: postgres
    restart: always
    # set shared memory limit when using docker-compose
    shm_size: 128mb
    # or set shared memory limit when deploy via swarm stack
    #volumes:
    #  - type: tmpfs
    #    target: /dev/shm
    #    tmpfs:
    #      size: 134217728 # 128*2^20 bytes = 128Mb
    environment:
      POSTGRES_USERNAME: What_you_want_make_ur_db_username
      POSTGRES_PASSWORD: db_container_pass
      POSTGRES_DB: db_name
    ports:
      - "5432:5432"
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

### [For changelog click here](CHANGELOG.md)