package com.example.yan.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by YAN on 2016/8/11.
 */
public class GLView extends GLSurfaceView
{
    private final GLRenderer renderer;//render 提供；翻译
    GLView(Context context)
    {
        super(context);
        //Uncomment this to turn on error-checking and logging
        //setDebugFlags(DEBUG_CHECK_GL_ERROR|DEBUG_LOG_GL_CALLS)
        renderer=new GLRenderer(context);
        setRenderer(renderer);
    }
}
