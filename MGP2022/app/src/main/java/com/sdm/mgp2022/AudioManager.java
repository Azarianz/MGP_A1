package com.sdm.mgp2022;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    public final static AudioManager Instance = new AudioManager();

    private SurfaceView view = null;
    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();
    private boolean sfxState = true;
    private boolean musicState = true;

    private AudioManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        view = _view;
        Release();
    }

    // after loading, adjust volume or set volume
    // vol range 0.0f to 1.0f
    // left and right vol (3d sound)
    public void PlayAudio(int _id, float _volume)
    {
        // if audio is loaded
        if(audioMap.containsKey(_id))
        {
            // have the clip
            MediaPlayer curr = audioMap.get(_id);
            curr.seekTo(0);
            curr.setVolume(_volume, _volume);
            curr.start();
        }
        else
        {
            MediaPlayer curr = MediaPlayer.create(view.getContext(), _id);
            audioMap.put(_id, curr);
            curr.start();
        }
    }

    public void StopAudio(int _id)
    {
        MediaPlayer curr = audioMap.get(_id);
        curr.stop();
    }

    // release the resource
    public void Release()
    {
        for(HashMap.Entry<Integer, MediaPlayer> entry : audioMap.entrySet())
        {
            entry.getValue().stop();
            entry.getValue().reset();
            entry.getValue().release();
        }
        // empty it
        audioMap.clear();
    }

    private MediaPlayer GetAudio(int _id)
    {
        // check if audio is loaded
        if(audioMap.containsKey(_id))
        {
            // had the clip
            return audioMap.get(_id);
        }
        MediaPlayer result = MediaPlayer.create(view.getContext(), _id);
        audioMap.put(_id, result);
        return result;
    }

    public void SetAudioState(boolean _sfxState, boolean _musicState)
    {
        sfxState = _sfxState;
        musicState = _musicState;
    }

    public boolean GetSFXState()
    {
        return sfxState;
    }

    public boolean GetMusicState()
    {
        return musicState;
    }
}