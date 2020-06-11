package cz.cuni.mff.java.mbeans.not;

import javax.management.*;

public class MyClass extends NotificationBroadcasterSupport implements MyClassMBean {

    private int state = 0;
    private String hidden = null;
    private long sequenceNumber = 0;

    @Override
    public int getState() {
        return (state);
    }

    @Override
    public void setState(int s) {
        int oldState = state;
        state = s;
        Notification n = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(), "State changed", "State", "int", oldState, state);
        sendNotification(n);
    }

    public String getHidden() {
        return (hidden);
    }

    public void setHidden(String h) {
        hidden = h;
    }

    @Override
    public void reset() {
        int oldState = state;
        state = 0;
        hidden = null;
        Notification n = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(), "State changed", "State", "int", oldState, state);
        sendNotification(n);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }
}
