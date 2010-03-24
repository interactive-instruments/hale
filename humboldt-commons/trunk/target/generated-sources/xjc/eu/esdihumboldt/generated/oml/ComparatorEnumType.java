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
 * <p>Java class for comparatorEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="comparatorEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="equal"/>
 *     &lt;enumeration value="not-equal"/>
 *     &lt;enumeration value="less-than"/>
 *     &lt;enumeration value="less-than-or-equal"/>
 *     &lt;enumeration value="greater-than"/>
 *     &lt;enumeration value="greater-than-or-equal"/>
 *     &lt;enumeration value="contains"/>
 *     &lt;enumeration value="starts-with"/>
 *     &lt;enumeration value="ends-with"/>
 *     &lt;enumeration value="matches"/>
 *     &lt;enumeration value="collection-contains"/>
 *     &lt;enumeration value="includes"/>
 *     &lt;enumeration value="includes-strictly"/>
 *     &lt;enumeration value="empty"/>
 *     &lt;enumeration value="oneOf"/>
 *     &lt;enumeration value="between"/>
 *     &lt;enumeration value="otherwise"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "comparatorEnumType")
@XmlEnum
public enum ComparatorEnumType {

    @XmlEnumValue("equal")
    EQUAL("equal"),
    @XmlEnumValue("not-equal")
    NOT_EQUAL("not-equal"),
    @XmlEnumValue("less-than")
    LESS_THAN("less-than"),
    @XmlEnumValue("less-than-or-equal")
    LESS_THAN_OR_EQUAL("less-than-or-equal"),
    @XmlEnumValue("greater-than")
    GREATER_THAN("greater-than"),
    @XmlEnumValue("greater-than-or-equal")
    GREATER_THAN_OR_EQUAL("greater-than-or-equal"),
    @XmlEnumValue("contains")
    CONTAINS("contains"),
    @XmlEnumValue("starts-with")
    STARTS_WITH("starts-with"),
    @XmlEnumValue("ends-with")
    ENDS_WITH("ends-with"),
    @XmlEnumValue("matches")
    MATCHES("matches"),
    @XmlEnumValue("collection-contains")
    COLLECTION_CONTAINS("collection-contains"),
    @XmlEnumValue("includes")
    INCLUDES("includes"),
    @XmlEnumValue("includes-strictly")
    INCLUDES_STRICTLY("includes-strictly"),
    @XmlEnumValue("empty")
    EMPTY("empty"),
    @XmlEnumValue("oneOf")
    ONE_OF("oneOf"),
    @XmlEnumValue("between")
    BETWEEN("between"),
    @XmlEnumValue("otherwise")
    OTHERWISE("otherwise");
    private final String value;

    ComparatorEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ComparatorEnumType fromValue(String v) {
        for (ComparatorEnumType c: ComparatorEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
