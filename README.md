# MeLi Challenge - Operación Fuego de Quasar

## Aplicacion web en Spring Boot + Jersey + Spring Data JDBC + PostgreSQL

### Configuracion
El servidor esta preparado para correrlo en ambiente dev rapidamente, solo necesitando modificar el application-dev.yml para configurar la conexion a la base de datos postgres local. El profile dev es el default. El mismo levanta por defecto en el puerto 8080.

Para el deploy, se incluye un application-prod.yml de ejemplo, y el app.yml utilizado para deployar en Google App Engine. El mismo configura de runtime a Java17, setea el profile de prod, y configura lo necesario para poder acceder a la base de datos de prod dentro de la subnet privada de la VPC de GCP.

### Estructura de la aplicacion
La aplicacion cuenta con 6 paquetes principales:
  - **config**: Clases de configuracion de la aplicacion (junto con MainWebapp), especialmente de Jersey
  - **models**: Los modelos de dominio de nuestra aplicacion. En particular solo contamos con Satellite y SatelliteCom.
  - **repositories**: La capa de persistencia de la aplicacion. Aqui vive Spring Data JDBC.
  - **resources**: Capa controller que se encarga de disponibilizar nuestra aplicacion web.
  - **service**: Capa de logica de negocio que hace de interface entre controller y repository.
  - **validation**: Un pequeño framework de validacion propio para ayudar en el manejo de errores y excepciones.
  
Como se puede ver la aplicacion posee una estructura de capas (Controller -> Service -> Repository). Si bien se respetaron las dependencias para evitar el acoplamiento, se opto por no dividir el proyecto en submodulos, para mantener una estructura mas simple.

### Trabajo futuro
Algunas cosas importantes que no se llegaron a implementar, o se decidieron fuera del scope del desafio son autenticacion, rate limiting y testing de integracion, especialmente de la capa de persistencia.

### Ejemplo de uso
Se proveen ejemplos de comandos que la aplicacion soporta.

1. Creamos los satelites con los que vamos a trabajar
```http
POST http://localhost:8080/satellites/kenobi HTTP/1.1
content-type: application/json

{
  "positionX": -500,
  "positionY": -200
}
```
```http
POST http://localhost:8080/satellites/skywalker HTTP/1.1
content-type: application/json

{
  "positionX": 100,
  "positionY": -100
}
```
```http
POST http://localhost:8080/satellites/sato HTTP/1.1
content-type: application/json

{
  "positionX": 500,
  "positionY": 100
}
```

2. Listamos los satelites recien creados
```http
GET http://localhost:8080/satellites HTTP/1.1
```

3. Resolvemos la posicion y el mensaje de una nave
```http
POST http://localhost:8080/topsecret HTTP/1.1
content-type: application/json

{
  "satellites": [
    {
      "name": "kenobi",
      "distance": 485.4122,
      "message": ["este", "", "", "mensaje", ""]
    },
    {
      "name": "skywalker",
      "distance": 265.7536,
      "message": ["", "es", "", "", "secreto"]
    },
    {
      "name": "sato",
      "distance": 600.5206,
      "message": ["este", "", "un", "", ""]
    }
  ]
}
```

Respuesta
```http
HTTP/1.1 200 OK

{
  "position": {
    "x": -99.99997978413711,
    "y": 74.99996362599751
  },
  "message": "este es un mensaje secreto"
}
```

4. Publicamos comunicaciones entre nave y satelites por separado
```http
POST http://localhost:8080/topsecret_split/kenobi HTTP/1.1
content-type: application/json

{
      "distance": 485.4122,
      "message": ["este", "", "", "mensaje", ""]
}
```
```http
POST http://localhost:8080/topsecret_split/skywalker HTTP/1.1
content-type: application/json

{
      "distance": 265.7536,
      "message": ["", "es", "", "", "secreto"]
}
```
```http
POST http://localhost:8080/topsecret_split/sato HTTP/1.1
content-type: application/json

{
      "distance": 600.5206,
      "message": ["este", "", "un", "", ""]
}
```

5. Listamos las comunicaciones recien publicadas
```http
GET http://localhost:8080/satelliteComs HTTP/1.1
```

6. Resolvemos la posicion y el mensaje de una nave utilizando la informacion de los satelites que creamos inicialmente
```http
GET http://localhost:8080/topsecret_split?satellites=kenobi&satellites=skywalker&satellites=sato HTTP/1.1
```

Respuesta
```http
HTTP/1.1 200 OK

{
  "position": {
    "x": -99.99928578175518,
    "y": 74.99520245659302
  },
  "message": "este es un mensaje secreto"
}
```

7. Eliminamos comunicaciones publicadas
```http
DELETE http://localhost:8080/satelliteComs HTTP/1.1
```

8. Eliminamos satelites creados
```http
DELETE http://localhost:8080/satellites HTTP/1.1
```

El servidor deberia haber quedado en el estado inicial
