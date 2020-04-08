paquete  org.apache.cordova.firebase ;

importar  android.util.Log ;

import  com.google.firebase.iid.FirebaseInstanceId ;
importar  com.google.firebase.iid.FirebaseInstanceIdService ;

 clase  pública FirebasePluginInstanceIDService  extiende  FirebaseInstanceIdService {

    Private  static  final  String  TAG  =  " FirebasePlugin " ;

    / **
     * Se llama si se actualiza el token InstanceID. Esto puede ocurrir si la seguridad de
     * la ficha anterior había sido comprometida. Tenga en cuenta que esto se llama cuando el token InstanceID
     * se genera inicialmente, así que aquí es donde recuperarías el token.
     * /
    @Anular
    public  void  onTokenRefresh () {
        // Obtenga el token InstanceID actualizado.
        Cadena refreshedToken =  FirebaseInstanceId . getInstance () . getToken ();
        Registro . d ( TAG , " Token actualizado: "  + token actualizado);

        FirebasePlugin . sendToken (refreshedToken);
    }
}
