package com.triangle.task.view.base

import androidx.navigation.NavController


interface NavigationHost {
  fun findNavControl(): NavController?
}
