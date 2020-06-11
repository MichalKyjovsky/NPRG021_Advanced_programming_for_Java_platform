package cz.cuni.mff.java.jmy;

import java.io.Serializable;

public class JMYMessageIn implements Serializable {
    private final JMYMessageInType type;
    private final String objectName;
    private final String methodName;
    private final Class<?>[] argTypes;
    private final Object[] args;

    public JMYMessageIn(JMYMessageInType type, String objectName, String methodName, Class<?>[] argTypes, Object[] args) {
        this.type = type;
        this.objectName = objectName;
        this.methodName = methodName;
        this.argTypes = argTypes;
        this.args = args;
    }

    public JMYMessageIn(String objectName) {
        this(JMYMessageInType.QUERY, objectName, null, null, null);
    }
    
    public JMYMessageIn(String objectName, String methodName, Class<?>[] argTypes, Object[] args) {
        this(JMYMessageInType.CALL, objectName, methodName, argTypes, args);
    }

    public JMYMessageInType getType() {
        return type;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class<?>[] getArgTypes() {
        return argTypes;
    }
}
