/**
 * @(#)BaseThriftController.java, Jul 25, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.thrift;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.cloudstone.menu.server.thrift.api.UserNotLoginException;

import com.cloudstone.emenu.ctrl.BaseController;
import com.cloudstone.emenu.data.ThriftSession;

/**
 * @author xuhongfeng
 *
 */
public abstract class BaseThriftController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseThriftController.class);
        
    protected static final TProtocolFactory inProtocolFactory = new TBinaryProtocol.Factory();  
    protected static final TProtocolFactory outProtocolFactory = new TBinaryProtocol.Factory();  
    
    //TODO session expired
    protected static final Map<String, ThriftSession> sessionMap = new HashMap<String, ThriftSession>();
    
    protected ThriftSession authorize(String sessionId) throws UserNotLoginException {
        if (!sessionMap.containsKey(sessionId)) {
            throw new UserNotLoginException();
        }
        return sessionMap.get(sessionId);
    }
    
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, TException {
        LOG.info("thrift start: " + request.getRequestURI());

        response.setContentType("application/x-thrift");
        InputStream in = request.getInputStream();
        OutputStream out = response.getOutputStream();

        TTransport transport = new TIOStreamTransport(in, out);
        TTransport inTransport = transport;
        TTransport outTransport = transport;
        TProtocol inProtocol = inProtocolFactory.getProtocol(inTransport);
        TProtocol outProtocol = outProtocolFactory.getProtocol(outTransport);

        getProcessor().process(inProtocol, outProtocol);

        out.flush();
        
        LOG.info("thrift end");
    }
    
    protected abstract TProcessor getProcessor();
}
