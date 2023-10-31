package com.miftah.mysubmissionintermediate.feature.add

import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.miftah.mysubmissionintermediate.R
import com.miftah.mysubmissionintermediate.core.data.Result
import com.miftah.mysubmissionintermediate.core.ui.ViewModelFactory
import com.miftah.mysubmissionintermediate.core.utils.getImageUri
import com.miftah.mysubmissionintermediate.databinding.ActivityAddStoryBinding

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userLocation: Location? = null
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }

                permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }

                else -> {}
            }
        }
    private val viewModel by viewModels<AddStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }
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
            currentImageUri?.let { uri ->
                if (userLocation != null) {
                    viewModel.storedStoryLocation(userLocation!!, desc, uri, this).observe(this) {
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
                } else {
                    viewModel.storedStory(desc, uri, this).observe(this) {
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getMyLastLocation()
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

    private fun getMyLastLocation() {
        if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            binding.switchLocation.isEnabled = true
            binding.switchLocation.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            userLocation = location
                            Log.d("tagingmap", "onCreate: ${userLocation.toString()}")
                            Toast.makeText(
                                this@AddStoryActivity,
                                "Current Location Save",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@AddStoryActivity,
                                "Location is not found. Try Again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    userLocation = null
                }
            }
        } else {
            binding.switchLocation.isEnabled = false
            requestPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
    }
}