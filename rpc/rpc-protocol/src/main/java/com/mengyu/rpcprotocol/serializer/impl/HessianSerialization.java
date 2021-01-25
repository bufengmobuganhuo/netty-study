package com.mengyu.rpcprotocol.serializer.impl;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;
import com.mengyu.rpcprotocol.serializer.RpcSerialization;
import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author yuzhang
 * @date 2021/1/18 下午8:40
 * TODO
 */
@Slf4j
@Component
public class HessianSerialization implements RpcSerialization {
    @Override
    public <T> byte[] serialize(T obj) throws Exception {
        if (obj == null) {
            throw new NullPointerException();
        }
        byte[] results;
        HessianSerializerOutput hessianOutput;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            hessianOutput = new HessianSerializerOutput(os);
            hessianOutput.writeObject(obj);
            hessianOutput.flush();
            results = os.toByteArray();
        } catch (Exception e) {
            throw new SerializationException(e);
        }
        return results;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws Exception {
        if (data == null){
            throw new NullPointerException();
        }
        T result;
        try (ByteArrayInputStream is = new ByteArrayInputStream(data)){
            HessianSerializerInput hessianInput = new HessianSerializerInput(is);
            result = (T) hessianInput.readObject(clz);
        }catch (Exception e){
            throw new DeserializationException(e);
        }
        return result;
    }
}
