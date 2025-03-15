# MULTI-DATASOURCE-SPRING

Proyecto ejemplo de cómo configurar una aplicación Spring Boot que utiliza dos datasources:
- **Datasource de Lectura:** Conectado a una base de datos MySQL.
- **Datasource de Escritura:** Conectado a una base de datos PostgreSQL.

La finalidad es demostrar cómo se pueden separar operaciones de lectura y escritura, lo cual es común en aplicaciones empresariales.

## Descripción

En este proyecto se configura:
- **Datasource de Lectura (MySQL):** Para operaciones de lectura.
- **Datasource de Escritura (PostgreSQL):** Para operaciones de escritura.

Se utiliza Spring Boot, Spring Data JPA y se levantan las bases de datos en contenedores Docker mediante Docker Compose.

El proyecto incluye además un controlador REST para exponer endpoints que permiten:
- Obtener registros del datasource de lectura.
- Guardar registros en el datasource de escritura.
- Transferir datos desde la base de lectura a la escritura.


## Requisitos

- **Java:** JDK 11 o superior.
- **Maven:** Para la gestión de dependencias y construcción del proyecto.
- **Docker & Docker Compose:** Para levantar los contenedores de bases de datos.
- **IDE:** IntelliJ IDEA, Eclipse, o el de tu preferencia.

## Estructura del Proyecto

La organización del código es de la siguiente forma:
```
com.sorz.multidatasourcespring
├── MultiDatasourceSpringApplication.java
├── config
│   ├── ReadingDatasourceConfiguration.java
│   ├── WritingDatasourceConfiguration.java
│   ├── ReadingDataSourceJPAConfig.java
│   └── WritingDataSourceJPAConfig.java
├── controller
│   ├── DataTransferController.java
├── model
│   ├── reading
│   │   └── ReadingEntity.java
│   └── writing
│       └── WritingEntity.java
├── repository
│   ├── reading
│   │   └── ReadingRepository.java
│   └── writing
│       └── WritingRepository.java
└── service
    └── DataTransferService.java
```

- **config:** Clases de configuración para los datasources y JPA.
- **controller:** Controlador REST para exponer los endpoints de la API.
- **model:** Entidades JPA para cada datasource.
- **repository:** Interfaces de Spring Data JPA para acceder a cada base de datos.
- **service:** Lógica de negocio, incluida la transferencia de datos entre los dos datasources.

## Configuración

### application.properties

Define las propiedades para cada datasource en el archivo `src/main/resources/application.properties`:

```properties
# DataSource de Lectura (MySQL)
spring.datasource.reading.url=jdbc:mysql://localhost:3306/readingdb
spring.datasource.reading.username=root
spring.datasource.reading.password=rootpass
spring.datasource.reading.driverClassName=com.mysql.cj.jdbc.Driver

# DataSource de Escritura (PostgreSQL)
spring.datasource.writing.url=jdbc:postgresql://localhost:5432/writingdb
spring.datasource.writing.username=postgres
spring.datasource.writing.password=postgrespass
spring.datasource.writing.driverClassName=org.postgresql.Driver

# Propiedades de JPA
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

**Importante**:
Se utiliza la anotación @Primary en la configuración del datasource de lectura para que sea el bean predeterminado en caso de ambigüedad.

## Ejecución
1. Clonar el repositorio:
```bash
git clone https://github.com/sorodriguezz/multi-datasource-spring.git
```
(Asegúrate de clonar la rama o directorio correspondiente al proyecto de multi-datasource)

2. Levantar los contenedores de bases de datos:

Desde la raíz del proyecto, ejecuta:
```bash
docker-compose up -d
```
Esto iniciará los contenedores de MySQL y PostgreSQL.

3. Ejecutar la aplicación Spring Boot:

Puedes hacerlo desde la terminal con Maven:
```bash
mvn spring-boot:run
```
o desde tu IDE ejecutando la clase MultidatasourceDemoApplication.java.

## Endpoints de la API
El controlador REST expone los siguientes endpoints:

- GET /api/readings
Retorna todos los registros de la base de datos de lectura (MySQL).
```bash
curl -X GET http://localhost:8080/api/readings
```

- POST /api/writings
Permite insertar un registro en la base de datos de escritura (PostgreSQL).
Ejemplo de cuerpo JSON:
```json
{
  "info": "Nuevo registro de escritura"
}
```

Ejemplo con cURL:

```bash
curl -X POST http://localhost:8080/api/writings \
    -H "Content-Type: application/json" \
    -d '{"info": "Nuevo registro de escritura"}'
```

- POST /api/transfer
Transfiere datos: lee todos los registros del datasource de lectura y los inserta en el datasource de escritura.
```bash
curl -X POST http://localhost:8080/api/transfer
```

## Docker Compose
El archivo docker-compose.yml levanta dos contenedores para las bases de datos:

```bash
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: mysql
    restart: always
    environment:
      MYSQL_DATABASE: readingdb
      MYSQL_ROOT_PASSWORD: rootpass
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  postgres:
    image: postgres:14
    container_name: postgres
    restart: always
    environment:
      POSTGRES_DB: writingdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgrespass
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  mysql_data:
  postgres_data:
```

## Notas Adicionales
- **Uso de @Primary**:
La anotación @Primary se utiliza en el datasource de lectura para que, cuando Spring necesite inyectar un DataSource sin especificar un @Qualifier, use este bean por defecto.

- **Logs y Depuración**:
Revisa los logs de la aplicación para confirmar que las conexiones a las bases de datos se establecen correctamente. Si no se transfieren registros, asegúrate de que la base de datos de lectura (MySQL) tenga datos disponibles.

- **Extensibilidad**:
Este proyecto sirve de base para escenarios más complejos. Puedes ampliar la funcionalidad agregando más endpoints, lógica de negocio o integrando otras tecnologías según tus necesidades.