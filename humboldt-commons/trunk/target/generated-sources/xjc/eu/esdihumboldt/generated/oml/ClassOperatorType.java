//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.24 at 10:45:19 AM MEZ 
//


package eu.esdihumboldt.generated.oml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClassOperatorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ClassOperatorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INTERSECTION"/>
 *     &lt;enumeration value="UNION"/>
 *     &lt;enumeration value="UNION_DUPLICATES"/>
 *     &lt;enumeration value="COMPLEMENT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ClassOperatorType")
@XmlEnum
public enum ClassOperatorType {

    INTERSECTION,
    UNION,
    UNION_DUPLICATES,
    COMPLEMENT;

    public String value() {
        return name();
    }

    public static ClassOperatorType fromValue(String v) {
        return valueOf(v);
    }

}
