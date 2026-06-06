package com.mobai.magisk.ui.install

import com.mobai.magisk.R
import com.mobai.magisk.arch.BaseFragment
import com.mobai.magisk.arch.viewModel
import com.mobai.magisk.databinding.FragmentInstallMd2Binding
import com.mobai.magisk.core.R as CoreR

class InstallFragment : BaseFragment<FragmentInstallMd2Binding>() {

    override val layoutRes = R.layout.fragment_install_md2
    override val viewModel by viewModel<InstallViewModel>()

    override fun onStart() {
        super.onStart()
        requireActivity().setTitle(CoreR.string.install)
    }
}
