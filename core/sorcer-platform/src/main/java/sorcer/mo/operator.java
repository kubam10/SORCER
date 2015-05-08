/*
 * Copyright 2009 the original author or authors.
 * Copyright 2009 SorcerSoft.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sorcer.mo;

import sorcer.co.tuple.Tuple2;
import sorcer.core.context.MapContext;
import sorcer.core.context.ServiceContext;
import sorcer.core.context.model.ent.EntModel;
import sorcer.core.context.model.ent.Entry;
import sorcer.core.context.model.srv.MultiFidelitySrvModel;
import sorcer.core.context.model.srv.SrvModel;
import sorcer.service.*;
import sorcer.service.modeling.Model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mike Sobolewski on 4/26/15.
 */
public class operator {


    public static Context entModel(Object... entries)
            throws ContextException {
        if (entries != null && entries.length == 1 && entries[0] instanceof Context) {
            ((Context)entries[0]).setModeling(true);
            try {
                return new EntModel((Context)entries[0]);
            } catch (RemoteException e) {
                throw new ContextException(e);
            }
        }
        EntModel model = new EntModel();
        Object[] dest = new Object[entries.length+1];
        System.arraycopy(entries,  0, dest,  1, entries.length);
        dest[0] = model;
        return sorcer.eo.operator.context(dest);
    }

    public static Model inConn(Model model, Context inConnector) {
        ((ServiceContext)model).setInConnector(inConnector);
        if (inConnector instanceof MapContext)
            ((MapContext)inConnector).direction =  MapContext.Direction.IN;
        return model;
    }

    public static Model outConn(Model model, Context outConnector) {
        ((ServiceContext) model).setOutConnector(outConnector);
        if (outConnector instanceof MapContext)
            ((MapContext)outConnector).direction = MapContext.Direction.OUT;
        return model;
    }

    public static Model addResponse(Model model, String... responsePaths) throws ContextException {
        for (String path : responsePaths)
            ((ServiceContext)model).addResponsePath(path);
        return model;
    }

    public static Context responses(Model model) throws ContextException {
        try {
            return model.getResponses();
        } catch (RemoteException e) {
            throw new ContextException(e);
        }
    }

    public static Object response(Model model, String path) throws ContextException {
        try {
            return model.getResponse(path);
        } catch (RemoteException e) {
            throw new ContextException(e);
        }
    }

    public static Object response(Model model) throws ContextException {
        try {
            return model.getResponse();
        } catch (RemoteException e) {
            throw new ContextException(e);
        }
    }

    public static Entry entry(Model model, String path) throws ContextException {
        return new Entry(path, ((Context)model).asis(path));
    }

    public static Context inConn(List<Tuple2<String, ?>> entries) throws ContextException {
        MapContext map = new MapContext();
        map.direction = MapContext.Direction.IN;
        sorcer.eo.operator.populteContext(map, entries);
        return map;
    }

    public static Context inConn(Tuple2<String, ?>... entries) throws ContextException {
        MapContext map = new MapContext();
        map.direction = MapContext.Direction.IN;
        List<Tuple2<String, ?>> items = Arrays.asList(entries);
        sorcer.eo.operator.populteContext(map, items);
        return map;
    }

    public static Context outConn(List<Tuple2<String, ?>> entries) throws ContextException {
        MapContext map = new MapContext();
        map.direction = MapContext.Direction.OUT;
        sorcer.eo.operator.populteContext(map, entries);
        return map;
    }

    public static Context outConn(Tuple2<String, ?>... entries) throws ContextException {
        MapContext map = new MapContext();
        map.direction = MapContext.Direction.OUT;
        List<Tuple2<String, ?>> items = Arrays.asList(entries);
        sorcer.eo.operator.populteContext(map, items);
        return map;
    }

    public static Paradigmatic modeling(Paradigmatic paradigm) {
        paradigm.setModeling(true);
        return paradigm;
    }

    public static Paradigmatic modeling(Paradigmatic paradigm, boolean modeling) {
        paradigm.setModeling(modeling);
        return paradigm;
    }

    public static Model mfiModel(Object... items) throws ContextException {
        List<Fidelity<String>> fidelities = new ArrayList<Fidelity<String>>();
        for (Object item : items) {
            if (item instanceof Fidelity) {
                fidelities.add((Fidelity)item);
        }
    }
        MultiFidelitySrvModel model = new MultiFidelitySrvModel();
        model.addSelectionFidelities(fidelities);
        return srvModel(items);
    }

    public static Model srvModel(Object... items) throws ContextException {
        sorcer.eo.operator.Complement complement = null;
        List<Signature> sigs = new ArrayList<Signature>();
        Model model = null;
        for (Object item : items) {
            if (item instanceof Signature) {
                sigs.add((Signature)item);
            } else if (item instanceof sorcer.eo.operator.Complement) {
                complement = (sorcer.eo.operator.Complement)item;
            } else if (item instanceof Model) {
                model = ((Model)item);
            }
        }
        if (model == null)
            model = new SrvModel();

        if (sigs != null && sigs.size() > 0) {
            Fidelity fidelity = new Fidelity();
            for (Signature sig : sigs)
                fidelity.getSelects().add(sig);
            ((SrvModel)model).addServiceFidelity(fidelity);
            ((SrvModel)model).setSelectedServiceFidelity(fidelity);
        }
//        else {
//            ((SrvModel)model).setSubject("execute", ServiceModeler.class);
//        }

        if (complement != null) {
            ((SrvModel)model).setSubject(complement.path(), complement.value());
        }

        Object[] dest = new Object[items.length+1];
        System.arraycopy(items,  0, dest,  1, items.length);
        dest[0] = model;
        return sorcer.eo.operator.context(dest);
    }
}