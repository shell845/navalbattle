/**
 * @author YC 04/06/2020
 */

package com.shell.navalbattle.netty;

public abstract  class Msg {
    public abstract byte[] toBytes();

    public abstract void parse(byte[] bytes);

    public abstract void handle();

    public abstract MsgType getMsgType();
}
