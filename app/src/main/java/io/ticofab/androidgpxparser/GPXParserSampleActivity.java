package io.ticofab.androidgpxparser;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.ticofab.androidgpxparser.parser.GPXParser;
import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.Track;
import io.ticofab.androidgpxparser.parser.domain.TrackPoint;
import io.ticofab.androidgpxparser.parser.domain.TrackSegment;

public class GPXParserSampleActivity extends AppCompatActivity {

    static final String TAG = GPXParserSampleActivity.class.getSimpleName();

    // consider injection with, eg. Dagger2
    GPXParser mParser = new GPXParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gpx parsedGpx = null;
        try {
            InputStream in = getAssets().open("test.gpx");
            parsedGpx = mParser.parse(in);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        if (parsedGpx != null) {
            // log stuff
            List<Track> tracks = parsedGpx.getTracks();
            for (int i = 0; i < tracks.size(); i++) {
                Track track = tracks.get(i);
                Log.d(TAG, "track " + i + ":");
                List<TrackSegment> segments = track.getTrackSegments();
                for (int j = 0; j < segments.size(); j++) {
                    TrackSegment segment = segments.get(j);
                    Log.d(TAG, "  segment " + j + ":");
                    for (TrackPoint trackPoint : segment.getTrackPoints()) {
                        Log.d(TAG, "    point: lat " + trackPoint.getLatitude() + ", lon " + trackPoint.getLongitude());
                    }
                }
            }
        } else {
            Log.e(TAG, "Error parsing gpx track!");
        }
    }
}
