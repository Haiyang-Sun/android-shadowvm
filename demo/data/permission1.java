public class IPCAnalysis extends RemoteIPCAnalysis {
    public static String analysisTag = "PermissionUsage";
    public void permissionUsed (
        Context ctx, int tid, ShadowString permissionName) {
        List<ThreadState> callers = ThreadState.getCallers (ctx, tid);
        for(ThreadState caller:callers){
            caller.addPermission(permissionName.toString ());
        }
    }
    @Override
    public void onRequestSent (
        TransactionInfo info, NativeThread client, Context ctx) {

        ThreadState clientState = ThreadState.get (client);
        clientState.recordRequestSent(client,info);
    }
    @Override
    public void onRequestReceived (
        TransactionInfo info, NativeThread client,
        NativeThread server, Context ctx) {
        ThreadState clientState = ThreadState.get (client);
        clientState.waitForRequestSent (info, server);
        ThreadState serverState = ThreadState.get (server);
        serverState.recordRequestReceived(client, server, info);
    }
    @Override
    public void onResponseSent (
        TransactionInfo info, NativeThread client, NativeThread server, Context ctx) {
            ThreadState serverState = ThreadState.get (server);
            serverState.recordResponseSent(client, server, info);
    }
    @Override
    public void onResponseReceived (
        TransactionInfo info, NativeThread client, NativeThread server, Context ctx) {
        ThreadState serverState = ThreadState.get (server);
        ThreadState clientState = ThreadState.get (client);
        if(clientState.checkResponseReceivedValid(info,server)) {
            serverState.waitForResponseSent (info, client);
            if(clientState.getPermissionCount()>0){
                IPCLogger.reportPermissionUsage (clientState);
                clientState.clearPermissions ();
            }
            clientState.recordResponseReceived(client, server, info);
        }
    }
}
