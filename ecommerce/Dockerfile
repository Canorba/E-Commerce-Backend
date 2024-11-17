# Etapa 1: Construcción de la aplicación
FROM maven:3.8.6-openjdk-17 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y construir el proyecto
COPY src /app/src
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen para ejecutar el servicio
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo en la imagen final
WORKDIR /app

# Copiar el archivo .jar generado en la etapa de construcción
COPY --from=build /app/target/ecommerce-backend.jar /app/ecommerce-backend.jar

# Exponer el puerto en el que la aplicación escuchará
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor arranque
ENTRYPOINT ["java", "-jar", "ecommerce-backend.jar"]
