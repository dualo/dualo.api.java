package com.dualo.api;

import com.dualo.api.internal.DualoMidiInputReceiver;
import com.dualo.api.internal.MidiDevicesHelper;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/07/13
 * Time: 18:29
 */
public class DualoDevice {

    private MidiDevice dualoInput = null;
    private MidiDevice dualoOutput = null;
    private DualoMidiInputReceiver midiRec = null;
    private Receiver dualoOutputReceiver = null;

    public DualoDevice() {
        //TODO case of multiple dualo
        try {
            dualoInput = MidiDevicesHelper.getDefaultDualoIN();
            dualoOutput = MidiDevicesHelper.getDefaultDualoOUT();
            Transmitter dualoInputTrans = dualoInput.getTransmitter();
            midiRec = new DualoMidiInputReceiver();
            dualoInputTrans.setReceiver(midiRec);
            dualoOutputReceiver = dualoOutput.getReceiver();

            dualoInput.open();
            dualoOutput.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public enum DualoKeyboard {
        LEFT_KEYBOARD, RIGHT_KEYBOARD
    }

    ;

    public static int keyboardToChannel(DualoKeyboard keyboard) {
        if (keyboard.equals(DualoKeyboard.LEFT_KEYBOARD)) {
            return 0;
        }
        if (keyboard.equals(DualoKeyboard.RIGHT_KEYBOARD)) {
            return 8;
        }
        return 0;
    }

    public static DualoKeyboard channelToKeyboard(int channel) {
        if (channel == 0) {
            return DualoKeyboard.LEFT_KEYBOARD;
        }
        if (channel == 1) {
            return DualoKeyboard.RIGHT_KEYBOARD;
        }
        return DualoKeyboard.LEFT_KEYBOARD;
    }

    public void displayNote(int note, DualoKeyboard keyboard) {
        try {
            ShortMessage shortMsg = new ShortMessage();
            shortMsg.setMessage(ShortMessage.NOTE_ON, keyboardToChannel(keyboard), note, 120); //TODO replace 93 by real velocity
            dualoOutputReceiver.send(shortMsg, System.currentTimeMillis());
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    public void undisplayNote(int note, DualoKeyboard keyboard) {
        try {
            ShortMessage shortMsg = new ShortMessage();
            shortMsg.setMessage(ShortMessage.NOTE_OFF, keyboardToChannel(keyboard), note, 0);
            dualoOutputReceiver.send(shortMsg, System.currentTimeMillis());
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private List<NoteReceiver> receivers = new ArrayList<NoteReceiver>();

    public void addNodeReceiver(NoteReceiver n) {
        receivers.add(n);
        midiRec.setChilds(receivers);
    }

}
