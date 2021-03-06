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
package org.mobicents.ss7.management.console.impl;

import org.mobicents.ss7.management.console.CommandContext;
import org.mobicents.ss7.management.console.CommandHandlerWithHelp;
import org.mobicents.ss7.management.console.Tree;
import org.mobicents.ss7.management.console.Tree.Node;

/**
 * @author amit bhayani
 *
 */
public class SccpCommandHandler extends CommandHandlerWithHelp {

    static final Tree commandTree = new Tree("sccp");
    static {
        Node parent = commandTree.getTopNode();
        Node sap = parent.addChild("sap");
        sap.addChild("create");
        sap.addChild("modify");
        sap.addChild("delete");
        sap.addChild("show");

        Node dest = parent.addChild("dest");
        dest.addChild("create");
        dest.addChild("modify");
        dest.addChild("delete");
        dest.addChild("show");

        Node rule = parent.addChild("rule");
        rule.addChild("create");
        rule.addChild("modify");
        rule.addChild("delete");
        rule.addChild("show");

        Node address = parent.addChild("address");
        address.addChild("create");
        address.addChild("modify");
        address.addChild("delete");
        address.addChild("show");

        Node rsp = parent.addChild("rsp");
        rsp.addChild("create");
        rsp.addChild("modify");
        rsp.addChild("delete");
        rsp.addChild("show");

        Node rss = parent.addChild("rss");
        rss.addChild("create");
        rss.addChild("modify");
        rss.addChild("delete");
        rss.addChild("show");

        Node lmr = parent.addChild("lmr");
        lmr.addChild("create");
        lmr.addChild("modify");
        lmr.addChild("delete");
        lmr.addChild("show");

        Node csp = parent.addChild("csp");
        csp.addChild("create");
        csp.addChild("modify");
        csp.addChild("delete");
        csp.addChild("show");

        Node set = parent.addChild("set");

    };

    public SccpCommandHandler() {
        super(commandTree, CONNECT_MANDATORY_FLAG);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mobicents.ss7.management.console.CommandHandler#isValid(java.lang .String)
     */
    public void handle(CommandContext ctx, String commandLine) {
        // TODO Validate command
        if (commandLine.contains("--help")) {
            this.printHelp(commandLine, ctx);
            return;
        }
        ctx.sendMessage(commandLine);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mobicents.ss7.management.console.CommandHandler#isAvailable(org.mobicents
     * .ss7.management.console.CommandContext)
     */
    public boolean isAvailable(CommandContext ctx) {
        if (!ctx.isControllerConnected()) {
            ctx.printLine("The command is not available in the current context. Please connnect first");
            return false;
        }
        return true;
    }

}
