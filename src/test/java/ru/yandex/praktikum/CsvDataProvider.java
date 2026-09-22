package ru.yandex.praktikum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvDataProvider {

    public static Object[][] loadFromResource(String resourcePath) throws IOException {
        List<Object[]> rows = new ArrayList<>();
        try (InputStream is = CsvDataProvider.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Ресурс не найден: " + resourcePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String header = reader.readLine(); // пропускаем заголовок
                if (header == null) return new Object[0][0];

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",", 2); // ровно 2 части: вопрос и ответ
                    if (parts.length == 2) {
                        rows.add(new Object[]{parts[0].trim(), parts[1].trim()});
                    }
                }
            }
        }
        // Конвертируем List<Object[]> в Object[][]
        return rows.toArray(new Object[0][]);
    }
}
