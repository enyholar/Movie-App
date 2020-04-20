package com.behruz.magmovie.network;

/**
 * Created by mishael.harry on 3/30/2018.
 */

public class NetworkUtil {

    public static NetworkInterface getNetworkInterface(){
        return NetworkClient.getClient().create(NetworkInterface.class);
    }
}
