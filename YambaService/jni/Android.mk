LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS += -llog

LOCAL_MODULE    := yamba
LOCAL_SRC_FILES := yamba.cpp

include $(BUILD_SHARED_LIBRARY)
