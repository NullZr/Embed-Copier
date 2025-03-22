# Embed Copier

Embed Copier is a simple Java application designed to fetch and copy Discord embed messages as JSON. It provides a user-friendly graphical interface built with Swing, allowing users to input a Discord bot token, channel ID, and message ID to retrieve embed data. The application leverages the JDA (Java Discord API) library to interact with Discord and Gson for JSON serialization.

## Features
- **Dynamic Token Input**: Enter your Discord bot token directly in the GUI to connect to Discord.
- **Embed Retrieval**: Fetch embed messages from a specified Discord channel and message ID.
- **Copy Functionality**: Easily copy the JSON data to the clipboard with a button or context menu.
- **Minimalistic Design**: A clean, pastel-themed interface with rounded buttons for a modern look.
- **Responsive Layout**: The JSON display area dynamically adjusts to window size, including fullscreen mode.

## Prerequisites
- **Java 17+**: Ensure you have a compatible JDK installed.
- **Maven**: Used for dependency management (optional if you manually include JARs).
- **Discord Bot Token**: You need a bot token from the [Discord Developer Portal](https://discord.com/developers/applications) with the `MESSAGE_CONTENT` intent enabled.

## Dependencies
- **JDA (Java Discord API)**: `net.dv8tion:JDA:5.0.0-beta.20` - For Discord integration.
- **Gson**: `com.google.code.gson:gson:2.10.1` - For JSON serialization.
Add these to your `pom.xml` if using Maven:
```xml
<dependencies>
    <dependency>
        <groupId>net.dv8tion</groupId>
        <artifactId>JDA</artifactId>
        <version>5.0.0-beta.20</version>
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
</dependencies>
