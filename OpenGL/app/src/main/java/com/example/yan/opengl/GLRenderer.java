package com.example.yan.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.provider.Settings;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by YAN on 2016/8/11.
 */
public class GLRenderer implements GLSurfaceView.Renderer
{
    private static final String TAG="GLRender";
    private final Context context;
    private final GLCube cube=new GLCube();
    private long startTime;
    private long fpsStartTime;
    private long numFrames;
    public GLRenderer(Context context)
    {
        this.context=context;
    }
    //新建或者重新称生成opengl surface类时出现（类似于canvas）
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        boolean SEE_THRU=true;
        if(SEE_THRU)
        {
            gl.glDisable(GL10.GL_DEPTH_TEST);
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE);
        }
        startTime= System.currentTimeMillis();
        fpsStartTime=startTime;
        numFrames=0;
        float lightAmbient[]=new float[]{0.2f,0.2f,0.2f,1};
        float lightDiffuse[]=new float[]{1,1,1,1};
        float lightPos[]=new float[]{1,1,1,1};
        //cube mode of
        float matAmbient[]=new float[]{1,1,1,1};
        float matDiffuse[]=new float[]{1,1,1,1};
        //Set up any opengl options we need
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightfv(GL10.GL_LIGHT0,GL10.GL_AMBIENT,lightAmbient,0);
        gl.glLightfv(GL10.GL_LIGHT0,GL10.GL_DIFFUSE,lightDiffuse,0);
        gl.glLightfv(GL10.GL_LIGHT0,GL10.GL_POSITION,lightPos,0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT,matAmbient,0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE,matDiffuse,0);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //Optional:disable dither to boost performance
        //gl.glDisable(GL10.GL_DITHER);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        //load the cube's texture from a bitmap
        GLCube.loadTexture(gl,context,R.drawable.android);
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        //Define the view frustum
        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        float ratio=(float)width/height;
        GLU.gluPerspective(gl,45.0f,ratio,1,100f);
    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        //Clear the screen to black
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        //Position model ao we can see it
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0,0,-3.0f);
        //Set rotation angle based on the time
        long elapsed=System.currentTimeMillis()-startTime;
        gl.glRotatef(elapsed*(30f/1000f),0,1,0);
        gl.glRotatef(elapsed*(15f/1000f),1,0,0);
        cube.draw(gl);
        //Keep track of number of frames drawn
        numFrames++;
        long fpsElapsed=System.currentTimeMillis()-fpsStartTime;
        if(fpsElapsed>5*1000)
        {
            //every 5 seconds
            float fps=(numFrames*1000.0F)/fpsElapsed;
            Log.d(TAG,"Frames per second:"+fps+"("+numFrames
            +"frames in"+fpsElapsed+"ms)");
            fpsStartTime=System.currentTimeMillis();
            numFrames=0;
        }
    }
}
