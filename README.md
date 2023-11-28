# Microservicio de usuario

### Descripcion
Este servicio se encagar de gestionar la informacion del usuario en la aplicacion.
Proporciona operaciones relacionadas con la creacion, login, obtencion y actualizacion
de usuarios

### Caracteristicas Principales

* Registra un usuario: Permite registrar nuevos usuarios
* login de uusuario: Proporciona la autenticacion y el token
* Consulta de usuarios: Proporciona informacion de los usuarios
* consulta por id: Permite obtener informacion del usuario por id
* Actualizacion de usuario: Permite modificar la informacion del usuario

### Uso
En el proyecto se encuentra el collection con nombre de archivo Usuario.postman_collection.json 
el cual puede ser importado con el postman

#### Registra un usuario
Endpoint POST /auth/signup
```json
{
  "name": "wilmerleonel",
  "email": "wilmerleonel@dominio.cl",
  "password": "leonel12345",
  "phones": [
    {
      "number": 1234567,
      "citycode": 1,
      "contrycode": 57
    }
  ]
}
```

#### login de uusuario
Endpoint POST /auth/login
```json
{
"email": "wilmerleonel@dominio.cl",
"password": "leonel12345"
}
```

#### Consulta de usuarios
Endpoint GET /v1/user .<br>
Authorization: Bearer tu_token

#### consulta por id
Endpoint GET /v1/user/{id} .<br>
Authorization: Bearer tu_token

#### Actualizacion de usuario
Endpoint PUT /v1/user/{id} .<br>
Authorization: Bearer tu_token
```json
{
  "name": "string",
  "email": "user@example.com"
}
```

### Requsitos Previos
Asegurese de tener instalado el jdk 17 o en el IDE hacer referencia
al archivo
