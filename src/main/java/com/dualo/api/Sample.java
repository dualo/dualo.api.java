package com.dualo.api;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/07/13
 * Time: 20:07
 */


/* Redirect Dualo node stream to the system speaker and redisplay to Dualo */
public class Sample {

    public static void main(String[] args) {
        final DualoDevice dualo = new DualoDevice();
        final SystemSynthesizer systemSynthesizer = new SystemSynthesizer();
        dualo.addNodeReceiver(new NoteReceiver() {
            @Override
            public void note(int note, int velocity, boolean down, int channel) {
                if (down) {
                    dualo.undisplayNote(note,DualoDevice.channelToKeyboard(channel));
                } else {
                    dualo.displayNote(note,DualoDevice.channelToKeyboard(channel));
                }
            }
        });
        dualo.addNodeReceiver(systemSynthesizer);
    }


}
