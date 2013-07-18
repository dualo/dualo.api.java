package com.dualo.api.internal;

import com.dualo.api.NoteReceiver;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/07/13
 * Time: 15:39
 */
public class DualoMidiInputReceiver implements Receiver, NoteReceiver {

    private List<NoteReceiver> childs = null;

    public void setChilds(List<NoteReceiver> childs) {
        this.childs = childs;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        ShortMessage msg = (ShortMessage) message;
        switch (msg.getCommand()) {
            case ShortMessage.NOTE_OFF:
                note(msg.getData1(), msg.getData1(), true,msg.getChannel());
                break;
            case ShortMessage.NOTE_ON:
                note(msg.getData1(), msg.getData1(), false,msg.getChannel());
                break;
            default:
                break;
        }
    }

    @Override
    public void close() {
    }

    @Override
    public void note(int note, int velocity, boolean down, int channel) {
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).note(note, velocity, down,channel);
        }
    }
}
