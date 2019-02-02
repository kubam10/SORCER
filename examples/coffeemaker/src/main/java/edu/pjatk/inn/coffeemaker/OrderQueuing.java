package edu.pjatk.inn.coffeemaker;

import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;

/**
 * Created by Pawe³ Wojtasiak
 */
public interface OrderQueuing {

    public Context QueOrder(Context context) throws RemoteException, ContextException;
}
