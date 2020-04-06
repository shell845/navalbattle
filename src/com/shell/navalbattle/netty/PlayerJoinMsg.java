/**
 * @author YC 04/06/2020
 */

package com.shell.navalbattle.netty;

import com.shell.navalbattle.NavalFrame;
import com.shell.navalbattle.gameobjects.Directions;
import com.shell.navalbattle.gameobjects.Groups;
import com.shell.navalbattle.gameobjects.SubmarinePlayer;

import java.io.*;
import java.util.UUID;

public class PlayerJoinMsg extends Msg {
    private int x, y;
    private Directions dir;
    private Groups group;
    private UUID id;

    public PlayerJoinMsg() {}

    public PlayerJoinMsg (SubmarinePlayer p) {
        this.x = p.getPosX();
        this.y = p.getPosY();
        this.dir = p.getDirection();
        this.group = p.getGroup();
        this.id = p.getUUID();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Directions getDir() {
        return dir;
    }

    public void setDir(Directions dir) {
        this.dir = dir;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(dos != null)
                    dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;

    }

    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Directions.values()[dis.readInt()];
            this.group = Groups.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String toString() {
        return "PlayerJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                ", id=" + id +
                '}';
    }

    public void handle() {
        //if this msg's id equals my tank's id return
        //otherwise add new tank to my collection
        if(this.id.equals(NavalFrame.MAIN_FRAME.getGameModel().getMySubmarine().getUUID())) return;
        if(NavalFrame.MAIN_FRAME.getGameModel().findSubmarineByUUID(this.id) != null) return;

        SubmarinePlayer p = new SubmarinePlayer(this.x, this.y, this.dir);

        NavalFrame.MAIN_FRAME.getGameModel().addGameObject(p);

        Client.client_instance.send(new PlayerJoinMsg(NavalFrame.MAIN_FRAME.getGameModel().getMySubmarine()));
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.PlayerJoin;
    }
}
