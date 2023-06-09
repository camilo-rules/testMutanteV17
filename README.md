# Título: Detector de Mutantes
Examen MELI, construir solución para detectar si una persona es mutante o no a través de una cadena ADN. Solución realizada en Java v17 e implementada en AWS Lambda.
Adicional contiene 2 servicios REST implementados en AWS API Gateway
y una BD en AWS RDS con motor Mysql.

# Estado del proyecto:
Totalmente implementado 

# Requisitos previos:

1. Java Development Kit (JDK) 17 instalado en tu sistema.
2. Maven instalado en tu sistema.
3. Acceso y credenciales para AWS (para la conexión a RDS y la configuración de la función Lambda y API Gateway).

# Pasos de instalación:

Clona o descarga el proyecto desde el repositorio de GitHub.

Asegúrate de tener el archivo JAR "testMutanteV17.jar" disponible en tu sistema.

Abre una terminal o línea de comandos y navega hasta el directorio del proyecto.

Ejecuta el siguiente comando para compilar y empaquetar el proyecto:

$ mvn clean package

Esto generará un archivo JAR con las dependencias en el directorio target/. Verifica que el archivo JAR "testMutanteV17.jar" se haya generado correctamente.

Configura y crea una base de datos MySQL en AWS RDS llamada "RESULTMUTANTES".

Ejecuta los scripts necesarios para crear la tabla "adn_resultado" en la base de datos. Asegúrate de que la tabla tenga las columnas "adn" (como String primary key) y "esMutante" (como booleano).

Configura dos funciones Lambda "PruebaMutante" y "StatusMutante" en AWS, ambas con el archivo JAR "testMutanteV17.jar" como código fuente.

Configura tu API Gateway "Mutante_API" en AWS con los métodos /pruebaMutante (POST) y /statusMutante (GET). Asocia la función Lambda "PruebaMutante" para el método POST, y asocia la función lambda "StatusMutante" para el método GET.

Verifica que tu API Gateway esté correctamente configurado para recibir solicitudes y enrutarlas a tu función Lambda.

Asegúrate de que tu proyecto tenga las credenciales de AWS configuradas adecuadamente para acceder a RDS y otros servicios de AWS, según las necesidades.

Una vez completados estos pasos, tu proyecto debería estar configurado y listo para funcionar. Puedes probar el endpoint /pruebaMutante con un array de Strings y el endpoint /statusMutante para obtener la respuesta en formato JSON con la información de mutantes, no mutantes y ratio.

URL pruebaMutante: https://eoa0h868wd.execute-api.us-east-1.amazonaws.com/testing/pruebaMutante
URL statusMutante: https://eoa0h868wd.execute-api.us-east-1.amazonaws.com/testing/statusMutante

# Documentación de API

1. Recurso '/pruebaMutante' (Método POST):
  
  Descripción: Endpoint utilizado para probar si una cadena de ADN corresponde a un mutante o no.
  
  Parámetros de entrada:
    Array de Strings: Un array que contiene la secuencia de ADN a analizar.
 
 Ejemplo de solicitud:
    POST /pruebaMutante
      Content-Type: application/json
      {
        "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
      }
  
  Ejemplo de respuesta (para un ADN mutante):
     HTTP/1.1 200 OK
     Content-Type: application/json
      {
        "statusCode": 200,
        "response": "El humano no es mutante"
      }
      
2. Recurso '/statusMutante' (Método GET):
   
   Descripción: Endpoint utilizado para obtener el estado actual del análisis de ADN.
  
  Parámetros de entrada: No requiere parámetros.
   
   Ejemplo de solicitud:
     GET /statusMutante
   
   Ejemplo de respuesta:
    HTTP/1.1 200 OK
    Content-Type: application/json
      {
        "statusCode": 200,
        "response": "{\"count_mutant_dna\":1,\"count_human_dna\":4,\"ratio\":0.25}"
      }
  
 3. Header requerido:
    
    Content-Type: application/json
    
    Descripción: Encabezado que indica el tipo de contenido del cuerpo de la solicitud o respuesta, que en este caso debe   ser JSON.
    
# Nota:
Para ejecutar las API´s y validar su funcionamiento se debe utilizar una herramienta de diseño e implementación de API´s como Postman, Insomnia, SoapUI, entre otras, y configurar el header y cuerpo de mensaje.
    
