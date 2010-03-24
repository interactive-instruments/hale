//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.24 at 10:45:19 AM MEZ 
//


package eu.esdihumboldt.generated.oml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for relationEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="relationEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Equivalence"/>
 *     &lt;enumeration value="Subsumes"/>
 *     &lt;enumeration value="SubsumedBy"/>
 *     &lt;enumeration value="InstanceOf"/>
 *     &lt;enumeration value="HasInstance"/>
 *     &lt;enumeration value="Disjoint"/>
 *     &lt;enumeration value="PartOf"/>
 *     &lt;enumeration value="Extra"/>
 *     &lt;enumeration value="Missing"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "relationEnumType", namespace = "http://knowledgeweb.semanticweb.org/heterogeneity/alignment")
@XmlEnum
public enum RelationEnumType {

    @XmlEnumValue("Equivalence")
    EQUIVALENCE("Equivalence"),
    @XmlEnumValue("Subsumes")
    SUBSUMES("Subsumes"),
    @XmlEnumValue("SubsumedBy")
    SUBSUMED_BY("SubsumedBy"),
    @XmlEnumValue("InstanceOf")
    INSTANCE_OF("InstanceOf"),
    @XmlEnumValue("HasInstance")
    HAS_INSTANCE("HasInstance"),
    @XmlEnumValue("Disjoint")
    DISJOINT("Disjoint"),
    @XmlEnumValue("PartOf")
    PART_OF("PartOf"),
    @XmlEnumValue("Extra")
    EXTRA("Extra"),
    @XmlEnumValue("Missing")
    MISSING("Missing");
    private final String value;

    RelationEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RelationEnumType fromValue(String v) {
        for (RelationEnumType c: RelationEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
