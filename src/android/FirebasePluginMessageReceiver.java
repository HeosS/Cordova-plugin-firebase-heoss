paquete  org.apache.cordova.firebase ;

importar  com.google.firebase.messaging.RemoteMessage ;

 clase abstracta  pública FirebasePluginMessageReceiver { 

    public  FirebasePluginMessageReceiver () {
        FirebasePluginMessageReceiverManager . registrarse ( esto );
    }

    / **
     * Las subclases concretas deberían anular esto y devolver verdadero si manejan el mensaje recibido.
     * *
     * @param remoteMessage
     * @return true si el mensaje recibido fue manejado por el receptor, por lo que FirebasePlugin no debería manejarlo.
     * /
     resumen  público booleano  onMessageReceived ( RemoteMessage  remoteMessage );
}
