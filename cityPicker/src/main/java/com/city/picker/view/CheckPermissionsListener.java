package com.city.picker.view;

import java.util.List;



public interface CheckPermissionsListener {
    void onGranted();
    void onDenied(List<String> permissions);
}
