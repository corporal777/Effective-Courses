package com.examle.effectivecourses.di.module

import com.examle.effectivecourses.ui.base.UIData
import org.koin.dsl.module


val uiDataModule = module {
    single { UIData() }
}