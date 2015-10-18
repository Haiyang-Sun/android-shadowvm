package ch.usi.dag.demo.netdiagnose.disl;

import java.io.FileDescriptor;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import ch.usi.dag.demo.netdiagnose.analysis.NetworkAnalysisStub;
import ch.usi.dag.disl.annotation.AfterReturning;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.processorcontext.ArgumentProcessorContext;
import ch.usi.dag.disl.processorcontext.ArgumentProcessorMode;

public class DiSLClass {
    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.connect(java.io.FileDescriptor,java.net.InetAddress,int,int)")
    public static void network_connect(final ArgumentProcessorContext apc, final DynamicContext dc){
        final Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        final FileDescriptor fd = (FileDescriptor)args[0];
        final InetAddress address = (InetAddress)args[1];
        final int port = (int)args[2];
        final int timeoutMs = (int) args[3];
        final boolean successful = dc.getStackValue (0, boolean.class);
        NetworkAnalysisStub.newConnection (fd, address, port, timeoutMs, successful);
    }

    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.sendto(java.io.FileDescriptor,byte[],int,int,int,java.net.InetAddress,int)")
    public static void sendto(final ArgumentProcessorContext apc, final DynamicContext dc){
        final Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        final FileDescriptor fd = (FileDescriptor)args[0];
        final byte[] buffer = (byte[])args[1];
        final int byteOffset = (int)args[2];
        //final int byteCount = (int)args[3];
        final int flags  = (int)args[4];
        final InetAddress address = (InetAddress)args[5];
        final int port = (int)args[6];
        final int sentSize = dc.getStackValue (0, int.class);
        if(sentSize > 0) {
            NetworkAnalysisStub.sendMessage (fd, buffer, byteOffset, sentSize, flags, address, port);
        }else {
            NetworkAnalysisStub.sendMessageFailed(fd, buffer, 0, buffer.length, flags, address, port);
        }
    }

    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.sendto(java.io.FileDescriptor,java.nio.ByteBuffer,int,java.net.InetAddress,int)")
    public static void sendto_bytebuffer(final ArgumentProcessorContext apc, final DynamicContext dc){
        final Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        final FileDescriptor fd = (FileDescriptor)args[0];
        final ByteBuffer buffer = (ByteBuffer)args[1];
        final int flags  = (int)args[2];
        final InetAddress address = (InetAddress)args[3];
        final int port = (int)args[4];
        final int sentSize = dc.getStackValue (0, int.class);
        if(sentSize > 0) {
            NetworkAnalysisStub.sendMessage (fd, buffer.array (), buffer.position () - sentSize, sentSize, flags, address, port);
        }else {
            NetworkAnalysisStub.sendMessageFailed (fd, buffer.array (), 0, buffer.position (), flags, address, port);
        }
    }

    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.recvfrom(boolean,java.io.FileDescriptor,byte[],int,int,int,java.net.DatagramPacket,boolean)")
    public static void recvfrom (
        final ArgumentProcessorContext apc, final DynamicContext dc
        ){
        final Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        //final boolean isRead = (boolean)args[0];
        final FileDescriptor fd = (FileDescriptor)args[1];
        final byte [] bytes = (byte[])args[2];
        final int byteOffset = (int)args[3];
        //final int byteCount = (int)args[4];
        final int flags = (int) args[5];
        final DatagramPacket packet = (DatagramPacket)args[6];
        //final boolean isConnected = (boolean)args[7];
        final int ret = dc.getStackValue (0, int.class);
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
        final ArgumentProcessorContext apc, final DynamicContext dc
        ){
        final Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        //final boolean isRead = (boolean)args[0];
        final FileDescriptor fd = (FileDescriptor)args[1];
        final ByteBuffer buffer = (ByteBuffer)args[2];
        final int flags = (int)args[3];
        final DatagramPacket packet = (DatagramPacket)args[4];
        //final boolean isConnected = (boolean)args[5];
        final int ret = dc.getStackValue (0, int.class);
        if(ret > 0) {
            NetworkAnalysisStub.recvMessage (fd, buffer.array (), buffer.position () - ret, ret, flags, packet.getAddress (), packet.getPort ());
        }else {
            NetworkAnalysisStub.recvMessageFailed (fd, null, 0, 0, flags, packet.getAddress (), packet.getPort ());
        }
    }

    @AfterReturning(marker=BodyMarker.class,
    order = 1,
    scope="libcore.io.IoBridge.bind(java.io.FileDescriptor,java.net.InetAddress,int)")
    public static void libcore_io_IoBridge_bind (final ArgumentProcessorContext apc, final DynamicContext dc) {
        final Object [] args = apc.getArgs (ArgumentProcessorMode.METHOD_ARGS);
        final FileDescriptor fd = (FileDescriptor)args[0];
        final InetAddress address = (InetAddress)args[1];
        final int port = (int)args[2];
        NetworkAnalysisStub.bind(fd, address, port);
    }

}
