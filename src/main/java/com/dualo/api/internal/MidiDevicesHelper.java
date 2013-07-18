package com.dualo.api.internal;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/07/13
 * Time: 15:18
 */
public class MidiDevicesHelper {

    private static final String intputClassName = "MidiInDevice";
    private static final String outputClassName = "MidiOutDevice";

    public static MidiDevice getDefaultDualoOUT() {
        return getDefaultDualo(outputClassName);
    }

    public static MidiDevice getDefaultDualoIN() {
        return getDefaultDualo(intputClassName);
    }

    private static MidiDevice getDefaultDualo(String className) {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; ++i) {
            MidiDevice.Info info = infos[i];
            if (info.getVendor().toLowerCase().contains("dualo") && info.getClass().getName().toLowerCase().contains(className.toLowerCase())) {
                try {
                    return MidiSystem.getMidiDevice(infos[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
