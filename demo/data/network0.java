public class DiSLClass {
    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.connect(java.io.FileDescriptor,java.net.InetAddress,int,int)")
    public static void network_connect(ArgumentProcessorContext apc, DynamicContext dc){
        Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        FileDescriptor fd = (FileDescriptor)args[0];
        InetAddress address = (InetAddress)args[1];
        int port = (int)args[2];
        int timeoutMs = (int) args[3];
        boolean successful = dc.getStackValue (0, boolean.class);
        NetworkAnalysisStub.newConnection (fd, address, port, timeoutMs, successful);
    }

    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.sendto(java.io.FileDescriptor,byte[],int,int,int,java.net.InetAddress,int)")
    public static void sendto(ArgumentProcessorContext apc, DynamicContext dc){
        Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        FileDescriptor fd = (FileDescriptor)args[0];
        byte[] buffer = (byte[])args[1];
        int byteOffset = (int)args[2];
        int flags  = (int)args[4];
        InetAddress address = (InetAddress)args[5];
        int port = (int)args[6];
        int sentSize = dc.getStackValue (0, int.class);
        if(sentSize > 0) {
            NetworkAnalysisStub.sendMessage (fd, buffer, byteOffset, sentSize, flags, address, port);
        }
    }

    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.sendto(java.io.FileDescriptor,java.nio.ByteBuffer,int,java.net.InetAddress,int)")
    public static void sendto_bytebuffer(ArgumentProcessorContext apc, DynamicContext dc){
        Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        FileDescriptor fd = (FileDescriptor)args[0];
        ByteBuffer buffer = (ByteBuffer)args[1];
        int flags  = (int)args[2];
        InetAddress address = (InetAddress)args[3];
        int port = (int)args[4];
        int sentSize = dc.getStackValue (0, int.class);
        if(sentSize > 0) {
            NetworkAnalysisStub.sendMessage (fd, buffer.array (), buffer.position () - sentSize, sentSize, flags, address, port);
        }
    }

    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.recvfrom(boolean,java.io.FileDescriptor,byte[],int,int,int,java.net.DatagramPacket,boolean)")
    public static void recvfrom (
        ArgumentProcessorContext apc, DynamicContext dc
        ){
        Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        FileDescriptor fd = (FileDescriptor)args[1];
        byte [] bytes = (byte[])args[2];
        int byteOffset = (int)args[3];
        int flags = (int) args[5];
        DatagramPacket packet = (DatagramPacket)args[6];
        int ret = dc.getStackValue (0, int.class);
        if(ret > 0) {
            NetworkAnalysisStub.recvMessage (fd, bytes, byteOffset, ret, flags, packet.getAddress (), packet.getPort ());
        }else {
            NetworkAnalysisStub.recvMessageFailed (fd, bytes, byteOffset, 0, flags, packet.getAddress (), packet.getPort ());
        }
    }

    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.recvfrom(boolean,java.io.FileDescriptor,java.nio.ByteBuffer,int,java.net.DatagramPacket,boolean)")
    public static void recvfrom_bytebuffer (
        ArgumentProcessorContext apc, DynamicContext dc
        ){
        Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        FileDescriptor fd = (FileDescriptor)args[1];
        ByteBuffer buffer = (ByteBuffer)args[2];
        int flags = (int)args[3];
        DatagramPacket packet = (DatagramPacket)args[4];
        int ret = dc.getStackValue (0, int.class);
        if(ret > 0) {
            NetworkAnalysisStub.recvMessage (fd, buffer.array (), buffer.position () - ret, ret, flags, packet.getAddress (), packet.getPort ());
        }
    }

    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.bind(java.io.FileDescriptor,java.net.InetAddress,int)")
    public static void libcore_io_IoBridge_bind (ArgumentProcessorContext apc, DynamicContext dc) {
        Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        FileDescriptor fd = (FileDescriptor)args[0];
        InetAddress address = (InetAddress)args[1];
        int port = (int)args[2];
        NetworkAnalysisStub.bind(fd, address, port);
    }

}
