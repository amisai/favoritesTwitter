Introducción
Soy un lurker, lo reconozco. Y lo de estar al día de páginas e ideas en Twitter es complicado. No siempre tienes tiempo para visitar los enlaces que te sugieren, no puedes copiar la idea que acabas de leer... Así que empiezas a marcar twits como favoritos, esperando encontrar el momento para revisarlos, pero no siempre es posible, y la tarea manual es bastante aburrida, así que necesito una aplicación para recuperar de manera automática los favoritos que voy marcando en Twitter. Y así surgió esta aplicación java, aprovechando twitter4j y algo de tiempo libre.

La aplicación recupera todos los favoritos de tu cuenta de Twitter, borrandolos como favoritos, y enviandolo en un email.

Configuración de la aplicación
Son necesarios 2 ficheros de configuración, uno contiene la información de la(s) cuenta(s) de correo desde/a la que enviarás el correo resumen, y otro que contendrá información para acceder a tu cuenta de twitter.

El fichero de configuración del correo se llama email.props. Por razones obvias, no proporciono mi email.props, sino un ejemplo. El fichero está en src/test/resources/email.props_sample, deberás rellenarlo y renombrarlo a email.props.

El fichero de configuración de la cuenta de twitter se llama twitter4j.properties. Como en el caso anterior, no proporciono mi twitter4j.properties, sino un ejemplo. El fichero está en src/test/resources/twitter4j.properties_sample. Deberás renombrarlo a twitter4j.properties y seguir los siguientes pasos:

Lo primero que hay que hacer es registrar la aplicación en Twitter (https://dev.twitter.com/apps/new),dando todos los datos necesarios (nombre, descripción,...) Es importante conceder permisos de lectura y escritura.

Una vez que tenemos consumer key y consumer secret, los escribimos en twitter4j.properties y ejecutamos 'mvn assembly:assembly', con lo que obtendremos un fichero .jar en target.

Al ejecutar dicho fichero con 'java -cp target/FavoritesTwitter-0.1-SNAPSHOT-jar-with-dependencies.jar  org.okiju.favoritestwitter.OAuthSetup' se nos proporcionara una URL, que ...

con esto habremos conseguido un accessToken, que guardaremos en el fichero twitter4j.properties que hemos usado antes.

Ademas, es necesario añadir la dirección de correo en la que se desea recibir el email con los favoritos recuperados.

Para invocar a la aplicación será necesario 'java -jar ...'. Si todo el proceso es correcto (sin problemas de red,...) a aplicación generara un fichero con los favoritos recogidos y enviara un email con la misma información.
