package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Scene
import android.transition.TransitionManager
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var scene1: Scene
    private lateinit var scene2: Scene
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scene1 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.scene_1, this)
        scene2 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.scene_2, this)

        var rg = binding.radioGroup
        var rg_student = binding.student
        var rg_worker = binding.worker

        rg_student.setOnClickListener(View.OnClickListener() {
            TransitionManager.go(scene1, ChangeBounds())
        })
        rg_worker.setOnClickListener(View.OnClickListener() {
            TransitionManager.go(scene2, ChangeBounds())
        })

    }
}