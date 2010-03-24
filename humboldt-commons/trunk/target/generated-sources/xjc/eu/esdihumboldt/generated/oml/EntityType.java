//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.24 at 10:45:19 AM MEZ 
//


package eu.esdihumboldt.generated.oml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EntityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.omwg.org/TR/d7/ontology/alignment}label" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.omwg.org/TR/d7/ontology/alignment}transf" minOccurs="0"/>
 *         &lt;element ref="{http://www.omwg.org/TR/d7/ontology/alignment}pipe" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.w3.org/1999/02/22-rdf-syntax-ns#}about"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntityType", propOrder = {
    "label",
    "transf",
    "pipe"
})
@XmlSeeAlso({
    InstanceType.class,
    RelationType.class,
    PropertyType.class,
    PropertyQualifierType.class,
    ClassType.class
})
public abstract class EntityType {

    protected List<String> label;
    protected FunctionType transf;
    protected TransfPipeType pipe;
    @XmlAttribute(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
    @XmlSchemaType(name = "anyURI")
    protected String about;

    /**
     * Gets the value of the label property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the label property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLabel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLabel() {
        if (label == null) {
            label = new ArrayList<String>();
        }
        return this.label;
    }

    /**
     * Gets the value of the transf property.
     * 
     * @return
     *     possible object is
     *     {@link FunctionType }
     *     
     */
    public FunctionType getTransf() {
        return transf;
    }

    /**
     * Sets the value of the transf property.
     * 
     * @param value
     *     allowed object is
     *     {@link FunctionType }
     *     
     */
    public void setTransf(FunctionType value) {
        this.transf = value;
    }

    /**
     * Gets the value of the pipe property.
     * 
     * @return
     *     possible object is
     *     {@link TransfPipeType }
     *     
     */
    public TransfPipeType getPipe() {
        return pipe;
    }

    /**
     * Sets the value of the pipe property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransfPipeType }
     *     
     */
    public void setPipe(TransfPipeType value) {
        this.pipe = value;
    }

    /**
     * Gets the value of the about property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbout() {
        return about;
    }

    /**
     * Sets the value of the about property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbout(String value) {
        this.about = value;
    }

}
