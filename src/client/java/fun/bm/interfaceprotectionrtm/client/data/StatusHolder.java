package fun.bm.interfaceprotectionrtm.client.data;

public class StatusHolder {
    public static Status resourceLoadStatus = new Status(false, 0);
    public static Status escScreenStatus = new Status(false, 0);

    public static void updateResourceLoadStatus(boolean loading) {
        resourceLoadStatus = new Status(loading, System.currentTimeMillis());
    }

    public static void updateEscScreenStatus(boolean loading) {
        escScreenStatus = new Status(loading, System.currentTimeMillis());
    }

    public static class Status {
        public final boolean enabled;
        public final long startTime;
        public boolean firstTick = true;

        public Status(boolean enabled, long startTime) {
            this.enabled = enabled;
            this.startTime = startTime;
        }
    }
}
