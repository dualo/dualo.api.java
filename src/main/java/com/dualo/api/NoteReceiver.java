package com.dualo.api;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/07/13
 * Time: 18:22
 */
public interface NoteReceiver {

    public void note(int note, int velocity, boolean down, int channel);

}
