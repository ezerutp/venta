# 🏢 Sistema de Ventas OLPESA

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-4.0.0-red.svg)](https://maven.apache.org/)

## 🧾 Nota académica

> ⚠️ **Aviso importante — No desplegar en producción sin revisar la seguridad**  
> Este proyecto se desarrolló con fines académicos. La configuración de seguridad (protección de endpoints, manejo fino de roles y políticas de autorización) no está completamente robusta y puede contener brechas si se usa en entornos productivos.

Riesgos clave:
- Endpoints o permisos demasiado permisivos.
- Manejo de credenciales y secretos en archivos de configuración.
- Configuraciones de seguridad y políticas no auditadas.

Recomendaciones rápidas antes de cualquier despliegue:
- Realizar una auditoría de seguridad y pruebas de penetración.
- Revisar y endurecer control de accesos por endpoints y roles.
- Gestionar secretos fuera del repositorio (variables de entorno / vault).
- Forzar HTTPS, validar CORS y encabezados de seguridad (CSP, HSTS).
- Desactivar DDL auto y ajustar políticas de logging y monitoreo en producción.
- Actualizar dependencias y aplicar buenas prácticas de almacenamiento de contraseñas.

Priorizar estas tareas para que la nota académica no pase desapercibida y evitar riesgos en producción.

## 📝 Descripción

Sistema de gestión de ventas desarrollado con **Spring Boot** para la empresa OLPESA. Este sistema permite gestionar clientes, productos, usuarios y ventas de manera integral, con autenticación y autorización de usuarios.

## ✨ Características Principales

- 🔐 **Autenticación y Autorización** - Sistema de login con Spring Security
- 👥 **Gestión de Usuarios** - Control de acceso con roles diferenciados
- 👤 **Gestión de Clientes** - CRUD completo de clientes con validaciones
- 📦 **Gestión de Productos** - Administración del catálogo de productos
- 💰 **Gestión de Ventas** - Registro y seguimiento de ventas
- 🎨 **Interfaz Web** - Templates con Thymeleaf y CSS personalizado
- 📊 **Base de Datos** - Persistencia con JPA/Hibernate y MySQL

## 🛠️ Tecnologías Utilizadas

### Backend
- ☕ **Java 21** - Lenguaje de programación
- 🍃 **Spring Boot 3.5.6** - Framework principal
- 🔒 **Spring Security** - Autenticación y autorización
- 💾 **Spring Data JPA** - Persistencia de datos
- ✅ **Spring Validation** - Validación de datos
- 🐘 **MySQL Connector** - Driver de base de datos
- 📋 **Lombok** - Reducción de código boilerplate

### Frontend
- 🌐 **Thymeleaf** - Motor de plantillas
- 🎨 **CSS3** - Estilos personalizados
- 📱 **HTML5** - Estructura de páginas

### Base de Datos
- 🗄️ **MySQL 8.0** - Sistema de gestión de base de datos

### Herramientas de Desarrollo
- 🏗️ **Maven** - Gestión de dependencias y construcción
- 🔄 **Spring Boot DevTools** - Desarrollo en caliente

## 📁 Estructura del Proyecto

```
venta/
├── 📄 pom.xml                          # Configuración de Maven
├── 📖 README.md                        # Documentación del proyecto
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/pe/olpesa/venta/
│   │   │   ├── 🚀 VentaApplication.java         # Clase principal
│   │   │   ├── 📁 config/                       # Configuraciones
│   │   │   │   ├── 🔐 SecurityConfig.java       # Configuración de seguridad
│   │   │   │   ├── 👤 UsuarioDetalle.java       # Detalles de usuario
│   │   │   │   └── 🌐 WebConfig.java            # Configuración web
│   │   │   ├── 📁 controllers/                  # Controladores REST
│   │   │   │   ├── 👥 ClienteController.java
│   │   │   │   ├── 🏠 HomeController.java
│   │   │   │   └── 🔑 LoginController.java
│   │   │   ├── 📁 entities/                     # Entidades JPA
│   │   │   │   ├── 👤 Cliente.java
│   │   │   │   ├── 📦 Producto.java
│   │   │   │   ├── 👨‍💻 Usuario.java
│   │   │   │   └── 💰 Venta.java
│   │   │   ├── 📁 repositories/                 # Repositorios de datos
│   │   │   ├── 📁 services/                     # Lógica de negocio
│   │   │   ├── 📁 seeders/                      # Datos iniciales
│   │   │   └── 📁 utils/                        # Utilidades y enums
│   │   └── 📁 resources/
│   │       ├── ⚙️ application.properties        # Configuración de la aplicación
│   │       ├── 📁 static/                       # Recursos estáticos
│   │       │   └── 🎨 estilo.css
│   │       └── 📁 templates/                    # Plantillas Thymeleaf
│   │           ├── 🏠 home.html
│   │           ├── 🔑 login.html
│   │           ├── 📁 clientes/
│   │           └── 📁 usuarios/
│   └── 📁 test/                                 # Pruebas unitarias
└── 📁 target/                                   # Archivos compilados
```

## 🚀 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- ☕ **Java 21** o superior
- 🗄️ **MySQL 8.0** o superior
- 🏗️ **Maven 3.6** o superior
- 🆔 **IDE** (IntelliJ IDEA, Eclipse, VS Code)

## ⚙️ Configuración

### 1. Configuración de Base de Datos

1. Crear una base de datos MySQL:
```sql
CREATE DATABASE olpesa;
```

2. Configurar las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/olpesa
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 2. Configuración del Proyecto

1. Clonar el repositorio:
```bash
git clone https://github.com/ezerutp/venta.git
cd venta
```

2. Instalar dependencias:
```bash
mvn clean install
```

## 🏃‍♂️ Ejecución

### Opción 1: Usando Maven
```bash
mvn spring-boot:run
```

### Opción 2: Usando Java directamente
```bash
mvn clean package
java -jar target/venta-0.0.1-SNAPSHOT.jar
```

### Opción 3: Desde el IDE
Ejecutar la clase `VentaApplication.java`

La aplicación estará disponible en: http://localhost:8080

## 🧑‍💻 Datos de Prueba

El proyecto incluye datos de prueba generados automáticamente por las clases `DataSeeder` ubicadas en el paquete `seeders`. Estas clases inicializan la base de datos con información básica para facilitar el desarrollo y las pruebas.
### Usuarios de Prueba

Puedes iniciar sesión con los siguientes usuarios de prueba:

| Usuario   | Rol    | Contraseña |
|-----------|--------|------------|
| admin     | ADMIN  | 123456     |
| jdoe      | USER   | 123456     |

Estos usuarios están pre-cargados por el sistema para facilitar el acceso y pruebas de funcionalidades.

## 📊 Modelo de Datos

El sistema maneja las siguientes entidades principales:

- 👤 **Cliente** - Información de clientes
- 📦 **Producto** - Catálogo de productos
- 👨‍💻 **Usuario** - Usuarios del sistema
- 💰 **Venta** - Transacciones de venta

### Enumeraciones
- 📄 **EnumComprobante** - Tipos de comprobantes
- 🆔 **EnumDocumento** - Tipos de documentos de identidad
- 📊 **EnumEstadoVenta** - Estados de las ventas
- 💳 **EnumMetodoPago** - Métodos de pago
- 👥 **EnumRol** - Roles de usuario

## 🔐 Seguridad

El sistema implementa:

- 🔑 Autenticación basada en sesiones
- 🛡️ Autorización por roles
- 🔒 Protección CSRF
- 🚫 Protección de endpoints

## 🧪 Pruebas

Para ejecutar las pruebas:

```bash
mvn test
```

## 📈 Características de Desarrollo

- 🔄 **Hot Reload** con Spring Boot DevTools
- 📝 **Logging** configurado para desarrollo
- 🗄️ **DDL Auto** para recreación automática de esquemas
- 🎯 **Validaciones** en entidades y formularios

## 📝 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor
**Desarrollado por [Ezer Vidarte](https://github.com/ezerutp)**  
*Ingeniero de Sistemas | UTP 🇵🇪*  
[GitHub](https://github.com/ezerutp) | [LinkedIn](https://www.linkedin.com/in/ezervidarte/)

⭐ **¡No olvides dar una estrella al proyecto si te resulta útil!** ⭐