package sorcer.arithmetic.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static sorcer.co.operator.array;
import static sorcer.co.operator.columnNames;
import static sorcer.co.operator.columnSize;
import static sorcer.co.operator.dbEntry;
import static sorcer.co.operator.dictionary;
import static sorcer.co.operator.entry;
import static sorcer.co.operator.inEntry;
import static sorcer.co.operator.inoutEntry;
import static sorcer.co.operator.isPersistent;
import static sorcer.co.operator.key;
import static sorcer.co.operator.list;
import static sorcer.co.operator.listContext;
import static sorcer.co.operator.map;
import static sorcer.co.operator.outEntry;
import static sorcer.co.operator.path;
import static sorcer.co.operator.persistent;
import static sorcer.co.operator.put;
import static sorcer.co.operator.row;
import static sorcer.co.operator.rowMap;
import static sorcer.co.operator.rowNames;
import static sorcer.co.operator.rowSize;
import static sorcer.co.operator.set;
import static sorcer.co.operator.store;
import static sorcer.co.operator.strategyEntry;
import static sorcer.co.operator.table;
import static sorcer.co.operator.tuple;
import static sorcer.co.operator.url;
import static sorcer.co.operator.value;
import static sorcer.eo.operator.access;
import static sorcer.eo.operator.add;
import static sorcer.eo.operator.asis;
import static sorcer.eo.operator.context;
import static sorcer.eo.operator.flow;
import static sorcer.eo.operator.get;
import static sorcer.eo.operator.put;
import static sorcer.eo.operator.strategy;
import static sorcer.eo.operator.value;
import static sorcer.po.operator.add;
import static sorcer.po.operator.asis;
import static sorcer.po.operator.dbPar;
import static sorcer.po.operator.invoker;
import static sorcer.po.operator.par;
import static sorcer.po.operator.parModel;
import static sorcer.po.operator.pars;
import static sorcer.po.operator.set;
import static sorcer.po.operator.value;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.Test;

import sorcer.co.tuple.Entry;
import sorcer.core.context.ListContext;
import sorcer.core.context.model.par.Par;
import sorcer.core.context.model.par.ParModel;
import sorcer.service.Context;
import sorcer.service.ContextException;
import sorcer.service.EvaluationException;
import sorcer.service.ExertionException;
import sorcer.service.SignatureException;
import sorcer.service.Strategy.Access;
import sorcer.service.Strategy.Flow;
import sorcer.util.Sorcer;
import sorcer.util.Table;

/**
 * @author Mike Sobolewski
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CollectionOperatorsTest {
	
	private final static Logger logger = Logger.getLogger(CollectionOperatorsTest.class.getName());
	
	static {
		System.setProperty("java.security.policy", Sorcer.getHome()
				+ "/configs/policy.all");
		System.setSecurityManager(new SecurityManager());
		Sorcer.setCodeBase(new String[] { "arithmetic-dl.jar",  "sorcer-dl.jar" });
		System.out.println("CLASSPATH :" + System.getProperty("java.class.path"));
		System.setProperty("java.protocol.handler.pkgs", "sorcer.util.url|org.rioproject.url");
//		System.setProperty("java.rmi.server.RMIClassLoaderSpi","org.rioproject.rmi.ResolvingLoader");	
	}
	
	
	@Test
	public void listOperator() throws Exception {
		
		// the list operator creates an instance of ArrayList
		List<Object> l = list(list(1.1, 2.1, 3.1),  4.1,  list(11.1, 12.1, 13.1));
		
		List<Double> al = (List<Double>)l.get(0);
		assertEquals(al, Arrays.asList(array(1.1, 2.1, 3.1)));
		
		assertEquals(l.get(0), list(1.1, 2.1, 3.1));
		assertEquals(l.get(1), 4.1);
		assertEquals(l.get(2), list(11.1, 12.1, 13.1));
		
	}
	
	
	@Test
	public void setOperator() throws Exception {
		
		// the set operator creates instances of java.util.Set
		Set<Serializable> s = set("name", "Mike", "name", "Ray", tuple("height", 174));
		assertEquals(s.size(), 4);
		assertEquals(tuple("height", 174)._1, "height");
		assertEquals((int)tuple("height", 174)._2, 174);

		assertTrue(s.contains(tuple("height", 174)));
		
	}
	
	
	@Test
	public void arrayOperator() throws Exception {
		
		Double[] da = array(1.1, 2.1, 3.1);
		assertArrayEquals(da, new Double[] { 1.1, 2.1, 3.1 } );
		
		Object oa = array(array(1.1, 2.1, 3.1),  4.1,  array(11.1, 12.1, 13.1));		
		assertArrayEquals((Double[])((Object[])oa)[0], array(1.1, 2.1, 3.1));
		assertEquals(((Object[])oa)[1], 4.1);
		assertArrayEquals((Double[])((Object[])oa)[2], array(11.1, 12.1, 13.1));
		
	}

	
	@Test
	public void tableOperator() throws Exception {
		
		Table t = table(
				row(1.1, 1.2, 1.3, 1.4, 1.5),
				row(2.1, 2.2, 2.3, 2.4, 2.5),
				row(3.1, 3.2, 3.3, 3.4, 3.5));
		
		columnNames(t, list("x1", "x2", "x3", "x4", "x5"));
		rowNames(t, list("f1", "f2", "f3"));
		//logger.info("table: " + table);
		assertEquals(rowSize(t), 3);
		assertEquals(columnSize(t), 5);
		
		assertEquals(rowNames(t), list("f1", "f2", "f3"));
		assertEquals(columnNames(t), list("x1", "x2", "x3", "x4", "x5"));
		assertEquals(rowMap(t, "f2"), map(entry("x1", 2.1), entry("x2", 2.2), 
				entry("x3", 2.3), entry("x4", 2.4), entry("x5",2.5)));
		assertEquals(value(t, "f2", "x2"), 2.2);
		assertEquals(value(t, 1, 1), 2.2);
		
	}
	
	
	@Test
	public void entryOperator() throws Exception {
		
		Entry<Double> e = entry("arg/x1", 10.0);
		assertEquals("arg/x1", key(e));
		// a path is a String - usually a sequence of attributes
		assertEquals("arg/x1", path(e));

		assertEquals(isPersistent(e), false);
		assertTrue(asis(e) instanceof Double);
		assertTrue(value(e).equals(10.0));
		assertTrue(asis(e).equals(10.0));
		
		// make the entry persistent
		// value is not yet persisted
		persistent(e);
		
		assertEquals(isPersistent(e), true);
		assertFalse(asis(e) instanceof URL);
		assertTrue(value(e).equals(10.0));
		assertTrue(asis(e) instanceof URL);
		
		put(e, 50.0);
		assertTrue(value(e).equals(50.0));
		assertTrue(asis(e) instanceof URL);
		
		Entry se = strategyEntry("j1/j2", strategy(Access.PULL, Flow.PAR));
		assertEquals(flow(se), Flow.PAR);
		assertEquals(access(se), Access.PULL);
		
		// store value of the entry
		e = entry("arg/x1", 100.0);
		store(e);
		assertEquals(isPersistent(e), true);
		assertTrue(asis(e) instanceof URL);
		assertTrue(value(e).equals(100.0));
		
	}
	
	
	@Test
	public void dbEntryOperator() throws Exception {
		
		// create an entry
		Entry<Double> e = entry("x1", 10.0);
		assertTrue(value(e).equals(10.0));
		assertTrue(asis(e).equals(10.0));
		
		// make it a persistent entry
		// 'url' operator makes the entry persistent		
		URL valUrl = url(e);
		assertTrue(value(e).equals(10.0));
		assertTrue(asis(e) instanceof URL);
		
		// create a persistent entry
		Entry<?> urle = dbEntry("x2", valUrl);
		assertTrue(value(urle).equals(10.0));
		assertTrue(asis(urle) instanceof URL);
		
		// assign a given URL
		Entry<Object> dbe = dbEntry("y1");
		put(dbe, valUrl);
		assertTrue(value(dbe).equals(10.0));
		assertTrue(asis(dbe) instanceof URL);

	}
	
	
	@Test
	public void parOperator() throws Exception {
		
		Par<?> add = par("add", invoker("x + y", pars("x", "y")));
		Context<Double> cxt = context(entry("x", 10.0), entry("y", 20.0));
		logger.info("par value: " + value(add, cxt));
		assertTrue(value(add, cxt).equals(30.0));

		cxt = context(entry("x", 20.0), entry("y", 30.0));
		add = par(cxt, "add", invoker("x + y", pars("x", "y")));
		logger.info("par value: " + value(add));
		assertTrue(value(add).equals(50.0));

	}
		
	@Test
	public void dbParOperator() throws Exception {	
		Par<Double> dbp1 = persistent(par("design/in", 25.0));
		Par<String> dbp2 = dbPar("url/sobol", "http://sorcersoft.org/sobol");

		assertFalse(asis(dbp1) instanceof URL);
		assertFalse(asis(dbp2) instanceof URL);
		
		assertEquals(value(dbp1), 25.0);
		assertEquals(value(dbp2), "http://sorcersoft.org/sobol");
		
		assertTrue(asis(dbp1) instanceof URL);
		assertTrue(asis(dbp2) instanceof URL);

		// store par args in the data store
		Par p1 = store(par("design/in", 30.0));
		Par p2 = store(par("url/sorcer", "http://sorcersoft.org"));
		
		assertEquals(value(url(p1)), 30.0);
		assertEquals(value(url(p2)), "http://sorcersoft.org");
	}
	
	@Test
	public void mapOperator() throws Exception {
		
		Map<Object, Object> map1 = dictionary(entry("name", "Mike"), entry("height", 174.0));
				
		Map<String, Double> map2 = map(entry("length", 248.0), entry("width", 2.0), entry("height", 17.0));
		
		// keys and values of entries
		String k = key(entry("name", "Mike"));
		Double v = value(entry("height", 174.0));
		assertEquals(k, "name");
		assertTrue(v.equals(174.0));
		
		// casts are needed for dictionary: Map<Object, Object>
		k = (String)map1.get("name");
		v = (Double)map1.get("height");
		assertEquals(k, "Mike");
		assertTrue(v.equals(174.0));
		
		// casts are NOT needed for map: Map<K, V>
		v = map2.get("length");
		assertTrue(v.equals(248.0));
		
		// check map keys
		assertEquals(map1.keySet(), set("name", "height"));
		// check map values
		assertTrue(map1.values().contains("Mike"));
		assertTrue(map1.values().contains(174.0));
		
	}
	

	@Test
	public void contextOperator() throws Exception {
		
		Context<Double> cxt = context(entry("arg/x1", 1.1), entry("arg/x2", 1.2), 
				 entry("arg/x3", 1.3), entry("arg/x4", 1.4), entry("arg/x5", 1.5));
		
		add(cxt, entry("arg/x6", 1.6));
		
		assertTrue(value(cxt, "arg/x1").equals(1.1));
		assertEquals(get(cxt, "arg/x1"), 1.1);
		assertEquals(asis(cxt, "arg/x1"), 1.1);
		
		put(cxt, entry("arg/x1", 5.0));
		assertEquals(get(cxt, "arg/x1"), 5.0);

		Context<Double> subcxt = context(cxt, list("arg/x4", "arg/x5"));
		logger.info("subcontext: " + subcxt);
		assertNull(get(subcxt, "arg/x1"));
		assertNull(get(subcxt, "arg/x2"));
		assertNull(get(subcxt, "arg/x3"));
		assertEquals(get(cxt, "arg/x4"), 1.4);
		assertEquals(get(cxt, "arg/x5"), 1.5);
		assertEquals(get(cxt, "arg/x6"), 1.6);
		
	}
	
	@Test
	public void parModelOperator() throws Exception {
		
		ParModel pm = parModel("par-model", entry("John/weight", 180.0));
		add(pm, par("x", 10.0), entry("y", 20.0));
		add(pm, invoker("add", "x + y", pars("x", "y")));

//		logger.info("adder value: " + value(pm, "add"));
		assertEquals(value(pm, "John/weight"), 180.0);
		assertEquals(value(pm, "add"), 30.0);
		set(pm, "x", 20.0);
		assertEquals(value(pm, "add"), 40.0);
		
	}
	
}
