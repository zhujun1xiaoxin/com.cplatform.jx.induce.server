package com.cplatform.jx.induce.server.protocol.message;
import java.nio.ByteBuffer;

import com.cplatform.jx.induce.server.protocol.tools.ToolKits;

/**
 * �����ļ�����
 * ���⡢��Ҫ˵��. <br>
 * ����ϸ˵��.
 * <p>
 * Copyright: Copyright (c) 2017��2��6�� ����4:10:46
 * <p>
 * Company: ��������ʮ�����ּ������޹�˾
 * <p>
 * @author huajun@c-platform.com
 * @version 1.0.0
 */
public class SendFileContentMessage extends AbstractMessage {

	public static final int FILE_BLOCK_MAX_SIZE=65535 ;
	 /**
     * ����������ʼ�������� ��ʼ�����ַ�������Ŀ���Ƿ�ֹ��ָ���쳣
     */
    public SendFileContentMessage(Header header)
    {
        super(header);
        this.getHeader().setCommand(SEND_FILE_CONTENT);
        this.getHeader().setTotalLength(9);//����Ϣ����
        this.getHeader().setAddress(getAddress());
        this.getHeader().setBegin(MESSAGE_BEGIN);
    }

    /**
     * ���ӿ�
     */
    public ByteBuffer byteBufferedMessage(int length)
    {
        if (length < 9) // ����Ϣ����Ϊ8���ֽ�
        {
            return null;
        }
        this.getHeader().setAddress(getAddress());
        //����ͷ�� �� ��ʼ����
        //ת��  0xAA �� 0xCC �� 0xEE
        byte[] headbytes = getHeader().byteMessage(3);
        
        int len =3+2;
        byte [] namebytes =getFileContent();  
        if(namebytes != null)
        len+=namebytes.length;      
       
        byte[] conbytes =new byte[len];
        System.arraycopy(headbytes, 0, conbytes, 0, headbytes.length);

        ToolKits.int2buf(getBlockNum(), conbytes, 3);
        
        if(namebytes != null)
        System.arraycopy(namebytes, 0, conbytes, 5, namebytes.length);
        
             
        byte[] ecsbytes= ToolKits.covertChar(conbytes);
        int ecslength = ecsbytes.length;
        //ת��� ���� ��ʼ������
        byte[] bytes = new byte[ecslength+2];
        bytes[0] =(byte)MESSAGE_BEGIN;
        System.arraycopy(ecsbytes, 0, bytes, 1, ecslength);
        bytes[ecslength+1] =(byte)MESSAGE_END;
        
        //����CRC16��֤��       
        ByteBuffer buffer = ByteBuffer.allocate(ecslength+2+2);
        buffer.put(bytes);
        buffer.put(byteCRC16(bytes));
        buffer.flip();
        return buffer;
    }

    /**
     * ���ӿ�
     */
    public int byteToMessage(ByteBuffer buffer)
    {
        if (buffer == null)
        {
            return MESSAGE_ERROR;
        }
        else
        {
        return MESSAGE_SUCCESS;
        }
    }
    
	/**�豸��ַ*/
	private int address;
	
	/**��� */
	private int blockNum;
	/**�ļ��ڶ�*/
	private byte[] fileContent;

	
    public int getAddress() {
    	return address;
    }

	
    public void setAddress(int address) {
    	this.address = address;
    }

	
    public int getBlockNum() {
    	return blockNum;
    }

	
    public void setBlockNum(int blockNum) {
    	this.blockNum = blockNum;
    }

	
    public byte[] getFileContent() {
    	return fileContent;
    }

	
    public void setFileContent(byte[] fileContent) {
    	this.fileContent = fileContent;
    }

	
    

}