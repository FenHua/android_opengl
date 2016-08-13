package com.example.yan.opengl;

import android.app.Activity;
import android.os.Bundle;

public class OpenGL extends Activity
{
    GLView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=new GLView(this);
        setContentView(view);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        view.onPause();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        view.onResume();
    }
}
