package Parser;

import java.util.Collection;
import java.util.Hashtable;


/* A simple symbol table for semantic analysis
 * 
 */
public class SymbolTable {
	// The Hash Table used to build the Symbol Table
	public Hashtable<String, Entry> st;
	
	// The level of the scope of the current instance
	public int level;
	
	// The current instance of the class
	private static SymbolTable inst;
	
	/*
	 * The constructor is set to private
	 * to prevent the creation of more than
	 * one instance of the class. 
	 */
	private SymbolTable() {
		st = new Hashtable<String, Entry>();
		level = 0;
	}
	
	/* 
	 * This method is used to get the current instance
	 * of the SymbolTable.
	 */
	public static SymbolTable getInstance() {
		if(inst==null)
			inst = new SymbolTable();
		
		return inst;
	}
	
	/*
	 * This method is used to add entries to the table
	 */
	public void add(Entry e) {
		e.level = level;
		st.put(e.id, e);
	}
	
	/*
	 * This method is used to get entries from the table
	 */
	public Entry get(String k) {
		return st.get(k);
	}
	

	
	/*
	 * This method checks if a certain identifier
	 * is already in the symbol table.
	 */
	public boolean contains(String id) {
		return st.containsKey(id);
	}
	
	/*
	 * This method opens a new scope in the hashtable
	 */
	public void openScope() {
		level++;
	}
	
	
	/*
	 * This methods closes the current scope and
	 * removes all the entries that were created
	 * created during that scope.
	 */
	public void closeScope() {
		
		Collection<Entry> ce = st.values();
		
		for(int i = 0 ; i < ce.size() ; i++){
			Entry e = (Entry) ce.toArray()[i];
			
			if(e.level == level){
				st.remove(e.id);
				i--;
			}
				
		}
		
		level--;
	}
}
