package com.dualo.api;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/07/13
 * Time: 18:48
 */
public class SystemSynthesizer implements NoteReceiver {

    private MidiChannel defChannel;
    private Synthesizer synthesizer = null;

    public SystemSynthesizer() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            defChannel = synthesizer.getChannels()[0];
            defChannel.controlChange(7,255); //set volume
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void note(int note, int velocity, boolean down, int channel) {
        if(down){
            defChannel.noteOff(note, velocity);
        } else {
            defChannel.noteOn(note, velocity);
        }
    }
}
