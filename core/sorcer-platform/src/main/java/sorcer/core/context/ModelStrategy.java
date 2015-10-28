package sorcer.core.context;

import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import sorcer.core.Name;
import sorcer.service.*;
import sorcer.service.MogramStrategy;
import sorcer.util.FileURLHandler;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mike Sobolewski
 */
public class ModelStrategy implements MogramStrategy, Projection<Arg>, Serializable {

    protected List<ThrowableTrace> exceptions;

    private List<String> traceList;

    private boolean isTraceable = false;

    private boolean isMonitorable = false;

    private boolean isProvisionable = false;

    private Mogram target;

    protected transient FileURLHandler dataService;

    // dependency management for this Context
    protected List<Evaluation> dependers = new ArrayList<Evaluation>();

    protected String currentSelector;

    // mapping from paths of this inConnector to input paths of this context
    protected Context inConnector;

    // mapping from paths of this context to input paths of requestors
    protected Context outConnector;

    protected Map<String, List<String>> dependentPaths;

    protected Fidelity<Arg> selectedFidelity;

    // select fidelities for this service context
    protected Map<String, Fidelity<Arg>> selectFidelities;

    // evaluated model response entries
    protected Context outcome;

    protected Exec.State execState = Exec.State.INITIAL;

    // reponse paths of the runtime model
    protected List<Name> responsePaths = new ArrayList<Name>();

    public ModelStrategy(Mogram service) {
        target = service;
    }

    public void setExceptions(List<ThrowableTrace> exceptions) {
        this.exceptions = exceptions;
    }

    public boolean isMonitorable() {
        return isMonitorable;
    }

    @Override
    public void setMonitorable(boolean state) {
        isMonitorable = state;
    }

    @Override
    public boolean isProvisionable() {
        return isProvisionable;
    }

    @Override
    public void setProvisionable(boolean state) {
        isProvisionable = state;
    }

    @Override
    public boolean isTracable() {
        return isTraceable;
    }

    @Override
    public void setTracable(boolean state) {
        isTraceable = state;
    }

    @Override
    public void setOpti(Strategy.Opti optiType) {

    }

    @Override
    public Strategy.Opti getOpti() {
        return null;
    }

    @Override
    public void addException(Throwable t) {
        exceptions.add(new ThrowableTrace(t));
    }

    @Override
    public void addException(String message, Throwable t) {
        exceptions.add(new ThrowableTrace(message, t));
    }

    public void setIsMonitorable(boolean isMonitorable) {
        this.isMonitorable = isMonitorable;
    }

    public List<ThrowableTrace> getExceptions() {
        exceptions = new ArrayList<ThrowableTrace>();
        if (exceptions != null)
            return exceptions;
        else
            return new ArrayList<ThrowableTrace>();
    }

    public List<String> getTraceList() {
        return traceList;
    }

    public void setTraceList(List<String> traceList) {
        this.traceList = traceList;
    }

    public List<ThrowableTrace> getAllExceptions() {
        return getExceptions();
    }

    public Map<String, List<String>> getDependentPaths() {
        if (dependentPaths == null) {
            dependentPaths = new HashMap<String, List<String>>();
        }
        return dependentPaths;
    }

    public Context getInConnector(Arg... arg) {
        return inConnector;
    }

    public void setInConnector(Context inConnector) {
        this.inConnector = inConnector;
    }


    public Context getOutConnector(Arg... args) {
        return outConnector;
    }

    public void setOutConnector(Context outConnector) {
        this.outConnector = outConnector;
    }

    public String getCurrentSelector() {
        return currentSelector;
    }

    public void setCurrentSelector(String currentSelector) {
        this.currentSelector = currentSelector;
    }

    public void addDependers(Evaluation... dependers) {
        if (this.dependers == null)
            this.dependers = new ArrayList<Evaluation>();
        for (Evaluation depender : dependers)
            this.dependers.add(depender);
    }

    public List<Evaluation> getDependers() {
        return dependers;
    }

    public void setSelectFidelities(Map<String, Fidelity<Arg>> selectFidelities) {
        this.selectFidelities = selectFidelities;
    }

    public Context getOutcome() {
        return outcome;
    }

    public void setResult(String path, Object value) throws ContextException {
        if (!responsePaths.contains(path))
            throw new ContextException("no such response path: " + path);
        outcome.putValue(path, value);
    }

    public List<Name> getResponsePaths() {
        return responsePaths;
    }

    public void setResponsePaths(List<Name> responsePaths) {
        this.responsePaths = responsePaths;
    }

    public <T extends Mogram> Mogram exert(Transaction txn, Arg... entries) throws TransactionException, MogramException, RemoteException {
        return target.exert(txn, entries);
    }

    public <T extends Mogram> Mogram exert(Arg... entries) throws TransactionException, MogramException, RemoteException {
        return target.exert(entries);
    }

    public void setOutcome(Context outcome) {
        this.outcome = outcome;
    }

    public Mogram getTarget() {
        return target;
    }

    public void setTarget(Mogram target) {
        this.target = target;
    }

    @Override
    public Fidelity<Arg> getFidelity() {
        return selectedFidelity;
    }

    @Override
    public void setFidelity(Fidelity<Arg> fidelity) {
        selectedFidelity = fidelity;
    }

    public void appendTrace(String info) {
        traceList.add(info);
    }

    public List<String> getTrace() {
        return traceList;
    }

    public FileURLHandler getDataService() {
        return dataService;
    }

    public void setDataService(FileURLHandler dataService) {
        this.dataService = dataService;
    }

    public Exec.State getExecState() {
        return execState;
    }

}