package liuyang.testclient.modules.app.spring.dto;
//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2021.09.13 时间 01:51:47 PM CST
//

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>AlmInfo complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="AlmInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="systemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="systemName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="devId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="alarmID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="generateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cause" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="advice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlmInfo", propOrder = {
        "cityId",
        "category",
        "systemId",
        "systemName",
        "devId",
        "alarmID",
        "severity",
        "generateTime",
        "description",
        "cause",
        "advice"
})
public class AlmInfo {

    @XmlElement(required = true)
    protected String cityId;
    protected int category;
    @XmlElement(required = true)
    protected String systemId;
    @XmlElement(required = true)
    protected String systemName;
    @XmlElement(required = true)
    protected String devId;
    protected long alarmID;
    protected int severity;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar generateTime;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String cause;
    @XmlElement(required = true)
    protected String advice;

    /**
     * 获取cityId属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * 设置cityId属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCityId(String value) {
        this.cityId = value;
    }

    /**
     * 获取category属性的值。
     *
     */
    public int getCategory() {
        return category;
    }

    /**
     * 设置category属性的值。
     *
     */
    public void setCategory(int value) {
        this.category = value;
    }

    /**
     * 获取systemId属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * 设置systemId属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * 获取systemName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * 设置systemName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSystemName(String value) {
        this.systemName = value;
    }

    /**
     * 获取devId属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDevId() {
        return devId;
    }

    /**
     * 设置devId属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDevId(String value) {
        this.devId = value;
    }

    /**
     * 获取alarmID属性的值。
     *
     */
    public long getAlarmID() {
        return alarmID;
    }

    /**
     * 设置alarmID属性的值。
     *
     */
    public void setAlarmID(long value) {
        this.alarmID = value;
    }

    /**
     * 获取severity属性的值。
     *
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * 设置severity属性的值。
     *
     */
    public void setSeverity(int value) {
        this.severity = value;
    }

    /**
     * 获取generateTime属性的值。
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getGenerateTime() {
        return generateTime;
    }

    /**
     * 设置generateTime属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setGenerateTime(XMLGregorianCalendar value) {
        this.generateTime = value;
    }

    /**
     * 获取description属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取cause属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCause() {
        return cause;
    }

    /**
     * 设置cause属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCause(String value) {
        this.cause = value;
    }

    /**
     * 获取advice属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAdvice() {
        return advice;
    }

    /**
     * 设置advice属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAdvice(String value) {
        this.advice = value;
    }

}

