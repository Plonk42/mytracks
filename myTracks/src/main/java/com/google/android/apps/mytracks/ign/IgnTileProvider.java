package com.google.android.apps.mytracks.ign;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.maps.mytracks.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IgnTileProvider implements TileProvider {

  private static final String TAG = IgnTileProvider.class.getSimpleName();

  private static final int TILE_SIZE = 256;

  private static String buildBaseUrl(Context context, int mapType) {
    StringBuilder stringBuilder = new StringBuilder(256);
    stringBuilder.append(context.getString(R.string.ign_api_host));
    stringBuilder.append('/');
    stringBuilder.append(context.getString(R.string.ign_api_key));
    stringBuilder.append('/');
    stringBuilder.append(context.getString(R.string.ign_api_path));
    stringBuilder.append('?');
    stringBuilder.append(context.getString((mapType == Ign.IGN_TYPE_MAP) ? R.string.ign_layer_map : R.string.ign_layer_satellite));
    stringBuilder.append(context.getString(R.string.ign_api_args));
    stringBuilder.append(context.getString(R.string.ign_api_coordinates));
    return stringBuilder.toString();
  }

  private String baseUrl;

  public IgnTileProvider(Context context, int mapType) {
    baseUrl = buildBaseUrl(context, mapType);
  }

  protected URL getTileUrl(int x, int y, int zoom) {
    String urlStr = baseUrl.replace("{z}", "" + zoom).replace("{x}", "" + x).replace("{y}", "" + y);
    try {
      return new URL(urlStr);
    } catch (MalformedURLException e) {
      Log.e(TAG, "getTileUrl: malformed URL " + urlStr);
      return null;
    }
  }

  protected void setupConnection(HttpURLConnection connection) {
    connection.setRequestProperty("User-Agent", "Android");
    connection.setRequestProperty("Referer", "localhost");
    connection.addRequestProperty("Cache-Control", "max-stale=99999");
    connection.addRequestProperty("Cache-Control", "max-age=99999");
    connection.addRequestProperty("Cache-Control", "public");
  }

  public final Tile getTile(int x, int y, int zoom) {
    URL url = this.getTileUrl(x, y, zoom);
    if (url == null) {
      return TileProvider.NO_TILE;
    }

    HttpURLConnection httpURLConnection = null;
    try {
      httpURLConnection = (HttpURLConnection) url.openConnection();
      setupConnection(httpURLConnection);

      InputStream is = null;
      ByteArrayOutputStream baos = null;
      try {
        is = new BufferedInputStream(httpURLConnection.getInputStream());
        baos = new ByteArrayOutputStream();
        byte[] byteChunk = new byte[4096];
        int n;
        while ((n = is.read(byteChunk)) > 0) {
          baos.write(byteChunk, 0, n);
        }
        return new Tile(TILE_SIZE, TILE_SIZE, baos.toByteArray());
      } finally {
        if (is != null) {
          is.close();
        }
        if (baos != null) {
          baos.close();
        }
      }
    } catch (IOException e) {
      Log.e(TAG, "getTile", e);
    } finally {
      if (httpURLConnection != null) {
        httpURLConnection.disconnect();
      }
    }
    return TileProvider.NO_TILE;
  }

}
