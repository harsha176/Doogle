//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.05 at 08:25:30 PM EST 
//


package edu.ncsu.csc573.project.common.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DownloadFileParamType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownloadFileParamType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.doogle.project.csc573.csc.ncsu.edu}FileParamType">
 *       &lt;sequence>
 *         &lt;element name="Downloads" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownloadFileParamType", propOrder = {
    "downloads"
})
public class DownloadFileParamType
    extends FileParamType
{

    @XmlElement(name = "Downloads")
    protected int downloads;

    /**
     * Gets the value of the downloads property.
     * 
     */
    public int getDownloads() {
        return downloads;
    }

    /**
     * Sets the value of the downloads property.
     * 
     */
    public void setDownloads(int value) {
        this.downloads = value;
    }

}
