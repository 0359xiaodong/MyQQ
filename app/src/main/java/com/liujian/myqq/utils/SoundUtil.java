package com.liujian.myqq.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by sh.liujian on 2015-9-29.
 */
public class SoundUtil {

    private static SoundPool mSoundPool;

    private static int[] mSoundResId;
    private static int[] mSoundId;
    private static int[] mStreamId;

    private static boolean isStop = false;

    private SoundUtil() {}

    public static void initSoundPool(Context context, int... soundResId) {
        mSoundResId = soundResId;
        mSoundPool = new SoundPool(soundResId.length, AudioManager.STREAM_MUSIC, 0);
        mSoundId = new int[soundResId.length];
        mStreamId = new int[soundResId.length];
        for (int i = 0; i < mSoundResId.length; i++) {
            mSoundId[i] = mSoundPool.load(context, mSoundResId[i], 0);
        }
    }

    public static void playSoundId(int rid) {
        int index = 0;
        for (index = 0; index < mSoundResId.length; index++) {
            if (mSoundResId[index] == rid) {
                break;
            }
        }
        play(index);
    }

    public static void play(int index) {
        if (isStop || index < 0 || index >= mSoundId.length) {
            return;
        }
        if (mSoundPool != null) {
            mStreamId[index] = mSoundPool.play(mSoundId[index], 1, 1, 0, 0, 1);
        }
    }

    public static void disablePlayer(boolean disable) {
        isStop = disable;
        if (mSoundPool != null) {
            for (int streamId : mStreamId) {
                mSoundPool.stop(streamId);
            }
        }
    }
}

