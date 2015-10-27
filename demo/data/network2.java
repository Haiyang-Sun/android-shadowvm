package ch.usi.dag.demo.netdiagnose.analysis;

import java.io.FileDescriptor;
import java.net.InetAddress;

import android.util.Base64;
import ch.usi.dag.dislre.AREDispatch;

public class NetworkAnalysisStub {

    public static short BIND = AREDispatch.registerMethod ("ch.usi.dag.demo.netdiagnose.analysis.NetworkAnalysis.bind");
    public static short NEW_CONNECTION = AREDispatch.registerMethod ("ch.usi.dag.demo.netdiagnose.analysis.NetworkAnalysis.newConnection");
    public static short SEND_MESSAGE = AREDispatch.registerMethod ("ch.usi.dag.demo.netdiagnose.analysis.NetworkAnalysis.sendMessage");
    public static short SEND_MESSAGE_FAILED = AREDispatch.registerMethod ("ch.usi.dag.demo.netdiagnose.analysis.NetworkAnalysis.sendMessageFailed");
    public static short RECV_MESSAGE = AREDispatch.registerMethod ("ch.usi.dag.demo.netdiagnose.analysis.NetworkAnalysis.recvMessage");
    public static short RECV_MESSAGE_FAILED = AREDispatch.registerMethod ("ch.usi.dag.demo.netdiagnose.analysis.NetworkAnalysis.recvMessageFailed");


    public static void bind (final FileDescriptor fd, final InetAddress inetAddress, final int port) {
        AREDispatch.analysisStart (BIND);
        AREDispatch.sendInt (AREDispatch.getThisThreadId ());
        AREDispatch.sendInt(fd.hashCode ());
        if(inetAddress != null) {
            AREDispatch.sendObjectPlusData (inetAddress.getHostAddress ());
        } else {
            AREDispatch.sendObjectPlusData (null);
        }
        AREDispatch.sendInt(port);
        AREDispatch.analysisEnd ();
    }

    public static void newConnection (
        final FileDescriptor fd, final InetAddress inetAddress, final int port, final int timeoutMs, final boolean successful) {
        AREDispatch.analysisStart (NEW_CONNECTION);
        AREDispatch.sendInt (AREDispatch.getThisThreadId ());
        AREDispatch.sendInt(fd.hashCode ());
        if(inetAddress != null) {
            AREDispatch.sendObjectPlusData (inetAddress.getHostAddress ());
        } else {
            AREDispatch.sendObjectPlusData (null);
        }
        AREDispatch.sendInt(port);
        AREDispatch.sendInt(timeoutMs);
        AREDispatch.sendBoolean (successful);
        AREDispatch.analysisEnd ();
    }

    public static void sendMessage (final FileDescriptor fd, final byte[] buffer, final int start, final int length, final int flags, final InetAddress inetAddress, final int port) {
        if(length <= 0) {
            return;
        }
        AREDispatch.analysisStart (SEND_MESSAGE);
        AREDispatch.sendInt (AREDispatch.getThisThreadId ());
        AREDispatch.sendInt(fd.hashCode ());
        AREDispatch.sendObjectPlusData (Base64.encodeToString (buffer, start, length, Base64.DEFAULT));
        AREDispatch.sendInt(flags);
        if(inetAddress != null) {
            AREDispatch.sendObjectPlusData (inetAddress.getHostAddress ());
        } else {
            AREDispatch.sendObjectPlusData (null);
        }
        AREDispatch.sendInt(port);
        AREDispatch.analysisEnd ();
    }

    public static void sendMessageFailed (final FileDescriptor fd, final byte[] buffer, final int start, final int length, final int flags, final InetAddress inetAddress, final int port) {
        if(length <= 0) {
            return;
        }
        AREDispatch.analysisStart (SEND_MESSAGE_FAILED);
        AREDispatch.sendInt (AREDispatch.getThisThreadId ());
        AREDispatch.sendInt(fd.hashCode ());
        AREDispatch.sendObjectPlusData (Base64.encodeToString (buffer, start, length, Base64.DEFAULT));
        AREDispatch.sendInt(flags);
        if(inetAddress != null) {
            AREDispatch.sendObjectPlusData (inetAddress.getHostAddress ());
        } else {
            AREDispatch.sendObjectPlusData (null);
        }
        AREDispatch.sendInt(port);
        AREDispatch.analysisEnd ();
    }

    public static void recvMessage (final FileDescriptor fd, final byte[] buffer, final int start, final int length, final int flags, final InetAddress inetAddress, final int port) {
        if(length <= 0) {
            return;
        }
        AREDispatch.analysisStart (RECV_MESSAGE);
        AREDispatch.sendInt (AREDispatch.getThisThreadId ());
        AREDispatch.sendInt(fd.hashCode ());
        AREDispatch.sendObjectPlusData (Base64.encodeToString (buffer, start, length, Base64.DEFAULT));
        AREDispatch.sendInt(flags);
        if(inetAddress != null) {
            AREDispatch.sendObjectPlusData (inetAddress.getHostAddress ());
        } else {
            AREDispatch.sendObjectPlusData (null);
        }
        AREDispatch.sendInt(port);
        AREDispatch.analysisEnd ();
    }
    public static void recvMessageFailed (final FileDescriptor fd, final byte[] buffer, final int start, final int length, final int flags, final InetAddress inetAddress, final int port) {
        if(length <= 0) {
            return;
        }
        AREDispatch.analysisStart (RECV_MESSAGE_FAILED);
        AREDispatch.sendInt (AREDispatch.getThisThreadId ());
        AREDispatch.sendInt(fd.hashCode ());
        AREDispatch.sendObjectPlusData ("");
        AREDispatch.sendInt(flags);
        if(inetAddress != null) {
            AREDispatch.sendObjectPlusData (inetAddress.getHostAddress ());
        } else {
            AREDispatch.sendObjectPlusData (null);
        }
        AREDispatch.sendInt(port);
        AREDispatch.analysisEnd ();
    }

}
