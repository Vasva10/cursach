package org.example.library.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class HttpUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static String authHeader(String login, String password) {
        String credentials = login + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());
        return "Basic " + encoded;
    }

    public static String authGet(String url, String login, String password) throws IOException {
        return Request.get(url)
                .addHeader("Authorization", authHeader(login, password))
                .execute()
                .returnContent()
                .asString();
    }

    public static String authPost(String url, String login, String password, Object body) throws IOException {
        String jsonBody = body != null ? mapper.writeValueAsString(body) : null;
        Content content = Request.post(url)
                .addHeader("Authorization", authHeader(login, password))
                .bodyString(jsonBody, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();
        return content.asString();
    }

    public static String authPut(String url, String login, String password, Object body) throws IOException {
        String jsonBody = body != null ? mapper.writeValueAsString(body) : null;
        Content content = Request.put(url)
                .addHeader("Authorization", authHeader(login, password))
                .bodyString(jsonBody, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();
        return content.asString();
    }

    public static String authDelete(String url, String login, String password) throws IOException {
        Content content = Request.delete(url)
                .addHeader("Authorization", authHeader(login, password))
                .execute()
                .returnContent();
        return content.asString();
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }
}
