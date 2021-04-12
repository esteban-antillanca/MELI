# MELI Android Development Test - Esteban Antillanca

## About the solution
Esta app consiste en dos vistas principales, listado de productos con búsqueda, y detalle de producto. La barra de búsqueda permite ingresar un texto, y los resultados se muestran luego en un listado. Cualquier elemento del listado puede ser clickeado para revisar sus detalle en una sección nueva.

### Implementation details
* Lenguaje: Se utilizó el Java standard para Android
* Design Pattern: Se usó el patrón de diseño MVP para la app. MVC es muy natural en el desarrollo mobile, y los ajustes que MVP presenta sobre MVC facilita un poco más la escritura de test unitarios, motivo por el cual se eligió este patrón de diseño. En conjunto con ello se implenentó un repositorio de datos como single source of truth, con una interfaz adecuada para permitir manejo de datos tanto remoto como local, aunque el scope the este ejemplo es sólo remoto.
*APIs: Se usaron los servicios de search, items y users provistos por Mercado Libre en https://developers.mercadolibre.com.
ar/es_ar/items-y-busquedas
* Librerías Principales:
	* Se usó [Junit](https://github.com/junit-team) y [Mockito](https://github.com/mockito/mockito) para unit testing, y [Espresso](https://developer.android.com/training/testing/espresso/) para instrumentation test.
	* [Retrofit](https://github.com/square/retrofit) para el consumo de las API's
	* [Glide](https://github.com/bumptech/glide) para el manejo de imágenes e implementación de lazy loading
	* [Android Image Slider](https://github.com/smarteist/Android-Image-Slider) para la presentación de múltiples imágenes en el detalle de un producto.



## Authors

* **Esteban Antillanca**
