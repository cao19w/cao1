package com.example1.utils;

public interface HttpCallbackListener {
  void onFinish(String response);
  void error(Exception e);
}
