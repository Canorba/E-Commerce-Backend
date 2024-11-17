Ecommerce Backend - Proyecto Dockerizado
Este proyecto es el backend de un sistema de e-commerce desarrollado con Spring Boot. Utiliza una base de datos MariaDB y está dockerizado para facilitar su ejecución en cualquier entorno.

Requisitos Previos
Docker instalado en tu máquina.
Docker Compose (si necesitas gestionar varios servicios, como la base de datos).
Maven para la construcción del proyecto (si no se utiliza Docker Compose).
JDK 17 o superior (para ejecutar Spring Boot).
Postman para probar y visualizar los endpoints.
Instrucciones de Instalación
Paso 1: Clonar el Repositorio
Primero, clona el repositorio en tu máquina local:

bash
Copiar código
git clone https://github.com/Canorba/E-Commerce-Backend
cd E-Commerce-Backend
Paso 2: Construir la Imagen Docker
Construir el archivo JAR: Si estás utilizando Maven, primero debes compilar el proyecto y generar el archivo JAR. Ejecuta el siguiente comando:
mvn clean package
Esto generará el archivo JAR de la aplicación en el directorio target/.

Construir la imagen Docker: Una vez generado el archivo JAR, construye la imagen Docker con el siguiente comando:
docker build -t ecommerce-backend .

Paso 3: Ejecutar los Contenedores
Usando Docker Compose
docker-compose up

Sin Docker Compose
Iniciar el contenedor de la base de datos:
docker run --name ecommerce-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=ecommerce -p 3306:3306 -d mariadb:10.5

Iniciar el contenedor de la aplicación:
docker run --name ecommerce-backend -p 8080:8080 --link ecommerce-db:ecommerce-db ecommerce-backend

Documentación de los Servicios con Postman
Se ha creado una colección en Postman para documentar y probar los servicios del API.

Importar la Colección en Postman
Abre Postman.
Haz clic en Import.
Selecciona el archivo ecommerce.postman_collection.json incluido en el proyecto.
La colección aparecerá en tu lista con todos los endpoints organizados.
Ejecución de los Endpoints
Configura la variable de entorno base_url en Postman con el valor http://localhost:8080.
Usa los endpoints para realizar pruebas. Ejemplo:
GET /api/products: Devuelve todos los productos.
POST /api/orders: Crea un nuevo pedido.
Pruebas Unitarias
Instrucciones para Ejecutar las Pruebas
Las pruebas unitarias se han implementado utilizando JUnit 5 y Mockito.

Para ejecutar las pruebas, utiliza el siguiente comando Maven:
mvn test

El resultado mostrará los casos de prueba ejecutados, con detalles de los éxitos y errores (si los hay).

Ejemplo de Prueba Unitaria
El siguiente ejemplo prueba la funcionalidad de obtener un producto por ID:
@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetProductById() {
        Product product = new Product(1L, "Product A", 100.0);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);
        assertEquals("Product A", result.getName());
    }}

Manejo de Errores
Se ha implementado un manejo robusto de errores para proporcionar respuestas claras y significativas a los clientes de la API.

Ejemplo de Código para Manejo de Errores
@ExceptionHandler(ProductNotFoundException.class)
public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException ex) {
    logger.error("Product not found: {}", ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse("Product Not Found", ex.getMessage()), HttpStatus.NOT_FOUND);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<Object> handleGeneralException(Exception ex) {
    logger.error("Internal server error: {}", ex.getMessage(), ex);
    return new ResponseEntity<>(new ErrorResponse("Internal Server Error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
}

Respuesta JSON para Errores
1. Producto No Encontrado (404)
Si se busca un producto que no existe:

Petición:

http
GET /api/products/999
Respuesta:

json
{
  "error": "Product Not Found",
  "message": "The product with ID 999 does not exist."
}
2. Error Interno del Servidor (500)
Si ocurre un error inesperado:

Respuesta:

json
Copiar código
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred."
}

Dockerización
Dockerfile
dockerfile

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/ecommerce-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
docker-compose.yml
yaml
Copiar código
version: '3.8'
services:
  ecommerce-backend:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mariadb://db:3306/ecommerce
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      - db
    networks:
      - ecommerce-network

  db:
    image: mariadb:10.5
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: ecommerce
    ports:
      - "3306:3306"
    networks:
      - ecommerce-network

networks:
  ecommerce-network:
    driver: bridge
