package edu.ncsu.csc573.project.doogle.test.schema;

/*
import java.util.ArrayList;
import javax.xml.bind.JAXBException;
import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.EnumParamsType;
import edu.ncsu.csc573.project.common.messages.IParameter;
import edu.ncsu.csc573.project.common.messages.IRequest;
*/

public class TestRequestMessages {

	/**
	 * @param args
	 * @throws JAXBException 
	 */
	/*public static void main(String[] args) throws JAXBException {
		// TODO Auto-generated method stub
		IRequest publishReq = new IRequest() {
			
			public void setParam(EnumParamsType paramType, String value) {
				// TODO Auto-generated method stub
				
			}
			
			public String getParam(EnumParamsType paramType) {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getAllParams() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public IRequest createRequest(EnumOperationType opType, IParameter parameter) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		ArrayList<String> fileList = new ArrayList<String>();
		IParameter param = new IParameter() {
			
			public void add(EnumParamsType param, Object value) {
				// TODO Auto-generated method stub
				
			}
		};
		param.add(EnumParamsType.CUSTOM, fileList);
		publishReq.createRequest(EnumOperationType.PUBLISH, param);
		//IRequest register = new
		/*HashMap<EnumParamsType, String> params = new HashMap<EnumParamsType, String>();
		params.put(EnumParamsType.USERNAME, "Krishna");
		params.put(EnumParamsType.PASSWORD, "fddsads");
		register.createRequest(EnumOperationType.LOGIN, params);
		
		
		/*Request req = new Request();
		req.setId(BigInteger.valueOf(System.currentTimeMillis()));
		CommandType register = new CommandType();
		RegisterType rt= new RegisterType();
		RegisterParamsType rpt = new RegisterParamsType();
		rpt.setUsername("Harsha");
		rpt.setEmailId("harsha176@abc.com");
		rpt.setPassword("***");
		rpt.setDesignation("guest");
		
		rt.setParmas(rpt);
		register.setRegister(rt);
		req.setCommand(register);
		
		JAXBContext context = JAXBContext.newInstance(Request.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(req, System.out);
		
	}*/

}
