package com.triangle.task.view.base

import androidx.navigation.NavController


interface NavigationHost {
  fun findNavControl(): NavController?
  fun hideNavigation(animate: Boolean)
  fun showNavigation(animate: Boolean)
}
