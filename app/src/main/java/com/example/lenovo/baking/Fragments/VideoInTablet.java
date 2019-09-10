package com.example.lenovo.baking.Fragments;


import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.lenovo.baking.Model.tabletModel;
import com.example.lenovo.baking.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoInTablet extends Fragment {
    TextView description_Item;
    tabletModel model;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    MediaSource mediaSource;
  Long POSITION;

    public VideoInTablet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_in_tablet, container, false);

        exoPlayerView = view.findViewById(R.id.exoplayerViewInTablet);
        description_Item = view.findViewById(R.id.descriptionTablet);

        model = new tabletModel();
        Exoplayer();

        if (model.getVideoURL1() == null) {

        } else if (model.getVideoURL1().equals("")) {
            description_Item.setText(model.getDescription1());
        } else {
            description_Item.setText(model.getDescription1());
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);


        }


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        exoPlayer.release();


    }



    void Exoplayer() {
        try {


            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            Uri videoURI = Uri.parse(model.getVideoURL1());

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);


        } catch (Exception e) {
            Log.e("error", " exoplayer error " + e.toString());
        }
    }

}
