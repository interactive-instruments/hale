//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.24 at 10:45:19 AM MEZ 
//


package eu.esdihumboldt.generated.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="componentproperties" type="{http://www.esdi-humboldt.eu/configuration}PropertiesType"/>
 *         &lt;element name="systemproperties" type="{http://www.esdi-humboldt.eu/configuration}PropertiesType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="componentName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "componentproperties",
    "systemproperties"
})
@XmlRootElement(name = "Configuration")
public class Configuration {

    @XmlElement(required = true)
    protected PropertiesType componentproperties;
    protected PropertiesType systemproperties;
    @XmlAttribute(required = true)
    protected String componentName;

    /**
     * Gets the value of the componentproperties property.
     * 
     * @return
     *     possible object is
     *     {@link PropertiesType }
     *     
     */
    public PropertiesType getComponentproperties() {
        return componentproperties;
    }

    /**
     * Sets the value of the componentproperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertiesType }
     *     
     */
    public void setComponentproperties(PropertiesType value) {
        this.componentproperties = value;
    }

    /**
     * Gets the value of the systemproperties property.
     * 
     * @return
     *     possible object is
     *     {@link PropertiesType }
     *     
     */
    public PropertiesType getSystemproperties() {
        return systemproperties;
    }

    /**
     * Sets the value of the systemproperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertiesType }
     *     
     */
    public void setSystemproperties(PropertiesType value) {
        this.systemproperties = value;
    }

    /**
     * Gets the value of the componentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * Sets the value of the componentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComponentName(String value) {
        this.componentName = value;
    }

}
