# IntergrupoContacts
Proyecto Contacts Java.

username = esteban0721@hotmail.com
password = verde0721

Estas credenciales serán necesarias para acceder al aplicativo móvil.

El proyecto está montado en un servidor de Amazon que tiene la siguiente URL:
52.5.56.177:8080/Contacts/

Los servicios Web que se pueden usar son los siguientes:

52.5.56.177:8080/Contacts/rest/contacts

Devuelve la lista de contactos alojados en el servidor

52.5.56.177:8080/Contacts/rest/contacts/id

Devuelve un contacto específicado por el ID

52.5.56.177:8080/Contacts/rest/users/login?username=yourUserName&password=yourPassword

Es el servicio encargado de validar el usuario y contraseña al inicio del aplicativo

52.5.56.177:8080/Contacts/rest/users/updatepass?username=yourUserName&oldPassword=yourActualPassword&newPassword=yourNewPassword

Este servicio no tiene entorno gráfico en el aplicativo, pero es un método GET que puede ser llamado desde el navegador para modificar la contraseña actual.


Si se desea ejecutar el proyecto java, debe copiarse el archivo users.json al escritorio de windows en el que esté trabajando.
Además deberá cambiar en los Strings de android la clave str_rest_server por la ip en la que lo esté ejecutando.
