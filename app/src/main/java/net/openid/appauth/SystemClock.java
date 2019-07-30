package net.openid.appauth;

public class SystemClock implements Clock {
    @Override
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
