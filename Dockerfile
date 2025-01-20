# Используем образ OpenJDK
FROM openjdk:17-jdk-slim

# Указываем рабочую директорию
WORKDIR /app

# Копируем JAR файл приложения
COPY target/whale-doc-0.0.1-SNAPSHOT.jar whale-doc.jar

# Команда запуска
ENTRYPOINT ["java", "-jar", "whale-doc.jar"]
