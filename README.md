# Proyecto Intermodular - Sistema de reservas

Azahara Gracia Maciá, 1ªDAW

## Descripción

Sistema de reservas genérico.

## Estructura del Proyecto

- `database/`: Scripts y herramientas para la gestión de la base de datos.
- `docs/`: Documentación del proyecto.
- `src/`: Código fuente de la aplicación.

## Decisiones html consultora

El type="email" solo valida que haya un @, pero no que ponga el '.com' u otro dominio, asi que intenté añadir el pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" para una validación más estricta, pero no funciona, al parecer está roto. Podría quitar el type y dejar el pattern, pero como en el ejercicio especificaba que pusiéramos el type, lo he dejado así.

## Decisiones CSS consultora

Creé el logo a partir de una IA generadora de imágenes y decidí adaptar la paleta de colores a los del logo.
