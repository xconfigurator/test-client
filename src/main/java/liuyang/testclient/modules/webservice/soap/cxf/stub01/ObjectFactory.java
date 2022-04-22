
package liuyang.testclient.modules.webservice.soap.cxf.stub01;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the liuyang.testclient.modules.webservice.soap.cxf.stub01 package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Foo_QNAME = new QName("http://services.server.modules.testcxf.liuyang/", "foo");
    private final static QName _FooResponse_QNAME = new QName("http://services.server.modules.testcxf.liuyang/", "fooResponse");
    private final static QName _SayHello_QNAME = new QName("http://services.server.modules.testcxf.liuyang/", "sayHello");
    private final static QName _SayHelloResponse_QNAME = new QName("http://services.server.modules.testcxf.liuyang/", "sayHelloResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: liuyang.testclient.modules.webservice.soap.cxf.stub01
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Foo }
     * 
     */
    public Foo createFoo() {
        return new Foo();
    }

    /**
     * Create an instance of {@link FooResponse }
     * 
     */
    public FooResponse createFooResponse() {
        return new FooResponse();
    }

    /**
     * Create an instance of {@link SayHello }
     * 
     */
    public SayHello createSayHello() {
        return new SayHello();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     * 
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Foo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Foo }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.server.modules.testcxf.liuyang/", name = "foo")
    public JAXBElement<Foo> createFoo(Foo value) {
        return new JAXBElement<Foo>(_Foo_QNAME, Foo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FooResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FooResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.server.modules.testcxf.liuyang/", name = "fooResponse")
    public JAXBElement<FooResponse> createFooResponse(FooResponse value) {
        return new JAXBElement<FooResponse>(_FooResponse_QNAME, FooResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.server.modules.testcxf.liuyang/", name = "sayHello")
    public JAXBElement<SayHello> createSayHello(SayHello value) {
        return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.server.modules.testcxf.liuyang/", name = "sayHelloResponse")
    public JAXBElement<SayHelloResponse> createSayHelloResponse(SayHelloResponse value) {
        return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME, SayHelloResponse.class, null, value);
    }

}
