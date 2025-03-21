package com.nekos.JDA;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import com.google.gson.*;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.OffsetDateTime;

public class EmbedCopier {
    private final JDA jda;

    public EmbedCopier(String token) throws Exception {
        this.jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        jda.awaitReady();
    }

    public String getEmbedJson(String channelId, String messageId, String messageIndex) {
        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel == null) {
            return "Канал не найден";
        }

        try {
            Message message = channel.retrieveMessageById(messageId).complete();
            if (!message.getEmbeds().isEmpty()) {
                MessageEmbed embed = message.getEmbeds().get(Integer.parseInt(messageIndex));
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeAdapter())
                        .create();
                return gson.toJson(embed);
            } else {
                return "Сообщение не содержит embed";
            }
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    // Адаптер для OffsetDateTime
    private static class OffsetDateTimeAdapter extends TypeAdapter<OffsetDateTime> {
        @Override
        public void write(JsonWriter out, OffsetDateTime value) throws IOException {
            out.value(value != null ? value.toString() : null);
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            String value = in.nextString();
            return value != null ? OffsetDateTime.parse(value) : null;
        }
    }
}