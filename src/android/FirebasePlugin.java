paquete  org.apache.cordova.firebase ;

importar  android.app.NotificationManager ;
importar  android.content.ComponentName ;
importar  android.content.Context ;
importar  android.content.Intent ;
importar  android.content.SharedPreferences ;
importar  android.os.Bundle ;
importar  android.support.v4.app.NotificationManagerCompat ;
importar  android.util.Base64 ;
importar  android.util.Log ;

importar  com.crashlytics.android.Crashlytics ;
importar  io.fabric.sdk.android.Fabric ;
import  java.lang.reflect.Field ;
importar  com.google.android.gms.tasks.OnFailureListener ;
importar  com.google.android.gms.tasks.OnSuccessListener ;
importar  com.google.android.gms.tasks.Task ;
importar  com.google.firebase.FirebaseApp ;
importar  com.google.firebase.analytics.FirebaseAnalytics ;
import  com.google.firebase.iid.FirebaseInstanceId ;
importar  com.google.firebase.messaging.FirebaseMessaging ;
importar  com.google.firebase.remoteconfig.FirebaseRemoteConfig ;
importar  com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo ;
importar  com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings ;
importar  com.google.firebase.remoteconfig.FirebaseRemoteConfigValue ;
importar  com.google.firebase.perf.FirebasePerformance ;
importar  com.google.firebase.perf.metrics.Trace ;

import  me.leolin.shortcutbadger.ShortcutBadger ;

import  org.apache.cordova.CallbackContext ;
import  org.apache.cordova.CordovaPlugin ;
import  org.apache.cordova.PluginResult ;
import  org.apache.cordova.CordovaWebView ;
import  org.json.JSONArray ;
import  org.json.JSONException ;
import  org.json.JSONObject ;

import  java.util.ArrayList ;
importar  java.util.HashMap ;
import  java.util.Iterator ;
import  java.util.Map ;
import  java.util.Set ;

// Firebase PhoneAuth
import  java.util.concurrent.TimeUnit ;

importar  com.google.firebase.auth.FirebaseAuth ;
importar  com.google.firebase.auth.AuthResult ;
importar  com.google.firebase.FirebaseException ;
importar  com.google.firebase.auth.FirebaseAuthException ;
importar  com.google.firebase.auth.FirebaseAuthInvalidCredentialsException ;
import  com.google.firebase.FirebaseTooManyRequestsException ;
importar  com.google.firebase.auth.PhoneAuthCredential ;
importar  com.google.firebase.auth.PhoneAuthProvider ;

 FirebasePlugin de clase  pública extiende CordovaPlugin {  

     FirebaseAnalytics privado mFirebaseAnalytics;
    aplicación  estática  privada CordovaWebView;
    Private  final  String  TAG  =  " FirebasePlugin " ;
     estática  final  protegida String  KEY  =  " insignia " ;

    privado  booleano estático  inBackground = true ; 
     ArrayList estático  privado < Bundle > notifyStack =  null ;
     notificación estática  privada de CallbackContextCallbackContext ;
     tokenRefreshCallbackContext estático  privado CallbackContext ;

    @Anular
     plugin vacío  protegidoInicializar () {
         Contexto final contexto =  esto . Cordova . getActivity () . getApplicationContext ();
         Paquete final extras =  esto . Cordova . getActivity () . getIntent () . getExtras ();
        esta . Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                Registro . d ( TAG , " Iniciando el complemento de Firebase " );
                FirebaseApp . initializeApp (contexto);
                mFirebaseAnalytics =  FirebaseAnalytics . getInstance (contexto);
                mFirebaseAnalytics . setAnalyticsCollectionEnabled ( true );
                if (extras ! =  nulo  && extras . size () >  1 ) {
                    if ( FirebasePlugin . notificationStack ==  null ) {
                        FirebasePlugin . notifyStack =  new  ArrayList < Bundle > ();
                    }
                    if (extras . contieneKey ( " google.message_id " )) {
                        extras . putBoolean ( " tap " , verdadero );
                        notificaciónStack . agregar (extras);
                    }
                }
            }
        });
    }

    @Anular
    pública  boolean  ejecutar ( Cadena  acción , JSONArray  args , CallbackContext  callbackContext ) lanza  JSONException {
        if (action . equals ( " getInstanceId " )) {
            esta . getInstanceId (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " getId " )) {
            esta . getId (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " getToken " )) {
            esta . getToken (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " hasPermission " )) {
            esta . hasPermission (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " setBadgeNumber " )) {
            esta . setBadgeNumber (callbackContext, args . getInt ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " getBadgeNumber " )) {
            esta . getBadgeNumber (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " suscribirse " )) {
            esta . suscribirse (callbackContext, args . getString ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " cancelar suscripción " )) {
            esta . cancelar suscripción (callbackContext, args . getString ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " cancelar registro " )) {
            esta . anular el registro (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " onNotificationOpen " )) {
            esta . onNotificationOpen (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " onTokenRefresh " )) {
            esta . onTokenRefresh (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " logEvent " )) {
            esta . logEvent (callbackContext, args . getString ( 0 ), args . getJSONObject ( 1 ));
            volver  verdadero ;
        } else  if (action . equals ( " logError " )) {
            esta . logError (callbackContext, args . getString ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " setCrashlyticsUserId " )) {
            esta . setCrashlyticsUserId (callbackContext, args . getString ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " setScreenName " )) {
            esta . setScreenName (callbackContext, args . getString ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " setUserId " )) {
            esta . setUserId (callbackContext, args . getString ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " setUserProperty " )) {
            esta . setUserProperty (callbackContext, args . getString ( 0 ), args . getString ( 1 ));
            volver  verdadero ;
        } else  if (action . equals ( " enableFetched " )) {
            esta . enableFetched (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " fetch " )) {
            if (argumentos . longitud () >  0 ) {
                esta . fetch (callbackContext, args . getLong ( 0 ));
            } más {
                esta . buscar (callbackContext);
            }
            volver  verdadero ;
        } else  if (action . equals ( " getByteArray " )) {
            if (argumentos . longitud () >  1 ) {
                esta . getByteArray (callbackContext, args . getString ( 0 ), args . getString ( 1 ));
            } más {
                esta . getByteArray (callbackContext, args . getString ( 0 ), nulo );
            }
            volver  verdadero ;
        } else  if (action . equals ( " getValue " )) {
            if (argumentos . longitud () >  1 ) {
                esta . getValue (callbackContext, args . getString ( 0 ), args . getString ( 1 ));
            } más {
                esta . getValue (callbackContext, args . getString ( 0 ), nulo );
            }
            volver  verdadero ;
        } else  if (action . equals ( " getInfo " )) {
            esta . getInfo (callbackContext);
            volver  verdadero ;
        } else  if (action . equals ( " setConfigSettings " )) {
            esta . setConfigSettings (callbackContext, args . getJSONObject ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " setDefaults " )) {
            if (argumentos . longitud () >  1 ) {
                esta . setDefaults (callbackContext, args . getJSONObject ( 0 ), args . getString ( 1 ));
            } más {
                esta . setDefaults (callbackContext, args . getJSONObject ( 0 ), null );
            }
            volver  verdadero ;
        } else  if (action . equals ( " verificar número de teléfono " )) {
            esta . Verificar número de teléfono (callbackContext, args . getString ( 0 ), args . getInt ( 1 ));
            volver  verdadero ;
        } else  if (action . equals ( " startTrace " )) {
            esta . startTrace (callbackContext, args . getString ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " incrementCounter " )) {
            esta . incrementCounter (callbackContext, args . getString ( 0 ), args . getString ( 1 ));
            volver  verdadero ;
        } else  if (action . equals ( " stopTrace " )) {
            esta . stopTrace (callbackContext, args . getString ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " setAnalyticsCollectionEnabled " )) {
            esta . setAnalyticsCollectionEnabled (callbackContext, args . getBoolean ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " setPerformanceCollectionEnabled " )) {
            esta . setPerformanceCollectionEnabled (callbackContext, args . getBoolean ( 0 ));
            volver  verdadero ;
        } else  if (action . equals ( " clearAllNotifications " )) {
            esta . clearAllNotifications (callbackContext);
            volver  verdadero ;
        }

        devuelve  falso ;
    }

    @Anular
    public  void  onPause ( multitarea booleana  ) {
        FirebasePlugin . inBackground =  true ;
    }

    @Anular
    public  void  onResume ( multitarea booleana  ) {
        FirebasePlugin . inBackground =  falso ;
    }

    @Anular
    public  void  onReset () {
        FirebasePlugin . notifyCallbackContext =  nulo ;
        FirebasePlugin . tokenRefreshCallbackContext =  nulo ;
    }

    @Anular
    public  void  onDestroy () {
        súper . onDestroy ();

        if ( this . appView ! =  null ) {
            appView . handleDestroy ();
        }
    }

     nulo  privado onNotificationOpen ( final  CallbackContext  callbackContext ) {
        FirebasePlugin . notifyCallbackContext = callbackContext;
        si ( FirebasePlugin . notificationStack ! =  nula ) {
            para ( paquete de paquetes :  FirebasePlugin . notifyStack ) {
                FirebasePlugin . sendNotification (paquete, este . cordova . getActivity () . getApplicationContext ());
            }
            FirebasePlugin . notificaciónStack . claro();
        }
    }

     nulo  privado onTokenRefresh ( final  CallbackContext  callbackContext ) {
        FirebasePlugin . tokenRefreshCallbackContext = callbackContext;

        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    Cadena currentToken =  FirebaseInstanceId . getInstance () . getToken ();
                    if (currentToken ! =  null ) {
                        FirebasePlugin . sendToken (currentToken);
                    }
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    public  static  void  sendNotification ( paquete de  paquete , contexto de  contexto ) {
        if ( ! FirebasePlugin . hasNotificationsCallback ()) {
            Cadena packageName = contexto . getPackageName ();
            if ( FirebasePlugin . notificationStack ==  null ) {
                FirebasePlugin . notifyStack =  new  ArrayList < Bundle > ();
            }
            notificaciónStack . agregar (paquete);

            volver ;
        }
         CallbackContext final callbackContext =  FirebasePlugin . notifyCallbackContext;
        if (callbackContext ! =  null  && bundle ! =  null ) {
            JSONObject json =  new  JSONObject ();
            Establecer < String > keys = bundle . juego de llaves();
            para ( Clave de cadena : teclas) {
                prueba {
                    JSON . put (clave, paquete . get (clave));
                } catch ( JSONException e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                    volver ;
                }
            }

            PluginResult pluginresult =  nuevo  PluginResult ( PluginResult . Estado . OK , json);
            resultado del complemento . setKeepCallback ( verdadero );
            callbackContext . sendPluginResult (pluginresult);
        }
    }

    public  static  void  sendToken ( token de cadena  ) {
        if ( FirebasePlugin . tokenRefreshCallbackContext ==  null ) {
            volver ;
        }

         CallbackContext final callbackContext =  FirebasePlugin . tokenRefreshCallbackContext;
        if (callbackContext ! =  null  && token ! =  null ) {
            PluginResult pluginresult =  nuevo  PluginResult ( PluginResult . Estado . OK , token);
            resultado del complemento . setKeepCallback ( verdadero );
            callbackContext . sendPluginResult (pluginresult);
        }
    }

    public  static  boolean  inBackground () {
        devuelve  FirebasePlugin . en el fondo;
    }

    public  static  boolean  hasNotificationsCallback () {
        devuelve  FirebasePlugin . notifyCallbackContext ! =  nulo ;
    }

    @Anular
    public  void  onNewIntent ( Intención de  intención ) {
        súper . onNewIntent (intento);
         Datos del paquete final = intención . getExtras ();
        if (data ! =  null  && data . usesKey ( " google.message_id " )) {
            los datos . putBoolean ( " tap " , verdadero );
            FirebasePlugin . sendNotification (datos, esto . cordova . getActivity () . getApplicationContext ());
        }
    }

    // DEPRECTED - alias de getToken
    privado  vacío  getInstanceId ( final  CallbackContext  callbackContext ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    Token de cadena =  FirebaseInstanceId . getInstance () . getToken ();
                    callbackContext . éxito (ficha);
                } catch ( Excepción e) {
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

     getId privado vacío  ( CallbackContext final callbackContext ) {  
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    Id. De cadena =  FirebaseInstanceId . getInstance () . getId ();
                    callbackContext . éxito (id);
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privado  getToken vacío  ( final CallbackContext callbackContext ) {  
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    Token de cadena =  FirebaseInstanceId . getInstance () . getToken ();
                    callbackContext . éxito (ficha);
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

     hasPermission privado vacío  ( final CallbackContext callbackContext ) {  
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    Contexto contexto = cordova . getActivity ();
                    NotificationManagerCompat notificaciónManagerCompat =  NotificationManagerCompat . de (contexto);
                    boolean areNotificationsEnabled = notificaciónManagerCompat . areNotificationsEnabled ();
                    Objeto JSONObject =  nuevo  JSONObject ();
                    objeto . put ( " isEnabled " , areNotificationsEnabled);
                    callbackContext . éxito (objeto);
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privado  void  setBadgeNumber ( final  CallbackContext  callbackContext , número int final  ) { 
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    Contexto contexto = cordova . getActivity ();
                    Preferencias compartidas . Editor editor = contexto . getSharedPreferences ( KEY , Context . MODE_PRIVATE ) . editar();
                    editor . putInt ( CLAVE , número);
                    editor . aplicar();
                    AtajoBadger . applyCount (contexto, número);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privada  vacío  getBadgeNumber ( última  CallbackContext  callbackContext ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    Contexto contexto = cordova . getActivity ();
                    Configuración de preferencias compartidas = contexto . getSharedPreferences ( KEY , Context . MODE_PRIVATE );
                    int número = configuración . getInt ( KEY , 0 );
                    callbackContext . éxito (número);
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

     suscripción privada vacía  ( final CallbackContext callbackContext , tema final de la cadena ) {    
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    FirebaseMessaging . getInstance () . subscribeToTopic (tema);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privada  vacío  darse de baja ( última  CallbackContext  callbackContext , última  cadena  tema ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    FirebaseMessaging . getInstance () . unsubscribeFromTopic (tema);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

     anulación del  registro de anulación privada ( final  CallbackContext  callbackContext ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    FirebaseInstanceId . getInstance () . deleteInstanceId ();
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privada  vacío  LOGEVENT ( última  CallbackContext  callbackContext , última  cadena  nombre , finales  JSONObject  params )
            lanza  JSONException {
         paquete final del paquete =  nuevo  paquete ();
        Iterator iter = params . llaves();
        while (iter . hasNext ()) {
            Clave de cadena = ( cadena ) iter . siguiente();
            Valor del objeto = params . obtener la clave);

            if (value instanceof  Integer  || value instanceof  Double ) {
                bundle . putFloat (clave, (( Número ) valor) . floatValue ());
            } más {
                bundle . putString (clave, valor . toString ());
            }
        }

        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    mFirebaseAnalytics . logEvent (nombre, paquete);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    private  void  logError ( final  CallbackContext  callbackContext , mensaje final de  cadena  ) arroja JSONException { 
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    Crashlytics . logException ( nueva  excepción (mensaje));
                    callbackContext . éxito ( 1 );
                } catch ( Excepción e) {
                    Crashlytics . log (e . getMessage ());
                    e . printStackTrace ();
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privado  void  setCrashlyticsUserId ( final  CallbackContext  callbackContext , final  String  userId ) {
        Cordova . getActivity () . runOnUiThread ( new  Runnable () {
            public  void  run () {
                prueba {
                    Crashlytics . setUserIdentifier (userId);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

     setScreenName privado vacío  ( CallbackContext final callbackContext , nombre de cadena final ) {    
        // Esto debe ser llamado en el hilo principal
        Cordova . getActivity () . runOnUiThread ( new  Runnable () {
            public  void  run () {
                prueba {
                    mFirebaseAnalytics . setCurrentScreen (cordova . getActivity (), name, null );
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privado  void  setUserId ( final  CallbackContext  callbackContext , final  String  id ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    mFirebaseAnalytics . setUserId (id);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privada  vacío  setUserProperty ( última  CallbackContext  callbackContext , última  cadena  nombre , última  cadena  de valor ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    mFirebaseAnalytics . setUserProperty (nombre, valor);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    private  void  enableFetched ( final  CallbackContext  callbackContext ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                     booleano final activado =  FirebaseRemoteConfig . getInstance () . activeFetched ();
                    callbackContext . success ( String . valueOf (activado));
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

     búsqueda de vacío  privado ( CallbackContext callbackContext ) { 
        fetch (callbackContext, FirebaseRemoteConfig . getInstance () . fetch ());
    }

     búsqueda de vacío  privado ( CallbackContext callbackContext , long cacheExpirationSeconds ) {  
        fetch (callbackContext, FirebaseRemoteConfig . getInstance () . fetch (cacheExpirationSeconds));
    }

     búsqueda privada vacía  ( final CallbackContext callbackContext , tarea final < Void > tarea ) {    
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    tarea . addOnSuccessListener ( nuevo  OnSuccessListener < Void > () {
                        @Anular
                        public  void  onSuccess ( datos nulos  ) {
                            callbackContext . éxito();
                        }
                    }) . addOnFailureListener ( nuevo  OnFailureListener () {
                        @Anular
                        pública  vacío  onFailure ( Excepción  e ) {
                            Crashlytics . logException (e);
                            callbackContext . error (e . getMessage ());
                        }
                    });
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privada  vacío  getByteArray ( última  CallbackContext  callbackContext , última  cadena  clave , última  cadena  de espacio de nombres ) {
      Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
        public  void  run () {
            prueba {
                byte [] bytes = espacio de nombres ==  nulo  ?  FirebaseRemoteConfig . getInstance () . getByteArray (clave)
                        :  FirebaseRemoteConfig . getInstance () . getByteArray (clave, espacio de nombres);
                Objeto JSONObject =  nuevo  JSONObject ();
                objeto . put ( " base64 " , Base64 . encodeToString (bytes, Base64 . DEFAULT ));
                objeto . put ( " matriz " , nuevo  JSONArray (bytes));
                callbackContext . éxito (objeto);
            } catch ( Excepción e) {
                Crashlytics . logException (e);
                callbackContext . error (e . getMessage ());
            }
        }
      });
    }

    privada  vacío  getValue ( última  CallbackContext  callbackContext , última  cadena  clave , última  cadena  de espacio de nombres ) {
      Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
        public  void  run () {
            prueba {
                FirebaseRemoteConfigValue value = namespace ==  null
                        ?  FirebaseRemoteConfig . getInstance () . getValue (clave)
                        :  FirebaseRemoteConfig . getInstance () . getValue (clave, espacio de nombres);
                callbackContext . éxito (valor . asString ());
            } catch ( Excepción e) {
                Crashlytics . logException (e);
                callbackContext . error (e . getMessage ());
            }
        }
      });
    }

    privado  vacío  getInfo ( final  CallbackContext  callbackContext ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    FirebaseRemoteConfigInfo remoteConfigInfo =  FirebaseRemoteConfig . getInstance () . conseguir información();
                    JSONObject info =  new  JSONObject ();

                    Configuración de JSONObject =  new  JSONObject ();
                    ajustes . put ( " developerModeEnabled " , remoteConfigInfo . getConfigSettings () . isDeveloperModeEnabled ());
                    info . put ( " configSettings " , configuración);

                    info . put ( " fetchTimeMillis " , remoteConfigInfo . getFetchTimeMillis ());
                    info . put ( " lastFetchStatus " , remoteConfigInfo . getLastFetchStatus ());

                    callbackContext . éxito (información);
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

     setConfigSettings privado vacío  ( final CallbackContext callbackContext , final JSONObject config ) {    
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    boolean devMode = config . getBoolean ( " developerModeEnabled " );
                    FirebaseRemoteConfigSettings . Generador de configuración =  nuevos  FirebaseRemoteConfigSettings . Constructor ()
                            .setDeveloperModeEnabled (devMode);
                    FirebaseRemoteConfig . getInstance () . setConfigSettings (settings . build ());
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

     setDefaults privado vacío  ( final CallbackContext callbackContext , final JSONObject defaults , final String namespace ) {      
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    if (espacio de nombres ==  nulo )
                        FirebaseRemoteConfig . getInstance () . setDefaults (defaultsToMap (valores predeterminados));
                    más
                        FirebaseRemoteConfig . getInstance () . setDefaults (defaultsToMap (predeterminado), espacio de nombres);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    El  mapa estático  privado < String , Object >  defaultsToMap ( objeto JSONObject  ) arroja JSONException { 
         mapa final < String , Object > map =  new  HashMap < String , Object > ();

        for ( Iterator < String > keys = object . keys (); keys . hasNext ();) {
            Clave de cadena = claves . siguiente();
            Objeto de valor = objeto . obtener la clave);

            if (valor de instancia de  entero ) {
                // setDefaults () debería tomar Longs
                valor =  nuevo  Long ( valor ( entero ));
            } else  if (valor de instancia de  JSONArray ) {
                JSONArray array = ( JSONArray ) valor;
                if (array . length () ==  1  && array . get ( 0 ) instanceof  String ) {
                    // analizar byte [] como cadena Base64
                    valor =  Base64 . decodificar (array . getString ( 0 ), Base64 . DEFAULT );
                } más {
                    // analizar byte [] como matriz numérica
                    byte [] bytes =  nuevo  byte [matriz . longitud()];
                    for ( int i =  0 ; i < array . length (); i ++ )
                        bytes [i] = ( byte ) matriz . getInt (i);
                    valor = bytes;
                }
            }

            mapear . poner (clave, valor);
        }
        mapa de retorno ;
    }

    Private  PhoneAuthProvider . OnVerificationStateChangedCallbacks mCallbacks;

    público  vacío  verifiqueNúmeroPhone (
             CallbackContext  final callbackContext ,
             número de cadena  final ,
            última  int  TimeoutDuration
    ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    mCallbacks =  nuevo  PhoneAuthProvider . OnVerificationStateChangedCallbacks () {
                        @Anular
                        public  void  onVerificationCompleted ( credencial de credencial de PhoneAuth  ) {
                            // Esta devolución de llamada se invocará en dos situaciones:
                            // 1 - Verificación instantánea. En algunos casos, el número de teléfono puede ser instantáneamente
                            //      verificado sin necesidad de enviar o ingresar un código de verificación.
                            // 2 - Recuperación automática. En algunos dispositivos, los servicios de Google Play pueden automáticamente
                            //      detecta el SMS de verificación entrante y realiza la verificación sin
                            //      acción del usuario.
                            Registro . d ( TAG , " éxito: verificar número de teléfono. Verificación completa " );

                            JSONObject returnResults =  new  JSONObject ();
                            prueba {
                                Cadena de verificación Id =  nulo ;
                                Código de cadena =  nulo ;
								
                                Campo [] campos = credencial . getClass () . getDeclaredFields ();
                                para ( Campo campo : campos) {
                                    Tipo de clase = campo . getType ();
                                    if (type ==  String . class) {
                                        Valor de cadena = getPrivateField (credencial, campo);
                                        if (valor ==  nulo ) continuar ;
                                        if (valor . longitud () >  100 ) verificacionId = valor;
                                        más  si (valor . longitud () > =  4  && valor . longitud () <=  6 ) código = valor;
                                    }
                                }
                                returnResults . put ( " verificado " , verIdentificación ! =  nulo  && código ! =  nulo );
                                returnResults . put ( " verificacionId " , verificacionId);
                                returnResults . put ( " código " , código);
                                returnResults . put ( " verificación instantánea " , verdadero );
                            } catch ( JSONException e) {
                                Crashlytics . logException (e);
                                callbackContext . error (e . getMessage ());
                                volver ;
                            }
                            PluginResult pluginresult =  nuevo  PluginResult ( PluginResult . Estado . OK , returnResults);
                            resultado del complemento . setKeepCallback ( verdadero );
                            callbackContext . sendPluginResult (pluginresult);
                        }

                        @Anular
                        public  void  onVerificationFailed ( FirebaseException  e ) {
                            // Esta devolución de llamada se invoca en una solicitud de verificación no válida,
                            // por ejemplo si el formato del número de teléfono no es válido.
                            Registro . w ( ETIQUETA , " falló: verificar número de teléfono. Verificación fallida " , e);

                            String errorMsg =  " error desconocido al verificar el número " ;
                            errorMsg + =  " Instancia de error: "  + e . getClass () . getName ();

                            if (e instanceof  FirebaseAuthInvalidCredentialsException ) {
                                // Solicitud inválida
                                errorMsg =  " Número de teléfono inválido " ;
                            } else  if (e instanceof  FirebaseTooManyRequestsException ) {
                                // Se ha excedido la cuota de SMS para el proyecto
                                errorMsg =  " Se ha excedido la cuota de SMS para el proyecto " ;
                            }

                            Crashlytics . logException (e);
                            callbackContext . error (errorMsg);
                        }

                        @Anular
                        pública  vacío  onCodeSent ( Cadena  verificationId , PhoneAuthProvider . ForceResendingToken  símbolo ) {
                            // El código de verificación de SMS se ha enviado al número de teléfono proporcionado, nosotros
                            // ahora necesita pedirle al usuario que ingrese el código y luego construir una credencial
                            // combinando el código con un ID de verificación [(en la aplicación)].
                            Registro . d ( TAG , " éxito: verificar número de teléfono. en Código " );

                            JSONObject returnResults =  new  JSONObject ();
                            prueba {
                                returnResults . put ( " verificacionId " , verificacionId);
                                returnResults . put ( " verificación instantánea " , falso );
                            } catch ( JSONException e) {
                                Crashlytics . logException (e);
                                callbackContext . error (e . getMessage ());
                                volver ;
                            }
                            PluginResult pluginresult =  nuevo  PluginResult ( PluginResult . Estado . OK , returnResults);
                            resultado del complemento . setKeepCallback ( verdadero );
                            callbackContext . sendPluginResult (pluginresult);
                        }
                    };
	
                    PhoneAuthProvider . getInstance () . verificar Número de teléfono (número, // Número de teléfono para verificar
                            timeOutDuration, // Duración del tiempo de espera
                            TimeUnit . SEGUNDOS , // Unidad de tiempo de espera
                            Cordova . getActivity (), // Actividad (para enlace de devolución de llamada)
                            mCallbacks); // OnVerificationStateChangedCallbacks
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }
	
     Cadena estática  privada getPrivateField ( credencial PhoneAuthCredential , campo Field ) {   
        prueba {
            campo . setAccessible ( verdadero );
            campo de retorno ( cadena ) . obtener (credencial);
        } catch ( IllegalAccessException e) {
            volver  nulo ;
        }
    }

    //
    // rendimiento de Firebase
    //

    private  HashMap < String , Trace > traces =  new  HashMap < String , Trace > ();

    private  void  startTrace ( final  CallbackContext  callbackContext , final  String  name ) {
        final  FirebasePlugin self =  this ;
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {

                    Traza myTrace =  nulo ;
                    if (self . traces . usesKey (name)) {
                        myTrace = self . rastros . obtener (nombre);
                    }

                    if (myTrace ==  null ) {
                        myTrace =  FirebasePerformance . getInstance () . newTrace (nombre);
                        myTrace . comienzo();
                        auto . rastros . poner (nombre, myTrace);
                    }

                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    e . printStackTrace ();
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privada  vacío  incrementCounter ( última  CallbackContext  callbackContext , última  cadena  nombre , última  cadena  counterNamed ) {
        final  FirebasePlugin self =  this ;
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {

                    Traza myTrace =  nulo ;
                    if (self . traces . usesKey (name)) {
                        myTrace = self . rastros . obtener (nombre);
                    }

                    if (myTrace ! =  null  && myTrace instanceof  Trace ) {
                        myTrace . incrementCounter (counterNamed);
                        callbackContext . éxito();
                    } más {
                        callbackContext . error ( " Rastreo no encontrado " );
                    }
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    e . printStackTrace ();
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privada  vacío  stopTrace ( última  CallbackContext  callbackContext , última  cadena  nombre ) {
        final  FirebasePlugin self =  this ;
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {

                    Traza myTrace =  nulo ;
                    if (self . traces . usesKey (name)) {
                        myTrace = self . rastros . obtener (nombre);
                    }

                    if (myTrace ! =  null  && myTrace instanceof  Trace ) { //
                        myTrace . detener();
                        auto . rastros . eliminar (nombre);
                        callbackContext . éxito();
                    } más {
                        callbackContext . error ( " Rastreo no encontrado " );
                    }
                } catch ( Excepción e) {
                    Crashlytics . logException (e);
                    e . printStackTrace ();
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privado  void  setAnalyticsCollectionEnabled ( final  CallbackContext  callbackContext , final  boolean  habilitado ) {
        final  FirebasePlugin self =  this ;
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    mFirebaseAnalytics . setAnalyticsCollectionEnabled (habilitado);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . log (e . getMessage ());
                    e . printStackTrace ();
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    privado  void  setPerformanceCollectionEnabled ( final  CallbackContext  callbackContext , final  boolean  habilitado ) {
        final  FirebasePlugin self =  this ;
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    FirebasePerformance . getInstance () . setPerformanceCollectionEnabled (habilitado);
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . log (e . getMessage ());
                    e . printStackTrace ();
                    callbackContext . error (e . getMessage ());
                }
            }
        });
    }

    public  void  clearAllNotifications ( final  CallbackContext  callbackContext ) {
        Cordova . getThreadPool () . ejecutar ( nuevo  Runnable () {
            public  void  run () {
                prueba {
                    Contexto contexto = cordova . getActivity ();
                    NotificationManager nm = ( NotificationManager ) contexto . getSystemService ( Contexto . NOTIFICATION_SERVICE );
                    nm . cancelalo todo();
                    callbackContext . éxito();
                } catch ( Excepción e) {
                    Crashlytics . log (e . getMessage ());
                }
            }
        });
    }
}
