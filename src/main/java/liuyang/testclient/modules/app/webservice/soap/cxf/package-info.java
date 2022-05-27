/**
 * stub01:
 *  调用test-cxf暴露的服务
 *  http://localhost/soap/hello?wsdl
 *
 *  生成stub
 *  wsdl2java -encoding utf-8 -p liuyang.testclient.modules.app.webservice.soap.cxf.stub01 -client http://localhost/soap/hello?wsdl
 *  wsdl2java -encoding utf-8 -p com.hbfec.pdt.debug.stub01 -client http://localhost/soap/hello?wsdl
 *
 * @author liuyang(wx)
 * @since 2022/4/19
 */
package liuyang.testclient.modules.app.webservice.soap.cxf;