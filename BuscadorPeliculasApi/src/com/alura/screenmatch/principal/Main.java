package com.alura.screenmatch.principal;

import com.alura.screenmatch.excepcion.ErrorEnConversionDeDuracionException;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal para realizar búsquedas de películas a través de la API de OMDb,
 * convertir la respuesta a objetos de tipo Titulo y guardar los resultados en un archivo JSON.
 *
 * El usuario puede realizar múltiples búsquedas y se muestran resultados claros en la consola.
 * Si la búsqueda contiene un error, se manejarán las excepciones apropiadamente.
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Inicializar el Scanner para la entrada del usuario
        Scanner lectura = new Scanner(System.in);
        // Lista para almacenar los títulos convertidos
        List<Titulo> titulos = new ArrayList<>();

        // Configuración de Gson para parsear los datos JSON
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        // Bucle principal para realizar las búsquedas de películas
        while (true) {
            // Pedir al usuario que ingrese el nombre de una película
            System.out.print("Escriba el nombre de una película (o 'salir' para terminar): ");
            var busqueda = lectura.nextLine();

            // Verificar si el usuario quiere salir
            if (busqueda.equalsIgnoreCase("salir")) {
                break;
            }

            // Formar la URL para la búsqueda en la API de OMDb
            String direccion = "https://www.omdbapi.com/?t=" +
                    busqueda.replace(" ", "+") +
                    "&apikey=f038ee93";

            try {
                // Crear un cliente HTTP y enviar la solicitud
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                // Obtener el cuerpo de la respuesta en formato JSON
                String json = response.body();
                System.out.println("\nRespuesta JSON formateada de la API:");
                System.out.println(gson.toJson(gson.fromJson(json, Object.class)));

                // Convertir el JSON en un objeto TituloOmdb
                TituloOmdb miTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println("\nDatos convertidos del JSON:");
                System.out.println(miTituloOmdb);

                // Intentar convertir el TituloOmdb en un Titulo válido
                try {
                    Titulo miTitulo = new Titulo(miTituloOmdb);
                    System.out.println("Título convertido con éxito: " + miTitulo);
                    // Agregar el título a la lista
                    titulos.add(miTitulo);
                } catch (ErrorEnConversionDeDuracionException e) {
                    // Mostrar el mensaje de error si no se pudo convertir la duración
                    System.out.println("Error al convertir la duración: " + e.getMessage());
                }

            } catch (NumberFormatException e) {
                // Mostrar mensaje de error si ocurre un problema de formato de número
                System.out.println("Ocurrió un error de formato numérico: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                // Mostrar mensaje si hay un error en la URI
                System.out.println("Error en la URI, verifique la dirección.");
            } catch (IOException | InterruptedException e) {
                // Capturar errores de red o interrupciones
                System.out.println("Error de conexión o interrupción: " + e.getMessage());
            }
        }

        // Mostrar todos los títulos almacenados
        System.out.println("\nPelículas almacenadas:");
        for (Titulo titulo : titulos) {
            System.out.println(titulo);
        }

        // Guardar los títulos en un archivo JSON
        try (FileWriter escritura = new FileWriter("titulos.json")) {
            escritura.write(gson.toJson(titulos));
            System.out.println("\nLos títulos se han guardado exitosamente en 'titulos.json'.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }

        // Finalización del programa
        System.out.println("\nFinalizó la ejecución del programa!");
    }
}
