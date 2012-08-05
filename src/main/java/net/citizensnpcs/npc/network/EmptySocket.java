package net.citizensnpcs.npc.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EmptySocket extends Socket {

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(EMPTY);
    }

    @Override
    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream(1);
    }

    private static final byte[] EMPTY = new byte[0];
}