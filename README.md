# Proyecto Intermodular - Sistema de reservas

Azahara Gracia Maciá, 1ªDAW


## Descripción

Sistema de reservas genérico.


## Qué es y para qué sirve

Este proyecto es un CRUD con JDBC para una base de datos MariaDB.

Con la aplicación de consola se pueden insertar, modificar o eliminar usuarios, horarios, recursos, disponibilidades y reservas, así como consultar los datos de los mismos.


## Estructura del Proyecto

- `database/`: Scripts y herramientas para la gestión de la base de datos.
- `docs/`: Documentación del proyecto.
- `src/`: Código fuente de la aplicación.


## Diseño

La aplicación está diseñada con un patrón modelo, vista, controlador para una vista desde consola


## Estructura del código fuente

```
app/            -> Capa donde se sitúa la clase Main, que inicia la aplicación.

controller/     -> Capa de controladores, que se encarga de unir y controlar al resto

dao/            -> Capa de "Data Access Object", que interactúa directamente con la BD

model/          -> Capa de declaración de entidades

view/           -> Capa de vistas, donde están los métodos para interactuar con la consola
```
