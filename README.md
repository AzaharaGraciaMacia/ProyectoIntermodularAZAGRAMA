# Proyecto Intermodular - Sistema de reservas

Azahara Gracia Maciá, 1ªDAW

## Descripción

Sistema de reservas genérico.

## Estructura del Proyecto

- `database/`: Scripts y herramientas para la gestión de la base de datos.
- `docs/`: Documentación del proyecto.
- `src/`: Código fuente de la aplicación.

## Decisiones y problemas encontrados en html consultora

- El type="email" en los input solo valida que haya un @, pero no que ponga el '.com' u otro dominio, asi que intenté añadir el pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" para una validación más estricta, pero no funciona, al parecer está roto. Podría quitar el type y dejar el pattern, pero como en el ejercicio especificaba que pusiéramos el type, lo he dejado así.
- He hecho dos páginas con textos legales generados por IA para tener enlaces a sitios propios también en el footer.
- Para intentar que la web sea lo más accesible posible, decidí dejar la tabla de la cronología con sus etiquetas html (y maquetar con flexbox), y a cambio he hecho una tabla con grid en la página de política de privacidad.

## Decisiones y problemas CSS consultora

- Creé el logo a partir de una IA generadora de imágenes y decidí adaptar la paleta de colores a los del logo, cuidando no mezclar tonalidades rojas y verdes.
- Declaré las variables de color y fuente en :root
- Añadí efecto parallax a la pantalla index y maqueté la galería de servicio con flexbox.
- Para la web del proyecto, maqueté el aside como un barra lateral y usé flex-grow para que mantuviese una proporción con la sección principal.
- En la cronología, intenté usar grid pensando que funcionaría también con la estructura de la tabla html, pero tras ver que no funcionaba y buscar más info, preferí maquetar con flexbox para que los lectores de pantalla sigan teniendo la información de la tabla.
- Maqueté el formulario de contacto siguiendo el estilo del resto de páginas.
- Cambié a distintos tamaños de pantalla y empecé a arreglar cada página para que fuera todo lo responsive posible y se viera bien tanto en vertical como en horizontal.
- Por último maqueté de forma más plana las dos páginas de textos legales, y probé el CSS grid en una tabla improvisada dentro de la política de privacidad. No me acaba de gustar CSS grid, en general, prefiero flexbox. Es cierto que quizás con CSS grid hacer una tabla responsive habría sido más fácil, pero creo que sería menos accesible.
