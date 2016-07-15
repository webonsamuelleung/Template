package com.web_on.template.ui.fragment;

 import android.hardware.Camera;
 import android.media.ExifInterface;
 import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
 import android.view.Surface;
 import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
 import android.widget.LinearLayout;
 import android.widget.RelativeLayout;
 import android.widget.TextView;

 import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
 import com.nostra13.universalimageloader.core.ImageLoader;
 import com.nostra13.universalimageloader.core.assist.ImageScaleType;
 import com.web_on.template.R;
 import com.web_on.template.common.CommonHelper;
 import com.web_on.template.common.FaceOverlayView;
 import com.web_on.template.common.ViewFinderView;

 import org.json.JSONException;
import org.json.JSONObject;

 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Vector;

/**
 * Created by samuel.leung on 11/3/2016.
 */
public class CameraFragment extends Fragment {
    private static final String TAG = CameraFragment.class.getSimpleName();

    private ViewFinderView cameraFocusPreview;

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private SurfaceView surfaceView;

    private LinearLayout imageContainer;
    private TextView AppTextView_takePic, retakeBtn, submitBtn, addMoreBtn;

    private FaceOverlayView mFaceView;
    private int mDisplayOrientation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_fragment, null);

        previewImages = new Vector<PreviewImagesItem>();
        imageContainer = (LinearLayout) view.findViewById(R.id.imageContainer);

        submitBtn = (TextView) view.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retakeImage();
            }
        });

        addMoreBtn = (TextView) view.findViewById(R.id.addMoreBtn);
        addMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retakeImage();
            }
        });

        retakeBtn = (TextView) view.findViewById(R.id.retakeBtn);
        retakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retakeImage();

            }
        });

        AppTextView_takePic = (TextView) view.findViewById(R.id.AppTextView_takePic);
        AppTextView_takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        surfaceView = (SurfaceView) view.findViewById(R.id.cameraSurfaceView);
        cameraFocusPreview = (ViewFinderView) view.findViewById(R.id.cameraFocusPreview);

        initSurfaceView();

        initTakePictureCallback();


        mFaceView = new FaceOverlayView( getActivity() );
        RelativeLayout layout_cam = (RelativeLayout) view.findViewById(R.id.layout_cam);
        layout_cam.addView( mFaceView );



//        volleyConnection();
        return view;
    }

    private void initSurfaceView()  {
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    mCamera = Camera.open();
                    mCamera.setPreviewDisplay(mSurfaceHolder);
                    mCamera.setDisplayOrientation(90);

                    //
                    mCamera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
                        @Override
                        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                            Log.d(TAG, "Number of Faces:" + faces.length);
                            mFaceView.setFaces(faces);
                        }
                    });
                    mCamera.startFaceDetection();
                    //

                }
                catch (IOException e){
                    mCamera.release();
                    mCamera = null;
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged isPreviewRunning " + isPreviewRunning );
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFocusMode("auto");
                mCamera.setParameters(parameters);

                if (isPreviewRunning)
                    stopPreviewCamera();

                previewCamera(holder);


                //
                mDisplayOrientation = getDisplayOrientation(mRotation, 0);
                mCamera.setDisplayOrientation(mDisplayOrientation);
                if (mFaceView != null) {
                    mFaceView.setDisplayOrientation(mDisplayOrientation);
                }
                //

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        });
//        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    private void retakeImage() {
        imagepath = "";
        isPause = false;
        previewCamera(mSurfaceHolder);
        AppTextView_takePic.setVisibility(View.VISIBLE);
        submitBtn.setVisibility(View.GONE);
        retakeBtn.setVisibility(View.GONE);
        addMoreBtn.setVisibility(View.GONE);
    }


    private boolean isPause = false, isPreviewRunning = false, isCapturing = false;
    public void previewCamera(SurfaceHolder holder) {
        if (isPause) return;
        if (isPreviewRunning) stopPreviewCamera();

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            isPreviewRunning = true;
        }
        catch (Exception e) {
            Log.d(TAG, "Cannot start preview", e);
        }
    }
    public void stopPreviewCamera() {
        isPreviewRunning = false;
        mCamera.stopPreview();
    }

 	private Camera.ShutterCallback shutterCallback;
    private Camera.PictureCallback rawCallback, jpegCallback;
    private String imageFolder = "", imagepath = "";
    private int mRotation = Surface.ROTATION_0;
    private void initTakePictureCallback(){
        rawCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.d(TAG, "onPictureTaken - raw");
            }
        };

        shutterCallback = new Camera.ShutterCallback() {
            public void onShutter() {
                Log.i(TAG, "onShutter'd");
            }
        };
        jpegCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                //create folder
                imageFolder = "/sdcard/k11/";;
                (new File(imageFolder)).mkdirs();
                imagepath = String.format(imageFolder + "%d.jpg", System.currentTimeMillis());
                try {
                    outStream = new FileOutputStream(imagepath);
                    outStream.write(data);
                    outStream.close();
                    Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
                    //apply exif
                    ExifInterface exif = new ExifInterface(imagepath);
                    int rotate = ExifInterface.ORIENTATION_NORMAL;
                    switch (mRotation) {
                        case Surface.ROTATION_0:
                            rotate = ExifInterface.ORIENTATION_ROTATE_90; break;
                        case Surface.ROTATION_90:
                            rotate = ExifInterface.ORIENTATION_ROTATE_180; break;
                        case Surface.ROTATION_180:
                            rotate = ExifInterface.ORIENTATION_ROTATE_270; break;
                        case Surface.ROTATION_270:
                            rotate = ExifInterface.ORIENTATION_NORMAL; break;
                    }
                    Log.d(TAG, "rotate: " + rotate);
                    exif.setAttribute(ExifInterface.TAG_ORIENTATION, "" + rotate);
                    exif.saveAttributes();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "er");
                }
                finally {
                }
                Log.d(TAG, "onPictureTaken - jpeg");
//                stopPreviewCamera();
                onCapturedImage();
            }
        };
    }
    private void captureImage() {
        if (isCapturing) return;
        isCapturing = true;
        if(mCamera!= null) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    isCapturing = false;
                    mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
                }
            });
        }
    }

    private boolean enablePreviewImages = true;
    private List<PreviewImagesItem> previewImages;
    private class PreviewImagesItem {
        ViewGroup view;
        String imagePath;
    }
    private final int maxImageCount = 4;
    private void onCapturedImage() {
        isPause = true;
        Log.d(TAG, "imagepath: " + imagepath);

//        AppTextView_takePic.setVisibility(View.GONE);
//        submitBtn.setVisibility(View.VISIBLE);
////        cameraFocusPreview.setRect(null, 0, 0);
//        if (enablePreviewImages) {
////            AppTextView.setVisibility(View.GONE);
            addPreviewImages();
            addMoreBtn.setVisibility(previewImages.size() >= maxImageCount ? View.GONE : View.VISIBLE);
//        }
//        else {
//            retakeBtn.setVisibility(View.VISIBLE);
//        }
    }

    private void addPreviewImages() {
        if (imagepath.equals("")) {
            Log.d(TAG, "imagepath.equals(\"\")");
            retakeImage();
            return;
        }
        final PreviewImagesItem item = new PreviewImagesItem();
        item.view = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.previewimage_item, imageContainer, false);
        item.imagePath = imagepath;
        ImageView imageView = (ImageView) item.view.findViewById(R.id.image);
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage("file:///mnt" + item.imagePath, imageView,
                CommonHelper.getDisplayImageBuilder().considerExifParams(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build());
        item.view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "removePreviewImages");
                imageContainer.removeView(item.view);
                previewImages.remove(item);
                addMoreBtn.setVisibility(previewImages.size() >= maxImageCount ? View.GONE : View.VISIBLE);
                if (previewImages.size() == 0) retakeImage();
            }
        });
        imageContainer.addView(item.view);
        previewImages.add(item);
    }


    public static int getDisplayOrientation(int degrees, int cameraId) {
        // See android.hardware.Camera.setDisplayOrientation for
        // documentation.
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }



    private void volleyConnection(){
        //volley
        String url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "response " + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                            String site = jsonResponse.getString("site");
                            String network = jsonResponse.getString("network");
                            Log.d(TAG, "Site: "+site+"\nNetwork: "+network);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("site", "site code 001");
                params.put("network", "internal network");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(postRequest);
        //
    }



}
