package cz.cuni.mff.java.jmy;

import java.io.Serializable;

public class JMYMessageOut implements Serializable {
    private final JMYMessageOutType type;
    private Object result;

    public JMYMessageOut(JMYMessageOutType type, Object result) {
        this.type = type;
        this.result = result;
    }

    public JMYMessageOut(JMYMessageOutType type) {
        this.type = type;
    }

    public JMYMessageOut(Throwable ex) {
        this.type = JMYMessageOutType.EXCEPTION;
        this.result = ex;
    }
    
    public JMYMessageOutType getType() {
        return type;
    }

    public Object getResult() {
        return result;
    }
    
    
}
