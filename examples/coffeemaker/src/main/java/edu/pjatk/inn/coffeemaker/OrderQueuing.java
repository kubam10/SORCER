package edu.pjatk.inn.coffeemaker;

import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;

/**
 * Created by Paweł Wojtasiak
 */
public interface OrderQueuing {

    public Context queOrder(Context context) throws RemoteException, ContextException;
}
