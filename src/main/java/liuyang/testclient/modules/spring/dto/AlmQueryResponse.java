//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2021.09.13 时间 01:51:47 PM CST 
//


package liuyang.testclient.modules.spring.dto;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="almList" type="{http://hbfec.com.cn}AlmInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="faultInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "almList",
    "faultInfo"
})
@XmlRootElement(name = "almQueryResponse")
public class AlmQueryResponse {

    protected List<AlmInfo> almList;
    @XmlElement(required = true)
    protected String faultInfo;

    /**
     * Gets the value of the almList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the almList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlmList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AlmInfo }
     * 
     * 
     */
    public List<AlmInfo> getAlmList() {
        if (almList == null) {
            almList = new ArrayList<AlmInfo>();
        }
        return this.almList;
    }

    /**
     * 获取faultInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultInfo() {
        return faultInfo;
    }

    /**
     * 设置faultInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultInfo(String value) {
        this.faultInfo = value;
    }

}
