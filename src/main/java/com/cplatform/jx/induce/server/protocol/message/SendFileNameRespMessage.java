package com.cplatform.jx.induce.server.protocol.message;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

/**
 * 
 * 发送文件名响应. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2017年2月6日 下午3:42:35
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * @author huajun@c-platform.com
 * @version 1.0.0
 */
public class SendFileNameRespMessage extends AbstractMessage {

	/**，1-成功，0-失败，2-文件已 存在。 */
	public static final int RESULT_OK=1;
	/**，1-成功，0-失败，2-文件已 存在。 */
	public static final int RESULT_FAIL=0;
	/**，1-成功，0-失败，2-文件已 存在。 */
	public static final int RESULT_SIMILAR=2;
	
  static Logger log = Logger.getLogger(SendFileNameRespMessage.class);
	
	public SendFileNameRespMessage(Header header)
    {
        super(header);
        getHeader().setTotalLength(8);
    }
    
    /**
     * 见接口
     */
    public ByteBuffer byteBufferedMessage(int length)
    {

         return null;
        
    }

    /**
     * 见接口
     */
    public int byteToMessage(ByteBuffer buffer)
    {
        if (buffer == null)
		{
			return MESSAGE_ERROR;
		}
		try
		{	    
			setResult(buffer.get());// 执行情况
		}
		catch (Exception e)
		{
			log.error("解析数据异常",e);
			return MESSAGE_ERROR;
		}

		return MESSAGE_SUCCESS;
    }
    /**执行情况*/
    private int result;

	
    public int getResult() {
    	return result;
    }

	
    public void setResult(int result) {
    	this.result = result;
    }
    
    @Override
   	public String toString(){		
       	StringBuffer buf = new StringBuffer(500);
       	buf.append("address=").append(getHeader().getAddress()).append(", ");
   		buf.append("result=").append(result);
       	return buf.toString();
   	}
}
