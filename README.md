Propósito y Alcance


Este documento ofrece una descripción técnica del repositorio TiendaDeTLOZ, una aplicación de escritorio JavaFX que implementa un sistema de gestión de tiendas basado en la leyenda de Zelda. La aplicación permite a los jugadores comprar y vender artículos con rupias (moneda del juego) mediante almacenamiento persistente SQLite.


Descripción del Sistema


TiendaDeTLOZ es una aplicación de escritorio desarrollada con JavaFX 24.0.1 que simula una tienda de artículos del universo de La Leyenda de Zelda. Los jugadores interactúan con una interfaz gráfica para comprar artículos (armas, pociones) con rupias y vender artículos de su inventario a la tienda. La aplicación mantiene un estado persistente a través de una base de datos SQLite integrada y sigue el patrón arquitectónico Modelo-Vista-Controlador (MVC) con implementaciones de Objetos de Acceso a Datos (DAO) para las operaciones de la base de datos.

Fuentes:
src/main/java/utch/edu/mx/tiendadetloz/HelloApplication.java
21


Arquitectura de la aplicación


La aplicación implementa una arquitectura en capas con una clara separación de las capas de presentación, lógica de negocio y persistencia de datos.


La clase HelloApplication sirve como punto de entrada de la aplicación JavaFX e inicializa la base de datos mediante Database.main(). El HelloController coordina las interacciones del usuario y delega las operaciones de datos a clases DAO especializadas (ItemDAO, JugadorDAO, InventoryDAO) que encapsulan las operaciones SQL en la base de datos SQLite TiendaDeTLOZ.db.

Fuentes:
src/main/java/utch/edu/mx/tiendadetloz/HelloApplication.java


src/main/java/utch/edu/mx/tiendadetloz/HelloController.java

Arquitectura de Persistencia de Datos
La aplicación utiliza el patrón de Objeto de Acceso a Datos (DAO) para abstraer las operaciones de la base de datos y mantener una clara separación entre la lógica de negocio y el almacenamiento de datos.

El esquema de la base de datos consta de tres tablas principales: items, que contiene el inventario de la tienda con información de precios y existencias; player, que mantiene el saldo en rupias del jugador; y player_inventory, que rastrea los artículos propiedad del jugador. Cada clase DAO proporciona métodos con seguridad de tipos que encapsulan las operaciones SQL y gestionan la conexión a la base de datos.

Fuentes: TiendaDeTLOZ.db
src/main/java/utch/edu/mx/tiendadetloz/HelloController.java


Arquitectura del flujo de transacciones
La aplicación implementa operaciones transaccionales para la compra y venta de artículos que mantienen la consistencia de los datos en múltiples tablas de la base de datos.

Tanto las operaciones de compra como las de venta requieren actualizaciones coordinadas en tres tablas de la base de datos. Los métodos HelloController.onBuyClick() y HelloController.onSellClick() orquestan estas transacciones de varios pasos, aunque la implementación actual carece de una gestión explícita de transacciones para garantizar la atomicidad.

Fuentes:
src/main/java/utch/edu/mx/tiendadetloz/HelloController.java


src/main/java/utch/edu/mx/tiendadetloz/HelloController.java


Sistema de compilación y módulos
La aplicación utiliza Maven para la gestión de dependencias y la automatización de la compilación, con el Sistema de Módulos de la Plataforma Java (JPMS) para la arquitectura modular.

El archivo module-info.java declara dependencias explícitas en los módulos javafx.controls, javafx.fxml y java.sql, mientras que pom.xml gestiona las dependencias externas, incluyendo JavaFX 24.0.1 y el controlador JDBC de SQLite 3.50.3.0. El complemento del compilador de Maven utiliza Java 24 como versión de origen y de destino.
