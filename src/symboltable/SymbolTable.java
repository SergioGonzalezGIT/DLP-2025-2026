package symboltable;

import ast.definition.Definition;

import java.util.*;

public class SymbolTable {
	
	private int scope=0;
	private List<Map<String, Definition>> table;


	public SymbolTable()  {
		this.table = new ArrayList<>();
		this.table.add(new HashMap<>());
	}

	public void set() {
		table.add(new HashMap<>());
		scope++;
	}
	
	public void reset() {
		table.remove(table.getLast());
		scope--;
	}
	
	public boolean insert(Definition definition) {
		if (!findInCurrentScope(definition.getName())) {
			definition.setScope(this.scope);
			Map<String, Definition> ambitoActual = table.getLast();
			ambitoActual.put(definition.getName(), definition);
			return true;
		}
		return false;
	}

	public Definition find(String id) {
		for(int i = table.size() - 1; i >= 0; i--){
			if (table.get(i).containsKey(id)) {
				return table.get(i).get(id);
			}
		}
		return null;
	}

	boolean findInCurrentScope(String id) {
		Map<String, Definition> ambitoActual = table.getLast();
		if(ambitoActual.containsKey(id)){
			return true;
		} else {
			return false;
		}
	}
}
