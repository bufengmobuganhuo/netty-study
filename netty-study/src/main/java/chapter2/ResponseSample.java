package chapter2;

/**
 * @author yuzhang
 * @date 2020/11/19 下午6:32
 * TODO
 */
public class ResponseSample {
    private String code;
    private String data;
    private long timestamp;

    public ResponseSample(String code, String data, long timestamp) {
        this.code = code;
        this.data = data;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
