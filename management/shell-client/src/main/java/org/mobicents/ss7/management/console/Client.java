/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2013, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.mobicents.ss7.management.console;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;

import org.mobicents.ss7.management.transceiver.ChannelProvider;
import org.mobicents.ss7.management.transceiver.ChannelSelectionKey;
import org.mobicents.ss7.management.transceiver.ChannelSelector;
import org.mobicents.ss7.management.transceiver.Message;
import org.mobicents.ss7.management.transceiver.ShellChannel;


/**
 *
 * @author amit bhayani
 *
 */
public class Client {

    private ChannelProvider provider;
    private ShellChannel channel;
    private ChannelSelector selector;
    private ChannelSelectionKey skey;

    private boolean isConnected = false;

    private boolean wrote = false;

    public Client() {
        provider = ChannelProvider.provider();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void connect(InetSocketAddress endpoint) throws IOException {

        channel = provider.openChannel();
        // channel.bind(new InetSocketAddress(address, port));

        selector = provider.openSelector();
        skey = channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        if (!channel.connect(endpoint)) {
            while (!channel.finishConnect()) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
        }
        this.isConnected = true;
    }

    public Message run(Message outgoing) throws IOException {

        if (!this.isConnected)
            return provider.getMessageFactory().createMessage("Not yet connected");

        if (outgoing != null)
            channel.send(outgoing);

        int i;
        Message msg;
        for (i = 0; i < 150; i++) {
            selector.selectNow();
            msg = (Message) channel.receive();
            if (msg != null)
                return msg;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        throw new IOException("No response from server");
    }

    protected void stop() {
        this.isConnected = false;

        if (skey != null) {
            skey.cancel();
            skey = null;
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
            }
            selector = null;
        }

        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
            }
            channel = null;
        }
    }
}
