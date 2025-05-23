/*
 * Copyright (C) 2024 Kevin Buzeau
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.buzbuz.gradle.convention

import com.buzbuz.gradle.core.libs.getLibs
import com.buzbuz.gradle.core.playStoreImplementation
import com.buzbuz.gradle.core.android
import com.buzbuz.gradle.core.plugins

import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class CrashlyticsConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()

        plugins {
            apply(libs.plugins.google.crashlytics)
            apply(libs.plugins.google.gms)
        }

        android {
            buildTypes {
                getByName("release") {
                    configure<CrashlyticsExtension> {
                        nativeSymbolUploadEnabled = true
                    }
                }
            }
        }

        dependencies {
            playStoreImplementation(platform(libs.getLibrary("google.firebase.bom")))
            playStoreImplementation(libs.getLibrary("google.firebase.crashlytics.ktx"))
            playStoreImplementation(libs.getLibrary("google.firebase.crashlytics.ndk"))
        }
    }
}