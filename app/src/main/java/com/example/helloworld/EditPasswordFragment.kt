package com.example.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class EditPasswordFragment : Fragment() {

    companion object {
        const val TAG = "edit_password_fragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_edit_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonBack = view.findViewById<ImageView>(R.id.buttonBack)

        buttonBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val confirmButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.confirmButton)

        confirmButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}