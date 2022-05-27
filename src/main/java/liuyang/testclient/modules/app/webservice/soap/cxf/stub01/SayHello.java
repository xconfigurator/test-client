
package liuyang.testclient.modules.app.webservice.soap.cxf.stub01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;sayHello complex type的 Java 类。
 * 
 * &lt;p&gt;以下模式片段指定包含在此类中的预期内容。
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="sayHello"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="arg0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="arg1" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayHello", propOrder = {
    "arg0",
    "arg1"
})
public class SayHello {

    protected String arg0;
    protected int arg1;

    /**
     * 获取arg0属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg0() {
        return arg0;
    }

    /**
     * 设置arg0属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg0(String value) {
        this.arg0 = value;
    }

    /**
     * 获取arg1属性的值。
     * 
     */
    public int getArg1() {
        return arg1;
    }

    /**
     * 设置arg1属性的值。
     * 
     */
    public void setArg1(int value) {
        this.arg1 = value;
    }

}
