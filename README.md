# Discord Embed to JSON Converter

## Description
**Discord Embed to JSON Converter** is an application developed using **JDA (Java Discord API)** that allows copying messages with Embeds from Discord and converting them into JSON format.

## Features
- Convert Embeds to JSON.

## Installation & Running
### Requirements
- **Java 17+**
- **Maven** (if building manually)
- **Discord Bot Token**

### Build
1. Clone the repository:
   ```sh
   git clone https://github.com/nullzr/embed-copier.git
   cd embed-copier
   ```
2. Build the project using Maven:
   ```sh
   mvn install
   ```
3. Run the JAR file:
   ```sh
   java -jar target/embedcopier-{ver}-SNAPSHOT-jar-with-dependencies.jar
   ```
   
## Example JSON
```json
{
  "title": "Sample Embed",
  "description": "This is a test Embed",
  "color": 16711680,
  "fields": [
    { "name": "Field 1", "value": "Value 1", "inline": true },
    { "name": "Field 2", "value": "Value 2", "inline": false }
  ]
}
```
## License
This project is licensed under the **MIT** license. See the `LICENSE` file for details.

