package org.draxent.funwap.environment;

import java.util.HashMap;

import org.draxent.funwap.FunwapException;
import org.draxent.funwap.lexicalanalysis.Token;

public class Env extends HashMap<String, Eval>{
	private static final long serialVersionUID = 1L;

	private Env parent;
	
    public Env()
    {
        this.parent = null;
    }
    
    // Searches in the Environment for identifier contained in the value of the Token
	public Eval apply(Token token)
    {
		// Search the key in the local Environment.
        if (containsKey(token.getValue())) {
			return this.get(token.getValue());        	
        }
		// If it could not found it in the local, try to recursively search it asking to the parent Environment.
        else if (parent != null) {
            return parent.apply(token);
        }
        else {
        	throw new FunwapException("Cannot find " + token.getValue() + " in the Environment.", token);
        }
    }
    
	public void Bind(String identifier, Eval value, boolean declaration)
    {
		if (declaration) {
			put(identifier, value);
		} else {
			// Try to Bind the identifier with the value using the RecursiveBind function
			boolean bound = recursiveBind(identifier, value);

			// If RecursiveBind did not performed the binding, we do it locally.
			if (!bound) {
				put(identifier, value);
			}			
		}
    }
	
	private boolean recursiveBind(String identifier, Eval value) {
		if (containsKey(identifier)) {
			Eval existingValue = this.get(identifier);
			if (existingValue.getType() != value.getType()) {
				throw new FunwapException(existingValue.getToken().getValue() + " is already declared with different type " +
						existingValue.getType() + ".", value.getToken());
			}
				
			put(identifier, value);
			return true;
		} else {
			if (parent != null) {
				return parent.recursiveBind(identifier, value);
			} else {
				return false;
			}
		}
	}

    public Env getParent() {
    	return parent;
    }
}
