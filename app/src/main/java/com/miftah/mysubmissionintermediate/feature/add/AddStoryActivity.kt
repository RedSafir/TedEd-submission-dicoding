package com.miftah.mysubmissionintermediate.feature.add

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.miftah.mysubmissionintermediate.R
import com.miftah.mysubmissionintermediate.core.data.Result
import com.miftah.mysubmissionintermediate.core.ui.ViewModelFactory
import com.miftah.mysubmissionintermediate.core.utils.getImageUri
import com.miftah.mysubmissionintermediate.databinding.ActivityAddStoryBinding

class AddStoryActivity : AppCompatActivity(){

    private lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null
    private val viewModel by viewModels<AddStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val args: AddStoryActivityArgs by navArgs()
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Toast.makeText(this, getString(R.string.err_no_media_selected), Toast.LENGTH_SHORT)
                .show()
        }
    }
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            Toast.makeText(this, getString(R.string.err_no_media_selected), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        check()

        binding.edAddDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                check()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.buttonToGallery.setOnClickListener { startGallery() }
        binding.buttonToCamera.setOnClickListener { startCamera() }
        binding.buttonAdd.setOnClickListener {
            val desc = binding.edAddDescription.text.toString()
            val token = args.token
            currentImageUri?.let { uri ->
                viewModel.storedStory(token, desc, uri, this).observe(this) {
                    when (it) {
                        is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                it.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                getString(R.string.inf_story_posted), Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                }
            }
        }

    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imgPreview.setImageURI(it)
        }
        check()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun check() {
        val text = binding.edAddDescription.text.toString()
        val check = (text.isNotEmpty() && (currentImageUri != null))
        binding.buttonAdd.isActivated = check
        binding.buttonAdd.isEnabled = check
    }
}