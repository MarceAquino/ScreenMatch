# ScreenMatch - Buscador de Películas

**ScreenMatch** es una aplicación Java que permite a los usuarios buscar películas mediante la API de [OMDb](https://www.omdbapi.com/), convertir los resultados JSON en objetos de Java y guardar las películas buscadas en un archivo `titulos.json`.

## Características

- **Búsqueda de películas**: Introduce el nombre de una película y obtén información como el título, año de lanzamiento y duración.
- **Conversión a JSON**: Los resultados se almacenan en un archivo JSON (`titulos.json`) para que puedas revisarlos más tarde.
- **Gestión de errores**: El programa maneja errores relacionados con la conversión de datos o problemas de conexión a la API.
  
## Instalación

1. Clona este repositorio en tu máquina local:

   ```bash
   git clone https://github.com/MarceAquino/ScreenMatch.git
   ```

2. Importa el proyecto en tu IDE favorito (IntelliJ IDEA, Eclipse, NetBeans, etc.).

3. Asegúrate de tener configurado **Java 17** o superior en tu sistema.

4. Incluye las siguientes dependencias en tu proyecto:
   - **Gson** para trabajar con el formato JSON.
   - **HttpClient** para manejar las solicitudes HTTP.
   
   En caso de usar **Maven**, asegúrate de tener las siguientes dependencias en tu archivo `pom.xml`:

   ```xml
   <dependencies>
       <dependency>
           <groupId>com.google.code.gson</groupId>
           <artifactId>gson</artifactId>
           <version>2.8.8</version>
       </dependency>
   </dependencies>
   ```

## Uso

1. Ejecuta el programa `PrincipalConBusqueda.java`.
2. Escribe el nombre de una película en la consola para realizar la búsqueda.
3. El programa te mostrará los detalles de la película en la consola.
4. Cuando termines, escribe `salir` para finalizar el programa.
5. Todas las películas que busques se guardarán en el archivo `titulos.json`.

### Ejemplo de uso

```
Escriba el nombre de una película (o 'salir' para terminar): Matrix

Respuesta JSON de la API:
{
  "Title": "The Matrix",
  "Year": "1999",
  "Runtime": "136 min",
  "Genre": "Action, Sci-Fi",
  ...
}

Título convertido con éxito: (nombre=The Matrix, fechaDeLanzamiento=1999, duracion=136)

Escriba el nombre de una película (o 'salir' para terminar): salir

Películas almacenadas:
(nombre=The Matrix, fechaDeLanzamiento=1999, duracion=136)
```

## Estructura del proyecto

```
src/
│
├── com/alura/screenmatch/excepcion
│   └── ErrorEnConversionDeDuracionException.java
│
├── com/alura/screenmatch/modelos
│   ├── Titulo.java
│   └── TituloOmdb.java
│
├── com/alura/screenmatch/principal
│   └── PrincipalConBusqueda.java
│
└── titulos.json   # Archivo donde se guardan las películas buscadas.
```

## Personalización

Puedes modificar el código para cambiar la clave de la API de OMDb directamente en la clase `PrincipalConBusqueda`:

```java
String direccion = "https://www.omdbapi.com/?t=" + busqueda.replace(" ", "+") + "&apikey=TU_API_KEY";
```

Obtén tu propia clave en [OMDb API](https://www.omdbapi.com/apikey.aspx).

## Contribuciones

Si quieres contribuir a este proyecto, por favor, abre un issue o crea un pull request.
