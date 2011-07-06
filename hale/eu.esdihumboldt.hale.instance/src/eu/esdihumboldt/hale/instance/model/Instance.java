/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2011.
 */

package eu.esdihumboldt.hale.instance.model;


import eu.esdihumboldt.hale.schema.model.TypeDefinition;
import eu.esdihumboldt.hale.schema.model.constraint.type.Binding;
import eu.esdihumboldt.hale.schema.model.constraint.type.HasValueFlag;

/**
 * Represents an instance of a type
 *
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 */
public interface Instance extends Group {
	
	/**
	 * Get the definition of the type associated with the instance
	 * 
	 * @return the instance's type definition
	 */
	@Override
	public TypeDefinition getDefinition();
	
	/**
	 * Get the instance value.<br>
	 * <br>
	 * The value is only present for certain types where the {@link HasValueFlag}
	 * constraint is enabled. The {@link Binding} constraint on the type 
	 * definition defines the binding of the value.<br>
	 * <br>
	 * <b>NOTE:</b> This is needed for instance for XML elements with text content
	 * and attributes. It may only be a simple value (i.e. no {@link Group}
	 * or {@link Instance}). 
	 * 
	 * @return the instance value if it is defined, otherwise <code>null</code>
	 */
	public Object getValue();

}
