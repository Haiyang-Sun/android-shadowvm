public class NetworkAnalysis extends RemoteAnalysis {
    private static String PREDEFINED_KEYWORD = "NONE";

    public static void bind (Context ctx, int tid,
        int fdHash, ShadowString address, int port) {
        WebLogger.reportNetworkBind (
            ctx.getProcessID (), ctx.getPname (), tid, fdHash,
            address==null?"Unknown":address.toString (), port, SVMCallStack.get (ctx, tid).getRuntimeStack ());
    }


    public static void newConnection (Context ctx, int tid,
        int fdHash, ShadowString address, int port, int timeoutMs, boolean successful) {
        ProcessProfiler processProfile = ProcessProfiler.initProfilerIfAbsent (ctx);
        ConnectionStruct.initConnectionIfAbsent (processProfile, fdHash, address.toString (), port);
        WebLogger.reportNetworkConnect (
            ctx.getProcessID (), ctx.getPname (), tid, fdHash,
            address==null?"Unknown":address.toString (), port, SVMCallStack.get (ctx, tid).getRuntimeStack ());
    }

    public static void sendMessage (Context ctx, int tid, int fdHash,
        ShadowString dataBase64, int flags, ShadowString address, int port){
        ProcessProfiler processProfile = ProcessProfiler.initProfilerIfAbsent(ctx);
        ConnectionStruct connection = ConnectionStruct.initConnectionIfAbsent (
            processProfile, fdHash, address==null?"Unknown ":address.toString (), port);
        connection.addNewData (dataBase64.toString ());
        if(connection.searchInSentData(PREDEFINED_KEYWORD)){
            //report
        }
        WebLogger.reportNetworkSend (
            ctx.getProcessID (), ctx.getPname (), tid, fdHash,
            connection.address, connection.port, Base64.decode (dataBase64.toString (), Base64.DEFAULT),
            SVMCallStack.get (ctx, tid).getRuntimeStack ());
    }

    public static void recvMessage (Context ctx, int tid, int fdHash,
        ShadowString dataBase64, int flags, ShadowString address, int port){
        ProcessProfiler processProfile = ProcessProfiler.initProfilerIfAbsent(ctx);
        ConnectionStruct connection = ConnectionStruct.initConnectionIfAbsent (
            processProfile, fdHash, address==null?"Unknown ":address.toString (), port);
        WebLogger.reportNetworkRecv(ctx.getProcessID (), ctx.getPname (), tid, fdHash,
            connection.address,connection.port, Base64.decode (dataBase64.toString (), Base64.DEFAULT),
            SVMCallStack.get (ctx, tid).getRuntimeStack ());
    }

    static class ConnectionStruct{
        public ConnectionStruct (int fdHash, String address, int port) {
            this.fdHash = fdHash;
            this.address = address;
            this.port = port;
            bf = ByteBuffer.allocate (1024);
        }
        public ByteBuffer addNewData(String base64){
            byte data[] = Base64.decode (base64.toString (), Base64.DEFAULT);
            while(bf.remaining () < data.length) {
                bf = bf.duplicate ();
            }
            bf.put (data);
            return bf;
        }
        public boolean searchInSentData(String str){
            if(str == null || str.equals ("")) {
                return false;
            }
            return DemoUtils.indexOf(bf.array (), str.getBytes ())  >= 0;
        }
        public static ConnectionStruct initConnectionIfAbsent(
            ProcessProfiler processProfile, int fdHash, String address, int port){
            ConnectionStruct connection = processProfile.get (fdHash);
            if(connection == null){
                ConnectionStruct temp = new ConnectionStruct(fdHash, address, port);
                connection = processProfile.putIfAbsent (fdHash, temp);
                if(connection == null) {
                    connection = temp;
                }
            }
            return connection;
        }
        ByteBuffer bf;
        int fdHash;
        String address;
        int port;
    }

    static class ProcessProfiler extends ConcurrentHashMap <Integer, ConnectionStruct> {
        public ProcessProfiler (int pid) {
            this.pid = pid;
        }
        int pid;
        public static ProcessProfiler initProfilerIfAbsent(Context ctx){
            ProcessProfiler processProfile = ctx.getState ("Net", ProcessProfiler.class);
            if (processProfile == null) {
                ProcessProfiler temp = new ProcessProfiler (ctx.getProcessID ());
                processProfile = (ProcessProfiler) ctx.setStateIfAbsent ("Net", temp);
                if(processProfile == null) {
                    processProfile = temp;
                }
            }
            return processProfile;
        }
    }
}
